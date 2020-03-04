package pauloamcosta.com.bankapi.service;

import pauloamcosta.com.bankapi.domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findAll();
    Optional<Client> findClient(Long id);
    void saveClient(Client client);
    void decryptClient(Optional<Client> client);

}
