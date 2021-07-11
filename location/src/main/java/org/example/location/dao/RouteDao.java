package org.example.location.dao;

import org.example.location.model.Route;

import java.util.List;

public interface RouteDao {
    void create(Route route);
    void update(Route route);
    void delete(int id);
    Route readById(int id);
    List<Route> readALl();
}
