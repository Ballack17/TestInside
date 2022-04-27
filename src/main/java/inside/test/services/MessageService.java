package inside.test.services;


import inside.test.data.dto.MessageDto;
import inside.test.data.entities.Message;
import inside.test.data.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Сервис по работе с репозиторием сообщений, по сути нам нужен только поиск
 * */

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    private final String getHistory = "history 10";

    /**
     * Метод обработки входящих ДТО
     * Проверяет существование юзера
     * Если сообщение history_10, то отрабатывает получения из БД 10 сообщений последних данного юзера
     * В другом случае, просто добавляется еще одно сообщение.
     * Проверка наличия пользователя в каждом методе, с целью -
     * минимизировать шанс ошибки забыть эту самую проверку сделать, мало ли методы будут в других местах использоваться,
     * либо в хендлер новые методы будут добавляться.
     * */
    public List<String> messageRestHandler(MessageDto messageDto) {
        isExistByName(messageDto.getName());
        if ((getHistory).equals(messageDto.getMessage())) {
            return getLastTenMessages(messageDto.getName());
        } else {createMessage(messageDto);
            return null;
        }
    }
    /**
     * Добавить сообщение
     * */
    public void createMessage(MessageDto messageDto) {
            isExistByName(messageDto.getName());
            Message message = new Message();
            message.setMessage(messageDto.getMessage());
            message.setUser(userService.findUserByName(messageDto.getName()).get());
            messageRepository.save(message);
    }

    /**
     * Получить историю сообщений
     * по идее можно через маппер сделать, в данном случае уточнения не было - получаем просто 10 сообщений,
     * если же сущность сообщения усложнить (от кого, когда, тип сообщения и тд), то логичнее конечно написать маппер
     * */
    public List<String> getLastTenMessages(String name) {
       isExistByName(name);
        return messageRepository.findAllByUser_NameOrderByCreatedAtDesc(name).stream().map(Message::getMessage).limit(10).collect(Collectors.toList());
    }

    /**
     * проверка наличия пользователя в базе
     * */
    public void isExistByName(String name) {
        userService.findUserByName(name).orElseThrow(() -> new NoSuchElementException("User is not exist"));
    }
}
