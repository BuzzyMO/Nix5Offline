package org.example.location.dao;

import org.example.location.model.Location;

import java.util.List;

public interface LocationDao {
    void create(Location location);
    void update(Location location);
    void delete(int id);
    Location readById(int id);
    List<Location> readALl();
}
