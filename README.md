<div align="center">
<img src="https://capsule-render.vercel.app/api?type=Waving&color=FFD700&height=200&section=header&text=BANANA%20TALK!&fontSize=50" />

### 🍌 자바 기반 채팅 프로그램 🍌
(Java-Based Chat Program)
<br><br>
<img width=30% src="https://user-images.githubusercontent.com/119314477/216045114-c2d3cb0c-ff53-405e-ae26-ce984480daf8.PNG">

---

### 📚USED

<img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Conda-Forge&logoColor=white" />
<img src="https://img.shields.io/badge/Oracle%20SQL-F80000?style=flat&logo=Oracle&logoColor=white" /><br>
<img src="https://img.shields.io/badge/Visual%20Studio%20Code-007ACC?style=flat&logo=VisualStudioCode&logoColor=white" />
<img src="https://img.shields.io/badge/IntelliJ-000000?style=flat&logo=IntelliJ IDEA&logoColor=white"/>

<br><br>
</div>

## 1. 프로젝트 소개
바나나톡은 Java 소켓 통신과 Oracle DB를 이용한 멀티 채팅 프로그램입니다.
<br><br>

## 2. 시작 가이드
1. 자바와 오라클을 설치해주세요. (개발에는 JDK 17.0.5와 오라클 11g 11.1.0.7.0을 사용했습니다)

2. DB 테이블을 생성해주세요. - app\src\main\java\banana_project\server\util\oracle_query.sql

3. DB 주소(13번줄)와 USER, PW(17번줄)를 확인해주세요. - app\src\main\java\banana_project\server\util\DBConnectionMgr.java

4. 서버 주소(144번줄)를 확인해주세요. - app\src\main\java\banana_project\client\login\Client.java

5. 서버를 실행한 후 클라이언트를 실행해주세요.  
    서버: app\src\main\java\banana_project\server\thread\Server.java  
    클라이언트: app\src\main\java\banana_project\client\login\Client.java
<br><br>

## 3. 주요 기능
<br><br>

## 4. 멤버구성
- [강동현](https://github.com/welsper53) : 친구로직, 채팅 목록 로직, 친구&채팅 목록, 친구&채팅 추가, 스레드(친구&채팅 목록, 친구&채팅 추가)
- [김도희](https://github.com/dodoringring) : 채팅방 로직, 회원가입, 채팅방 스레드
- [목정윤](https://github.com/jungyunmok) : 로그인, 아이디&비밀번호 찾기, 채팅방, 스레드(회원가입, 로그인, 아이디찾기, 채팅방)
- [박소연](https://github.com/Soyeonthdus) : 마이페이지, 마이페이지 스레드
- [송우진](https://github.com/woojinyy) : 채팅방, 비밀번호 재설정 스레드
- [이은재](https://github.com/77EJ77) : 멤버 로직, 로그 로직, 비밀번호 찾기 스레드
<br><br>

## 5. PPT 자료
[바나나톡 PPT 바로가기](https://docs.google.com/presentation/d/1ifeG_DB13EMnl8IcqKHrzOZzpquR1uUyHiAaPD50YjA/edit?usp=sharing)
<br>

<div align="center">
<img src="https://capsule-render.vercel.app/api?type=Waving&color=FFD700&height=200&section=footer&text=👋Goodbye!&fontSize=50" />
</div>
