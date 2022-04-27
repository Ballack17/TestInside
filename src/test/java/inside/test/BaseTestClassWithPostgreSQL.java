package inside.test;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Расширение базового тестового класса Постгрес контейнером с данными ликвибейза
 */

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class BaseTestClassWithPostgreSQL extends BaseTestClass {

    public static PostgreSQLContainer<?> postgresDBContainer = new PostgreSQLContainer<>("postgres:13");

    static {
        postgresDBContainer
                .withDatabaseName("test")
                .withUsername("postgres")
                .withPassword("qwerty");
        postgresDBContainer.withReuse(true);
        postgresDBContainer.start();
    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgresDBContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgresDBContainer::getPassword);
        registry.add("spring.datasource.username", postgresDBContainer::getUsername);
        registry.add("spring.liquibase.enabled", BaseTestClassWithPostgreSQL::enabled);
    }

    private static String enabled(){
        return "true";
    }

}
