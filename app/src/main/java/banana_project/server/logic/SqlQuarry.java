package banana_project.server.logic;

import banana_project.server.util.DBConnectionMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlQuarry {
    // DB 연결 변수
    DBConnectionMgr dbMgr = new DBConnectionMgr();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;


    /**
     * 오라클 INSERT 쿼리문
     * -1 : 쿼리문 실패
     * 1  : 레코드 1개 생성
     *
     * @param table         테이블 명
     * @param columns       컬럼명 들
     * @param values        밸류값 들
     * @return result
     */
    public int quInsert(String table, String[] columns, String[] values) {
        // 리턴값 기본 -1
        int result = -1;

        // 컬럼 파라미터 설정
        String column = "";
        for (int i=0; i<columns.length-1; i++) {
            column += columns[i] + ", ";
        }
        column = columns[columns.length-1];

        // 밸류 파라미터 설정
        String value = "";
        for (int i=0; i<values.length-1; i++) {
            value += values[i] + ", ";
        }
        value = values[values.length-1];


        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ? ( ? ) VALUES ( ? ) ");

        try {
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            pstmt.setString(1, table);
            pstmt.setString(2, column);
            pstmt.setString(3, value);

            // 쿼리 동작 레코드 수
            // 성공: 1 / 실패: -1
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

        return result;
    } // end of quInsert (INSERT문)


    /**
     * 오라클 UPDATE 쿼리문
     * -1 : 쿼리문 실패
     * 1  : 레코드 1개 생성
     *
     * @param table             테이블 명
     * @param column            해당컬럼 명
     * @param chValue           변경할 값
     * @param whereClause       조건절 (ex. user_name='홍길동' AND user_id='hong')
     * @return result
     */
    public  int quUpdate(String table, String column, String chValue, String whereClause) {
        // 리턴값 기본 -1
        int result = -1;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ? SET ? = ? WHERE ? ");

        try {
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            pstmt.setString(1, table);
            pstmt.setString(2, column);
            pstmt.setString(3, chValue);
            pstmt.setString(4, whereClause);

            // 쿼리 동작 레코드 수
            // 성공: 1 / 실패: -1
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

        return result;
    } // end of quUpdate (UPDATE문)


    /**
     * 오라클 DELETE 쿼리문
     * -1 : 쿼리문 실패
     * 1  : 레코드 1개 생성
     *
     * @param table             테이블 명
     * @param whereClause       조건절 (ex. user_name='홍길동' AND user_id='hong')
     * @return result
     */
    public int quDelete(String table, String whereClause) {
        // 리턴값 기본 -1
        int result = -1;

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ? WHERE ? ");

        try {
            conn = dbMgr.getConnection();
            pstmt = conn.prepareStatement(sql.toString());

            pstmt.setString(1, table);
            pstmt.setString(2, whereClause);

            // 쿼리 동작 레코드 수
            // 성공: 1 / 실패: -1
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

        return result;
    } // end of quDelete (DELETE문)
}
