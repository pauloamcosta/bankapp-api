package pauloamcosta.com.bankapi.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pauloamcosta.com.bankapi.domain.Client;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ClientRepository clientRepository;


    Client c1 = new Client("Test1", "1111111");
    Client c2 = new Client("Test2", "2222222");

    @Test
    public void shouldSaveAClient() throws Exception {

        clientRepository.save(c1);
        Client clientFound = this.testEntityManager.find(Client.class, c1.getId());
        assertThat(clientFound).isNotNull();
        assertThat(clientFound.getName())
                .isEqualTo(c1.getName());
    }

    @Test
    public void shouldGetAllClients() {
        clientRepository.save(c1);
        clientRepository.save(c2);
        List<Client> clientsList = clientRepository.findAll();
        assertThat(clientsList).isNotEmpty();
        assertThat(clientsList).size().isEqualTo(2);
    }
}
