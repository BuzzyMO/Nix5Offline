package org.example.finance.model;

import org.example.finance.annotation.CsvColumn;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OperationDto {

    @CsvColumn(colName = "ID")
    public final Long id;
    @CsvColumn(colName = "account_ID")
    public final Long accountId;
    @CsvColumn(colName = "Category_ID")
    public final Long categoryId;
    @CsvColumn(colName = "Category_Name")
    public final String categoryName;
    @CsvColumn(colName = "Category_Type_Name")
    public final String categoryTypeName;
    @CsvColumn(colName = "Money")
    public final BigDecimal money;
    @CsvColumn(colName = "Timestamp")
    public final Timestamp timestamp;

    public OperationDto(Long id, Long accountId, Long categoryId, String categoryName, String categoryTypeName, BigDecimal money, Timestamp timestamp) {
        this.id = id;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryTypeName = categoryTypeName;
        this.money = money;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }


    public Long getCategoryId() {
        return categoryId;
    }


    public BigDecimal getMoney() {
        return money;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryTypeName() {
        return categoryTypeName;
    }
}
