package org.example.location.service.impl;

import org.example.location.dao.LocationDao;
import org.example.location.dao.ProblemDao;
import org.example.location.dao.RouteDao;
import org.example.location.dao.SolutionDao;
import org.example.location.model.Location;
import org.example.location.model.Problem;
import org.example.location.model.Route;
import org.example.location.model.Solution;
import org.example.location.service.ShortestPathService;

import java.util.ArrayList;
import java.util.List;

public class ShortestPathServiceImpl implements ShortestPathService {
    private final LocationDao locationDao;
    private final RouteDao routeDao;
    private final ProblemDao problemDao;
    private final SolutionDao solutionDao;
    private final List<Location> locations;
    private final List<Route> routes;
    private final List<Problem> problems;

    public ShortestPathServiceImpl(LocationDao locationDao, RouteDao routeDao, ProblemDao problemDao, SolutionDao solutionDao){
        this.locationDao = locationDao;
        this.routeDao = routeDao;
        this.problemDao = problemDao;
        this.solutionDao = solutionDao;
        locations = readAllLocations();
        routes = readAllRoutes();
        problems = readAllProblems();
    }

    public void computeProblems(){
        for (Problem p: problems) {
            int cost = computeCost(p);
            Solution solution = new Solution();
            solution.setProblemId(p.getId());
            solution.setCost(cost);
            createSolution(solution);
        }
    }

    private int computeCost(Problem p) {
        List<Node> nodes = createNodes(locations);
        Dijkstra dijkstra = new Dijkstra(nodes, routes);
        Node start = getNodeByLocationId(nodes, p.getFromId());
        Node end = getNodeByLocationId(nodes, p.getToId());
        return dijkstra.minimalCost(start, end);
    }

    private List<Node> createNodes(List<Location> locations){
        List<Node> nodes = new ArrayList<>();
        for(Location l : locations){
            Node node = new Node(l);
            nodes.add(node);
        }
        return nodes;
    }

    private Node getNodeByLocationId(List<Node> nodeList, int locationId){
        for(Node n : nodeList) {
            if(n.getLocationId() == locationId){
                return n;
            }
        }
        throw new RuntimeException("Node isn't exists");
    }

    private List<Location> readAllLocations(){
        return locationDao.readALl();
    }

    private List<Route> readAllRoutes(){
        return routeDao.readALl();
    }

    private List<Problem> readAllProblems(){
        return problemDao.readALl();
    }

    private void createSolution(Solution solution){
        solutionDao.create(solution);
    }
}
