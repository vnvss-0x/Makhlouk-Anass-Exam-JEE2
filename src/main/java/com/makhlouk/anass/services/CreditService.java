package com.makhlouk.anass.services;

import com.makhlouk.anass.dtos.CreditDTO;
import com.makhlouk.anass.enums.StatutCredit;

import java.util.List;

public interface CreditService {
    List<CreditDTO> getAllCredits();
    CreditDTO getCreditById(Long id);
    List<CreditDTO> getCreditsByClientId(Long clientId);
    List<CreditDTO> getCreditsByStatut(StatutCredit statut);
    CreditDTO saveCredit(CreditDTO creditDTO);
    CreditDTO updateStatut(Long id, StatutCredit statut);
}
