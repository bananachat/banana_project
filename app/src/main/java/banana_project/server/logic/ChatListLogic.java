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
    String chatNo = null;         // 채팅방 번호
    String chatTitle = null;      // 채팅방 타이틀



    /**
     * [채팅방 정보 출력]
     * mResult[PROTOCOL] - 503: 채팅리스트 없음 | 502: 채팅리스트 출력,
     * mResult[CHAT_LIST] - 채팅방 VO,
     *
     * @param uservo        사용자 정보
     * @return mResult      Map_채팅방 정보
     */
    public Map<String, Object> printChatList(UserVO uservo) {
        System.out.println("ChatListLogic_printChatList() 메소드 시작");
        // NF_CHATLIST = 채팅리스트 없음
        protocol = 503;

        // 리턴값
        Map<String, Object> mResult = new HashMap<>();

        List<ChatListVO> lChatList = null;

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
                    ChatListVO clVO = new ChatListVO();
                    clVO.setChat_no(rs.getInt("ul.chat_no"));
                    clVO.setChat_title(rs.getString("cl.chat_title"));
                    lChatList.add(clVO);
                }

                // PRT_CHATLIST = 채팅리스트 출력
                protocol = 502;
            }

        } catch (SQLException se) {
            System.out.println("SQLException : " + se.getMessage());

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
        mResult.put("PROTOCOL", protocol);
        mResult.put("CHAT_LIST", lChatList);

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
     * @param userList      채팅방 사용자 리스
     * @return protocol     608: 채팅방 만들기 실패 | 606: 채팅방 만들기 성공
     */
    public int createChat(String userList) {
        System.out.println("ChatListLogic_createChat() 메소드 시작");
        // FAIL_CRE_CHAT = 채팅방 만들기 실패
        protocol = 608;

        // 리턴값 기본 0
        int result = 0;

        System.out.println("초대된 ID : " + userList);

        // tb_chat_list의 chat_no 최대값 구하기
        String sql = "select max(chat_no) from TB_CHAT_LIST";
        int maxNum = 1;    // chat_no 최대값
        try {
            // 오라클 서버와 연결
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                maxNum = rs.getInt(1) +1;
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

        System.out.println("채팅방 최대넘버 : " + maxNum);

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

        for (int i=0; i<count; i++) {
            String stUserID = st.nextToken();

            String sql3 = "insert into tb_chat_user_list (chat_no, user_id, flag) VALUES (?, ?, 1)";

            try {
                conn = dbMgr.getConnection();
                pstmt = conn.prepareStatement(sql3);

                pstmt.setInt(1, maxNum);
                pstmt.setString(2, stUserID);

                // 쿼리 동작 레코드 수
                // 성공: 1 / 실패: 0
                result *= pstmt.executeUpdate();

                if (result == 1) {
                    // ADD_FRIEND = 친구 추가 이벤트
                    protocol = 605;
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // DB 사용한 자원 반납
                try {
                    dbMgr.freeConnection(conn, pstmt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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


    // TODO: ChatLogic의 delChatContents 메소드 작성되었으므로 주석 처리
    /**
     * [채팅방 나가기]
     * 708 : 잘못된 채팅방 번호
     * 706 : 채팅방에서 나감
     *
     * @param uservo        사용자 정보
     * @param chatNo        접속한 채팅방 번호
     * @return protocol     708: 잘못된 채팅방 번호 | 706: 채팅방에서 나감
     */
    /*
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
    */

}
