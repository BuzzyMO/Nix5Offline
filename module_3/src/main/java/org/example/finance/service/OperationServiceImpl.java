package org.example.finance.service;

import com.opencsv.CSVWriter;
import org.example.finance.CsvMapper;
import org.example.finance.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class OperationServiceImpl implements OperationService {
    private static final Logger LOGGER = LoggerFactory.getLogger("org.example.finance.logback");

    private final Supplier<EntityManager> persistence;
    private final Supplier<Connection> connectionSupplier;
    private final Long userId;

    public OperationServiceImpl(Supplier<EntityManager> persistence, Supplier<Connection> connectionSupplier, Long userId){
        this.persistence = persistence;
        this.connectionSupplier = connectionSupplier;
        this.userId = userId;
    }

    @Override
    public void createOperation(Long accountId, Long categoryId, BigDecimal money, Instant timestamp) {
        EntityManager entityManager = persistence.get();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try{
            User user = entityManager.find(User.class, userId);
            TypedQuery<Account> query = entityManager.createQuery("select a from Account a where a.id=:accountId and a.user=:user", Account.class);
            query.setParameter("accountId", accountId);
            query.setParameter("user", user);
            Account account = query.getSingleResult();
            Category category = entityManager.find(Category.class, categoryId);
            Operation operation = new Operation();
            operation.setAccount(account);
            operation.setCategory(category);
            operation.setMoney(money);
            operation.setTimestamp(timestamp);
            entityManager.persist(operation);
            transaction.commit();
        } catch (RuntimeException ex){
            LOGGER.error("Create operation is failed" + ex);
            transaction.rollback();
            throw ex;
        }
    }

    @Override
    public void writeCsv(Long accountId, LocalDateTime fromTime, LocalDateTime toTime){
        LOGGER.info("Start write operation to csv file, accountID:" + accountId);
        List<String[]> csvData = new ArrayList<>();
        if(!new File("operations.csv").exists()){
            csvData.add(headerCsv());
        }
        try(CSVWriter writer = new CSVWriter(new FileWriter("operations.csv",true))) {
            List<OperationDto> operations = getOperations(accountId, fromTime, toTime);
            for(OperationDto op : operations){
                csvData.add(operationToStringArray(op));
            }
            writer.writeAll(csvData);
            LOGGER.info("End write operation to csv file, accountID:" + accountId);
        } catch (IOException ex){
            LOGGER.error("Can't write csv" + ex.getMessage());
        }
    }

    private List<OperationDto> getOperations(Long accountId, LocalDateTime fromTime, LocalDateTime toTime){
        List<OperationDto> operations = new ArrayList<>();
        Connection connection = connectionSupplier.get();
        String query = "SELECT o.id, o.category_id, c.name, ct.name, o.money, o.timestamp " +
                "FROM operations o INNER JOIN categories c on c.id = o.category_id " +
                "INNER JOIN category_types ct on ct.id = c.category_type_id " +
                "WHERE o.account_id = ? AND o.timestamp BETWEEN ? AND ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, accountId);
            statement.setTimestamp(2, Timestamp.from(fromTime.toInstant(ZoneOffset.UTC)));
            statement.setTimestamp(3, Timestamp.from(toTime.toInstant(ZoneOffset.UTC)));
            ResultSet res = statement.executeQuery();
            while (res.next()){
                Long id = res.getLong(1);
                Long categoryId = res.getLong(2);
                String categoryName = res.getString(3);
                String categoryTypeName = res.getString(4);
                BigDecimal money = res.getBigDecimal(5);
                Timestamp timestamp = res.getTimestamp(6);
                OperationDto operation = new OperationDto(id, accountId, categoryId, categoryName, categoryTypeName, money, timestamp);
                operations.add(operation);
            }
        } catch (SQLException ex) {
            LOGGER.error("Can't read operations from database:" + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return operations;
    }

    private String[] headerCsv(){
        CsvMapper mapper = new CsvMapper();
        return mapper.getHeaderCsv(OperationDto.class);
    }

    private String[] operationToStringArray(OperationDto operation){
        String[] strings = new String[7];
        strings[0] = String.valueOf(operation.getId());
        strings[1] = String.valueOf(operation.getAccountId());
        strings[2] = String.valueOf(operation.getCategoryId());
        strings[3] = operation.getCategoryName();
        strings[4] = operation.getCategoryTypeName();
        strings[5] = String.valueOf(operation.getMoney());
        strings[6] = operation.getTimestamp().toString();
        return strings;
    }
}
