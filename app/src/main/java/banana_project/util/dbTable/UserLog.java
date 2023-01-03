package banana_project.util.dbTable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLog {
    String login_date;
    int protocol;
    String comments;
    String user_id;

    public UserLog(){}
}
