package org.example.finance;

import org.example.finance.connection.ConnectionProvider;
import org.example.finance.connection.ConnectionProviderImpl;
import org.example.finance.service.OperationService;
import org.example.finance.service.OperationServiceImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    private static String username;
    private static String password;
    private static Long userId;

    public static void main(String[] args) {
        init(args);
        Configuration conf = new Configuration().configure()
                .setProperty("hibernate.connection.username", username)
                .setProperty("hibernate.connection.password", password);
        ConnectionProvider connectionProvider = new ConnectionProviderImpl(username, password);
        try(SessionFactory sessionFactory = conf.buildSessionFactory();
            Connection connection = connectionProvider.getConnection()){
            EntityManager entityManager = sessionFactory.createEntityManager();
            OperationService operationService = new OperationServiceImpl(() -> entityManager, () -> connection, userId);
            FinanceUI financeUI = new FinanceUI(operationService);
            financeUI.exec();
            entityManager.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void init(String[] args){
        if(args.length > 2){
            username = args[0];
            password = args[1];
            userId = Long.parseLong(args[2]);
        } else {
            username = System.getenv("USERNAME_DB");
            password = System.getenv("PASSWORD_DB");
            userId = Long.parseLong(System.getenv("USER_ID"));
        }
    }
}
