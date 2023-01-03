package banana_project.util.dbTable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatListVO {
    private int chat_no;
    private String chat_title;

    public ChatListVO() {

    }
}
