package com.assignment.banking.BankingService.repository;

import com.assignment.banking.BankingService.entity.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICardDetailsRepository extends JpaRepository<CardDetails, UUID> {
}
