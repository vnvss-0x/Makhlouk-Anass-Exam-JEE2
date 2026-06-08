package com.makhlouk.anass.services;

import com.makhlouk.anass.dtos.*;
import com.makhlouk.anass.entities.*;
import com.makhlouk.anass.enums.StatutCredit;
import com.makhlouk.anass.enums.TypeBien;
import com.makhlouk.anass.mappers.CreditMapper;
import com.makhlouk.anass.repositories.ClientRepository;
import com.makhlouk.anass.repositories.CreditRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final ClientRepository clientRepository;
    private final CreditMapper creditMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CreditDTO> getAllCredits() {
        return creditRepository.findAll()
                .stream()
                .map(creditMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CreditDTO getCreditById(Long id) {
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Crédit non trouvé avec l'id : " + id));
        return creditMapper.toDTO(credit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditDTO> getCreditsByClientId(Long clientId) {
        return creditRepository.findByClientId(clientId)
                .stream()
                .map(creditMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditDTO> getCreditsByStatut(StatutCredit statut) {
        return creditRepository.findByStatut(statut)
                .stream()
                .map(creditMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CreditDTO saveCredit(CreditDTO creditDTO) {
        Client client = clientRepository.findById(creditDTO.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client non trouvé avec l'id : " + creditDTO.getClientId()));

        Credit credit = buildCreditFromDTO(creditDTO, client);
        Credit saved = creditRepository.save(credit);
        return creditMapper.toDTO(saved);
    }

    @Override
    public CreditDTO updateStatut(Long id, StatutCredit statut) {
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Crédit non trouvé avec l'id : " + id));

        credit.setStatut(statut);
        if (statut == StatutCredit.ACCEPTE) {
            credit.setDateAcceptation(LocalDate.now());
        }

        Credit updated = creditRepository.save(credit);
        return creditMapper.toDTO(updated);
    }

    private Credit buildCreditFromDTO(CreditDTO dto, Client client) {
        String type = dto.getTypeCredit() != null ? dto.getTypeCredit().toUpperCase() : "";

        switch (type) {
            case "PERSONNEL" -> {
                CreditPersonnelDTO cpDto = (CreditPersonnelDTO) dto;
                return CreditPersonnel.builder()
                        .dateDemande(dto.getDateDemande() != null ? dto.getDateDemande() : LocalDate.now())
                        .statut(StatutCredit.EN_COURS)
                        .montant(dto.getMontant())
                        .dureeRemboursement(dto.getDureeRemboursement())
                        .tauxInteret(dto.getTauxInteret())
                        .client(client)
                        .motif(cpDto.getMotif())
                        .build();
            }
            case "IMMOBILIER" -> {
                CreditImmobilierDTO ciDto = (CreditImmobilierDTO) dto;
                return CreditImmobilier.builder()
                        .dateDemande(dto.getDateDemande() != null ? dto.getDateDemande() : LocalDate.now())
                        .statut(StatutCredit.EN_COURS)
                        .montant(dto.getMontant())
                        .dureeRemboursement(dto.getDureeRemboursement())
                        .tauxInteret(dto.getTauxInteret())
                        .client(client)
                        .typeBien(ciDto.getTypeBien() != null ? ciDto.getTypeBien() : TypeBien.APPARTEMENT)
                        .build();
            }
            case "PROFESSIONNEL" -> {
                CreditProfessionnelDTO cprDto = (CreditProfessionnelDTO) dto;
                return CreditProfessionnel.builder()
                        .dateDemande(dto.getDateDemande() != null ? dto.getDateDemande() : LocalDate.now())
                        .statut(StatutCredit.EN_COURS)
                        .montant(dto.getMontant())
                        .dureeRemboursement(dto.getDureeRemboursement())
                        .tauxInteret(dto.getTauxInteret())
                        .client(client)
                        .motif(cprDto.getMotif())
                        .raisonSociale(cprDto.getRaisonSociale())
                        .build();
            }
            default -> throw new IllegalArgumentException("Type de crédit inconnu : " + type);
        }
    }
}
