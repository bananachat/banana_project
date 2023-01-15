package banana_project.server.util;

import banana_project.server.logic.LogLogic;
import banana_project.server.vo.LogVO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class EncryptPassword {
    //로그 작성용
    LogLogic ll = new LogLogic();
    /**
     * 난수 생성 메소드
     *
     * @return sb.toString() 난수 리턴
     */
    public String getSalt() {
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(), new LogVO());
        //Random, byte 객체 생성
        SecureRandom ran = new SecureRandom();
        byte[] salt = new byte[20];

        //난수 생성
        ran.nextBytes(salt);

        //byte To String (10진수의 문자열로 변경)
        StringBuffer sb = new StringBuffer();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(), new LogVO());
        return sb.toString();
    }

    /**
     * 패스워드 암호화 메소드
     *
     * @param password 사용자 입력 비밀번호
     * @param salt 난수
     * @return result 암호화된 비밀번호
     */
    public String getEncrypt(String password, String salt) {
        ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(), new LogVO());
        String result = "";
        try {
            //SHA256 알고리즘 객체 생성
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            //password와 salt 합친 문자열에 SHA 256 적용
            md.update((password + salt).getBytes());
            byte[] pwsalt = md.digest();

            //byte To String (10진수의 문자열로 변경)
            StringBuffer sb = new StringBuffer();
            for (byte b : pwsalt) {
                sb.append(String.format("%02x", b));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        ll.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(), new LogVO());
        return result;
    }
}
