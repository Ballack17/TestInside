package inside.test.configs;

import inside.test.data.dto.ErrorDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * обработчик исключений
 * */
@ControllerAdvice
public class WebErrorHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exc) {
        return new ResponseEntity<>(exc.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<List<ErrorDto>> validationErrorHandler(MethodArgumentNotValidException exc) {
        List<ErrorDto> errorDtoList = new ArrayList<>(); {
            exc.getBindingResult().getAllErrors().forEach(objectError -> {
                String fieldName = ((FieldError) objectError).getField();
                String errorMessage = objectError.getDefaultMessage();
                errorDtoList.add(new ErrorDto(fieldName, errorMessage));
            });
        }
        return new ResponseEntity<>(errorDtoList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException exc) {
        return new ResponseEntity<>(exc.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException exc) {
        return new ResponseEntity<>(exc.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }


}
