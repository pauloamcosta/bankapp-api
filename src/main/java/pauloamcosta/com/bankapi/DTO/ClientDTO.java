package pauloamcosta.com.bankapi.DTO;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ClientDTO implements Serializable {

    private Long id;

    @NotEmpty
    private String name;
    @NotEmpty
    private String account;

    public ClientDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
