package com.example.banki.modules.account.model;

import com.example.banki.modules.BaseEntity;
import com.example.banki.modules.bankBranch.model.BankBranch;
import com.example.banki.modules.customer.model.Customer;
import com.example.banki.modules.transaction.model.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "account_number")
    @SequenceGenerator(name = "acNum", initialValue = 5000)
    @GeneratedValue(generator = "acNum")
    private int accNumber;

    //creat valid
    private double currentBalance;


    @JsonIgnore
    @OneToMany
    List<Transaction> transactions;

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creationDate;
    @ManyToOne
    private Customer author;


    @ManyToOne
    private BankBranch bankBranch;

    public Account(Customer author, BankBranch bankBranch) {
        this.currentBalance = 0;
        this.author = author;
        this.bankBranch = bankBranch;
        this.creationDate = LocalDateTime.now();
    }


}