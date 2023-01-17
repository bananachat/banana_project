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
        String msg = null;
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
          // 비번 틀림 103
          case Protocol.WRONG_PW: {
            client.wrong_pw();
          }
            break;
          // 비번 시도횟수 초과 104
          case Protocol.OVER_FAIL_CNT: {
            client.over_fail_cnt();
          }
            break;
          // 계정없음 102
          case Protocol.WRONG_ID: {
            client.wrong_id();
          }
            break;

          /**
           * MemJoin 스레드
           */
          // 사용가능한 아이디 201
          case Protocol.MAIL_CHK: {
            client.memJoin.mail_chk();
          }
            break;
          // 이미 존재하는 아이디 202
          case Protocol.EXIST_MAIL: {
            client.memJoin.exist_mail();
          }
            break;
          // 사용가능한 닉네임 203
          case Protocol.NICK_CHK: {
            client.memJoin.nick_chk();
          }
            break;
          // 이미 존재하는 닉네임 204
          case Protocol.EXIST_NICK: {
            client.memJoin.exist_nick();
          }
            break;
          // 계정(핸드폰) 존재 206
          case Protocol.EXIST_ACNT: {
            client.memJoin.exist_acnt();
          }
            break;
          // 회원가입 성공 207#이름
          case Protocol.SIGN_SUS: {
            String userName = st.nextToken();
            client.memJoin.sign_sus(userName);
          }
            break;
          // 회원가입 실패 208
          case Protocol.SIGN_ERR: {
            client.memJoin.sign_err();
          }
            break;

          /**
           * IdFind 스레드
           */
          // 아이디 존재 303#이름#아이디
          case Protocol.EXIST_FID: {
            String userName = st.nextToken();
            String userId = st.nextToken();
            client.idFind.exist_fid(userName, userId);
          }
            break;
          // 아이디가 존재하지 않음 302
          case Protocol.NF_FID: {
            client.idFind.nf_fid();
          }
            break;

          /**
           * PwFind스레드 -> 아직 미구현!!
           */
          // 계정이 존재할 때
          case Protocol.EXIST_FACNT: {
            // 아이디 받아오기 추가할것!!
            client.pwfind.exist_facnt();
          }
            break;
          // 계정이 존재하지 않을때
          case Protocol.NF_FACNT: {
            client.pwfind.nf_facnt();
          }
            break;

          /**
           * PwFindDialog 스레드 -> 아직 미구현!!
           */
          case Protocol.RESET_PW: {
            client.pwfind.pwFindDialog.reset_pw();
          }
            break;

          /**
           * Main 스레드
           */
          // 친구가 존재하지 않음 501
          case Protocol.NF_FRDLIST: {
            client.main.nf_frdlist();
          }
            break;
          // 친구가 존재함 500 -> 친구
          case Protocol.PRT_FRDLIST: {
            String fList = st.nextToken();
            client.main.prt_frdlist(fList);
          }
            break;

          /**
           * MyPage 스레드
           */
          // 사용자 정보 가져오기 성공 504#이름#핸드폰번호#아이디#닉네임
          case Protocol.BTN_MYPAGE: {
            String userName = st.nextToken();
            String userHp = st.nextToken();
            String userId = st.nextToken();
            String nickName = st.nextToken();
            client.main.myPage.btn_mypage(userName, userHp, userId, nickName);
          }
            break;
          // 가져오기 실패 513
          case Protocol.NF_MYPAGE: {
            client.main.myPage.nf_mypage();
          }
            break;
          // 사용가능한 닉네임 514
          case Protocol.NICK_MCHK: {
            client.main.myPage.nick_mchk();
          }
            break;
          // 이미 존재하는 닉네임 515
          case Protocol.EXIST_MNICK: {
            client.main.myPage.exist_mnick();
          }
            break;
          // 사용자 정보 수정 성공 516
          case Protocol.EDIT_MYPAGE: {
            String newNick = st.nextToken();
            client.main.myPage.edit_mypage(newNick);
          }
          // 사용자 정보 수정 실패 517
          case Protocol.FAIL_MYPAGE: {
            client.main.myPage.fail_mypage();
          }

        } // end of switch
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
