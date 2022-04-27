package inside.test.services;

import inside.test.BaseTestClassWithPostgreSQL;
import inside.test.data.dto.MessageDto;
import inside.test.data.repositories.MessageRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MessageServiceTest extends BaseTestClassWithPostgreSQL {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;


    @SneakyThrows
    @Test
    @Sql("/dbdatainsert.sql")
    void messageRestHandlerTestCreate() {

        MessageDto messageDtoUserExist = new MessageDto();
        messageDtoUserExist.setName("Danil");
        messageDtoUserExist.setMessage("message1");
        /**
         * Проверяяем метод обработки входящих запосов (юзер существует)
         * запрос на добавление сообщения
         * было в БД 0 сообщений, добавили, стало 1
         * */
        assertEquals(messageRepository.count(), 0);
        messageService.messageRestHandler(messageDtoUserExist);
        assertEquals(messageRepository.count(), 1);
    }

    @SneakyThrows
    @Test
    @Sql("/dbdatainsert.sql")
    void messageRestHandlerTestGetHistory() {

        MessageDto messageDtoUserExist = new MessageDto();
        messageDtoUserExist.setName("Danil");
        messageDtoUserExist.setMessage("message1");
        /**
         * Проверяяем метод обработки входящих запосов (юзер существует)
         * запрос на получение последних 10 сообщений
         * было в БД 0 сообщений, добавили, стало 11
         * */
        assertEquals(messageRepository.count(), 0);
        messageService.messageRestHandler(messageDtoUserExist);
        messageDtoUserExist.setMessage("message2");
        messageService.messageRestHandler(messageDtoUserExist);
        messageDtoUserExist.setMessage("message3");
        messageService.messageRestHandler(messageDtoUserExist);
        messageDtoUserExist.setMessage("message4");
        messageService.messageRestHandler(messageDtoUserExist);
        messageDtoUserExist.setMessage("message5");
        messageService.messageRestHandler(messageDtoUserExist);
        messageDtoUserExist.setMessage("message6");
        messageService.messageRestHandler(messageDtoUserExist);
        messageDtoUserExist.setMessage("message7");
        messageService.messageRestHandler(messageDtoUserExist);
        messageDtoUserExist.setMessage("message8");
        messageService.messageRestHandler(messageDtoUserExist);
        messageDtoUserExist.setMessage("message9");
        messageService.messageRestHandler(messageDtoUserExist);
        messageDtoUserExist.setMessage("message10");
        messageService.messageRestHandler(messageDtoUserExist);
        messageDtoUserExist.setMessage("message11");
        messageService.messageRestHandler(messageDtoUserExist);
        assertEquals(messageRepository.count(), 11);

        messageDtoUserExist.setMessage("history 10");
        /**
         * отправляем запрос на получение, убеждаемся, что 10 элементов, и нет первого добавленного сообщения,
         * так как оно не поместилось
         * */
        List<String> messageList = messageService.messageRestHandler(messageDtoUserExist);
        assertEquals(messageList.size(), 10);
        assertTrue(messageList.contains("message11"));
        assertFalse(messageList.contains("message1"));
    }

    @SneakyThrows
    @Test
    @Sql("/dbdatainsert.sql")
    void messageRestHandlerTestCreateFail() {

        MessageDto messageDtoUserNotExist = new MessageDto();
        messageDtoUserNotExist.setName("Danil123");
        messageDtoUserNotExist.setMessage("message1234");

        /**
         * Проверяяем метод добавления сообщения (юзер не существует)
         * было в БД 0 сообщений, и осталось 0
         * мы же получаем ожидаемые ошибки - NoSuchElementException
         * */

        assertEquals(messageRepository.count(), 0);
        assertThrows(NoSuchElementException.class, () -> {messageService.messageRestHandler(messageDtoUserNotExist);});
        assertEquals(messageRepository.count(), 0);

        assertThrows(NoSuchElementException.class, () -> {messageService.createMessage(messageDtoUserNotExist);});
        assertThrows(NoSuchElementException.class, () -> {messageService.getLastTenMessages(messageDtoUserNotExist.getName());});

    }

}