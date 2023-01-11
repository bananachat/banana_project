package banana_project.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptPassword {
    public String getSalt() {
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
        return sb.toString();
    }

    public String getEncrypt(String password, String salt) {
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
        return result;
    }
}
