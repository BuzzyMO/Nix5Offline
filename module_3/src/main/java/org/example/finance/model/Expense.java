package org.example.finance.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "expense_categories")
public class Expense extends Category{
}
