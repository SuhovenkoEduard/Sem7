package com.uni.lab3;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.uni.lab3.model.Product;
import com.uni.lab3.model.ProductSearchByPrice;
import com.uni.lab3.productsReader.ProductsReader;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Product[] products = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView productsListView = findViewById(R.id.productsListView);
        productsListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Fragment previousFragment = getFullInfoProductFragment();
            if (previousFragment != null) {
                removeFullProductInfoFragment(previousFragment);
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("product", products[i]);
            getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainerView, FullProductInfoFragment.class, bundle, FullProductInfoFragment.FRAGMENT_TAG)
                .commit();
        });

        if (getIntent().getSerializableExtra("products") != null) {
            products = (Product[]) getIntent().getSerializableExtra("products");
            if (getIntent().getSerializableExtra("searchQuery") != null) {
                ProductSearchByPrice productSearchByPrice = (ProductSearchByPrice) getIntent().getSerializableExtra("searchQuery");
                setSearchQueryText(productSearchByPrice.getName());
                setMaxPriceQueryText(Integer.toString(productSearchByPrice.getMaxPrice()));
                setProductsInListView(Arrays.stream(products).filter(product ->
                       product.getName().toLowerCase(Locale.ROOT).contains(productSearchByPrice.getName().toLowerCase(Locale.ROOT))
                           && product.getPrice() <= productSearchByPrice.getMaxPrice())
                        .toArray(Product[]::new)
                );
            }
            handleIntents();
        } else {
            try {
                ProductsReader productsReader = new ProductsReader(
                        MainActivity.this,
                        new BufferedReader(new InputStreamReader(getAssets().open("products.json"))
                        ),
                        (products) -> {
                            this.products = products;
                            setProductsInListView(products);
                            handleIntents();
                        });
                productsReader.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                onSearchRequested();
                return true;
            case R.id.search_with_price: {
                Intent intent = new Intent(this, ConstrainedSearch.class);
                intent.putExtra("products", products);
                startActivity(intent);
                return true;
            }
            case R.id.full_list: {
                applySearchQuery("");
                setMaxPriceQueryText("");
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void setProductsInListView(Product[] products) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, Arrays.stream(products).map(Product::toShortString).toArray(String[]::new));
        ListView productsListView = findViewById(R.id.productsListView);
        productsListView.setAdapter(arrayAdapter);
    }

    public Fragment getFullInfoProductFragment() {
        return getSupportFragmentManager().findFragmentByTag(FullProductInfoFragment.FRAGMENT_TAG);
    }

    public void removeFullProductInfoFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commit();
    }

    public void applySearchQuery(String searchQuery) {
        if (getFullInfoProductFragment() != null) {
            removeFullProductInfoFragment(getFullInfoProductFragment());
        }
        setSearchQueryText(searchQuery);
        setProductsInListView(Arrays.stream(products).filter(product -> product.getName().toLowerCase(Locale.ROOT).contains(searchQuery)).toArray(Product[]::new));
    }

    public void handleIntents() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            applySearchQuery(query.toLowerCase(Locale.ROOT));
        }
    }

    public void setSearchQueryText(String text) {
        TextView searchQueryTextView = findViewById(R.id.searchQueryTextView);
        if (text.isEmpty()) {
            searchQueryTextView.setText("");
        } else {
            searchQueryTextView.setText(String.format("Name: %s", text));
        }
    }

    public void setMaxPriceQueryText(String text) {
        TextView maxPriceTextView = findViewById(R.id.maxPriceTextView);
        if (text.isEmpty()) {
            maxPriceTextView.setText("");
        } else {
            maxPriceTextView.setText(String.format("Max price: %s", text));
        }
    }
}