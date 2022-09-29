package com.example.code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.code.model.Route;
import com.example.code.model.RoutePath;

import java.util.Date;

public class RoutesGrid extends AppCompatActivity {
    private final Route[] routes = new Route[] {
            new Route(5, "Singapore - Copenhagen", new Date(), RoutePath.DEFAULT),
            new Route(6, "Seoul - Chandigarh", new Date(), RoutePath.BY_PLANE),
            new Route(7, "Zurich - Washington DC", new Date(), RoutePath.BY_PLANE),
            new Route(8, "Montreal - Canada", new Date(), RoutePath.BY_TRAIN)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_grid);

        updateRoutesGrid(routes);
        GridView gridView = findViewById(R.id.routesGridView);
        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, MainActivity.class);
            String selectedRoute = gridView.getItemAtPosition(i).toString();
            intent.putExtra(MainActivity.SELECTED_ACTIVITY, "Activity 2");
            intent.putExtra(MainActivity.SELECTED_ROUTE, selectedRoute);
            startActivity(intent);
        });
    }

    private void updateRoutesGrid(Route[] routes) {
        GridView gridView = findViewById(R.id.routesGridView);
        ArrayAdapter<Route> adapter = new ArrayAdapter<>(this, R.layout.route_grid_item, routes);
        gridView.setAdapter(adapter);
    }
}