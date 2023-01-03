package banana_project.util.dbTable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatLog {
    String chat_date;
    int protocol;
    String comments;
    String user_id;
}
