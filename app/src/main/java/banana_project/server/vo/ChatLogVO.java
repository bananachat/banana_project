package banana_project.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatLogVO {
    private String chat_date;
    private int protocol;
    private String comments;
    private String user_id;

    public ChatLogVO() {

    }
}
