package com.makhlouk.anass;

import com.makhlouk.anass.entities.*;
import com.makhlouk.anass.enums.*;
import com.makhlouk.anass.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final RemboursementRepository remboursementRepository;

    @Override
    public void run(String... args) {

        Client client1 = clientRepository.save(Client.builder()
                .nom("Ahmed Ahmed")
                .email("ahmed.Ahmed@email.com")
                .build());

        Client client2 = clientRepository.save(Client.builder()
                .nom("Fatima Zahra")
                .email("fatima.zahra@email.com")
                .build());

        Client client3 = clientRepository.save(Client.builder()
                .nom("Youssef Youssef")
                .email("youssef.Youssef@email.com")
                .build());

        Client client4 = clientRepository.save(Client.builder()
                .nom("Sara Sara")
                .email("sara.Sara@email.com")
                .build());

        Client client5 = clientRepository.save(Client.builder()
                .nom("Karim Karim")
                .email("karim.Karim@email.com")
                .build());

        CreditPersonnel cp1 = (CreditPersonnel) creditRepository.save(
                CreditPersonnel.builder()
                        .dateDemande(LocalDate.of(2024, 1, 15))
                        .statut(StatutCredit.ACCEPTE)
                        .dateAcceptation(LocalDate.of(2024, 1, 25))
                        .montant(50000.0)
                        .dureeRemboursement(24)
                        .tauxInteret(4.5)
                        .client(client1)
                        .motif("Achat voiture")
                        .build());

        CreditPersonnel cp2 = (CreditPersonnel) creditRepository.save(
                CreditPersonnel.builder()
                        .dateDemande(LocalDate.of(2024, 6, 10))
                        .statut(StatutCredit.EN_COURS)
                        .montant(20000.0)
                        .dureeRemboursement(12)
                        .tauxInteret(5.0)
                        .client(client2)
                        .motif("Voyage et formation")
                        .build());

        CreditImmobilier ci1 = (CreditImmobilier) creditRepository.save(
                CreditImmobilier.builder()
                        .dateDemande(LocalDate.of(2023, 9, 1))
                        .statut(StatutCredit.ACCEPTE)
                        .dateAcceptation(LocalDate.of(2023, 9, 20))
                        .montant(800000.0)
                        .dureeRemboursement(240)
                        .tauxInteret(3.2)
                        .client(client3)
                        .typeBien(TypeBien.APPARTEMENT)
                        .build());

        CreditImmobilier ci2 = (CreditImmobilier) creditRepository.save(
                CreditImmobilier.builder()
                        .dateDemande(LocalDate.of(2024, 5, 20))
                        .statut(StatutCredit.REJETE)
                        .montant(1500000.0)
                        .dureeRemboursement(300)
                        .tauxInteret(3.8)
                        .client(client4)
                        .typeBien(TypeBien.MAISON)
                        .build());

        CreditProfessionnel cpr1 = (CreditProfessionnel) creditRepository.save(
                CreditProfessionnel.builder()
                        .dateDemande(LocalDate.of(2024, 3, 5))
                        .statut(StatutCredit.ACCEPTE)
                        .dateAcceptation(LocalDate.of(2024, 3, 18))
                        .montant(300000.0)
                        .dureeRemboursement(60)
                        .tauxInteret(4.0)
                        .client(client5)
                        .motif("Extension activité")
                        .raisonSociale("Karim Import Export ")
                        .build());

        CreditProfessionnel cpr2 = (CreditProfessionnel) creditRepository.save(
                CreditProfessionnel.builder()
                        .dateDemande(LocalDate.of(2024, 7, 12))
                        .statut(StatutCredit.EN_COURS)
                        .montant(150000.0)
                        .dureeRemboursement(36)
                        .tauxInteret(4.8)
                        .client(client1)
                        .motif("Achat équipement")
                        .raisonSociale("Ahmed Tech")
                        .build());

        remboursementRepository.save(Remboursement.builder()
                .date(LocalDate.of(2024, 2, 25))
                .montant(2200.0)
                .type(TypeRemboursement.MENSUALITE)
                .credit(cp1)
                .build());

        remboursementRepository.save(Remboursement.builder()
                .date(LocalDate.of(2024, 3, 25))
                .montant(2200.0)
                .type(TypeRemboursement.MENSUALITE)
                .credit(cp1)
                .build());

        remboursementRepository.save(Remboursement.builder()
                .date(LocalDate.of(2024, 4, 25))
                .montant(10000.0)
                .type(TypeRemboursement.REMBOURSEMENT_ANTICIPE)
                .credit(cp1)
                .build());

        remboursementRepository.save(Remboursement.builder()
                .date(LocalDate.of(2023, 10, 20))
                .montant(3500.0)
                .type(TypeRemboursement.MENSUALITE)
                .credit(ci1)
                .build());

        remboursementRepository.save(Remboursement.builder()
                .date(LocalDate.of(2023, 11, 20))
                .montant(3500.0)
                .type(TypeRemboursement.MENSUALITE)
                .credit(ci1)
                .build());

        remboursementRepository.save(Remboursement.builder()
                .date(LocalDate.of(2024, 4, 18))
                .montant(5200.0)
                .type(TypeRemboursement.MENSUALITE)
                .credit(cpr1)
                .build());

        remboursementRepository.save(Remboursement.builder()
                .date(LocalDate.of(2024, 5, 18))
                .montant(5200.0)
                .type(TypeRemboursement.MENSUALITE)
                .credit(cpr1)
                .build());

        System.out.println(">>> Base de données initialisée : 5 clients, 6 crédits, 7 remboursements");
    }
}
