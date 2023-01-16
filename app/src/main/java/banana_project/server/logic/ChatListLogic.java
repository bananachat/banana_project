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
import java.util.StringTokenizer;
import java.util.Vector;

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
    String[] chatNo = null;         // 채팅방 번호
    String[] chatTitle = null;      // 채팅방 타이틀



    /**
     * [채팅방 정보 출력]
     * lResult[0] : 503: 채팅리스트 없음 | 502: 채팅리스트 출력,
     * lResult[1] : 채팅방 번호,
     * lResult[1] : 채팅방 타이틀,
     *
     * @param uservo        사용자 정보
     * @return              채팅방 리스트
     */
    public List<Object> printChatList(UserVO uservo) {
        System.out.println("ChatListLogic.printChatList() 메소드 시작");
        // NF_CHATLIST = 채팅리스트 없음
        protocol = 503;

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

            if (result) {
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

                // PRT_CHATLIST = 채팅리스트 출력
                protocol = 502;
            }

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

        // 메소드 반환값
        lResult.add(protocol);
        lResult.add(chatNo);
        lResult.add(chatTitle);

        System.out.println("프로토콜 : " + lResult.get(0).toString());
        System.out.println("채팅방 번호 : " + lResult.get(1).toString());
        System.out.println("채팅방 타이틀 : " + lResult.get(2).toString());
        System.out.println("ChatListLogic.printChatList() 메소드 종료");

        return lResult;
    } // end of printChatList (채팅방 정보 출력)


    /**
     * [채팅방 생성]
     * 608: 채팅방 만들기 실패
     * 606: 채팅방 만들기 성공
     *
     * @param userList      채팅방 사용자 리스
     * @return protocol     608: 채팅방 만들기 실패 | 606: 채팅방 만들기 성공
     */
    public int createChat(String userList) {
        System.out.println("ChatListLogic.createChat() 메소드 시작");
        // FAIL_CRE_CHAT = 채팅방 만들기 실패
        protocol = 608;

        // 리턴값 기본 0
        int result = 0;

        System.out.println("초대된 ID : " + userList);

        // tb_chat_list의 chat_no 최대값
        String sql = "select max(chat_no) from tb_chat_list";
        String num = "";    // chat_no 최대값
        try {
            // 오라클 서버와 연결
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                num = rs.getString(1);
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

        int maxNum = Integer.parseInt(num)+1;
        maxNum += 1;

        // 쿼리문에 사용할 변수
        String table = "TB_CHAT_LIST";
        String[] columns = { "chat_no", "chat_title" };
        String[] values = { Integer.toString(maxNum), userList };

        // INSERT INTO TB_CHAT_LIST (columns) VALUES values
        result = quarry.quInsert(table, columns, values);


        // tb_chat_user_list 테이블에 insert
        int result2 = 0;
        StringTokenizer st = new StringTokenizer(userList, ",");

        for (int i=0; i<st.countTokens(); i++) {
            String stUserID = st.nextToken();
            columns = new String[] { "chat_no", "user_id", "flag" };
            values = new String[] { Integer.toString(maxNum), stUserID, "1"};

            // 마지막 쿼리문만 되면 1이 되는 증상이 있음
            result2 = quarry.quInsert("tb_chat_user_list", columns, values);
        }

        if ((result*result2) == 1) {
            // CREATE_CHAT = 채팅방 만들기 성공
            protocol = 606;
        }

        return protocol;
    } // end of createChat (채팅방 생성)



    // TODO: 채팅방 입장 메소드 작성 중
    public int enterChat() {
        System.out.println("ChatListLogic.enterChat() 메소드 시작");
        // 리턴값 기본 -1
        int result = -1;




        return result;
    } // end of enterChat (채팅방 입장)


    /**
     * [채팅방 나가기]
     * 708 : 잘못된 채팅방 번호
     * 706 : 채팅방에서 나감
     *
     * @param uservo        사용자 정보
     * @param chatNo        접속한 채팅방 번호
     * @return protocol     708: 잘못된 채팅방 번호 | 706: 채팅방에서 나감
     */
    public int removeChat(UserVO uservo, int chatNo) {
        System.out.println("ChatListLogic.removeChat() 메소드 시작");
        // WRONG_NUM = 잘못된 채팅방 번호
        protocol = 708;

        // 리턴값 기본 0
        int result = 0;

        System.out.println("사용자 ID : " + uservo.getUser_id());
        System.out.println("채팅방 번호 : " + chatNo);

        String table = "TB_CHAT_USER_LIST";
        String[] columns = { "flag" };
        String[] chgValues = { "1" };
        String whereClause = "user_id = " + uservo.getUser_id() + " AND chat_no = " + chatNo;

        // 해당 채팅방이 DB에 없는지 확인
        String selQuarry = " SELECT * FROM " + table + " WHERE " + whereClause;
        boolean isOk = quarry.quSelect(selQuarry);

        if (isOk) {
            // 접속 flag 퇴장으로 수정
            result = quarry.quUpdate(table, columns, chgValues, whereClause);

            if (result == 1) {
                // EXIT_MEM = 채팅방에서 나감
                protocol = 706;
            }
        }

        return protocol;
    } // end of removeChat (채팅방 나가기)

}
