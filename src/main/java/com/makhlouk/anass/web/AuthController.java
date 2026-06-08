package com.makhlouk.anass.web;

import com.makhlouk.anass.dtos.LoginRequestDTO;
import com.makhlouk.anass.dtos.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentification", description = "Endpoints de connexion JWT")
public class AuthController {

    private static final Map<String, String[]> USERS = Map.of(
            "client1",   new String[]{"secret1234", "ROLE_CLIENT"},
            "employe1",  new String[]{"secret1234", "ROLE_EMPLOYE"},
            "admin",     new String[]{"secret1234", "ROLE_ADMIN"}
    );

    @PostMapping("/login")
    @Operation(summary = "Connexion et obtention du token JWT")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        String[] userData = USERS.get(request.getUsername());

        if (userData == null || !userData[0].equals(request.getPassword())) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(LoginResponseDTO.builder()
                .username(request.getUsername())
                .roles(List.of(userData[1]))
                .token("JWT_PLACEHOLDER_COMMIT_08")
                .build());
    }
}
