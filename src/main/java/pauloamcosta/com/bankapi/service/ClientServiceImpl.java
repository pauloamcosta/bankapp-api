package pauloamcosta.com.bankapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pauloamcosta.com.bankapi.DTO.ClientDTO;
import pauloamcosta.com.bankapi.domain.Client;
import pauloamcosta.com.bankapi.repository.ClientRepository;
import pauloamcosta.com.bankapi.resources.exceptions.ClientNotFoundException;
import pauloamcosta.com.bankapi.resources.utils.AESEncryption;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    private AESEncryption aesEncryption;


    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, AESEncryption aesEncryption){
        this.clientRepository = clientRepository;
        this.aesEncryption = aesEncryption;
    }


    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    public ClientServiceImpl() {

    }

    @Override
    public List<Client> findAll() {
        log.info("Get Request to all clients");
        return clientRepository.findAll();
    }

    public Client findClient(Long id)  throws ClientNotFoundException {
        log.info("Get Request to id: " + id);
        try {
            decryptClient(id);
        } catch (Exception e) {
            log.error("Unable to decrypt client");
        }

        return clientRepository.findClientById(id);
    }

    @Override
    public void saveClient(ClientDTO clientDto) {
        Client client = fromDTO(clientDto);
        try {
            encryptClient(client);
            clientRepository.save(client);
            log.info("Client saved: " + client.getName());

        } catch (Exception e) {
            log.error(e + "Unable to Save Client");
        }
    }

    public void decryptClient(Long id) throws Exception {
        Client newClient = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        newClient.setName(aesEncryption.decrypt(newClient.getName()));
        newClient.setAccount(aesEncryption.decrypt(newClient.getAccount()));
    }

    public void encryptClient(Client client) throws Exception {
        client.setName(aesEncryption.encrypt(client.getName()));
        client.setAccount(aesEncryption.encrypt(client.getAccount()));
    }

    @Override
    public Client fromDTO(ClientDTO clientDto) {
        return new Client(clientDto.getName(), clientDto.getAccount());
    }
}
