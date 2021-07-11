package org.example.location.service.impl;

import org.example.location.model.Route;

import java.util.List;
import java.util.stream.Collectors;

public class Dijkstra {
    private final List<Node> nodes;
    private final List<Route> routes;

    public Dijkstra(List<Node> nodes, List<Route> routes) {
        this.nodes = nodes;
        this.routes = routes;
    }

    public int minimalCost(Node start, Node end){
        start.setWeight(0);
        findShortestPath(end);
        return end.getWeight();
    }

    private void findShortestPath(Node endNode){
        while(true) {
            Node currentNode = getMinWeightNode();

            if(currentNode.equals(endNode)){
                return;
            }

            List<Route> routesOfNode = getRoutesOfNode(currentNode);
            for(Route r : routesOfNode){
                Node nextNode = getNextNode(r);
                if (currentNode.getWeight() + r.getCost() < nextNode.getWeight()) {
                    nextNode.setWeight(currentNode.getWeight() + r.getCost());
                    nextNode.setFromNode(currentNode);
                }
            }
            currentNode.setVisited(true);
        }
    }

    private Node getMinWeightNode(){
        return nodes
                .stream()
                .filter(c -> !c.isVisited())
                .min(Node::compareTo)
                .orElseThrow();
    }

    private List<Route> getRoutesOfNode(Node node){
        return routes
                .stream()
                .filter(n -> n.getFromId() == node.getLocationId())
                .collect(Collectors.toList());
    }

    private Node getNextNode(Route route){
        for(Node n : nodes){
            if(n.getLocationId() == route.getToId()){
                return n;
            }
        }
        throw new RuntimeException("Route is not complete.");
    }
}
