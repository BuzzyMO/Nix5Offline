package org.example.location.dao;

import org.example.location.model.Solution;

import java.util.List;

public interface SolutionDao {
    void createSolutions(List<Solution> solutions);
    List<Solution> readALl();
}
