package com.makhlouk.anass.entities;

import com.makhlouk.anass.enums.TypeRemboursement;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "remboursements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Remboursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Double montant;

    @Enumerated(EnumType.STRING)
    private TypeRemboursement type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Credit credit;
}
