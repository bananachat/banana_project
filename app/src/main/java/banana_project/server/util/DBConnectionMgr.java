package banana_project.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnectionMgr {
    public static final String _DRIVER = "oracle.jdbc.driver.OracleDriver";
    // public static final String _URL = "jdbc:oracle:thin:@192.168.10.72:1521:orcl11";
    public static final String _URL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl11";

    public static String _USER = "scott";
    public static String _PW = "tiger";

    public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(_DRIVER);
            con = DriverManager.getConnection(_URL, _USER, _PW);
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 클래스를 찾을 수 없습니다.");
        } catch (Exception e) {
            System.out.println("오라클 서버와 커넥션 실패!!");
        } // end of try-catch

        return con;
    } // end of getConnection()

    public void freeConnection(Connection con, Statement stmt) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // end of freeConnection

    public void freeConnection(Connection con, PreparedStatement pstmt) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // end of freeConnection - [INSERT, UPDATE, DELETE]

    public void freeConnection(Connection con, Statement stmt, ResultSet rs) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // end of freeConnection

    public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // end of freeConnection - [SELECT]

    public static void main(String[] args) {
        DBConnectionMgr dbMgr = new DBConnectionMgr();
        Connection con = dbMgr.getConnection();
        System.out.println("con ===> " + con);
    }
}