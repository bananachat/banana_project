package banana_project.client.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
  public static void main(String[] args) {
    List<Map<String, String>> userList = new ArrayList<>();
    Map<String, String> map = new HashMap<>();
    String userId = "banana@email.com";
    String chatNo = "1";

    // 채팅방 아이디, 닉네임 list, map 예시
    map.put("userId", "banana@email.com");
    map.put("userNick", "바나나");
    userList.add(map);

    map = new HashMap<>();
    map.put("userId", "test@email.com");
    map.put("userNick", "테스트");
    userList.add(map);

    // 채팅방 번호, 서버스레드 list, map 예시
    map = new HashMap<>();
    map.put("chatNo", "1");
    map.put("ServerThread", "서버스레드1");
    userList.add(map);

    map = new HashMap<>();
    map.put("chatNo", "2");
    map.put("ServerThread", "서버스레드2");
    userList.add(map);

    System.out.println(userList.size());
    // 아이디, 닉네임 출력 예시
    for (int i = 0; i < userList.size(); i++) {
      if (userId.equals(userList.get(i).get("userId"))) {
        System.out.println(userList.get(i).get("userNick") + ": " + "메시지내용");
      }

      if (chatNo.equals(userList.get(i).get("chatNo"))) {
        System.out.println("결과: " + userList.get(i).get("ServerThread"));
      }
    }
  }
}
