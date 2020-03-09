package pauloamcosta.com.bankapi.resources.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pauloamcosta.com.bankapi.DTO.ClientDTO;
import pauloamcosta.com.bankapi.domain.Client;

import javax.validation.Valid;
import java.net.URI;

public class URIUtils {
    public static URI getUri(@Valid ClientDTO client) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(client.getId()).toUri();
    }
}
