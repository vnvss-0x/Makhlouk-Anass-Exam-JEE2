package com.makhlouk.anass.web;

import com.makhlouk.anass.dtos.CreditDTO;
import com.makhlouk.anass.enums.StatutCredit;
import com.makhlouk.anass.services.CreditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Crédits", description = "Gestion des crédits bancaires")
public class CreditController {

    private final CreditService creditService;

    @GetMapping("/api/credits")
    @Operation(summary = "Lister tous les crédits")
    public ResponseEntity<List<CreditDTO>> getAllCredits() {
        return ResponseEntity.ok(creditService.getAllCredits());
    }

    @GetMapping("/api/credits/{id}")
    @Operation(summary = "Obtenir un crédit par son ID")
    public ResponseEntity<CreditDTO> getCreditById(@PathVariable Long id) {
        return ResponseEntity.ok(creditService.getCreditById(id));
    }

    @GetMapping("/api/clients/{clientId}/credits")
    @Operation(summary = "Lister les crédits d'un client")
    public ResponseEntity<List<CreditDTO>> getCreditsByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(creditService.getCreditsByClientId(clientId));
    }

    @GetMapping("/api/credits/statut/{statut}")
    @Operation(summary = "Lister les crédits par statut")
    public ResponseEntity<List<CreditDTO>> getCreditsByStatut(@PathVariable StatutCredit statut) {
        return ResponseEntity.ok(creditService.getCreditsByStatut(statut));
    }

    @PostMapping("/api/credits")
    @Operation(summary = "Créer une nouvelle demande de crédit")
    public ResponseEntity<CreditDTO> createCredit(@RequestBody CreditDTO creditDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creditService.saveCredit(creditDTO));
    }

    @PutMapping("/api/credits/{id}/statut")
    @Operation(summary = "Mettre à jour le statut d'un crédit (valider ou rejeter)")
    public ResponseEntity<CreditDTO> updateStatut(
            @PathVariable Long id,
            @RequestParam StatutCredit statut) {
        return ResponseEntity.ok(creditService.updateStatut(id, statut));
    }
}
