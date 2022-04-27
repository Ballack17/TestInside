package inside.test.controllers;

import inside.test.BaseTestClassWithPostgreSQL;
import inside.test.data.dto.JwtRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.http.HttpResponse;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends BaseTestClassWithPostgreSQL {

    /**
     * Проверка работы ендпоинта авторизации
     * */
    @SneakyThrows
    @Test
    @Sql("/dbdatainsert.sql")
    public void getAuth() {

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setName("Danil");
        jwtRequest.setPassword("123");
        /**
         * Создали запрос с существующим юзером, послали и получили токен и статус ОК
         * */
        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jwtRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        JwtRequest jwtRequestUnexistUser = new JwtRequest();
        jwtRequestUnexistUser.setName("Danil45");
        jwtRequestUnexistUser.setPassword("12345");
        /**
         * Создали запрос с несуществующим юзером и получили статус Unauthorized
         * */
        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jwtRequestUnexistUser)))
                .andExpect(status().isUnauthorized());

    }
}
