package banana_project.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatUserListVO {
    private int chat_no;
    private String user_id;
    private int flag;

    public ChatUserListVO() {

    }
}
