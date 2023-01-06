package banana_project.server;

public class Protocol {
    //          [구분자]           //
    public static final String seperator = "#";

    //          [로그인]           //
    public static final int CLIENT_START = 100;     // 클라이언트 시작
    public static final int LOGIN_S = 101;          // 로그인 성공
    public static final int WRONG_ID = 102;         // 아이디가 틀림
    public static final int WRONG_PW = 103;         // 패스워드 틀림

    //          [회원가입]           //
    public static final int SIGN_IN = 200;          // 회원가입 시작
    public static final int SIGN_COMPLETE = 201;    // 가입 완료
    public static final int MAIL_CHK = 202;         // 이메일 중복 체크
    public static final int MAIL_DUP_EXIT = 203;    // 이메일 중복 존재
    public static final int NICK_CHK = 204;         // 닉네임 중복 체크
    public static final int NICK_DUP_EXIT = 205;    // 닉네임 중복 존재
    public static final int SIGN_SUS = 206;         // 회원가입 성공
    public static final int SIGN_ERR = 207;         // 회원가입 실패

    //          [아이디 찾기]           //
    public static final int FID_START = 300;        // 아이디찾기 시작
    public static final int FID_EXIT = 301;         // 아이디찾기 종료
    public static final int NOT_FID = 302;          // 아이디 존재하지 않음
//    public static final int  = 30

    //          [비밀번호 찾기]           //
    public static final int FPW_START = 400;        // 비밀번호찾기 시작
    public static final int FPW_EXIT = 401;         // 비밀번호찾기 종료
    public static final int NOF_ACNT = 402;         // 계정 존재하지 않음
    public static final int RESET_PW = 403;         // 비밀번호 재설정
//    public static final int  = 40;

    //          [리스트 화면]           //
    public static final int PRI_CHATS = 500;        // 채팅리스트 출력
    public static final int NOF_CHATS = 501;        // 채팅리스트 없음
    public static final int PRI_FRIENDS = 502;      // 친구목록 출력
    public static final int NOF_FRIENDS = 503;      // 친구리스트 없음
    public static final int BTN_MYPAGE = 504;       // 마이페이지 버튼 클릭
    public static final int BTN_ADFRIEND = 505;     // 친구추가 버튼 클릭
    public static final int BTN_NEWCHAT = 506;      // 새채팅 버튼 클릭
    public static final int BTN_CHAT= 507;          // 채팅방 버튼 클릭
    public static final int BTN_FRIENDS = 508;      // 친구리스트 버튼 클릭
    public static final int LI_CHAT = 509;          // 채팅방 더블클릭
    public static final int LI_FRIEND = 510;        // 친구 더블클릭
//    public static final int  = 50;

    //          [친구 검색]           //
    public static final int PRINT_FRIENDS = 600;    // 친구검색 출력
    public static final int SCH_FRIENDS = 601;      // 검색버튼 이벤트
    public static final int NOF_RESULT = 602;       // 검색 결과가 없음
    public static final int ADD_FRIENDS = 603;      // 친구 추가 이벤트
    public static final int CREATE_CHAT = 604;      // 채팅방 만들기 이벤트
//    public static final int  = 60;


    //          [대화방 화면]           //
    public static final int CHAT_START = 700;       // 대화방화면 출력
    public static final int P_MESSAGE = 701;        // 1:1 대화 메세지
    public static final int G_MESSAGE = 702;        // 단체 대화 메세지
//    public static final int  = 70;

}
