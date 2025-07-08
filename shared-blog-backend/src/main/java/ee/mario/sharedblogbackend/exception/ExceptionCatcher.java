package ee.mario.sharedblogbackend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.Date;

@ControllerAdvice
public class ExceptionCatcher {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> catchException(RuntimeException e) {
        ErrorMessage message = new ErrorMessage();
        message.setDate(new Date());
        message.setStatus(400);
        message.setMessage(e.getMessage());
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> catchException(SQLException e) {
        ErrorMessage message = new ErrorMessage();
        message.setDate(new Date());
        message.setStatus(400);
        message.setMessage("Database error!");
        return ResponseEntity.status(400).body(message);
    }

}
