package ee.mario.sharedblogbackend.model;

import lombok.Data;

import java.util.Date;

@Data
public class AuthToken {
    private String token;
    private Date expiration;
}

