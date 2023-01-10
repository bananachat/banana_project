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

    // DB에서 가져온 채팅방 리스트
    String[] chatList = null;


    public List<Object> printChatList(UserVO uservo) {
        System.out.println("ChatListLogic.printChatList() 메소드 시작");
        // 리턴값
        List<Object> lResult = new ArrayList<>();
        // 쿼리결과 기본 false
        Boolean result = false;

        // 로그 작성
        // Protocol 502 : PRT_CHATLIST, 채팅리스트 출력
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName()
                    , new LogVO(502, uservo.toString(), uservo.getUser_id()));

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

            // 쿼리문 내 파라미터 값
            pstmt.setString(1, uservo.getUser_id());

            // 쿼리문 결과
            result = pstmt.execute();

            rs = pstmt.executeQuery();
            System.out.println("쿼리값 : " + rs.toString());

            Vector<String> vChatList = new Vector<String>();

            while (rs.next()) {
                String cListNo = rs.getString("ul.chat_no");
                String cTitle = rs.getString("cl.chat_title");
                // TODO: 반환값 설정 필요
            }

            // 채팅리스 배열 생성
            chatList = new String[vChatList.size()];
            vChatList.copyInto(chatList);

        } catch (SQLException se) {
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

        lResult.add(result);
        lResult.add(chatList);

        System.out.println("쿼리 결과 : " + lResult.get(0).toString());
        System.out.println("채팅리스트 : " + lResult.get(1).toString());


        return lResult;
    }

}
