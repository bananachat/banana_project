package banana_project.server.logic;

import banana_project.server.util.ConstantsLog;
import banana_project.server.util.DBConnectionMgr;
import banana_project.server.vo.LogVO;
import banana_project.server.vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberLogic {
    LogLogic ll = new LogLogic();
    DBConnectionMgr mgr = new DBConnectionMgr();
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * MemberLogic constructor
     */
    public MemberLogic() {

    }

    //입력, 수정, 삭제인 경우에는 executeUpdate()사용하고 리턴타입은 int
    //조회인 경우에는 executeQuery()를 사용하고 리턴타입은 ResultSet
    //테이블을 생성할 때는 execute()를 사용함.

    /* 로그인 -> 아이디/비번 -> 맞다 안맞다
       +아이디찾기 -> 이름/폰번호 -> 일치 한다 안한다
       +비밀번호 찾기 -> 이름/아이디/폰번호 -> 일치한다 안한다
     O 회원가입 -> 가입됐다 안됐다
       아이디 중복체크 -> 있다 없다
       닉네임 중복체크 -> 있다 없다
       폰번호 중복체크 -> 있다 없다
       마이페이지 정보 -> 정보출력
    */

    /**
     * 회원가입
     * -1 : 가입 실패
     * 1 : 가입 성공
     *
     * @param uservo 회원정보 객체
     * @return result 가입 결과 반환
     */
    public int joinUser(UserVO uservo) {
        //로그 출력
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(200, uservo.toString(), uservo.getUser_id()));
        //리턴값 기본 -1
        int result = -1;
        //회원정보 INSERT sql 작성
        String sql = "INSERT INTO TB_USER (user_id, user_pw, user_name, user_hp, user_nickname) VALUES (?,?,?,?,?)";
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_id());
            pst.setString(2, uservo.getUser_pw());
            pst.setString(3, uservo.getUser_name());
            pst.setString(4, uservo.getUser_hp());
            pst.setString(5, uservo.getUser_nickname());
            result = pst.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(201, uservo.toString(), ""));
        return result;
    }

    /**
     * 회원정보 업데이트
     * -1, 0 : 정보 업데이트 실패
     * 1 : 업데이트 성공
     *
     * @param uservo 회원정보 객체
     * @return result 업데이트 결과 반환
     */
    public int updateUser(UserVO uservo){
        int result = -1;
        String sql = "UPDATE TB_USER SET USER_PW=?, USER_NAME =?, USER_HP=?, USER_NICKNAME=?, UPD_DATE=SYSDATE WHERE USER_ID=?";
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_pw());
            pst.setString(2, uservo.getUser_name());
            pst.setString(3, uservo.getUser_hp());
            pst.setString(4, uservo.getUser_nickname());
            pst.setString(5, uservo.getUser_id());
            result = pst.executeUpdate();
        }catch (SQLException se){
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();}
        }
        return result;
    }

    public int checkDuplication(){
        int result = -1;
        String sql = "SELECT USER_ID FROM TB_USER WHERE USER_ID=?";
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
//            pst.setString(1, user_id);
        } catch (SQLException se){
            se.printStackTrace();
        }
        return result;
    }

//    public int loginUser(UserVO uservo){
//        int result = -1;
//        StringBuilder sql = new StringBuilder();
//        sql.append("UPDATE `member` SET `user_name` =? WHERE `user_id` =?");
//        try {
//            con = mgr.getConnection();
//        }catch (SQLException se){
//            se.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                mgr.freeConnection(con, pst, rs);
//            } catch (Exception e) {
//                e.printStackTrace();}
//        }
//        return result;
//    }

    public static void main(String[] args) {
        MemberLogic ml = new MemberLogic();
        UserVO uv = new UserVO();
        uv.setUser_id("hello3");
        uv.setUser_pw("password4444");
        uv.setUser_name("name44");
        uv.setUser_hp("010-5555-3333");
        uv.setUser_nickname("자고싶어");
//        int result = ml.joinUser(uv);
//        System.out.println("결과값 : " + result);
        int result = ml.updateUser(uv);
        System.out.println("결과값 : " + result);
    }
}
