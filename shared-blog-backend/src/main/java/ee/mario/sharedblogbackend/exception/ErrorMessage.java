package ee.mario.sharedblogbackend.exception;

import lombok.Data;

import java.util.Date;

@Data //getterid ja setterid

public class ErrorMessage {
    private String message;
    private Date date;
    private int status;
}

