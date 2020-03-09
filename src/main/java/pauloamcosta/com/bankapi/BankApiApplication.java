package pauloamcosta.com.bankapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pauloamcosta.com.bankapi.repository.ClientRepository;

@SpringBootApplication
public class BankApiApplication implements CommandLineRunner {
    @Autowired
    ClientRepository clientRepository;

    public static void main(String[] args) {
        SpringApplication.run(BankApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
