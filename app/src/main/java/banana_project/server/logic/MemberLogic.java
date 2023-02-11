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
    public MemberLogic() {
    }

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
            //난수 생성
            String salt = ep.getSalt();
            //난수(Salt)를 합쳐서 PW 암호화
            String password = ep.getEncrypt(uservo.getUser_pw(), salt);
            //회원 가입 정보 DB에 저장
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_id());
            pst.setString(2, password);
            pst.setString(3, uservo.getUser_name());
            pst.setString(4, uservo.getUser_hp());
            pst.setString(5, uservo.getUser_nickname());
            pst.setString(6, salt);
            //쿼리 실행
            result = pst.executeUpdate();
        } catch (SQLException se) { //SQLException 처리 로그 저장
            se.printStackTrace();
            ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                    new LogVO(Protocol.SIGN_ERR, uservo.toString(), uservo.getUser_id()));
        } catch (Exception e) { //Exception 처리 로그 저장
            e.printStackTrace();
            ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                    new LogVO(Protocol.SIGN_ERR, uservo.toString(), uservo.getUser_id()));
        } finally {
            //자원 반납
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //퇴장 로그 출력
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(Protocol.SIGN_SUS, uservo.toString(), uservo.getUser_id()));
        //결과 반환
        return result;
    }

    /**
     * 회원정보(닉네임) 업데이트
     * 결과값 -1, 0 : 정보 업데이트 실패
     * 결과값 1 : 업데이트 성공
     *
     * @param uservo 회원정보 객체
     * @return result 업데이트 결과 반환
     */
    public int updateUserNick(UserVO uservo) {
        //로그 출력
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
        //반환값 초기화
        int result = -1;
        //회원정보 UPDATE sql 작성
        String sql = "UPDATE TB_USER SET USER_NICKNAME=?, UPD_DATE=SYSDATE WHERE USER_ID=?";
        try {
            //회원 정보 업데이트 실행
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_nickname());
            pst.setString(2, uservo.getUser_id());
            result = pst.executeUpdate();
        } catch (SQLException se) { //SQLException 처리 로그 저장
            se.printStackTrace();
        } catch (Exception e) { //Exception 처리 로그 저장
            e.printStackTrace();
        } finally {
            //자원 반납
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //로그 출력
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
        return result;
    }

    /**
     * 회원정보(비밀번호) 업데이트
     * 결과값 -1, 0 : 정보 업데이트 실패
     * 결과값 1 : 업데이트 성공
     *
     * @param uservo 회원정보 객체
     * @return result 업데이트 결과 반환
     */
    public int updateUserPW(UserVO uservo) {
        //로그 출력
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
        //반환값 초기화
        int result = -1;
        //비밀번호 UPDATE sql 작성
        String sql = "UPDATE TB_USER SET USER_PW=?, SALT=?, UPD_DATE=SYSDATE WHERE USER_ID=?";
        try {
            //난수 생성
            String salt = ep.getSalt();
            //난수(Salt)를 합쳐서 PW 암호화
            String password = ep.getEncrypt(uservo.getUser_pw(), salt);
            //회원 정보 업데이트 실행
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, password);
            pst.setString(2, salt);
            pst.setString(3, uservo.getUser_id());
            result = pst.executeUpdate();
        } catch (SQLException se) { //SQLException 처리 로그 저장
            se.printStackTrace();
        } catch (Exception e) { //Exception 처리 로그 저장
            e.printStackTrace();
        } finally {
            //자원 반납
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //로그 출력
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
        //로그 출력
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(201, uservo.toString(), uservo.getUser_id()));
        //반환값 초기화
        int result = 1;
        //ID SELECT문 SQL 작성
        String sql = "SELECT USER_ID FROM TB_USER WHERE USER_ID=?";
        try {
            // DB에서 가져오기
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_id());
            rs = pst.executeQuery();
            //결과값이 존재할 경우 해당 아이디가 존재한다는 의미이므로 -1 반환
            while (rs.next()) {
                result = -1;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            //자원 반납
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //로그 출력
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
        //로그 출력
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(203, uservo.toString(), uservo.getUser_id()));
        //반환값 초기화
        int result = 1;
        //NICKNAME SELECT문 SQL 작성
        String sql = "SELECT USER_NICKNAME FROM TB_USER WHERE USER_NICKNAME=? AND STATUS=0";
        try {
            //DB에서 가져오기
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_nickname());
            rs = pst.executeQuery();
            while (rs.next()) {
                //결과값이 존재할 경우 해당 닉네임이 존재한다는 의미이므로 -1 반환
                result = -1;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                //자원 반납
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //로그 출력
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
        //로그 출력
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(205, uservo.toString(), uservo.getUser_id()));
        //반환값 초기화
        int result = 1;
        //HP SELECT문 SQL 작성
        String sql = "SELECT USER_HP FROM TB_USER WHERE USER_HP=? AND STATUS=0";
        try {
            //DB에서 가져오기
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_hp());
            rs = pst.executeQuery();
            //결과값이 존재할 경우 해당 HP가 존재한다는 의미이므로 -1 반환
            while (rs.next()) {
                result = -1;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            //자원 반납
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //로그 출력
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
        //로그 출력
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
        //반환값 초기화
        UserVO userInfo = new UserVO();
        //회원정보 SELECT문 SQL 작성
        String sql = "SELECT * FROM TB_USER WHERE USER_ID=?";
        try {
            //DB 정보 가져오기
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_id());
            rs = pst.executeQuery();
            //값을 vo객체에 담아주기
            while (rs.next()) {
                userInfo.setUser_id(rs.getString("user_id"));
                userInfo.setUser_name(rs.getString("user_name"));
                userInfo.setUser_nickname(rs.getString("user_nickname"));
                userInfo.setUser_hp(rs.getString("user_hp"));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            //자원 반납
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //로그 출력
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
        //유저 정보 반환
        return userInfo;
    }

    /**
     * 회원 로그인
     *
     * @param uservo 회원정보 객체
     * @return result 로그인 결과 프로토콜, 유저 정보 반환
     */
    public Map<String, Object> loginUser(UserVO uservo) {
        //로그 출력
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(Protocol.CLIENT_START, uservo.toString(), uservo.getUser_id()));
        //반환값 초기화
        Map<String, Object> resultMap = new HashMap<>();
        //회원 정보 SELECT SQL작성
        String sql = "SELECT user_id, user_pw, user_nickname, salt, fail_cnt FROM TB_USER WHERE user_id = ? AND STATUS = 0";
        try {
            //DB에서 정보 가져오기
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_id());
            rs = pst.executeQuery();
            //값이 존재할 경우 계정이 존재한다는 의미
            if (rs.next()) {
                //로그인 실패 횟수 확인위해 카운트값 가져옴
                int fail_cnt = rs.getInt("fail_cnt");
                //실패 횟수가 5회 미만일 경우 로그인 정보 확인
                if (fail_cnt < 5) {
                    //아이디와 비밀번호를 변수에 담아줌
                    String u_pw = rs.getString("user_pw");
                    String salt = rs.getString("salt");
                    //비밀번호 암호화하여 DB값과 비교
                    if (u_pw.equals(ep.getEncrypt(uservo.getUser_pw(), salt))) {//일치할 경우 로그인 성공
                        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                                new LogVO(Protocol.LOGIN_S, uservo.toString(), uservo.getUser_id()));
                        //로그인 성공 프로토콜을 반환값에 삽입
                        resultMap.put("result", Protocol.LOGIN_S);
                        UserVO uv = new UserVO();
                        //회원 정보를 받아와 반환 객체에 저장
                        uv.setUser_id(rs.getString("user_id"));
                        uv.setUser_nickname(rs.getString("user_nickname"));
                        //로그인 성공시에 실패 카운트가 0이 아니면 0으로 리셋
                        if (fail_cnt != 0) {
                            uv.setFail_cnt(ConstantsMember.RESET_FAIL_CNT);
                            //DB에 리셋된 실패 카운트 저장
                            updateFailCnt(uv);
                        }
                        //반환 객체에 회원정보 넣어줌
                        resultMap.put("userVO", uv);
                        //결과 반환
                        return resultMap;
                    } else { //일치하지 않을 경우 로그인 실패
                        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                                new LogVO(Protocol.WRONG_PW, uservo.toString(), uservo.getUser_id()));
                        //반환 객체에 비밀번호 오류 프로토콜 삽입
                        resultMap.put("result", Protocol.WRONG_PW);
                        //로그인 실패 카운트 하나 올림
                        uservo.setFail_cnt(fail_cnt + 1);
                        updateFailCnt(uservo);
                        //결과값 반환
                        return resultMap;
                    }
                    //5회 이상인 경우 실패 카운트 초과로 로그인 거부
                } else {
                    ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                            new LogVO(Protocol.OVER_FAIL_CNT, uservo.toString(), uservo.getUser_id()));
                    //로그인 실패 카운트 초과 프로토콜 객체에 담아 반환
                    resultMap.put("result", Protocol.OVER_FAIL_CNT);
                    return resultMap;
                }
                //값이 존재하지 않을 경우 해당 계정이 존재하지 않음.
            } else {
                ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                        new LogVO(Protocol.WRONG_ID, uservo.toString(), uservo.getUser_id()));
                //계정 없음 프로토콜 객체에 담아 반환
                resultMap.put("result", Protocol.WRONG_ID);
                return resultMap;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //자원 반납
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
        //카운트 업데이트 SQL문 작성
        String sql = "UPDATE TB_USER SET FAIL_CNT=? WHERE USER_ID=?";
        //DB 업데이트 실행
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, uservo.getFail_cnt());
            pst.setString(2, uservo.getUser_id());
            pst.executeUpdate();
            //예외 처리
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //자원 반납
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }//로그 출력
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
    }

    /**
     * 회원 탈퇴
     * 결과값 1 : 탈퇴 성공
     * 결과값 523 : 비밀번호 오류로 탈퇴 실패
     * 결과값 -1 : 에러 발생
     *
     * @param uservo 회원 정보 객체
     * @return result 탈퇴 결과 반환
     */
    public int deleteAccount(UserVO uservo) {
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(999, uservo.toString(), uservo.getUser_id()));
        //반환값 초기화
        int result = -1;
        //비밀번호 확인용 SQL작성
        String sql = "SELECT user_pw, salt FROM TB_USER WHERE user_id = ? AND STATUS = 0";
        try {
            //DB에서 정보 가져오기
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_id());
            rs = pst.executeQuery();
            //비밀번호가 일치하는지 확인
            if (rs.next()) {
                String u_pw = rs.getString("user_pw");
                String salt = rs.getString("salt");
                //비밀번호 일치하지 않을 경우 탈퇴 불가 프로토콜 리턴
                if (!u_pw.equals(ep.getEncrypt(uservo.getUser_pw(), salt)))
                    return Protocol.FAIL_DACNT;
            }
            //회원정보 업데이트 SQL 작성
            sql = "UPDATE TB_USER SET STATUS=? WHERE USER_ID=?";
            //DB 정보 업데이트 실행
            pst = con.prepareStatement(sql);
            pst.setInt(1, ConstantsMember.DELETE_ACCOUNT);
            pst.setString(2, uservo.getUser_id());
            result = pst.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //자원 반납
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //로그 출력
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
        //반환 객체 생성 및 초기화
        UserVO user = new UserVO();
        //유저 정보 불러오는 SQL문 작성
        String sql = "SELECT USER_ID FROM TB_USER WHERE USER_NAME=? AND USER_HP=? AND STATUS = 0";
        //DB 실행
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_name());
            pst.setString(2, uservo.getUser_hp());
            rs = pst.executeQuery();
            //결과값이 존재할 경우엔 존재하는 계정
            while (rs.next()) {
                //결과값에서 유저 아이디를 가져옴
                String userId = rs.getString("user_id");
                //보안을 위해 **처리 후 객체에 담아 반환
                userId = userId.replaceAll("(?<=.{3}).(?=.*@)", "*");
                user.setUser_id(userId);
            }
            //예외 처리
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            //자원 반납
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(301, uservo.toString(), uservo.getUser_id()));
        return user;
    }

    /**
     * 비밀번호 재설정 가능 여부 확인
     * EXIST_FACNT : 비밀번호 재설정 가능
     *
     * @param uservo 비밀번호 재설정할 회원 정보 객체
     * @return 비밀번호 변경 가능 여부 반환
     */
    public int findUserPW(UserVO uservo) {
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(Protocol.FPW_START, uservo.toString(), uservo.getUser_id()));
        //반환값 초기화
        int pw_protocol = Protocol.NF_FACNT;
        //회원 정보 읽어오는 SQl문 설정
        String sql = "SELECT USER_PW FROM TB_USER WHERE USER_NAME=? AND USER_HP=? AND USER_ID=? AND STATUS = 0";
        //DB에서 읽어오기
        try {
            con = mgr.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, uservo.getUser_name());
            pst.setString(2, uservo.getUser_hp());
            pst.setString(3, uservo.getUser_id());
            rs = pst.executeQuery();
            //값이 있을 경우 정보가 일치한다는 의미 -> 비밀번호 재설정
            while (rs.next()) {
                pw_protocol = Protocol.EXIST_FACNT;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            //자원 반납
        } finally {
            try {
                mgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO(Protocol.FPW_EXIT, uservo.toString(), uservo.getUser_id()));
        return pw_protocol;
    }
}
