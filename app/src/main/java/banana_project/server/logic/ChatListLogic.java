/*
 JAVA Name    :  ChatList.java
 작성자        :  강동현
 작성일        :  2023.01.10
 내용         :  채팅방 리스트 출력
 비고	     :  printChatList - 채팅방 리스트 출력
*/

package banana_project.server.logic;

import banana_project.server.util.ConstantsLog;
import banana_project.server.util.DBConnectionMgr;
import banana_project.server.vo.ChatListVO;
import banana_project.server.vo.LogVO;
import banana_project.server.vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ChatListLogic {
    // DB 연결 변수
    DBConnectionMgr dbMgr = new DBConnectionMgr();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    // 쿼리문 클래스 인스턴스화
    SqlQuarry quarry = new SqlQuarry();

    // 로그 생성
    LogLogic ll = new LogLogic();

    // 프로토콜
    int protocol = 0;

    // DB에서 가져온 채팅방 리스트
    String chatNo = null; // 채팅방 번호
    String chatTitle = null; // 채팅방 타이틀

    /**
     * [채팅방 정보 출력]
     * mResult[PROTOCOL] - 503: 채팅리스트 없음 | 502: 채팅리스트 출력,
     * mResult[CHAT_LIST] - 채팅방 VO,
     *
     * @param uservo 사용자 정보
     * @return mResult Map_채팅방 정보
     */
    public Map<String, Object> printChatList(UserVO uservo) {
        System.out.println("ChatListLogic_printChatList() 메소드 시작");
        // NF_CHATLIST = 채팅리스트 없음
        protocol = 503;

        // 리턴값
        Map<String, Object> mResult = new HashMap<>();
        List<ChatListVO> lChatList = new ArrayList<>();
        ArrayList<Integer> countList = new ArrayList<>();

        // 쿼리결과 기본 false
        Boolean result = false;

        // 로그 작성
        // Protocol 508 : BTN_CHATLIST, 채팅방목록 버튼 클릭
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(508, uservo.toString(), uservo.getUser_id()));

        System.out.println("UserVO : " + uservo.toString());
        System.out.println("로그: " + ll.toString());

        // 채팅방 호출 쿼리문
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ul.chat_no, cl.chat_title FROM TB_CHAT_USER_LIST ul, TB_CHAT_LIST cl ");
        sql.append("    WHERE ul.chat_no = cl.chat_no AND ul.user_id = ? ");
        sql.append("    ORDER BY ul.chat_no ");

        try {
            // 오라클 서버와 연결
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            // 쿼리문 내 파라미터 값 (사용자ID)
            pstmt.setString(1, uservo.getUser_id());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                // TODO: 리스트 출력 이슈
                ChatListVO clVO = new ChatListVO();
                clVO.setChat_no(rs.getInt(1));
                //채팅 타이틀이 22자 이상일 경우 타이틀 생략처리
                if(rs.getString(2).length() > 25)
                    clVO.setChat_title(rs.getString(2).substring(0, 25) + "...");
                else
                    clVO.setChat_title(rs.getString(2));
                lChatList.add(clVO);
                String[] temp = rs.getString(2).split(",");
                countList.add(temp.length);
                // PRT_CHATLIST = 채팅리스트 출력
                protocol = 502;
            }

        } catch (SQLException se) {
            System.out.println("SQLException : " + se.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            if (conn == null) { // DB 접속 실패 시
                protocol = 800;
            }
            if (lChatList == null) {
                System.out.println("친구리스트가 없습니다");
            }
        } finally {
            // DB 사용한 자원 반납
            try {
                dbMgr.freeConnection(conn, pstmt, rs);
            } catch (Exception e) {
                // 디버깅
                e.printStackTrace();
            }
        }

        // 메소드 반환값
        mResult.put("PROTOCOL", protocol);
        mResult.put("CHAT_LIST", lChatList);
        mResult.put("COUNT", countList);

        System.out.println("프로토콜 : " + mResult.get("PROTOCOL"));
        System.out.println("채팅방 정보 : " + mResult.get("CHAT_LIST"));
        System.out.println("ChatListLogic_printChatList() 메소드 종료");

        return mResult;
    } // end of printChatList (채팅방 정보 출력)

    /**
     * [채팅방 생성]
     * 608: 채팅방 만들기 실패
     * 606: 채팅방 만들기 성공
     *
     * @param userList 채팅방 사용자 리스
     * @return protocol 608: 채팅방 만들기 실패 | 606: 채팅방 만들기 성공
     */
    public String createChat(String userList) {
        System.out.println("ChatListLogic_createChat() 메소드 시작");
        // FAIL_CRE_CHAT = 채팅방 만들기 실패
        protocol = 608;

        // 리턴값 기본 0
        int result = 0;

        System.out.println("초대된 닉네임 : " + userList);

        // tb_chat_list의 chat_no 최대값 구하기
        String sql = "select max(chat_no) from TB_CHAT_LIST";
        int maxNum = 1; // chat_no 최대값

        try {
            // 오라클 서버와 연결
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                maxNum = rs.getInt(1) + 1;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            if (conn == null) { // DB 접속 실패 시
                protocol = 800;
            }
        } finally {
            // DB 사용한 자원 반납
            try {
                dbMgr.freeConnection(conn, pstmt, rs);
            } catch (Exception e) {
                // 디버깅
                e.printStackTrace();
            }
        }

        System.out.println("새 채팅방 넘버 : " + maxNum);

        // TB_CHAT_LIST 데이터 추가
        String sql2 = "INSERT INTO TB_CHAT_LIST (chat_no, chat_title) VALUES ( ?, ? )";

        try {
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql2);

            pstmt.setInt(1, maxNum);
            pstmt.setString(2, userList);

            // 쿼리 동작 레코드 수
            // 성공: 1 / 실패: 0
            result = pstmt.executeUpdate();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            if (conn == null) { // DB 접속 실패 시
                protocol = 800;
            }
        } finally {
            // DB 사용한 자원 반납
            try {
                dbMgr.freeConnection(conn, pstmt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // tb_chat_user_list 테이블에 insert
        int result2 = 0;
        StringTokenizer st = new StringTokenizer(userList, ", ");
        int count = st.countTokens();
        System.out.println("사용자 수 : " + count);

        for (int i = 0; i < count; i++) {
            String stUserNick = st.nextToken();
            String userID = "";

            String selID = "SELECT user_id FROM tb_user WHERE user_nickname = ? "; // 사용자 ID로 반

            try {
                conn = dbMgr.getConnection();
                pstmt = conn.prepareStatement(selID);

                pstmt.setString(1, stUserNick);

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    userID = rs.getString(1);
                }

            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // DB 사용한 자원 반납
                try {
                    dbMgr.freeConnection(conn, pstmt, rs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("사용자 ID : " + userID);

            // StringBuilder sql3 = new StringBuilder();
            // insert into tb_chat_user_list (chat_no, user_id, flag)
            // VALUES (?, (select user_id from tb_user where user_nickname = ?), 1);
            String sql3 = "INSERT INTO tb_chat_user_list (chat_no, user_id, flag) VALUES ( ?, ? , 1) ";

            try {
                conn = dbMgr.getConnection();
                pstmt = conn.prepareStatement(sql3);

                pstmt.setInt(1, maxNum);
                pstmt.setString(2, userID);

                // 쿼리 동작 레코드 수
                // 성공: 1 / 실패: 0
                if (i == 0) {
                    result2 = pstmt.executeUpdate();
                } else {
                    result2 *= pstmt.executeUpdate();
                }

            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                if (conn == null) { // DB 접속 실패 시
                    protocol = 800;
                }
            } finally {
                // DB 사용한 자원 반납
                try {
                    dbMgr.freeConnection(conn, pstmt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("쿼리1 : " + result + " | 쿼리2 : " + result2);
        }

        if ((result * result2) == 1) {
            // CREATE_CHAT = 채팅방 만들기 성공
            protocol = 606;
        }

        // TODO: 만약에 모든 쿼리가 정상적으로 안될 경우, 추가한 데이터들 삭제 로직 작성 필요
        System.out.println("결과 프로토콜 : " + protocol);

        String returnVal = protocol + "#" + maxNum;
        return returnVal;
    } // end of createChat (채팅방 생성)

    /**
     * [채팅방 타이틀 변경]
     * 0: 타이틀 변경 실패
     * 1: 타이틀 변경 성공
     *
     * @param chatNo 채팅방 번호
     * @return  result
     */
    public int updChatTitle(int chatNo) {
        // 리턴값 기본 0
        int result = 0;
        int seResult = 0;

        String userId = "";
        Vector<String> vUserIDs = new Vector<String>();
        String chatTitle = "";

        System.out.println("채팅방 번호 : " + chatNo);

        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT u.user_nickname FROM     ");
        sql.append("    (SELECT user_id FROM tb_chat_user_list WHERE chat_no = ? ) ul   ");
        sql.append("        , (SELECT user_id, user_nickname FROM tb_user) u    ");
        sql.append("    where ul.user_id = u.user_id    ");

        try {
            // 오라클 서버와 연결
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            pstmt.setInt(1, chatNo);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                userId = rs.getString(1);
                vUserIDs.add(userId);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // DB 사용한 자원 반납
            try {
                dbMgr.freeConnection(conn, pstmt, rs);
            } catch (Exception e) {
                // 디버깅
                e.printStackTrace();
            }
        }

        if (vUserIDs.size() > 0) {
            seResult = 1;
        }

        if (seResult == 1) {
            for (int i = 0; i < vUserIDs.size() -1; i++) {
                chatTitle += vUserIDs.get(i) + ", ";
            }
            chatTitle += vUserIDs.get(vUserIDs.size() -1);

            System.out.println("변경된 채팅타이틀 : " + chatTitle);

            String sql2 = "UPDATE tb_chat_list SET chat_title = ? WHERE chat_no = ?";
            int quResult = 0;

            try {
                // 오라클 서버와 연결
                conn = dbMgr.getConnection();
                pstmt = conn.prepareStatement(sql2);

                pstmt.setString(1, chatTitle);
                pstmt.setInt(2, chatNo);

                quResult = pstmt.executeUpdate();

                if (quResult == 1) {
                    result = 1;
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // DB 사용한 자원 반납
                try {
                    dbMgr.freeConnection(conn, pstmt, rs);
                } catch (Exception e) {
                    // 디버깅
                    e.printStackTrace();
                }
            }

            System.out.println("새 채팅방 타이틀 : " + chatTitle);
        }

        return result;
    }


    // 단위테스트
    public static void main(String[] args) {
        ChatListLogic cl = new ChatListLogic();
        UserVO uVO = new UserVO();
        FriendLogic fl = new FriendLogic();

        uVO.setUser_id("hong@mail.com");

        int i = 0;
        Map<String, Object> map = new HashMap<String, Object>();

        String userList = "banana1, 장군님이나가신다1, 자고싶어2";

        // 채팅방 출력
        map = cl.printChatList(uVO);

        // 채팅방 만들기
        // i = cl.createChat(userList);
    }

}
