package pauloamcosta.com.bankapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pauloamcosta.com.bankapi.domain.Client;
import pauloamcosta.com.bankapi.repository.ClientRepository;
import pauloamcosta.com.bankapi.resources.utils.AESEncryption;
import pauloamcosta.com.bankapi.resources.exceptions.ClientNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AESEncryption aesEncryption;

    Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public List<Client> findAll() {
        log.info("Get Request to all clients");
        return clientRepository.findAll();
    }

    public Optional<Client> findClient(Long id) {
        log.info("Get Request to id: " + id);
        return Optional.ofNullable(clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException()));
    }

    @Override
    public void saveClient(Client client) {
        try {
            client.setName(aesEncryption.encrypt(client.getName()));
            client.setAccount(aesEncryption.encrypt(client.getAccount()));
            clientRepository.save(client);
            log.info("Client saved: " + client.getName());

        } catch (Exception e) {
            log.error("Unable to Save Client");
            e.printStackTrace();
        }
    }
    public void decryptClient(Optional<Client> clientOptional) {

        clientOptional.ifPresent(client -> {
            try {
                client.setName(aesEncryption.decrypt(client.getName()));
                client.setAccount(aesEncryption.decrypt(client.getAccount()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}
