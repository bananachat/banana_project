package banana_project.server.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatContentsVO {
    private String chat_date;
    private String chat_content;
    private String user_id;
    private int chat_no;

    public ChatContentsVO() {

    }
}
