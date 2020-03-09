package pauloamcosta.com.bankapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pauloamcosta.com.bankapi.DTO.ClientDTO;
import pauloamcosta.com.bankapi.domain.Client;
import pauloamcosta.com.bankapi.service.ClientService;
import pauloamcosta.com.bankapi.resources.utils.URIUtils;
import pauloamcosta.com.bankapi.service.ClientServiceImpl;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        return ResponseEntity.ok().body(clientService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> findAndDecryptClient(@PathVariable Long id) {
        try {
            Client client = clientService.findClient(id);
            return ResponseEntity.ok().body(client);

        } catch (Exception e) {
            log.error(e + " Unable to find client");
        }
        return ResponseEntity.notFound().build();

    }


    @PostMapping
    public ResponseEntity saveClient(@Valid @RequestBody ClientDTO clientDto) {
        clientService.saveClient(clientDto);
        URI uri = URIUtils.getUri(clientDto);
        return ResponseEntity.created(uri).build();
    }
}
