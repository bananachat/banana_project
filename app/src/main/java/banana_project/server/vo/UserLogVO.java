package banana_project.util.dbTable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLogVO {
    private String login_date;
    private int protocol;
    private String comments;
    private String user_id;

    public UserLogVO() {
    }
}
