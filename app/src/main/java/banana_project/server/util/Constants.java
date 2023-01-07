package banana_project.server.util;

public class Constants {

    /**
     * 로그 출력 처리용 기호
     */
    public static final String SIGN_SPACE = " ";
    public static final String SIGN_ENTER = "\n";

    /**
     * 기록할 로그 타입 설정
     * COMMON_LOG : 일반 로그(유저, 채팅방 관리용)
     * ENTER_LOG : 메소드 입장 로그
     * EXIT_LOG : 메소드 퇴장로그
     */
    public static final int COMMON_LOG = 1;
    public static final int ENTER_LOG = 2;
    public static final int EXIT_LOG = 3;

    /**
     * 로그 출력용 파일 경로
     */
    public static final String LOG_PATH = "app\\src\\main\\log\\";

    /**
     * 로그 출력시 날짜 정보 불러오는 옵션
     * FILE_NAME : 파일명 생성용
     * PRINT_LOG : 로그 출력용
     */
    public static final int FILE_NAME = 1;
    public static final int PRINT_LOG = 2;

    /**
     * 로그 출력용 String 메세지 설정
     */
    public static final String ENTER_MSG = "ENTER";
    public static final String EXIT_MSG = "EXIT";
    public static final String ERROR_MSG = "ERROR";
    public static final String MK_LOG_MSG = "makeLogMsg()";
}
