package org.example.location.dao;

import org.example.location.model.Problem;

import java.util.List;

public interface ProblemDao {
    void create(Problem problem);
    void update(Problem problem);
    void delete(int id);
    Problem readById(int id);
    List<Problem> readALl();
}
