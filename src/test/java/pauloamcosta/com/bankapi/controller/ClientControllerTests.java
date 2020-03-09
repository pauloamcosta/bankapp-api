package pauloamcosta.com.bankapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pauloamcosta.com.bankapi.DTO.ClientDTO;
import pauloamcosta.com.bankapi.domain.Client;
import pauloamcosta.com.bankapi.resources.exceptions.ClientNotFoundException;
import pauloamcosta.com.bankapi.service.ClientService;


import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService clientService;

    @Test
    public void findAll() throws Exception {
        // given
        Client c1 = new Client();
        c1.setId(1l);
        c1.setName("Test Client");
        c1.setAccount("31231231");

        //when
        List<Client> clients = Arrays.asList(c1);
        given(clientService.findAll()).willReturn(clients);


        //then
        this.mvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id': 1,'name': 'Test Client';'account': '31231231'}]"));
        verify(clientService, times(1)).findAll();

    }

    @Test
    public void testFindAndDecryptClientWhenClientExistsShouldReturn200() throws Exception {
        // given
        Client c1 = new Client();
        c1.setId(1l);
        c1.setName("Test Client");
        c1.setAccount("31231231");

        //when
        given(clientService.findClient(1l)).willReturn(c1);

        //then
        mvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1,'name': 'Test Client';'account': '31231231'}"));
        verify(clientService, times(1)).findClient(1l);

    }

    @Test
    public void testFindAndDecryptClientWhenClientDoesntExistsShouldReturn404() throws Exception {
        // given
        when(clientService.findClient(1l)).thenThrow(new ClientNotFoundException());

        mvc.perform(get("/clients/1"))
                .andExpect(status().isNotFound());
        verify(clientService, times(1)).findClient((long) 1l);
    }

    @Test
    public void testSaveClientValidShouldReturn201() throws Exception {
        ClientDTO c1 = new ClientDTO();
        c1.setId(1l);
        c1.setName("Test Client");
        c1.setAccount("31231231");

        doNothing().when(clientService).saveClient(c1);
        //when(clientService.fromDTO( c1)).thenReturn(c1);

        mvc.perform(
                post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(c1)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testSaveClientInvalidAndReturn400() throws Exception {
        ClientDTO c1 = new ClientDTO();
        c1.setName("Test Client");

        doNothing().when(clientService).saveClient(c1);
        mvc.perform(
                post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(c1)))
                .andExpect(status().is4xxClientError());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
