package inside.test.controllers;


import inside.test.data.dto.MessageDto;
import inside.test.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Обработка ендпоинт по добавлению сообщений, либо получению истории сообщений
 * Вся логика обработки в МессаджСервисе, здесь в одном случае просто ОК, если сообщение по запросу истории,
 * то в ответ список сообщений и статус ОК
 * */

@RestController
@RequestMapping("api/v1/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> addMessage(@Valid @RequestBody MessageDto messageDto) {
        return new ResponseEntity<>(messageService.messageRestHandler(messageDto), HttpStatus.OK);
    }

}
