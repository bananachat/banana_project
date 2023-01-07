package banana_project.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@ToString
public class LogVO {
    private String log_date;
    private int protocol;
    private String comments;
    private String user_id;

}
