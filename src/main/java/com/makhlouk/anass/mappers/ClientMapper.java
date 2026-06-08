package com.makhlouk.anass.mappers;

import com.makhlouk.anass.dtos.ClientDTO;
import com.makhlouk.anass.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDTO toDTO(Client client) {
        if (client == null) return null;
        return ClientDTO.builder()
                .id(client.getId())
                .nom(client.getNom())
                .email(client.getEmail())
                .build();
    }

    public Client toEntity(ClientDTO dto) {
        if (dto == null) return null;
        return Client.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .email(dto.getEmail())
                .build();
    }
}
