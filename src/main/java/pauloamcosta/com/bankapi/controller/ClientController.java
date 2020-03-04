package pauloamcosta.com.bankapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pauloamcosta.com.bankapi.domain.Client;
import pauloamcosta.com.bankapi.service.ClientService;
import pauloamcosta.com.bankapi.resources.exceptions.ClientNotFoundException;
import pauloamcosta.com.bankapi.resources.utils.URIUtils;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        List<Client> clientList = clientService.findAll();
        return ResponseEntity.ok().body(clientList);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity< Optional<Client>> findAndDecryptClient(@PathVariable Long id) {

        try {
            Optional<Client> client = clientService.findClient(id);
            clientService.decryptClient(client);
            return ResponseEntity.ok().body(client);

        } catch (ClientNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity saveClient(@Valid @RequestBody Client client){
        clientService.saveClient(client);
        URI uri = URIUtils.getUri(client);
        return ResponseEntity.created(uri).build();
    }
}
