//package banana_project.server.logic;
//
//import banana_project.server.util.DBConnectionMgr;
//import banana_project.server.vo.UserVO;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//public class MemberLogic {
//
//    DBConnectionMgr mgr = new DBConnectionMgr();
//    Connection con = null;
//    PreparedStatement pst = null;
//    ResultSet rs = null;
//
//
//    public MemberLogic(){
//
//    }
//
//    public int joinUser(UserVO uservo){
//
//        int result = -1;
//        StringBuilder sql = new StringBuilder();
//        S
//        try {
//            con = mgr.getConnection();
//            pst = con.prepareStatement(sql.toString());
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//}
