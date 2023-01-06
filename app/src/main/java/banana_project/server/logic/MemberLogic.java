package banana_project.server.logic;

import banana_project.server.util.DBConnectionMgr;
import banana_project.server.vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberLogic {

    DBConnectionMgr mgr = new DBConnectionMgr();
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * MemberLogic constructor
     */
    public MemberLogic(){

    }

    /**
     * 회원가입 INSERT
     *
     * @param uservo
     * @return result
     */
    public int joinUser(UserVO uservo){

        int result = -1;
        StringBuilder sql = new StringBuilder();

        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql.toString());


        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
