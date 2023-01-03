package banana_project.util.dbTable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatUserList {
    int chat_no;
    String user_id;
    int flag;
}
