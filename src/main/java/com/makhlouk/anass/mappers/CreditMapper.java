package com.makhlouk.anass.mappers;

import com.makhlouk.anass.dtos.*;
import com.makhlouk.anass.entities.*;
import org.springframework.stereotype.Component;

@Component
public class CreditMapper {

    public CreditDTO toDTO(Credit credit) {
        if (credit == null) return null;

        if (credit instanceof CreditPersonnel cp) {
            return CreditPersonnelDTO.builder()
                    .id(cp.getId())
                    .dateDemande(cp.getDateDemande())
                    .statut(cp.getStatut())
                    .dateAcceptation(cp.getDateAcceptation())
                    .montant(cp.getMontant())
                    .dureeRemboursement(cp.getDureeRemboursement())
                    .tauxInteret(cp.getTauxInteret())
                    .clientId(cp.getClient() != null ? cp.getClient().getId() : null)
                    .typeCredit("PERSONNEL")
                    .motif(cp.getMotif())
                    .build();
        }

        if (credit instanceof CreditImmobilier ci) {
            return CreditImmobilierDTO.builder()
                    .id(ci.getId())
                    .dateDemande(ci.getDateDemande())
                    .statut(ci.getStatut())
                    .dateAcceptation(ci.getDateAcceptation())
                    .montant(ci.getMontant())
                    .dureeRemboursement(ci.getDureeRemboursement())
                    .tauxInteret(ci.getTauxInteret())
                    .clientId(ci.getClient() != null ? ci.getClient().getId() : null)
                    .typeCredit("IMMOBILIER")
                    .typeBien(ci.getTypeBien())
                    .build();
        }

        if (credit instanceof CreditProfessionnel cpr) {
            return CreditProfessionnelDTO.builder()
                    .id(cpr.getId())
                    .dateDemande(cpr.getDateDemande())
                    .statut(cpr.getStatut())
                    .dateAcceptation(cpr.getDateAcceptation())
                    .montant(cpr.getMontant())
                    .dureeRemboursement(cpr.getDureeRemboursement())
                    .tauxInteret(cpr.getTauxInteret())
                    .clientId(cpr.getClient() != null ? cpr.getClient().getId() : null)
                    .typeCredit("PROFESSIONNEL")
                    .motif(cpr.getMotif())
                    .raisonSociale(cpr.getRaisonSociale())
                    .build();
        }

        return CreditDTO.builder()
                .id(credit.getId())
                .dateDemande(credit.getDateDemande())
                .statut(credit.getStatut())
                .dateAcceptation(credit.getDateAcceptation())
                .montant(credit.getMontant())
                .dureeRemboursement(credit.getDureeRemboursement())
                .tauxInteret(credit.getTauxInteret())
                .clientId(credit.getClient() != null ? credit.getClient().getId() : null)
                .build();
    }

    public RemboursementDTO toRemboursementDTO(Remboursement remboursement) {
        if (remboursement == null) return null;
        return RemboursementDTO.builder()
                .id(remboursement.getId())
                .date(remboursement.getDate())
                .montant(remboursement.getMontant())
                .type(remboursement.getType())
                .creditId(remboursement.getCredit() != null ? remboursement.getCredit().getId() : null)
                .build();
    }
}
