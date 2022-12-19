package com.assignment.banking.BankingService.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "card_details")
@NoArgsConstructor
public class CardDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "card_number", insertable = false, updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID cardNumber;

    @Enumerated(EnumType.STRING)
    private CardType type;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number")
    private Account account;

}
