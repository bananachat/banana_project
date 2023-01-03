package banana_project.util.dbTable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class User {
    String user_id;
    String user_pw;
    String user_name;
    String user_hp;
    String user_nickname;
    int fail_cnt;
    String ins_date;
    String upd_date;
    String login_date;

}
