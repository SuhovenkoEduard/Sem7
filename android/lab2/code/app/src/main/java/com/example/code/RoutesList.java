package com.example.code;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.code.model.Route;

import androidx.appcompat.app.AppCompatActivity;

import com.example.code.databinding.ActivityRoutesListBinding;
import com.example.code.model.RoutePath;

import java.util.Arrays;
import java.util.Date;

public class RoutesList extends AppCompatActivity {
    private final Route[] routes = new Route[] {
        new Route(1, "New York - Dubai", new Date(), RoutePath.BY_PLANE),
        new Route(2, "Los Angeles - Minsk", new Date(), RoutePath.BY_TRAIN),
        new Route(3, "Gomel - Mozyr", new Date(), RoutePath.BY_TRAIN),
        new Route(4, "Brest - Mogilev", new Date(), RoutePath.DEFAULT)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.code.databinding.ActivityRoutesListBinding binding = ActivityRoutesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateRoutesList(routes);

        Spinner filterSpinner = findViewById(R.id.filterSpinner);
        filterSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.route_list_item, RoutePath.values()));
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RoutePath selectedFilter = RoutePath.valueOf(filterSpinner.getSelectedItem().toString());
                if (selectedFilter == RoutePath.DEFAULT) {
                    updateRoutesList(routes);
                } else {
                    updateRoutesList(Arrays
                        .stream(routes).filter(route -> route.getRoutePath() == selectedFilter)
                        .toArray(Route[]::new)
                    );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        ListView listView = findViewById(R.id.routesListView);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, MainActivity.class);
            String selectedRoute = listView.getItemAtPosition(i).toString();
            intent.putExtra(MainActivity.SELECTED_ACTIVITY, "Activity 1");
            intent.putExtra(MainActivity.SELECTED_ROUTE, selectedRoute);
            startActivity(intent);
        });
    }

    private void updateRoutesList(Route[] routes) {
        ListView listView = findViewById(R.id.routesListView);
        ArrayAdapter<Route> adapter = new ArrayAdapter<>(this, R.layout.route_list_item, routes);
        listView.setAdapter(adapter);
    }
}