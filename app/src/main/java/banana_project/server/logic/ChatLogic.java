package banana_project.server.logic;

import banana_project.server.util.ConstantsLog;
import banana_project.server.util.DBConnectionMgr;
import banana_project.server.vo.ChatContentsVO;
import banana_project.server.vo.ChatLogVO;
import banana_project.server.vo.LogVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//채팅불러오기
//채팅저장하기
//채팅삭제

public class ChatLogic {
    //로그출력용
    LogLogic ll =new LogLogic();
    //프로토콜
    int protocol =0;
    //DB연결용
    DBConnectionMgr dbMgr = new DBConnectionMgr();
    Connection  con = null;

    PreparedStatement pst = null;
    ResultSet rs = null;
    public ChatLogic(){}

    public List<ChatContentsVO> ChatCall(int chatnum){
        //리턴값
        List<ChatContentsVO> crs = new ArrayList<>();
        ChatContentsVO ccvo=null;
        //쿼리결과 기본 false
        //        Boolean result = false;

        //로그작성
        ll.writeLog(ConstantsLog.ENTER_LOG,Thread.currentThread().getStackTrace()[1].getMethodName()
            ,new LogVO(700, "Room number :"+chatnum, ""));
        String sql= "select * from tb_chat_contents where chat_no=?";

        try{
            con=dbMgr.getConnection();
            pst=con.prepareStatement(sql);
//            pst.setString(1,chatnum);?????????????????????????????
            rs=pst.executeQuery();
            while(rs.next()){
                String chat_date=rs.getString("chat_date");
                String chat_contents=rs.getString("chat_contents");
                String user_id=rs.getString("user_id");
                ccvo= new ChatContentsVO();
                ccvo.setChat_content(chat_contents);
                ccvo.setChat_date(chat_date);
                ccvo.setChat_no(chatnum);
                ccvo.setUser_id(user_id);
//                crs.add()



            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    return crs;
    }





}
