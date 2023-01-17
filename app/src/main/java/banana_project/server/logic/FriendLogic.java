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
import java.util.*;

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
     * [사용자 친구리스트 출력]
     * [0]_PROTOCOL - 604: 친구 검색 결과가 없음 | 607: 친구 검색 존재,
     * [1]_Friends - 친구리스트
     *
     * @param uservo        사용자 정보
     * @return lResult      [0]_Protocol/[1]_Friends
     */
    public List<Object> printFriend(UserVO uservo) {
        System.out.println("FriendLogic.printFriend() 메소드 시작");
        // NF_RESULT = 친구 검색 결과가 없음
        protocol = 604;

        // 리턴값
        List<Object> lResult = new ArrayList<>();

        Vector<String> vfList = new Vector<String>();
        String friends = "";


        // 쿼리결과 기본 false
        Boolean result = false;

        // 로그 작성
        // Protocol 500 : PRT_FRDLIST, 친구목록 출력
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName()
                    , new LogVO(500, uservo.toString(), uservo.getUser_id()));

        System.out.println("UserVO : " + uservo.toString());
        System.out.println("로그: " + ll.toString());

        // 친구리스트 호출 쿼리문
        String sql = "";
        sql = "SELECT f_id FROM TB_FRIENDS_LIST WHERE user_id = ?";

        try {
            // 오라클 서버와 연결
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql);

            // 쿼리문 내 파라미터 값
            pstmt.setString(1, uservo.getUser_id());

            // 쿼리문 결과
            result = pstmt.execute();       // 호출: true, 실패: false

            // 조회 성공 시
            if (result) {
                rs = pstmt.executeQuery();


                // 친구리스트 쿼리결과값
                while (rs.next()) {
                    String f = rs.getString("f_id");
                    vfList.add(f);
                }

                // EXIST_FRIEND = 친구 검색 존재
                protocol = 607;
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

        for(int i = 0; i < vfList.size()-1; i++) {
            friends += vfList.get(i) + "#";
        }
        friends += vfList.get(vfList.size()-1);

        lResult.add(protocol);
        lResult.add(friends);

        System.out.println("프로토콜 : " + lResult.get(0).toString());
        System.out.println("친구리스트 : " + lResult.get(1).toString());
        System.out.println("FriendLogic.printFriend() 메소드 종료");

        return lResult;
    } // end of printFriend (사용자 친구리스트 출력)



    /**
     * [친구 ID로 조회]
     * vResult."PROTOCOL" - 604: 친구 검색 결과가 없음 | 607: 친구 검색 존재,
     * vResult."F_ID      - 친구리스트
     *
     * @param uservo        사용자 정보
     * @param friendID      조회할 친구 ID
     * @return vResult      친구 조회 결과
     */
    public List<Object>  findFriend(UserVO uservo, String friendID) {
        System.out.println("FriendLogic_printFriend() 메소드 시작");
        // NF_RESULT = 친구 검색 결과가 없음
        protocol = 604;

        // 리턴값
        List<Object> lResult = new ArrayList<>();
        String fList = "";

        // 쿼리결과 기본 false
        Boolean result = false;

        // 로그 작성
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName()
                    , new LogVO(600, uservo.toString(), uservo.getUser_id()));

        System.out.println("UserVO : " + uservo.toString() + "\nfriendName : " + friendID);

        // 친구 조회 호출 쿼리문
        String sql = "SELECT f_id FROM TB_FRIENDS_LIST WHERE user_id = ? AND f_id = ?";

        try {
            // 오라클 서버와 연결
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql);

            // 쿼리문 내 파라미터 값
            pstmt.setString(1, uservo.getUser_id());
            pstmt.setString(2, friendID);

            result = pstmt.execute();

            // 조회 결과 있을 경우
            if (result) {
                // 쿼리문 결과
                rs = pstmt.executeQuery();       // 호출: true, 실패: false

                // 조회 성공 시
                while(rs.next()) {
                    fList = rs.getString("f_id");

                    // EXIST_FRIEND = 친구 검색 존재
                    protocol = 607;
                }
            }

        } catch (SQLException se) {
            System.out.println("SQLException : " + se.getMessage());
            System.out.println("쿼리문 : " + sql);
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

        lResult.add(protocol);
        lResult.add(fList);

        System.out.println("프로토콜 : " + lResult.get(0));
        System.out.println("친구리스트 : " + lResult.get(1));
        System.out.println("FriendLogic.printFriend() 메소드 종료");

        return lResult;
    } // end of findFriend (친구 이름으로 조회)



    /**
     * [선택한 ID를 친구 추가]
     * 607 : 친구 검색 존재,
     * 605  : 친구 추가 이벤트
     *
     * @param uservo            사용자 정보
     * @param selectID          선택한 계정ID
     * @return protocol         607:친구 검색 존재 | 605: 친구 추가 이벤트
     */
    public int addFriend(UserVO uservo, String selectID) {
        System.out.println("FriendLogic.addFriend() 메소드 시작");
        // EXIST_FRIEND = 친구 검색 존재
        protocol = 607;

        int isOk = -1;
        int result = 0;

        System.out.println("사용자 ID : " + uservo.getUser_id());
        System.out.println("선택한 계정ID : " + selectID);


        // 해당 친구가 DB에 없는지 확인
        String selQuarry = "SELECT * FROM TB_FRIENDS_LIST WHERE USER_ID = ? AND F_ID = ?";

        try {
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(selQuarry);

            pstmt.setString(1, uservo.getUser_id());
            pstmt.setString(2, selectID);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                isOk = 1;
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

        System.out.println("select 쿼리 성공유무: " + isOk);

        if (isOk == -1) {
            // 해당 ID 친구 추가

            String sql = "INSERT INTO TB_FRIENDS_LIST (USER_ID, F_ID) VALUES (?, ?)";

            try {
                conn = dbMgr.getConnection();
                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, uservo.getUser_id());
                pstmt.setString(2, selectID);

                // 쿼리 동작 레코드 수
                // 성공: 1 / 실패: 0
                result = pstmt.executeUpdate();

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

        return protocol;
    } // end of addFriend (친구 추가)



    /**
     * [선택한 ID를 친구 삭제]
     * 604: 검색 결과가 없음
     * 511: 친구 삭제
     *
     * @param uservo            사용자 정보
     * @param selectID          선택한 계정ID
     * @return protocol         604: 검색 결과가 없음 | 511: 친구 삭제
     */
    public int delFriend(UserVO uservo, String selectID) {
        System.out.println("FriendLogic.addFriend() 메소드 시작");
        // NF_RESULT = 검색 결과가 없음
        protocol = 604;

        // 리턴값 기본 0
        int isOk = -1;
        int result = 0;

        System.out.println("사용자 ID : " + uservo.getUser_id());
        System.out.println("선택한 계정ID : " + selectID);

        // 쿼리문에 사용할 변수
        String table = " TB_FRIENDS_LIST ";
        String whereClause = " USER_ID = " + uservo.getUser_id() + " AND F_id" + selectID;

        // 해당 친구가 DB에 없는지 확인
        String selQuarry = "SELECT * FROM TB_FRIENDS_LIST WHERE USER_ID = ? AND F_ID = ?";

        try {
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(selQuarry);

            pstmt.setString(1, uservo.getUser_id());
            pstmt.setString(2, selectID);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                isOk = 1;
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

        System.out.println("select 쿼리 성공유무: " + isOk);

        if (isOk == 1) {
            // 해당 ID 친구 삭제

            String sql = "DELETE FROM TB_FRIENDS_LIST WHERE USER_ID = ? AND F_ID = ?";

            try {
                conn = dbMgr.getConnection();
                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, uservo.getUser_id());
                pstmt.setString(2, selectID);

                // 쿼리 동작 레코드 수
                // 성공: 1 / 실패: 0
                result = pstmt.executeUpdate();

                if (result == 1) {
                    // DEL_FRIEND = 친구 삭제
                    protocol = 511;
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

        return protocol;
    } // end of delFriend (친구 삭제)



    // TODO: "친구 정보 조회" 기능 추가 시 작성 필요
}
