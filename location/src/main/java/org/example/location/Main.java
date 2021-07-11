package org.example.location;

import org.example.location.connection.ConnectionProvider;
import org.example.location.connection.ConnectionProviderImpl;
import org.example.location.dao.LocationDao;
import org.example.location.dao.ProblemDao;
import org.example.location.dao.RouteDao;
import org.example.location.dao.SolutionDao;
import org.example.location.dao.impl.LocationDaoImpl;
import org.example.location.dao.impl.ProblemDaoImpl;
import org.example.location.dao.impl.RouteDaoImpl;
import org.example.location.dao.impl.SolutionDaoImpl;
import org.example.location.service.ShortestPathService;
import org.example.location.service.impl.ShortestPathServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ConnectionProvider provider = new ConnectionProviderImpl();
        try(Connection connection = provider.getConnection()){
            LocationDao locationDao = new LocationDaoImpl(connection);
            RouteDao routeDao = new RouteDaoImpl(connection);
            ProblemDao problemDao = new ProblemDaoImpl(connection);
            SolutionDao solutionDao = new SolutionDaoImpl(connection);
            ShortestPathService pathService = new ShortestPathServiceImpl(locationDao, routeDao, problemDao, solutionDao);
            pathService.computeProblems();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
