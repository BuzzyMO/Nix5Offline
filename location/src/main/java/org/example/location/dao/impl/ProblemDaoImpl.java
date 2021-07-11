package org.example.location.dao.impl;

import org.example.location.dao.ProblemDao;
import org.example.location.model.Problem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProblemDaoImpl implements ProblemDao {
    private final Connection connection;

    public ProblemDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create(Problem problem) {
        String sqlQuery = "INSERT INTO problems (from_id, to_id) VALUES (?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, problem.getFromId());
            statement.setInt(2, problem.getToId());
            statement.executeUpdate();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Problem problem) {
        String sqlQuery = "UPDATE problems SET from_id=?, to_id=? WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, problem.getFromId());
            statement.setInt(2, problem.getToId());
            statement.setInt(3, problem.getId());
            statement.executeUpdate();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "DELETE FROM problems WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Problem readById(int id) {
        Problem problem = new Problem();
        String sqlQuery = "SELECT * FROM problems WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if(res.next()){
                problem.setId(res.getInt(1));
                problem.setFromId(res.getInt(2));
                problem.setToId(res.getInt(3));
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return problem;
    }

    @Override
    public List<Problem> readALl() {
        List<Problem> problemList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM problems";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet res = statement.executeQuery();
            while(res.next()){
                Problem problem = new Problem();
                problem.setId(res.getInt(1));
                problem.setFromId(res.getInt(2));
                problem.setToId(res.getInt(3));
                problemList.add(problem);
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return problemList;
    }
}
