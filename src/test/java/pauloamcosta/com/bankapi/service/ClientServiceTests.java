package pauloamcosta.com.bankapi.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pauloamcosta.com.bankapi.domain.Client;
import pauloamcosta.com.bankapi.repository.ClientRepository;
import pauloamcosta.com.bankapi.resources.utils.AESEncryption;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientServiceTests {

    @MockBean
    private ClientRepository clientRepository;
    @InjectMocks
    private ClientServiceImpl clientService;


    @Before

    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllShouldReturnAllClients() {
        Client c1 = new Client("Test1", "1111111");
        Client c2 = new Client("Test2", "2222222");
        List<Client> listClient = Arrays.asList(c1, c2);
        when(clientRepository.findAll())
                .thenReturn(listClient);
        when(clientRepository.save(c1)).thenReturn(c1);

        List<Client> clientsList = clientService.findAll();
        assertThat(clientsList).isNotEmpty();
        assertThat(clientsList).size().isEqualTo(2);
    }

    @Test
    public void GivenAValidClientId_FindAndDecryptClientShouldReturnAClient() {
        clientService.findClient(3l);
        assertThatNullPointerException();
    }

}
