package pauloamcosta.com.bankapi.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pauloamcosta.com.bankapi.domain.Client;
import pauloamcosta.com.bankapi.repository.ClientRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientServiceTests {

    @TestConfiguration
    static class ClientServiceImplTestContextConfiguration {

        @Bean
        public ClientService clientService() {
            return new ClientServiceImpl();
        }
    }

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Before
    public void setUp() {
        Client c1 = new Client("Test1", "1111111");
        Client c2 = new Client("Test2", "2222222");

        List<Client> listClient = Arrays.asList(c1, c2);
        when(clientRepository.findAll())
                .thenReturn(listClient);
        when(clientRepository.save(c1)).thenReturn(c1);
    }

    @Test
    public void findAllShouldReturnAllClients() {
        List<Client> clientsList = clientService.findAll();
        assertThat(clientsList).isNotEmpty();
        assertThat(clientsList).size().isEqualTo(2);
    }

    @Test
    public void GivenAValidClientId_FindAndDecryptClientShouldReturnAClient() {
        Optional<Client> clientOpt = clientService.findClient((long) 1);
        clientOpt.ifPresent(client -> assertThat(client.getName()).isEqualTo("Test1"));
    }
    @Test
    public void GivenAnInvalidClientId_FindAndDecryptClientShouldReturnAClient() {
        Optional<Client> clientOpt = clientService.findClient((long) 3);
        assertThat(!clientOpt.isPresent());
    }
}
