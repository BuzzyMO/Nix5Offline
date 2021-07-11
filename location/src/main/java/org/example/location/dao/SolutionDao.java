package org.example.location.dao;

import org.example.location.model.Solution;

import java.util.List;

public interface SolutionDao {
    void create(Solution solution);
    void update(Solution solution);
    void delete(int id);
    Solution readById(int id);
    List<Solution> readALl();
}
