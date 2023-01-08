package banana_project.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
public class LogVO {
    private String log_date;
    private int protocol;
    private String comments;
    private String user_id;

    /**
     * 생성자
     */
    public LogVO() {
    }

    /**
     * 생성자
     *
     * @param protocol
     * @param comments
     * @param user_id
     */
    public LogVO( int protocol, String comments, String user_id) {
        this.protocol = protocol;
        this.comments = comments;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "LogVO { protocol=" + protocol + ", comments=" + comments + ", user_id=" + user_id + "}";
    }

}
