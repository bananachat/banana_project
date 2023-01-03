package banana_project.util.dbTable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatContets {
    String chat_date;
    String chat_content;
    String user_id;
    int chat_no;
}
