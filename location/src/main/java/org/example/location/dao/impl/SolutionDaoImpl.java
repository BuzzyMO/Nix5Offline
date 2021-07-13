package org.example.location.dao.impl;

import org.example.location.dao.SolutionDao;
import org.example.location.model.Solution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SolutionDaoImpl implements SolutionDao {
    private final Connection connection;

    public SolutionDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void createSolutions(List<Solution> solutions) {
        String sqlQuery = "INSERT INTO solutions (problem_id, cost) VALUES (?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            for(Solution solution : solutions){
                statement.setInt(1, solution.getProblemId());
                statement.setInt(2, solution.getCost());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Solution> readALl() {
        List<Solution> solutionList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM solutions";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Solution solution = new Solution();
                solution.setProblemId(res.getInt(1));
                solution.setCost(res.getInt(2));
                solutionList.add(solution);
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return solutionList;
    }
}
