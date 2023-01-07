package banana_project.server.logic;

import banana_project.server.util.Constants;
import banana_project.server.vo.LogVO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;

public class LogLogic {

    String fileName = "LOG_" + setTimer(Constants.FILE_NAME) + ".txt";

    /**
     * 로그 출력용 메세지 설정
     * 1 : COMMON_LOG(회원 정보관리, 채팅 로그)
     * 2 : ENTER_LOG(메소드 입장 기록)
     * 3 : EXIT_LOG(메소드 퇴장 기록)
     * ELSE : 기타 에러 로그
     *
     * @param msg_type 로그 메세지 타입
     * @return msg 로그 메세지
     */
    public String makeLogMsg(int msg_type, LogVO logVO) {
        //로그 메세지 담을 변수
        String msg = "";

        if (msg_type == Constants.COMMON_LOG) //일반 메세지 - 현재 시각과 logVO 객체 출력
            msg = setTimer(Constants.PRINT_LOG) + Constants.SIGN_SPACE + logVO.toString() + Constants.SIGN_ENTER;
        else if (msg_type == Constants.ENTER_LOG) //메소드 입장 메세지 - 메소드 이름을 logVO객체의 comments에서 받아옴.
            msg = setTimer(Constants.PRINT_LOG) + Constants.SIGN_SPACE + Constants.ENTER_MSG
                    + Constants.SIGN_SPACE + logVO.getComments()+ Constants.SIGN_ENTER;
        else if (msg_type == Constants.EXIT_LOG) //메소드 퇴장 메세지 - 메소드 이름을 logVO객체의 comments에서 받아옴.
            msg = setTimer(Constants.PRINT_LOG) + Constants.SIGN_SPACE + Constants.EXIT_MSG
                    + Constants.SIGN_SPACE + logVO.getComments()+ Constants.SIGN_ENTER;
        else //기타 오류 메세지
            msg = setTimer(Constants.PRINT_LOG) + Constants.SIGN_SPACE + Constants.ERROR_MSG + Constants.SIGN_SPACE
                    + Constants.MK_LOG_MSG + Constants.SIGN_SPACE + logVO.toString() + Constants.SIGN_ENTER;
        return msg;
    }

    /**
     * 로그 파일 생성
     *
     * @param logVO 로그 정보를 담은 VO
     */
    public void writeLog(int log_type, LogVO logVO) {
        try {
            //로그를 저장할 경로 설정
            File path = new File(Constants.LOG_PATH);
            if (!path.exists())// 경로 확인 후 해당 디렉토리가 없을 경우 생성
                path.mkdir();
            File f = new File(Constants.LOG_PATH + fileName);//로그 파일 생성
            PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new FileWriter(f.getAbsolutePath(), true)));
            pw.append(makeLogMsg(log_type, logVO));//로그 파일에 로그 append
            pw.close();
        } catch (Exception e) { // 예외 발생시 출력
            e.printStackTrace();
        }
    }

    /**
     * 시스템의 오늘 날짜, 시간 정보 가져오기
     *
     * @param 해당사항 없음.
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
     */
    public static void main(String[] args) {
        LogLogic logLogic = new LogLogic();
        logLogic.writeLog(Constants.COMMON_LOG,
                new LogVO("2020-03-13", 200, "정보를 담은 VO", ""));
        logLogic.writeLog(Constants.ENTER_LOG,
                new LogVO("2020-03-14", 300, "채팅방 입장 메소드 이름()", "tomato"));
        logLogic.writeLog(Constants.EXIT_LOG,
                new LogVO("2020-03-15", 400, "회원 가입 메소드 이름()", "lemon"));
        logLogic.writeLog(7,
                new LogVO("2020-03-15", 400, "회원 가입 메소드 이름()", "lemon"));
    }
}

