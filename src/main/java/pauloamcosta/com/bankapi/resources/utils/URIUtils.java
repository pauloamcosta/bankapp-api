package pauloamcosta.com.bankapi.resources.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pauloamcosta.com.bankapi.domain.Client;

import java.net.URI;

public class URIUtils {
    public static URI getUri(Client client) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(client.getId()).toUri();
    }
}
