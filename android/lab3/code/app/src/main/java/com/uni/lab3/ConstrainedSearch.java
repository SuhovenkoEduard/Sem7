package com.uni.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.uni.lab3.model.Product;
import com.uni.lab3.model.ProductSearchByPrice;

public class ConstrainedSearch extends AppCompatActivity {
    private Product[] products;

    private String currentName;
    private String lastMaxPriceString = "";
    private int currentMaxPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constrained_search);
        products = (Product[]) getIntent().getSerializableExtra("products");

        EditText nameInput = findViewById(R.id.nameInput);
        EditText maxPriceInput = findViewById(R.id.maxPriceInput);

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentName = charSequence.toString();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        maxPriceInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isInteger(charSequence.toString())) {
                    lastMaxPriceString = charSequence.toString();
                    currentMaxPrice = Integer.parseInt(charSequence.toString());
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!isInteger(charSequence.toString()) && charSequence.length() > 0) {
                    int previousSelection = maxPriceInput.getSelectionEnd() - 1;
                    maxPriceInput.setText(lastMaxPriceString);
                    maxPriceInput.setSelection(Math.min(previousSelection, lastMaxPriceString.length()));
                } else {
                    lastMaxPriceString = charSequence.toString();
                    currentMaxPrice = Integer.parseInt(charSequence.toString());
                }
                if (charSequence.length() == 0) {
                    lastMaxPriceString = "";
                    currentMaxPrice = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        Button constrainedSearchButton = findViewById(R.id.constrainedSearchButton);
        constrainedSearchButton.setOnClickListener(view -> {
            Intent mainActivity = new Intent(this, MainActivity.class);
            mainActivity.putExtra("products", products);
            mainActivity.putExtra("searchQuery", new ProductSearchByPrice(currentName, currentMaxPrice));
            startActivity(mainActivity);
        });
    }

    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}