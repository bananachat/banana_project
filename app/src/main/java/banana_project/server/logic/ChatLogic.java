package banana_project.server.logic;

import banana_project.server.thread.Protocol;
import banana_project.server.util.ConstantsLog;
import banana_project.server.util.DBConnectionMgr;
import banana_project.server.vo.ChatContentsVO;

import banana_project.server.vo.LogVO;
import banana_project.server.vo.ChatUserListVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

//채팅불러오기
//채팅저장하기
//채팅방 나가기


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

    /**
     * 채팅내용불러오기
     * @param chatnum
     * @return
     */
    public List<ChatContentsVO> ChatCall(int chatnum){
        //리턴값
        List<ChatContentsVO> crs = new ArrayList<>();
        ChatContentsVO ccvo=null;
        //쿼리결과 기본 false
        //        Boolean result = false;

        //로그작성
        ll.writeLog(ConstantsLog.ENTER_LOG,Thread.currentThread().getStackTrace()[1].getMethodName()
            ,new LogVO(Protocol.CHAT_START, "Room number :"+chatnum, ""));
        String sql= "select * from tb_chat_contents where chat_no=?";

        try{
            con=dbMgr.getConnection();
            pst=con.prepareStatement(sql);
//            pst.setString(1,chatnum);?????????????????????????????
            rs=pst.executeQuery();
            while(rs.next()){//대기하다가 오면 값넣어주기
                String chat_date=rs.getString("chat_date");
                String chat_contents=rs.getString("chat_contents");
                String user_id=rs.getString("user_id");
                ccvo= new ChatContentsVO();
                ccvo.setChat_content(chat_contents);
                ccvo.setChat_date(chat_date);
                ccvo.setChat_no(chatnum);
                ccvo.setUser_id(user_id);
                crs.add(ccvo);
            }
        }catch(SQLException se) { //채팅방없음
            se.printStackTrace();
            System.out.println("SQLException :" + se.getMessage());
            System.out.println("쿼리문 : " + sql.toString());
            ll.writeLog(ConstantsLog.ENTER_LOG,Thread.currentThread().getStackTrace()[1].getMethodName()
                    ,new LogVO(Protocol.WRONG_NUM, "Wrong Room number :"+chatnum, ""));
        }finally{
            // DB 사용한 자원 반납
            try {
                dbMgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                // 디버깅
                e.printStackTrace();
            }
        }
        return crs;
    }//end of ChatCall

    /**
     * 채팅내용 디비에 저장
     * @param chatconvo
     * @return
     */
    public int insertChat(ChatContentsVO chatconvo) { //성공은 1, 실패는 0
        //리턴값 기본 -1
        int result= -1;
        //로그저장-프로토콜 Save_Chat : 707
        ll.writeLog(ConstantsLog.ENTER_LOG,Thread.currentThread().getStackTrace()[1].getMethodName()
                ,new LogVO(Protocol.SAVE_CHAT, "Room number :"+chatconvo.getChat_no(), ""));
        String sql= "insert into TB_CHAT_CONTENTS (chat_date, chat_contents, user_id,chat_no)values(?,?,?,?)";
        try {
            con=dbMgr.getConnection();
            pst=con.prepareStatement(sql);
            pst.setString(1,chatconvo.getChat_date());
            pst.setString(2,chatconvo.getChat_content());
            pst.setString(3,chatconvo.getUser_id());
            pst.setInt(4,chatconvo.getChat_no());
            result=pst.executeUpdate();// 실행문에 따라서 성공하면 1건만 바꾸니까 1이 들어올것이다.
        }catch(SQLException se) {
            se.printStackTrace();
            System.out.println("SQLException :" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            //대화 저장 실패 로그
            ll.writeLog(ConstantsLog.ENTER_LOG, Thread.currentThread().getStackTrace()[1].getMethodName(),
                    new LogVO(Protocol.SAVE_FAIL, chatconvo.toString(), chatconvo.getUser_id()));
        } finally {//사용반납
            try {
                dbMgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }//end of finally
        return result;
    }//end of insertChat

    //채팅방을 나가고 싶다.


    /**
     * 채팅방 나가기
     * @param chatlistvo
     */
    public void delChatContents(ChatUserListVO chatlistvo) {
        ll.writeLog(ConstantsLog.ENTER_LOG,Thread.currentThread().getStackTrace()[1].getMethodName()
               ,new LogVO(Protocol.DEL_CHAT, "Room number :"+chatlistvo.getChat_no(), chatlistvo.getUser_id()));
        //결과값 기본
        int result= -1;

        //flag는 나가면 1 있으면 0
        String sql= "update TB_CHAT_USER_LIST  set flag=1 where chat_no=? and user_id=?";///이래도 되나요?
        try{
            con=dbMgr.getConnection();
            pst=con.prepareStatement(sql);
            pst.setInt(1,chatlistvo.getChat_no());////은재고수님께질문-완
            pst.setString(2,chatlistvo.getUser_id());

            result=pst.executeUpdate();
        }catch(SQLException se){
            se.printStackTrace();
            System.out.println("SQLException :" + se.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
        }finally {//사용반납
            try {
                dbMgr.freeConnection(con, pst, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }//end of finally

    }//end of delChat

}
