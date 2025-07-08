package ee.mario.sharedblogbackend.dto;

import lombok.Data;

@Data

public class PersonDTO {
    private Long id;
    private String name;
    private String email;
    private Long profilePicture;
    private String motto;
}
