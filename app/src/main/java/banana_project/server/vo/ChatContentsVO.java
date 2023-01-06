package banana_project.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatContentsVO {
    private String chat_date;
    private String chat_content;
    private String user_id;
    private int chat_no;

    public ChatContentsVO() {

    }
}
