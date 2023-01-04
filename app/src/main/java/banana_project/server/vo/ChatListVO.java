package banana_project.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatListVO {
    private int chat_no;
    private String chat_title;

    public ChatListVO() {

    }
}
