package banana_project.server.logic;

import banana_project.server.util.ConstantsLog;
import banana_project.server.util.DBConnectionMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//채팅불러오기
//채팅저장하기
//채팅삭제

public class ChatLogic {
    //로그출력용
    LogLogic ll =new LogLogic();
    //DB연결용
    DBConnectionMgr dbMgr = new DBConnectionMgr();
    Connection  con = null;

    PreparedStatement pst = null;
    ResultSet rs = null;
//    public ChatCall(int chatnum){
//        ll.writeLog(ConstantsLog.ENTER_LOG,Thread.);
//
//    }



}
