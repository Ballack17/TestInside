package inside.test.controllers;

import inside.test.BaseTestClassWithPostgreSQL;
import inside.test.data.dto.MessageDto;
import inside.test.data.repositories.MessageRepository;
import inside.test.services.MessageService;
import inside.test.services.UserService;
import inside.test.utils.JwtTokenUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class MessageControllerTest extends BaseTestClassWithPostgreSQL {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageRepository messageRepository;

    @SneakyThrows
    @Test
    @Sql("/dbdatainsert.sql")
    public void addMessage() {

        UserDetails userDetails = userService.loadUserByUsername("Danil");
        String token = jwtTokenUtil.generateToken(userDetails);
        /**
         * создали токен и послали его в Хеадере "Authorization", в нужном нам формате.
         * в теле запроса - Имя юзера и сообщение.
         * перед проверкой убедились, что сообщений нет в БД
         * в ответ получили статус ОК и проверили БД - есть 1 сообщение и именно то, которое послали
         * */
        assertEquals(messageRepository.count(), 0);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/message")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer_" + token)
                .content(asJsonString(
                                new MessageDto("Danil","title"))))
                .andExpect(status().isOk());
        assertEquals(messageRepository.count(), 1);
        assertTrue(messageService.getLastTenMessages("Danil").contains("title"));
    }

}
