package banana_project.server.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatUserListVO {
    private int chat_no;
    private String user_id;
    private int flag;

    public ChatUserListVO() {

    }
}
