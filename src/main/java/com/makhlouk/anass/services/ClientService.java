package com.makhlouk.anass.services;

import com.makhlouk.anass.dtos.ClientDTO;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getAllClients();
    ClientDTO getClientById(Long id);
    ClientDTO saveClient(ClientDTO clientDTO);
    void deleteClient(Long id);
}
