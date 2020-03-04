package pauloamcosta.com.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pauloamcosta.com.bankapi.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
