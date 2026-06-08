package com.makhlouk.anass.repositories;

import com.makhlouk.anass.entities.Remboursement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {

    List<Remboursement> findByCreditId(Long creditId);
}
