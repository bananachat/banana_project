package banana_project.client.login;

import java.awt.Color;
import java.util.StringTokenizer;

import javax.swing.text.AttributeSet.ColorAttribute;

import banana_project.client.common.SetFontNJOp;
import banana_project.client.common.SetImg;
import banana_project.client.join.MemJoin;
import banana_project.server.thread.Protocol;
import lombok.Builder.Default;

public class ClientThread extends Thread {
  /**
   * 서버연결부 선언
   */
  Client client = null;

  /**
   * 생성자
   * 
   * @param client
   */
  public ClientThread(Client client) {
    this.client = client;
  }

  @Override
  public void run() {
    boolean isStop = false;
    while (!isStop) {
      try {
        String msg = "";
        msg = String.valueOf(client.ois.readObject()); // 서버스레드가 클라이언트에게 전송한 메시지
        StringTokenizer st = null;
        int protocol = 0;
        // 토큰 설정 및 전송받은 프로토콜
        if (msg != null) {
          st = new StringTokenizer(msg, "#");
          protocol = Integer.parseInt(st.nextToken());
        }
        // 프로토콜 switch문 시작
        switch (protocol) {
          /**
           * Client 스레드
           */
          // 로그인 성공 -> 101#아이디
          case Protocol.LOGIN_S: {
            String userId = st.nextToken();
            client.login_s(userId);
          }
            break;
          // 비밀번호 틀림
          case Protocol.WRONG_PW: {
            client.wrong_pw();
          }
            break;
          // 비밀번호 시도횟수 초과
          case Protocol.OVER_FAIL_CNT: {
            client.over_fail_cnt();
          }
            break;
          // 아이디 틀림(계정없음)
          case Protocol.WRONG_ID: {
            client.wrong_id();
          }
            break;

          /**
           * MemJoin 스레드
           */
          // 사용가능한 아이디
          case Protocol.MAIL_CHK: {
            client.memJoin.mail_chk();
          }
            break;
          // 이미 존재하는 아이디
          case Protocol.EXIST_MAIL: {
            client.memJoin.exist_mail();
          }
            break;
          // 사용가능한 닉네임
          case Protocol.NICK_CHK: {
            client.memJoin.nick_chk();
          }
            break;
          // 이미 존재하는 닉네임
          case Protocol.EXIST_NICK: {
            client.memJoin.exist_nick();
          }
            break;
          // 사용 가능한 계정(핸드폰)
          case Protocol.ACNT_CHK: {
            client.memJoin.acnt_chk();
          }
            break;
          // 이미 존재하는 계정(핸드폰 중복)
          case Protocol.EXIST_ACNT: {
            client.memJoin.exist_acnt();
          }
            break;
          // 회원가입 성공
          case Protocol.SIGN_SUS: {
            String userName = st.nextToken();
            client.memJoin.sign_sus(userName);
          }
            break;
          // 회원가입 실패
          case Protocol.SIGN_ERR: {
            client.memJoin.sign_err();
          }
            break;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
