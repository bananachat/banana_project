package banana_project.server.logic;

import banana_project.server.thread.Protocol;
import banana_project.server.util.ConstantsLog;
import banana_project.server.util.ConstantsMember;
import banana_project.server.util.DBConnectionMgr;
import banana_project.server.util.EncryptPassword;
import banana_project.server.vo.LogVO;
import banana_project.server.vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MemberLogic {
    // 로그 출력용
    LogLogic ll = new LogLogic();
    // DB 연결용
    DBConnectionMgr mgr = new DBConnectionMgr();
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    //Password 암호화용
    EncryptPassword ep = new EncryptPassword();

    /**
     * MemberLogic constructor
     */
    public MemberLogic() {}

    /*  +아이디찾기 -> 이름/폰번호 -> 일치 한다 안한다
        +비밀번호 찾기 -> 이름/아이디/폰번호 -> 일치한다 안한다
    */

    /**
     * 회원가입
     * 결과값 -1 : 가입 실패
     * 결과값 1 : 가입 성공
     *
     * @param uservo 회원정보 객체
     * @return result 가입 결과 반환
     */
    public int joinUser(UserVO uservo) {
        //로그 출력
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(Protocol.SIGN_UP, uservo.toString(), uservo.getUser_id()));
        //리턴값 기본 -1
        int result = -1;
        //HP 중복검사
        if (checkDuplHP(uservo) < 0) {
            result = 0;
            return result;
        }
        //회원정보 INSERT sql 작성
        String sql = "INSERT INTO TB_USER (user_id, user_pw, user_name, user_hp, user_nickname, salt) VALUES (?,?,?,?,?,?)";
        try {
            String salt = ep.getSalt();
            String password = ep.getEncrypt(uservo.getUser_pw(), salt);
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_id());
            pst.setString(2, password);
            pst.setString(3, uservo.getUser_name());
            pst.setString(4, uservo.getUser_hp());
            pst.setString(5, uservo.getUser_nickname());
            pst.setString(6, salt);
            result = pst.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                    new LogVO(Protocol.SIGN_ERR, uservo.toString(), uservo.getUser_id()));
        } catch (Exception e) {
            e.printStackTrace();
            ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                    new LogVO(Protocol.SIGN_ERR, uservo.toString(), uservo.getUser_id()));
        } finally {
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(Protocol.SIGN_SUS, uservo.toString(), uservo.getUser_id()));
        return result;
    }

    /**
     * 회원정보 업데이트
     * 결과값 -1, 0 : 정보 업데이트 실패
     * 결과값 1 : 업데이트 성공
     *
     * @param uservo 회원정보 객체
     * @return result 업데이트 결과 반환
     */
    public int updateUser(UserVO uservo) {
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
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
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
        return result;
    }

    /**
     * 아이디 중복검사
     * 결과값 1 : 중복 아님
     * 결과값 -1 : 중복
     *
     * @param uservo 회원정보
     * @return result ID 중복검사 결과 반환
     */
    public int checkDuplId(UserVO uservo) {
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(201, uservo.toString(), uservo.getUser_id()));
        int result = 1;
        String sql = "SELECT USER_ID FROM TB_USER WHERE USER_ID=?";
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_id());
            rs = pst.executeQuery();
            while (rs.next()) {
                result = -1;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(201, uservo.toString(), uservo.getUser_id()));
        return result;
    }

    /**
     * 닉네임 중복검사
     * 결과값 1 : 중복 아님
     * 결과값 -1 : 중복
     *
     * @param uservo 회원정보
     * @return result 닉네임 중복검사 결과 반환
     */
    public int checkDuplNickname(UserVO uservo) {
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(203, uservo.toString(), uservo.getUser_id()));
        int result = 1;
        String sql = "SELECT USER_NICKNAME FROM TB_USER WHERE USER_NICKNAME=? AND STATUS=0";
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_nickname());
            rs = pst.executeQuery();
            while (rs.next()) {
                result = -1;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(203, uservo.toString(), uservo.getUser_id()));
        return result;
    }

    /**
     * 폰번호 중복검사
     * 결과값 1 : 중복 아님
     * 결과값 -1 : 중복
     *
     * @param uservo 회원정보
     * @return result 폰번호 중복검사 결과 반환
     */
    public int checkDuplHP(UserVO uservo) {
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(205, uservo.toString(), uservo.getUser_id()));
        int result = 1;
        String sql = "SELECT USER_HP FROM TB_USER WHERE USER_HP=? AND STATUS=0";
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_hp());
            rs = pst.executeQuery();
            while (rs.next()) {
                result = -1;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(205, uservo.toString(), uservo.getUser_id()));
        return result;
    }

    /**
     * 마이페이지 유저 정보 출력
     *
     * @param uservo 유저 아이디 담은 uservo
     * @return userInfo 유저 정보 객체
     */
    public UserVO getUserInfo(UserVO uservo) {
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
        UserVO userInfo = new UserVO();
        String sql = "SELECT * FROM TB_USER WHERE USER_ID=?";
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_id());
            rs = pst.executeQuery();
            while (rs.next()) {
                userInfo.setUser_id(rs.getString("user_id"));
                userInfo.setUser_name(rs.getString("user_name"));
                userInfo.setUser_nickname(rs.getString("user_nickname"));
                userInfo.setUser_hp(rs.getString("user_hp"));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
        return userInfo;
    }

    /**
     * 회원 로그인
     *
     * @param uservo 회원정보 객체
     * @return result 로그인 결과 프로토콜, 유저 정보 반환
     */
    public Map<String, Object> loginUser(UserVO uservo) {
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(Protocol.CLIENT_START, uservo.toString(), uservo.getUser_id()));
        Map<String, Object> resultMap = new HashMap<>();
        String sql = "SELECT user_id, user_pw, user_name, user_hp, user_nickname, salt, fail_cnt FROM TB_USER WHERE user_id = ? AND STATUS = 0";
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_id());
            rs = pst.executeQuery();
            if (rs.next()) {
                int fail_cnt = rs.getInt("fail_cnt");
                if (fail_cnt < 5) {
                    String u_pw = rs.getString("user_pw");
                    String salt = rs.getString("salt");
                    if (u_pw.equals(ep.getEncrypt(uservo.getUser_pw(), salt))) {
                        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                                new LogVO(Protocol.LOGIN_S, uservo.toString(), uservo.getUser_id()));
                        resultMap.put("result", Protocol.LOGIN_S);
                        uservo.setUser_id(rs.getString("user_id"));
                        uservo.setUser_pw(rs.getString("user_pw"));
                        uservo.setUser_name(rs.getString("user_name"));
                        uservo.setUser_hp(rs.getString("user_hp"));
                        uservo.setUser_nickname(rs.getString("user_nickname"));
                        resultMap.put("userVO", uservo);
                        return resultMap;
                    } else {
                        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                                new LogVO(Protocol.WRONG_PW, uservo.toString(), uservo.getUser_id()));
                        resultMap.put("result", Protocol.WRONG_PW);
                        updateFailCnt(uservo);
                        return resultMap;
                    }
                } else {
                    ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                            new LogVO(Protocol.OVER_FAIL_CNT, uservo.toString(), uservo.getUser_id()));
                    resultMap.put("result", Protocol.OVER_FAIL_CNT);
                    return resultMap;
                }
            } else {
                ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                        new LogVO(Protocol.WRONG_ID, uservo.toString(), uservo.getUser_id()));
                resultMap.put("result", Protocol.WRONG_ID);
                return resultMap;
            }
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
                new LogVO(ConstantsLog.EXIT_LOG, uservo.toString(), uservo.getUser_id()));
        return resultMap;
    }

    /**
     * 로그인 실패 카운트 업데이트
     *
     * @param uservo 회원 정보 객체
     */
    public void updateFailCnt(UserVO uservo) {
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
        String sql = "UPDATE TB_USER SET FAIL_CNT=? WHERE USER_ID=?";
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, uservo.getFail_cnt() + 1);
            pst.setString(2, uservo.getUser_id());
            pst.executeUpdate();
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
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
    }

    /**
     * 회원 탈퇴
     *
     * @param uservo 회원 정보 객체
     * @return result 탈퇴 결과 반환
     */
    public int deleteAccount(UserVO uservo) {
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
        int result = -1;
        String sql = "UPDATE TB_USER SET STATUS=? WHERE USER_ID=?";
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, ConstantsMember.DELETE_ACCOUNT);
            pst.setString(2, uservo.getUser_id());
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
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
        return result;
    }

    /**
     * 아이디 찾기
     *
     * @param uservo 아이디 찾기할 회원 정보 객체(USER_NAME, USER_HP)
     * @return user 아이디 마스킹처리 후 회원 객체에 담아 반환
     */
    public UserVO findUserId(UserVO uservo) {
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(300, uservo.toString(), uservo.getUser_id()));
        UserVO user = new UserVO();
        String sql = "SELECT USER_ID FROM TB_USER WHERE USER_NAME=? AND USER_HP=?";
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_name());
            pst.setString(2, uservo.getUser_hp());
            rs = pst.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("user_id");
                userId = userId.replaceAll("(?<=.{3}).(?=.*@)", "*");
                user.setUser_id(userId);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(201, uservo.toString(), uservo.getUser_id()));
        return user;
    }


    public static void main(String[] args) {
        MemberLogic ml = new MemberLogic();
        UserVO uv = new UserVO();
        uv.setUser_id("test3@gmail.com");
        uv.setUser_pw("password");
        uv.setUser_name("tname3");
        uv.setUser_hp("0103");
        uv.setUser_nickname("빨리끝내자3");
        int result = ml.joinUser(uv);
//        int result = ml.updateUser(uv);
//         int result = Integer.parseInt(ml.loginUser(uv).get("result").toString());
//        int result = ml.checkDuplId(uv);
//        int result = ml.deleteAccount(uv);
//        System.out.println("결과값 : " + result);
//        result = Integer.parseInt(ml.loginUser(uv).get("result").toString());
        System.out.println("결과값 : " + result);
        System.out.println(ml.findUserId(uv));
    }
}
