package banana_project.server.thread;

public class Protocol {
    //          [구분자]           //
    public static final String seperator = "#";

    //          [로그인]           //
    public static final int CLIENT_START = 100;     // 클라이언트 시작
    public static final int LOGIN_S = 101;          // 로그인 성공
    public static final int WRONG_ID = 102;         // 존재하지 않는 아이디
    public static final int WRONG_PW = 103;         // 패스워드 틀림
    public static final int OVER_FAIL_CNT = 104;       // 비밀번호 실패 횟수 초과

    //          [회원가입]           //
    public static final int SIGN_UP=200;	        //회원가입 시작
    public static final int MAIL_CHK=201;	        //이메일 중복 체크
    public static final int EXIST_MAIL=202;     	//이메일 중복 존재
    public static final int NICK_CHK=203;	        //닉네임 중복 체크
    public static final int EXIST_NICK=204;	        //닉네임 중복 존재
    public static final int ACNT_CHK=205;	        //계정(이름, 핸드폰번호) 중복 체크
    public static final int EXIST_ACNT=206;     	//계정 중복 존재
    public static final int SIGN_SUS=207;	        //회원가입 성공
    public static final int SIGN_ERR=208;	        //회원가입 실패

    //          [아이디 찾기]           //
    public static final int	FID_START = 300;	    //아이디찾기 시작
    public static final int	FID_EXIT = 301; 	    //아이디찾기 종료
    public static final int	NF_FID = 302;	        //아이디 존재하지 않음
    public static final int	EXIST_FID = 303;	    //아이디가 존재함

    //          [비밀번호 찾기]           //
    public static final int	FPW_START = 400;	    //비밀번호찾기 시작
    public static final int	FPW_EXIT = 401;	        //비밀번호찾기 종료
    public static final int	NF_FACNT = 402;	        //계정 존재하지 않음
    public static final int	EXIST_FACNT = 403;      //계정이 존재함
    public static final int	RESET_PW = 404; 	    //비밀번호 재설정

    //          [리스트 화면]           //
    public static final int	PRT_FRDLIST = 500; 	    //친구목록 출력
    public static final int	NF_FRDLIST = 501; 	    //친구리스트 없음
    public static final int	PRT_CHATLIST = 502; 	//채팅리스트 출력
    public static final int	NF_CHATLIST = 503;  	//채팅리스트 없음
    public static final int	BTN_MYPAGE = 504;   	//마이페이지 버튼 클릭
    public static final int	BTN_ADDFRD = 505; 	    //친구추가 버튼 클릭
    public static final int	BTN_NEWCHAT = 506;  	//새채팅 버튼 클릭
    public static final int	BTN_FRDLIST = 507;  	//친구리스트 버튼 클릭
    public static final int	BTN_CHATLIST = 508; 	//채팅방목록 버튼 클릭
    public static final int	SEL_FRIEND = 509; 	    //친구 더블클릭
    public static final int	SEL_CHAT = 510; 	    //채팅방 더블클릭
    public static final int	DEL_FRIEND = 511;   	//친구 삭제
    public static final int	DEL_CHAT = 512; 	    //채팅방 삭제
    public static final int	NF_MYPAGE = 513;    	//마이페이지 출력 실패
    public static final int	NICK_MCHK = 514;    	//마이페이지 닉네임변경 중복체크
    public static final int	EXIST_MNICK = 515;    	//마이페이지 닉네임변경 중복실패
    public static final int	EDIT_MNICK = 516;    	//마이페이지 닉네임변경
    public static final int	FAIL_MNICK = 517;    	//마이페이지 닉네임 변경 실패
    public static final int	EDIT_MPW = 518;    	//마이페이지 비번 변경
    public static final int	FAIL_MPW = 519;    	//마이페이지 비번 변경 실패

    //          [친구 검색]           //
    public static final int	PRT_USERS = 600; 	    //친구검색 출력(친구추가 → 검색한 값)
    public static final int	SRCH_USERS = 601; 	    //검색버튼(친구추가 → 모든 사용자 중에 검색)
    public static final int	PRT_FRIENDS = 602; 	    //친구검색 출력(새채팅 → 친구목록)
    public static final int	SRCH_FRIEDNDS = 603; 	//검색버튼(새채팅 → 친구 중에 검색)
    public static final int	NF_RESULT = 604; 	    //친구 검색 결과가 없음
    public static final int	ADD_FRIEND = 605;   	//친구 추가 이벤트
    public static final int	CREATE_CHAT = 606;  	//채팅방 만들기 성공
    public static final int	EXIST_FRIEND = 607;  	//친구 검색 존재
    public static final int	FAIL_CRE_CHAT = 608;  	//채팅방 만들기 실패


    //          [대화방 화면]           //
    public static final int	CHAT_START = 700; 	    //대화방화면 출력
    public static final int	PRIVATE_MSG = 701;  	//1:1 대화 메세지
    public static final int	GROUP_MSG = 702; 	    //단체 대화 메세지
    public static final int	PRT_MEMLIST = 703;  	//채팅방 멤버목록 출력
    public static final int	PRT_TEMPLSIT = 704; 	//현 채팅방에 없는 친구 출력(친구초대 클릭했을 때)
    public static final int	ADD_MEM = 705; 	        //현 채팅방에 친구 초대(입장메세지)
    public static final int	EXIT_MEM = 706;     	//채팅방에서 나감(채팅방 삭제했을 때)
    public static final int	SAVE_CHAT = 707;    	//대화저장
    public static final int	WRONG_NUM = 708;    	//잘못된 채팅방 번호
    public static final int	SAVE_FAIL = 708;    	//대화저장실패


    //          [데이터베이스]           //
    public static final int	FAIL_CONN = 800;    	//데이터베이스 접속 실패

}