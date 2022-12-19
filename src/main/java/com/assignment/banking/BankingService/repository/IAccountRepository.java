package com.assignment.banking.BankingService.repository;

import com.assignment.banking.BankingService.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAccountRepository extends JpaRepository<Account, UUID> {

    @Query(value = "select * from account_details  where first_name = :firstName"
            + " and last_name = :lastName", nativeQuery = true)
    Optional<Account> searchByFirstAndLastName(@Param("firstName") String firstName,
                                                @Param("lastName") String lastName);
}
