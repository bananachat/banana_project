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
import banana_project.server.vo.LogVO;
import banana_project.server.vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ChatListLogic {
    // DB 연결 변수
    DBConnectionMgr dbMgr = new DBConnectionMgr();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    // 로그 생성
    LogLogic ll = new LogLogic();

    // 프로토콜
    int protocol = 0;

    // DB에서 가져온 채팅방 리스트
    String[] chatNo = null;         // 채팅방 번호
    String[] chatTitle = null;      // 채팅방 타이틀


    /**
     * 채팅방 정보 출력
     * lResult[0] : 결과 프토토콜(존재: 502 / 미존재: 503),
     * lResult[1] : 채팅방 번호,
     * lResult[1] : 채팅방 타이틀,
     *
     * @param uservo        사용자 정보
     * @return              채팅방 리스트
     */
    public List<Object> printChatList(UserVO uservo) {
        System.out.println("ChatListLogic.printChatList() 메소드 시작");
        // 리턴값
        List<Object> lResult = new ArrayList<>();
        // 쿼리결과 기본 false
        Boolean result = false;


        // 로그 작성
        // Protocol 508 : BTN_CHATLIST, 채팅방목록 버튼 클릭
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName()
                    , new LogVO(508, uservo.toString(), uservo.getUser_id()));

        System.out.println("UserVO : " + uservo.toString());
        System.out.println("로그: " + ll.toString());

        // 채팅방 호출 쿼리문
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ul.chat_no, cl.chat_title ");
        sql.append("FROM TB_CHAT_USER_LIST ul, TB_CHAT_LIST cl");
        sql.append("WHERE ul.chat_no = cl.chat_no ");
        // 빈문자열이 아닌것도 필터링
        if (uservo.getUser_id()!= null && uservo.getUser_id().length()>0) {
            sql.append("AND ul.user_id = ? ");
        }

        try {
            // 오라클 서버와 연결
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            // 쿼리문 내 파라미터 값 (사용자ID)
            pstmt.setString(1, uservo.getUser_id());

            // 쿼리문 결과
            result = pstmt.execute();
            if (result==true){
                // 채팅리스트 존재 (PRT_CHATLIST)
                protocol=502;
            } else {
                // 채팅리스트 미존재 (NF_CHATLIST)
                protocol=503;
            }

            // 쿼리 반환값
            rs = pstmt.executeQuery();
            System.out.println("쿼리값 : " + rs.toString());

            Vector<String> vChatNo = new Vector<String>();
            Vector<String> vChatTitle = new Vector<String>();

            while (rs.next()) {
                String cListNo = rs.getString("ul.chat_no");
                String cTitle = rs.getString("cl.chat_title");
                vChatNo.add(cListNo);
                vChatTitle.add(cTitle);
            }

            // 채팅방 번호
            chatNo = new String[vChatNo.size()];
            vChatNo.copyInto(chatNo);
            // 채팅방 타이틀
            chatTitle = new String[vChatTitle.size()];
            vChatTitle.copyInto(chatTitle);

        } catch (SQLException se) {
            // 채팅리스트 미존재 (NF_CHATLIST)
            protocol=503;

            System.out.println("SQLException : " + se.getMessage());
            System.out.println("쿼리문 : " + sql.toString());

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

        // 메소드 반환값
        lResult.add(protocol);
        lResult.add(chatNo);
        lResult.add(chatTitle);

        System.out.println("쿼리 결과 : " + lResult.get(0).toString());
        System.out.println("채팅방 번호 : " + lResult.get(1).toString());
        System.out.println("채팅방 타이틀 : " + lResult.get(2).toString());
        System.out.println("ChatListLogic.printChatList() 메소드 종료");

        return lResult;
    } // end of printChatList (채팅방 정보 출력)



    // TODO: 채팅방 생성 메소드 작성 중
    public int createChat() {
        System.out.println("ChatListLogic.createChat() 메소드 시작");
        // 리턴값 기본 -1
        int result = -1;




        return result;
    } // end of createChat (채팅방 생성)



    // TODO: 채팅방 나가기 메소드 작성 중
    public int removeChat() {
        System.out.println("ChatListLogic.removeChat() 메소드 시작");
        // 리턴값 기본 -1
        int result = -1;




        return result;
    } // end of removeChat (채팅방 나가기)

}
