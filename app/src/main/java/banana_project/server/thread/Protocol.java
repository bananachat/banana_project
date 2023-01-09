package banana_project.server.thread;

public class Protocol {
  public static final int TALK_IN = 100;
  public static final int MESSAGE = 200;
  public static final int WHISPER = 300;
  public static final int CHANGE = 400;
  public static final int TALK_OUT = 500;
  public static final String separator = "#";
}
/*
 * final 클래스 앞에 오면 상속을 못함
 * 메소드 앞에 오면 메소드 오버라이딩 못함
 * 변수 앞에 오면 상수임
 * 
 */