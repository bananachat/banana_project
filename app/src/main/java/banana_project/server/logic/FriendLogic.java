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
     * @param uservo 사용자 정보
     * @return lResult [0]_Protocol/[1]_Friends
     */
    public List<Object> printFriend(UserVO uservo) {
        System.out.println("FriendLogic.printFriend() 메소드 시작");
        // NF_RESULT = 친구 검색 결과가 없음
        protocol = 604;

        // 리턴값
        List<Object> lResult = new ArrayList<>();

        Vector<String> vfList = new Vector<String>();
        String friends = "";

        // 로그 작성
        // Protocol 500 : PRT_FRDLIST, 친구목록 출력
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(500, uservo.toString(), uservo.getUser_id()));

        System.out.println("UserVO : " + uservo.toString());
        System.out.println("로그: " + ll.toString());

        // 친구리스트 호출 쿼리문
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ur.user_nickname FROM     ");
        sql.append("        (SELECT f_id FROM tb_friends_list WHERE user_id= ? ) fl   ");
        sql.append("        , (SELECT user_id, user_nickname FROM tb_user) ur    ");
        sql.append("WHERE fl.f_id = ur.user_id  ");

        try {
            // 오라클 서버와 연결
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            // 쿼리문 내 파라미터 값
            pstmt.setString(1, uservo.getUser_id());

            rs = pstmt.executeQuery();

            // 친구리스트 쿼리결과값 있을 경우
            while (rs.next()) {
                String f = rs.getString(1);
                vfList.add(f);

                // EXIST_FRIEND = 친구 검색 존재
                protocol = 607;
            }

            for (int i = 0; i < vfList.size() - 1; i++) {
                friends += vfList.get(i) + "#";
            }
            friends += vfList.get(vfList.size() - 1);

        } catch (SQLException se) {
            se.printStackTrace();
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

        lResult.add(protocol);
        lResult.add(friends);

        System.out.println("프로토콜 : " + lResult.get(0).toString());
        System.out.println("친구리스트 : " + lResult.get(1).toString());
        System.out.println("FriendLogic.printFriend() 메소드 종료");

        return lResult;
    } // end of printFriend (사용자 친구리스트 출력)

    /**
     * [친구 닉네임으로 조회]
     * vResult."PROTOCOL" - 604: 친구 검색 결과가 없음 | 607: 친구 검색 존재,
     * vResult."F_ID - 친구리스트
     *
     * @param uservo     사용자 정보
     * @param friendNick 조회할 친구 닉네임
     * @return vResult 친구 조회 결과
     */
    public List<Object> findFriend(UserVO uservo, String friendNick) {
        System.out.println("FriendLogic_printFriend() 메소드 시작");
        // NF_RESULT = 친구 검색 결과가 없음
        protocol = 604;

        // 리턴값
        List<Object> lResult = new ArrayList<>();
        String fList = "";

        // 로그 작성
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(600, uservo.toString(), uservo.getUser_id()));

        System.out.println("UserVO : " + uservo.toString() + "\nfriendName : " + friendNick);

        // 친구 조회 호출 쿼리문
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ur.user_nickname FROM     ");
        sql.append("        (SELECT f_id FROM tb_friends_list WHERE user_id= ? ) fl   ");
        sql.append("        , (SELECT user_id, user_nickname FROM tb_user) ur    ");
        sql.append("WHERE fl.f_id = ur.user_id  ");
        sql.append("AND ur.user_nickname = ? ");

        try {
            // 오라클 서버와 연결
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            // 쿼리문 내 파라미터 값
            pstmt.setString(1, uservo.getUser_id());
            pstmt.setString(2, friendNick);

            // 쿼리문 결과
            rs = pstmt.executeQuery(); // 호출: true, 실패: false

            // 조회 성공 시
            while (rs.next()) {
                fList = rs.getString(1);

                // EXIST_FRIEND = 친구 검색 존재
                protocol = 607;
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
     * [사용자 닉네임으로 조회 - 모든 사용자 중]
     * vResult."PROTOCOL" - 604: 친구 검색 결과가 없음 | 607: 친구 검색 존재,
     * vResult."friendName" - 친구리스트
     *
     * @param userId    사용자 ID
     * @param friendNick 조회할 친구 ID
     * @return vResult 친구 조회 결과
     */
    public List<Object> findAllUser(String userId, String friendNick) {
        System.out.println("FriendLogic_printFriend()<-모든사용자중 메소드 시작");
        // NF_RESULT = 친구 검색 결과가 없음
        protocol = 604;

        // 리턴값
        List<Object> lResult = new ArrayList<>();
        String userNick = "";
        String fList = "";

        // 로그 작성
        // ll.writeLog(ConstantsLog.ENTER_LOG,
        // Thread.currentThread().getStackTrace()[1].getMethodName()
        // , new LogVO(600, uservo.toString(), uservo.getUser_id()));

        System.out.println("friendName : " + friendNick);

        String sql = "SELECT USER_NICKNAME FROM TB_USER WHERE USER_ID = ?";

        // 사용자의 닉네임 구하기
        try {
            // 오라클 서버와 연결
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql);

            // 쿼리문 내 파라미터 값
            pstmt.setString(1, userId);

            // 쿼리문 결과
            rs = pstmt.executeQuery(); // 호출: true, 실패: false

            // 조회 성공 시
            while (rs.next()) {
                userNick = rs.getString(1);
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

        if (friendNick.equals(userNick)) {
            System.out.println("사용자 본인을 검색함");
        } else {
            // 친구 조회 호출 쿼리문
            String sql2 = "SELECT USER_NICKNAME FROM TB_USER WHERE USER_NICKNAME like '%' || ? || '%'";

            try {
                // 오라클 서버와 연결
                conn = dbMgr.getConnection();
                pstmt = conn.prepareStatement(sql2);

                // 쿼리문 내 파라미터 값
                pstmt.setString(1, friendNick);

                // 쿼리문 결과
                rs = pstmt.executeQuery(); // 호출: true, 실패: false

                // 조회 성공 시
                while (rs.next()) {
                    fList = rs.getString(1);

                    // EXIST_FRIEND = 친구 검색 존재
                    protocol = 607;
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
        }

        lResult.add(protocol);
        lResult.add(fList);

        System.out.println("프로토콜 : " + lResult.get(0));
        System.out.println("친구리스트 : " + lResult.get(1));

        System.out.println("FriendLogic.printFriend() 메소드 종료");

        return lResult;
    } // end of findFriend (친구 이름으로 조회)

    /**
     * [선택한 닉네임을 친구 추가]
     * 607 : 친구 검색 존재,
     * 605 : 친구 추가 이벤트
     *
     * @param uservo     사용자 정보
     * @param selectNick 선택한 계정ID
     * @return protocol 607:친구 검색 존재 | 605: 친구 추가 이벤트
     */
    public int addFriend(UserVO uservo, String selectNick) {
        System.out.println("FriendLogic.addFriend() 메소드 시작");
        // EXIST_FRIEND = 친구 검색 존재
        protocol = 607;

        int isOk = -1;
        int result = 0;

        System.out.println("사용자 ID : " + uservo.getUser_id());
        System.out.println("선택한 계정ID : " + selectNick);

        // 해당 친구가 DB에 없는지 확인
        StringBuilder selQuarry = new StringBuilder();
        selQuarry.append("SELECT ur.user_nickname FROM    ");
        selQuarry.append(" (SELECT f_id FROM tb_friends_list WHERE user_id= ? ) fl    ");
        selQuarry.append("        , (SELECT user_id, user_nickname FROM tb_user WHERE status=0) ur   ");
        selQuarry.append("WHERE fl.f_id = ur.user_id  ");
        selQuarry.append("AND ur.user_nickname = ?   ");

        try {
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(selQuarry.toString());

            pstmt.setString(1, uservo.getUser_id());
            pstmt.setString(2, selectNick);

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
            String selectID = "";

            String findID = " SELECT user_id FROM tb_user WHERE user_nickname = ? ";

            try {
                conn = dbMgr.getConnection();
                pstmt = conn.prepareStatement(findID);

                pstmt.setString(1, selectNick);

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    selectID = rs.getString(1);
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

            System.out.println("선택한 사용자ID : " + selectID);

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

            String sql2 = "INSERT INTO TB_FRIENDS_LIST (USER_ID, F_ID) VALUES (?, ?)";

            try {
                conn = dbMgr.getConnection();
                pstmt = conn.prepareStatement(sql2);

                pstmt.setString(1, selectID);
                pstmt.setString(2, uservo.getUser_id());

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
     * [선택한 닉네임을 친구 삭제]
     * 604: 검색 결과가 없음
     * 511: 친구 삭제
     *
     * @param uservo  사용자 정보
     * @param selNick 선택한 계정 닉네임
     * @return protocol 604: 검색 결과가 없음 | 511: 친구 삭제
     */
    public int delFriend(UserVO uservo, String selNick) {
        System.out.println("FriendLogic.addFriend() 메소드 시작");
        // NF_RESULT = 검색 결과가 없음
        protocol = 604;

        // 리턴값 기본 0
        int isOk = -1;
        int result = 0;

        String f_id = "";

        System.out.println("사용자 ID : " + uservo.getUser_id());
        System.out.println("선택한 계정NICK : " + selNick);

        // 쿼리문에 사용할 변수
        String table = " TB_FRIENDS_LIST ";
        String whereClause = " USER_ID = " + uservo.getUser_id() + " AND F_id" + selNick;

        // 해당 친구가 DB에 없는지 확인
        // String selQuarry = "SELECT * FROM TB_FRIENDS_LIST WHERE USER_ID = ? AND F_ID
        // = ?";
        StringBuilder selQuarry = new StringBuilder();
        selQuarry.append(" SELECT fl.f_id FROM TB_FRIENDS_LIST fl                      ");
        selQuarry.append("         , (SELECT user_id, user_nickname FROM tb_user) ur   ");
        selQuarry.append(" WHERE fl.f_id = ur.user_id                                  ");
        selQuarry.append("         AND fl.user_id = ?                                  ");
        selQuarry.append("         AND ur.user_nickname = ?                            ");

        try {
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(selQuarry.toString());

            pstmt.setString(1, uservo.getUser_id());
            pstmt.setString(2, selNick);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                isOk = 1;
                f_id = rs.getString(1); // 삭제할 친구의 ID
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
        System.out.println("삭제할 친구의 ID: " + f_id);

        if (isOk == 1) {
            // 해당 ID 친구 삭제

            String sql = "DELETE FROM TB_FRIENDS_LIST WHERE USER_ID = ? AND F_ID = ?";
            // StringBuilder delQuarry = new StringBuilder();
            // delQuarry.append(" DELETE FROM TB_FRIENDS_LIST fl ");
            // delQuarry.append(" , (SELECT user_id, user_nickname FROM tb_user) ur ");
            // delQuarry.append(" WHERE fl.f_id = ur.user_id ");
            // delQuarry.append(" AND fl.user_id = ? ");
            // delQuarry.append(" AND ur.user_nickname = ? ");

            try {
                conn = dbMgr.getConnection();
                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, uservo.getUser_id());
                pstmt.setString(2, f_id);

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

    // 단위테스트
    public static void main(String[] args) {
        UserVO uVO = new UserVO();
        FriendLogic fl = new FriendLogic();
        int r = 0;

        uVO.setUser_id("banana@email.com");
        String selectNick1 = "banana1";
        String selectNick2 = "자고싶어2";
        //
        // // 친구추가
        r = fl.addFriend(uVO, selectNick1);
        System.out.println(r);
        //
        // // 친구 삭제
        //// r = fl.delFriend(uVO, selectID);
        //// System.out.println(r);
        //
        // // 친구 조회
        // List<Object> m = new ArrayList<>();
        // m = fl.findFriend(uVO, selectName2);
        // // m = fl.findFriend(selectName1);
        // //
        // // // 친구 리스트 출력
        // List<Object> l = new ArrayList<Object>();
        // // l = fl.printFriend(uVO);
    }
}
