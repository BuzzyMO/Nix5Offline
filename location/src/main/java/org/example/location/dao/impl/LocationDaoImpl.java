package org.example.location.dao.impl;

import org.example.location.dao.LocationDao;
import org.example.location.model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDaoImpl implements LocationDao {
    private final Connection connection;

    public LocationDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Location> readALl() {
        List<Location> locationList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM locations";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Location location = new Location();
                location.setId(res.getInt(1));
                location.setName(res.getString(2));
                locationList.add(location);
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return locationList;
    }
}
