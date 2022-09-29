package com.example.code.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class Route {
    private final int id;
    private final String name;
    private final Date date;
    private final RoutePath routePath;

    public Route(int id, String name, Date date, RoutePath routePath) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.routePath = routePath;
    }

    public RoutePath getRoutePath() {
        return routePath;
    }

    @NonNull
    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", routePath=" + routePath +
                '}';
    }
}
