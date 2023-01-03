package banana_project.util.dbTable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatLogVO {
    private String chat_date;
    private int protocol;
    private String comments;
    private String user_id;

    public ChatLogVO() {

    }
}
