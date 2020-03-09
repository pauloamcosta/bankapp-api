package pauloamcosta.com.bankapi.service;

import pauloamcosta.com.bankapi.DTO.ClientDTO;
import pauloamcosta.com.bankapi.domain.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAll();

    Client findClient(Long id);

    void saveClient(ClientDTO client);

    void decryptClient(Long id) throws Exception;

    Client fromDTO(ClientDTO clientDto);
}
