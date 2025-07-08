package ee.mario.sharedblogbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String subTitle;
    private String content;
    private boolean published;
}
