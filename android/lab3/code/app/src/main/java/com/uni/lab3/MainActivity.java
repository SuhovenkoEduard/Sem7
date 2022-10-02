package com.uni.lab3;

import static com.uni.lab3.Themes.*;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.uni.lab3.model.Product;
import com.uni.lab3.model.ProductsRepository;
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
    private ProductsRepository productsRepository;
    private Themes currentTheme = GREEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            productsRepository = (ProductsRepository) savedInstanceState.getSerializable("productsRepository");
            currentTheme = (Themes) savedInstanceState.getSerializable("currentTheme");
        }
        setTheme(ThemesUtils.getThemeId(currentTheme));
        setContentView(R.layout.activity_main);

        ListView productsListView = findViewById(R.id.productsListView);
        productsListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Fragment previousFragment = getFullInfoProductFragment();
            if (previousFragment != null) {
                removeFullProductInfoFragment(previousFragment);
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("product", productsRepository.getByIndex(i));
            getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainerView, FullProductInfoFragment.class, bundle, FullProductInfoFragment.FRAGMENT_TAG)
                .commit();
        });

        if (productsRepository != null) {
            setProductsInListView(productsRepository.getAll());
        } else {
            readProducts();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                onSearchRequested();
                return true;
            case R.id.search_with_price: {
                Intent intent = new Intent(this, ConstrainedSearch.class);
                intent.putExtra("currentTheme", currentTheme);
                startActivityForResult(intent, 1);
                return true;
            }
            case R.id.full_list: {
                applySearchQuery("");
                setMaxPriceQueryText("");
                return true;
            }
            case R.id.default_theme: {
                currentTheme = DEFAULT;
                recreate();
                return true;
            }
            case R.id.red_theme: {
                currentTheme = RED;
                recreate();
                return true;
            }
            case R.id.green_theme: {
                currentTheme = GREEN;
                recreate();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                String name = data.getStringExtra("name");
                int maxPrice = data.getIntExtra("maxPrice", 0);
                applySearchWithPriceQuery(name, maxPrice);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("productsRepository", productsRepository);
        savedInstanceState.putSerializable("currentTheme", currentTheme);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            applySearchQuery(query.toLowerCase(Locale.ROOT));
        }
    }

    public void setProductsInListView(Product[] products) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, Arrays.stream(products).map(Product::toShortString).toArray(String[]::new));
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
        setSearchQueryText(searchQuery);
        setProductsInListView(Arrays.stream(productsRepository.getAll()).filter(product -> product.getName().toLowerCase(Locale.ROOT).contains(searchQuery)).toArray(Product[]::new));
    }

    public void applySearchWithPriceQuery(String name, int maxPrice) {
        setSearchQueryText(name);
        setMaxPriceQueryText(Integer.toString(maxPrice));
        setProductsInListView(Arrays.stream(productsRepository.getAll()).filter(product ->
                        product.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT))
                                && product.getPrice() <= maxPrice)
                .toArray(Product[]::new)
        );
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

    private void readProducts() {
        try {
            ProductsReader productsReader = new ProductsReader(
                    MainActivity.this,
                    new BufferedReader(new InputStreamReader(getAssets().open("products.json"))
                    ),
                    (products) -> {
                        productsRepository = new ProductsRepository(products);
                        setProductsInListView(products);
                    });
            productsReader.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}