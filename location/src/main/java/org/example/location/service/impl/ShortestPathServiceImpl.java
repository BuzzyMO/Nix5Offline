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
    private final List<Solution> solutions;

    public ShortestPathServiceImpl(LocationDao locationDao, RouteDao routeDao, ProblemDao problemDao, SolutionDao solutionDao){
        this.locationDao = locationDao;
        this.routeDao = routeDao;
        this.problemDao = problemDao;
        this.solutionDao = solutionDao;
        locations = readAllLocations();
        routes = readAllRoutes();
        problems = readAllProblems();
        solutions = readAllSolutions();
    }

    public void computeProblems(){
        List<Solution> solutions = new ArrayList<>();
        for (Problem p: problems) {
            if(!problemIsSolved(p)){
                int cost = computeCost(p);
                Solution solution = new Solution();
                solution.setProblemId(p.getId());
                solution.setCost(cost);
                solutions.add(solution);
            }
        }
        createSolutions(solutions);
    }

    private int computeCost(Problem p) {
        List<Node> nodes = createNodes();
        Dijkstra dijkstra = new Dijkstra(nodes, routes);
        Node start = getNodeByLocationId(nodes, p.getFromId());
        Node end = getNodeByLocationId(nodes, p.getToId());
        return dijkstra.minimalCost(start, end);
    }

    private List<Node> createNodes(){
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

    private boolean problemIsSolved(Problem problem){
        for(Solution solution : solutions) {
            if(solution.getProblemId() == problem.getId()){
                return true;
            }
        }
        return false;
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

    private List<Solution> readAllSolutions(){
        return solutionDao.readALl();
    }

    private void createSolutions(List<Solution> solutions){
        solutionDao.createSolutions(solutions);
    }
}
