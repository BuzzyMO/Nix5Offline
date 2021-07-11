package org.example.location.dao.impl;

import org.example.location.dao.RouteDao;
import org.example.location.model.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private final Connection connection;

    public RouteDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create(Route route) {
        String sqlQuery = "INSERT INTO routes(from_id, to_id, cost) VALUES (?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, route.getFromId());
            statement.setInt(2, route.getToId());
            statement.setInt(3, route.getCost());
            statement.executeUpdate();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Route route) {
        String sqlQuery = "UPDATE routes SET from_id=?, to_id=?, cost=? WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, route.getFromId());
            statement.setInt(2, route.getToId());
            statement.setInt(3, route.getCost());
            statement.setInt(4, route.getId());
            statement.executeUpdate();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "DELETE FROM routes WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Route readById(int id) {
        Route route = new Route();
        String sqlQuery = "SELECT * FROM routes WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if(res.next()){
                route.setId(res.getInt(1));
                route.setFromId(res.getInt(2));
                route.setToId(res.getInt(3));
                route.setCost(res.getInt(4));
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return route;
    }

    @Override
    public List<Route> readALl() {
        List<Route> routeList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM routes";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Route route = new Route();
                route.setId(res.getInt(1));
                route.setFromId(res.getInt(2));
                route.setToId(res.getInt(3));
                route.setCost(res.getInt(4));
                routeList.add(route);
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return routeList;
    }
}
