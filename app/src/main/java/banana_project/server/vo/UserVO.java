package banana_project.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserVO {
    private String user_id;
    private String user_pw;
    private String user_name;
    private String user_hp;
    private String user_nickname;
    private int fail_cnt;
    private String ins_date;
    private String upd_date;
    private String login_date;
    private String salt;
    private int status;

    public UserVO() {

    }
}
