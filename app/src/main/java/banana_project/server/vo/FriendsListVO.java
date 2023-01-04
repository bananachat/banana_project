package banana_project.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FriendsListVO {
    private String user_id;
    private String f_id;

    public FriendsListVO() {

    }
}
