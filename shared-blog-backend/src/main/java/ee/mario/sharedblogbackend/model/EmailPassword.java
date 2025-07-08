package ee.mario.sharedblogbackend.model;

import lombok.Data;

@Data
public class EmailPassword {
    private String email;
    private String password;
}
