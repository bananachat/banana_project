package banana_project.util.dbTable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class Chat_list {
    int chat_no;
    String chat_title;
}
