package com.assignment.banking.BankingService.repository;

import com.assignment.banking.BankingService.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {

    @Query(value = "select * from transactions t where t.account_number = :id", nativeQuery = true)
    List<Transactions> findByAccountNumber(@Param("id") UUID accountNumber);
}
