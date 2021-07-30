package org.example.finance;

import org.example.finance.service.OperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FinanceUI {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final OperationService operationService;
    private static final Logger LOGGER = LoggerFactory.getLogger("org.example.finance.logback");

    public FinanceUI(OperationService operationService){
        this.operationService = operationService;
    }

    public void exec() {
        try {
            String helpStr = "Set an action: " + "\n 1 - Create new operation" + "\n 2 - Get csv file of operations"
                    + "\n 0 - exit";
            System.out.println(helpStr);
            setAction(reader.readLine());
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private void setAction(String read) throws IOException {
        switch (read) {
            case "1":{
                createOperation();
                exec();
            }
            break;
            case "2":{
                writeOperationsToCsv();
                exec();
            }
            break;
            case "0":{
                System.exit(0);
            }
            break;
            default:{
                System.out.println("Enter correct operation!");
                exec();
            }
        }
    }

    private void createOperation() throws IOException {
        System.out.println("Enter account ID:");
        String accountId = reader.readLine();
        System.out.println("Enter category ID:");
        String categoryId = reader.readLine();
        System.out.println("Enter amount of money:");
        String moneyString = reader.readLine();
        BigDecimal money = BigDecimal.valueOf(Long.parseLong(moneyString));
        if(money.equals(BigDecimal.ZERO)){
            LOGGER.error("amount of money < 0, money of operation: " + money);
            throw new RemoteException("amount money of operation should be > 0");
        }
        System.out.println("Enter date & time of operation:");
        String dateTimeString = reader.readLine();
        Instant dateTime = LocalDateTime.parse(dateTimeString).toInstant(ZoneOffset.UTC);
        LOGGER.info("Start create operation");
        operationService.createOperation(Long.parseLong(accountId),Long.parseLong(categoryId), money, dateTime);
        LOGGER.info("End create operation");
    }

    private void writeOperationsToCsv() throws IOException {
        System.out.println("Enter account ID:");
        String accountId = reader.readLine();
        System.out.println("Enter the lower bound of date & time range:");
        String lowerString = reader.readLine();
        LocalDateTime fromDateTime = LocalDateTime.parse(lowerString);
        System.out.println("Enter the upper bound of date & time range:");
        String upperString = reader.readLine();
        LocalDateTime toDateTime = LocalDateTime.parse(upperString);

        operationService.writeCsv(Long.parseLong(accountId), fromDateTime, toDateTime);
        System.out.println("Successful!");
    }
}
