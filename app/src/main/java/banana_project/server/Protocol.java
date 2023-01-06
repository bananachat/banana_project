package banana_project.server;

public class Protocol {
    //          [구분자]           //
    public static final String separator = "#";

    //          [로그인]           //
    public static final int CLIENT_START = 100;
    public static final int LOGIN_S = 101;
    public static final int WRONG_ID = 102;
    public static final int WRONG_PW = 103;

    //          [회원가입]           //
    public static final int SIGN_IN = 200;
    public static final int SIGN_COMPLETE = 201;
    public static final int MAIL_CHK = 202;
    public static final int MAIL_DUP_EXIT = 203;
    public static final int NICK_CHK = 204;
    public static final int NICK_DUP_EXIT = 205;
    public static final int SIGN_SUS = 206;
    public static final int SIGN_ERR = 207;

    //          [아이디 찾기]           //
    public static final int FID_START = 300;
    public static final int FID_EXIT = 301;
    public static final int NOT_FID = 302;
//    public static final int  = 303
//    public static final int  = 304
//    public static final int  = 305
//    public static final int  = 306
//    public static final int  = 307

    //          [비밀번호 찾기]           //
    public static final int FPW_START = 400;
    public static final int FPW_EXIT = 401;
    public static final int NOF_ACNT = 402;
    public static final int RESET_PW = 403;
//    public static final int  = 404;
//    public static final int  = 405;
//    public static final int  = 406;
//    public static final int  = 407;
//    public static final int  = 408;
//    public static final int  = 409;
//    public static final int  = 410;
//    public static final int  = 411;
//    public static final int  = 412;
//    public static final int  = 413;
//    public static final int  = 414;
//    public static final int  = 415;

    //          [친구 목록]           //
    public static final int PRI_FRIENDS = 500;
    public static final int MYPAGE_EV = 501;
    public static final int ADD_FRIENDS_EV = 502;
    public static final int NOF_FRIENDS = 503;
//    public static final int  = 504;
//    public static final int  = 505;
//    public static final int  = 506;
//    public static final int  = 507;
//    public static final int  = 508;
//    public static final int  = 509;
//    public static final int  = 510;
//    public static final int  = 511;
//    public static final int  = 512;
//    public static final int  = 513;
//    public static final int  = 514;

    //          [친구 검색]           //
//    public static final int PRIN = 600;
//    public static final int  = 601;
//    public static final int  = 602;
//    public static final int  = 603;
//    public static final int  = 604;
//    public static final int  = 605;
//    public static final int  = 606;
//    public static final int  = 607;
//    public static final int  = 608;
//    public static final int  = 609;
//    public static final int  = 610;
//    public static final int  = 611;
//    public static final int  = 612;
//    public static final int  = 613;
//    public static final int  = 614;


    //          [대화방 화면]           //






//    public static final int TALK_IN = 100;
//    public static final int MESSAGE = 200;
//    public static final int WHISPER = 300;
//    public static final int CHANGE = 400;
//    public static final int TALK_OUT = 500;
}
