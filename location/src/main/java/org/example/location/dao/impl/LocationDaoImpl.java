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

    public void create(Location location) {
        String sqlQuery = "INSERT INTO locations (name) VALUES (?)";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, location.getName());
            statement.executeUpdate();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Location location) {
        String sqlQuery = "UPDATE locations SET name=? WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, location.getName());
            statement.setInt(2, location.getId());
            statement.executeUpdate();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "DELETE FROM locations WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Location readById(int id) {
        Location location = new Location();
        String sqlQuery = "SELECT * FROM locations WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if(res.next()){
                location.setId(res.getInt(1));
                location.setName(res.getString(2));
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return location;
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
