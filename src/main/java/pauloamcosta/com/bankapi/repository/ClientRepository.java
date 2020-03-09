package pauloamcosta.com.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pauloamcosta.com.bankapi.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT u FROM Client u WHERE u.id = ?1")
    Client findClientById(Long id);

}
