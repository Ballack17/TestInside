package inside.test.services;

import inside.test.BaseTestClassWithPostgreSQL;
import inside.test.data.entities.User;
import inside.test.data.repositories.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
public class UserServiceTest extends BaseTestClassWithPostgreSQL {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    /**
     * проверка создания Юзера (хоть метод и не обязательный, но хотел сделать максимальное покрытие тестами)
     * */
    @SneakyThrows
    @Test
    @Sql("/dbdatainsert.sql")
    void createNewUserTest () {
        User user = new User();
        user.setName("Danil");
        user.setPassword("123");

        assertThrows(IllegalArgumentException.class, () -> {userService.createNewUser(user);});

        user.setName("DanilNew");
        assertFalse(userRepository.existsByName(user.getName()));
        userService.createNewUser(user);
        assertTrue(userRepository.existsByName(user.getName()));
    }

    /**
     * Загрузили юзера в UserDetails, проверили по паролю - наш там юзер или нет
     * */
    @SneakyThrows
    @Test
    @Sql("/dbdatainsert.sql")
    void findUserByNameTest () {
        UserDetails userDetails = userService.loadUserByUsername("Danil");
        assertTrue(passwordEncoder.matches("123", userDetails.getPassword()));
    }
}
