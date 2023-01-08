package banana_project.server.logic;

import banana_project.server.util.ConstantsLog;
import banana_project.server.vo.LogVO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;

public class LogLogic {

    String fileName = "LOG_" + setTimer(ConstantsLog.FILE_NAME) + ".txt";

    /**
     * 로그 출력용 메세지 설정
     * 1 : COMMON_LOG(회원 정보관리, 채팅 로그)
     * 2 : ENTER_LOG(메소드 입장 기록)
     * 3 : EXIT_LOG(메소드 퇴장 기록)
     * ELSE : 기타 에러 로그
     *
     * @param msg_type 로그 메세지 타입
     * @param methodName 로그 출력 호출한 메소드명
     * @param logVO 로그 정보
     * @return msg 로그 메세지
     */
    public String makeLogMsg(int msg_type, String methodName, LogVO logVO) {
        //로그 메세지 담을 변수
        String msg = "";

        if (msg_type == ConstantsLog.COMMON_LOG) //일반 메세지 - 현재 시각과 logVO 객체 출력
            msg = setTimer(ConstantsLog.PRINT_LOG) + ConstantsLog.SIGN_SPACE + logVO.toString() + ConstantsLog.SIGN_ENTER;
        else if (msg_type == ConstantsLog.ENTER_LOG) //메소드 입장 메세지
            msg = setTimer(ConstantsLog.PRINT_LOG) + ConstantsLog.SIGN_SPACE + ConstantsLog.ENTER_MSG
                    + ConstantsLog.SIGN_SPACE + methodName + ConstantsLog.METHOD_SIGN + logVO.toString() + ConstantsLog.SIGN_ENTER;
        else if (msg_type == ConstantsLog.EXIT_LOG) //메소드 퇴장 메세지
            msg = setTimer(ConstantsLog.PRINT_LOG) + ConstantsLog.SIGN_SPACE + ConstantsLog.EXIT_MSG
                    + ConstantsLog.SIGN_SPACE + methodName + ConstantsLog.METHOD_SIGN + logVO.toString() + ConstantsLog.SIGN_ENTER;
        else //기타 오류 메세지
            msg = setTimer(ConstantsLog.PRINT_LOG) + ConstantsLog.SIGN_SPACE + ConstantsLog.ERROR_MSG + ConstantsLog.SIGN_SPACE
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + ConstantsLog.METHOD_SIGN
                    + logVO.toString() + ConstantsLog.SIGN_ENTER;
        return msg;
    }

    /**
     * 로그 파일 생성
     *
     * @param log_type 로그 타입
     * @param logVO 로그 정보를 담은 VO
     */
    public void writeLog(int log_type, String methodName, LogVO logVO) {
        try {
            //로그를 저장할 경로 설정
            File path = new File(ConstantsLog.LOG_PATH);
            if (!path.exists())// 경로 확인 후 해당 디렉토리가 없을 경우 생성
                path.mkdir();
            File f = new File(ConstantsLog.LOG_PATH + fileName);//로그 파일 생성
            PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new FileWriter(f.getAbsolutePath(), true)));
            pw.append(makeLogMsg(log_type, methodName, logVO));//로그 파일에 로그 append
            pw.close();
        } catch (Exception e) { // 예외 발생시 출력
            e.printStackTrace();
        }
    }

    /**
     * 시스템의 오늘 날짜, 시간 정보 가져오기
     *
     * @param option 날짜 형식 선택 변수
     * @return YYYY-MM-DD
     */
    public String setTimer(int option) {
        Calendar cal = Calendar.getInstance();
        int yyyy = cal.get(Calendar.YEAR);
        int mm = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (option == 1) {//파일명 설정용 날짜 리턴
            return yyyy + "-" +
                    (mm < 10 ? "0" + mm : "" + mm) + "-" +
                    (day < 10 ? "0" + day : "" + day);
        } else { // 로그 기록용 날짜 + 시간 리턴
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int min = cal.get(Calendar.MINUTE);
            int sec = cal.get(Calendar.SECOND);
            return yyyy + "-" +
                    (mm < 10 ? "0" + mm : mm) + "-" +
                    (day < 10 ? "0" + day : day) + " " +
                    (hour < 10 ? "0" + hour : "" + hour) + ":" +
                    (min < 10 ? "0" + min : "" + min) + ":" +
                    (sec < 10 ? "0" + sec : "" + sec);
        }
    }

    /**
     * 테스트용 메인 -> 삭제예정
     * 로그 설정 방법
     */
    public static void main(String[] args) {
        LogLogic logLogic = new LogLogic();
        // 유저 정보 관리 / 채팅 로그 -> LOGVO 객체에 값을 세팅한 후에 writeLog(LOGVO)를 불러서 사용
        // Thread.currentThread().getStackTrace()[1].getMethodName()로 현재 메소드 이름 가져올 수 있음
        logLogic.writeLog(ConstantsLog.COMMON_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO("2020-03-13", 200, "정보를 담은 VO", ""));
        // 메소드 ENTER 메소드
        // 각 메소드 시작 부분에 넣어줄 것
        logLogic.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO("2020-03-14", 300, "대충 회원가입 메소드()", "tomato"));
        // 메소드 EXIT 메소드
        // 각 메소드 종료 부분(return 전)에 넣어줄 것
        logLogic.writeLog(ConstantsLog.EXIT_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO("2020-03-15", 400, "친구 어쩌고 메소드()", "lemon"));
        logLogic.writeLog(7, Thread.currentThread().getStackTrace()[1].getMethodName(),
                new LogVO("2020-03-15", 400, "회원 가입 메소드 이름()", "lemon"));
    }
}

