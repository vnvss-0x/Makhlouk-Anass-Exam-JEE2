package com.makhlouk.anass.repositories;

import com.makhlouk.anass.entities.Credit;
import com.makhlouk.anass.enums.StatutCredit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findByClientId(Long clientId);

    List<Credit> findByStatut(StatutCredit statut);

    List<Credit> findByClientIdAndStatut(Long clientId, StatutCredit statut);
}
