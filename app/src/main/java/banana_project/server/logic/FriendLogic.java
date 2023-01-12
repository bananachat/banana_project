/*
 JAVA Name    :  FriendLogic.java
 작성자        :  강동현
 작성일        :  2023.01.10
 내용         :  친구리스트 호출
 비고	     :  printFriend - 사용자 친구 리스트 출력
                findFriend - 친구 ID로 조회
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

public class FriendLogic {
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

    // DB에서 가져온 친구리스트
    String[] friendsList = null;


    /**
     * 사용자 친구리스트 출력,
     * lResult[0] : 쿼리문 결과(false/true),
     * lResult[1] : 친구리스트
     *
     * @param uservo        사용자 정보
     * @return lResult      친구리스트
     */
    public List<Object> printFriend(UserVO uservo) {
        System.out.println("FriendLogic.printFriend() 메소드 시작");
        // 리턴값
        List<Object> lResult = new ArrayList<>();
        // 쿼리결과 기본 false
        Boolean result = false;

        // 로그 작성
        // Protocol 500 : PRT_FRDLIST, 친구목록 출력
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName()
                    , new LogVO(500, uservo.toString(), uservo.getUser_id()));

        System.out.println("UserVO : " + uservo.toString());
        System.out.println("로그: " + ll.toString());


        // 친구리스트 호출 쿼리문
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT f_id ");
        sql.append("FROM TB_FRIENDS_LIST ");
        if (uservo.getUser_id()!= null && uservo.getUser_id().length()>0) {              // 빈문자열이 아닌것도 필터링
            sql.append("WHERE user_id = ? ");
        }

        try {
            // 오라클 서버와 연결
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            // 쿼리문 내 파라미터 값
            pstmt.setString(1, uservo.getUser_id());

            // 쿼리문 결과
            result = pstmt.execute();       // 호출: true, 실패: false

            rs = pstmt.executeQuery();
            System.out.println("쿼리값 : " + rs.toString());

            // 친구리스트 쿼리결과값
            Vector<String> vFList = new Vector<String>();

            while(rs.next()) {
                String fList = rs.getString("f_id");
                vFList.add(fList);
            }

            // 친구리스트 배열 생성
            friendsList = new String[vFList.size()];
            vFList.copyInto(friendsList);

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
        lResult.add(friendsList);

        System.out.println("쿼리 결과 : " + lResult.get(0).toString());
        System.out.println("친구리스트 : " + lResult.get(1).toString());
        System.out.println("FriendLogic.printFriend() 메소드 종료");

        return lResult;
    } // end of printFriend (사용자 친구리스트 출력)


    /**
     * 친구 ID로 조회,
     * lResult[0] : 쿼리문 결과(false/true),
     * lResult[1] : 친구리스트
     *
     * @param uservo        사용자 정보
     * @param friendID      조회할 친구 ID
     * @return lResult      친구 조회 결과
     */
    public List<Object> findFriend(UserVO uservo, String friendID) {
        System.out.println("FriendLogic.printFriend() 메소드 시작");
        // 리턴값
        List<Object> lResult = new ArrayList<>();
        // 쿼리결과 기본 false
        Boolean result = false;

        // 로그 작성
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName()
                    , new LogVO(600, uservo.toString(), uservo.getUser_id()));

        System.out.println("UserVO : " + uservo.toString() + " | friendName : " + friendID);
        System.out.println("로그: " + ll.toString());


        // 친구 조회 호출 쿼리문
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT f_id ");
        sql.append("FROM TB_FRIENDS_LIST ");
        if (uservo.getUser_id()!= null && uservo.getUser_id().length()>0) {              // 빈문자열이 아닌것도 필터링
            sql.append("WHERE user_id = ? ");
        }
        if (friendID!= null && friendID.length()>0) {              // 빈문자열이 아닌것도 필터링
            sql.append("    AND f_id = ? ");
        }

        try {
            // 오라클 서버와 연결
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            // 쿼리문 내 파라미터 값
            pstmt.setString(1, uservo.getUser_id());
            pstmt.setString(2, friendID);

            // 쿼리문 결과
            result = pstmt.execute();       // 호출: true, 실패: false

            rs = pstmt.executeQuery();
            System.out.println("쿼리문 : " + rs.toString());

            // 친구리스트 쿼리결과값
            Vector<String> vFList = new Vector<String>();

            while(rs.next()) {
                String fList = rs.getString("f_id");
                vFList.add(fList);
            }

            // 친구리스트 배열 생성
            friendsList = new String[vFList.size()];
            vFList.copyInto(friendsList);

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
        lResult.add(friendsList);

        System.out.println("쿼리 결과 : " + result);
        System.out.println("친구리스트 : " + friendsList);
        System.out.println("FriendLogic.printFriend() 메소드 종료");

        return lResult;
    } // end of findFriend (친구 이름으로 조회)



    /**
     * 선택한 ID를 친구추가
     * -1 : 친구 추가 실패
     * 1  : 친구 추가 성공
     *
     * @param uservo            사용자 정보
     * @param selectID          선택한 계정ID
     * @return result
     */
    public int addFriend(UserVO uservo, String selectID) {
        // 로그 출력

        System.out.println("FriendLogic.addFriend() 메소드 시작");
        // 리턴값 기본 -1
        int result = -1;

//        // 선택한 아이디 리스트가 존재하는지 확인
//        if (selectID==null) {
//            // 선택한 계정이 없을 경우
//            // NF_RESULT = 604 - 검색 결과가 없음
//            System.out.println("선택한 아이디가 없음");
//
//            //TODO: 널값일 경우
//        }

        System.out.println("사용자 ID : " + uservo.getUser_id());
        System.out.println("선택한 계정ID : " + selectID);

        /*
        // 친구 추가 쿼리문
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO TB_FRIENDS_LIST ");
        sql.append("(user_id, f_id) ");
        sql.append("VALUES (?, ?) ");

        try {
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            // 쿼리문 내 파라미터 값
            pstmt.setString(1, uservo.getUser_id());
            pstmt.setString(2, selectID);

            // 쿼리문 결과<-레코드 개수(1개)
            result = pstmt.executeUpdate();
            if (result == 1) {
                // 정상동작 (ADD_FRIEND = 605;   	//친구 추가 이벤트)
                protocol=605;
            } else {
                // 비정상 동작 TODO:친구추가 실패 프로토콜 설정 필요
//                protocol=??
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
                // 디버깅
                e.printStackTrace();
            }
        }

         */


        String table = "TB_FRIENDS_LIST";                   // 테이블 명
        String[] columns = {"user_id", "f_id"};             // 컬럼 배열 (사용자ID, 친구ID)
        String[] values = {uservo.getUser_id(), selectID};  // 값 배열 (사용자ID, 선택한ID)


        result = quarry.quInsert(table, columns, values);

        return result;
    } // end of addFriend (친구 추가)


    // TODO: 추후 친구 삭제 로직 작성
}
