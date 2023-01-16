-- 2023-01-03 오라클 쿼리문
-- 수정 전

CREATE TABLE TB_USER
(
    user_id         VARCHAR2(32)	PRIMARY KEY,
    user_pw	        VARCHAR2(256)	NOT NULL,
    user_name	    VARCHAR2(32)	NOT NULL,
    user_hp	        VARCHAR2(32)	UNIQUE      NOT NULL,
    user_nickname	VARCHAR2(32)    UNIQUE      NOT NULL,
    fail_cnt	    NUMBER(32)	    DEFAULT 0   NOT NULL,
    ins_date	    DATE	        DEFAULT	SYSDATE,
    upd_date	    DATE	        DEFAULT	SYSDATE,
    login_date	    DATE	        DEFAULT	SYSDATE,
    salt            VARCHAR2(128)   NOT NULL,
    status          NUMBER(2)       DEFAULT 0   NOT NULL
);
COMMENT ON TABLE TB_USER IS '사용자 정보';
COMMENT ON COLUMN TB_USER.user_id IS '사용자 ID(이메일)';
COMMENT ON COLUMN TB_USER.user_pw IS '패스워드';
COMMENT ON COLUMN TB_USER.user_name IS '이름';
COMMENT ON COLUMN TB_USER.user_hp IS '연락처';
COMMENT ON COLUMN TB_USER.user_nickname IS '닉네임';
COMMENT ON COLUMN TB_USER.fail_cnt IS '로그인 실패 카운트';
COMMENT ON COLUMN TB_USER.ins_date IS '가입 날짜 (예. 2022-12-26)';
COMMENT ON COLUMN TB_USER.upd_date IS '계정 수정 날짜 (예. 2022-12-26)';
COMMENT ON COLUMN TB_USER.login_date IS '로그인 시간 (예. 2022-12-26)';
COMMENT ON COLUMN TB_USER.salt IS '비밀번호 암호화용 코드';
COMMENT ON COLUMN TB_USER.status IS '회원 상태(탈퇴 구분)';


CREATE TABLE TB_CHAT_LIST
(
    chat_no	        NUMBER(32)	    PRIMARY KEY,
    chat_title	    VARCHAR2(512)	DEFAULT	'새 채팅'  NOT NULL
);
COMMENT ON TABLE TB_CHAT_LIST IS '채팅방 목록';
COMMENT ON COLUMN TB_CHAT_LIST.chat_no IS '채팅방 번호; auto_increment';
COMMENT ON COLUMN TB_CHAT_LIST.chat_title IS '채팅 타이틀(초대된 사람 이름)';
ALTER TABLE TB_CHAT_LIST MODIFY (chat_title VARCHAR2(512));


CREATE TABLE TB_CHAT_CONTENTS
(
    chat_date	    DATE	        DEFAULT SYSDATE     PRIMARY KEY,
    chat_contents	VARCHAR2(512),
    user_id	        VARCHAR2(32)	NOT NULL,
    chat_no	        NUMBER(32)	    NOT NULL
);
COMMENT ON TABLE TB_CHAT_CONTENTS IS '채팅 내용';
COMMENT ON COLUMN TB_CHAT_CONTENTS.chat_date IS '메시지 시간 (예. 2022-12-26 15:46:34)';
COMMENT ON COLUMN TB_CHAT_CONTENTS.chat_contents IS '채팅 내용';
COMMENT ON COLUMN TB_CHAT_CONTENTS.user_id IS '사용자 리스트';
COMMENT ON COLUMN TB_CHAT_CONTENTS.chat_no IS '채팅방 번호';


CREATE TABLE TB_CHAT_USER_LIST
(
    chat_no	    NUMBER(32)	    NOT NULL,
    user_id	    VARCHAR2(32)	NOT NULL,
    flag        NUMBER(32)      DEFAULT 0       NOT NULL
);
COMMENT ON TABLE TB_CHAT_USER_LIST IS '채팅 참여 유저 목록';
COMMENT ON COLUMN TB_CHAT_USER_LIST.chat_no IS '채팅방 번호';
COMMENT ON COLUMN TB_CHAT_USER_LIST.user_id IS '사용자 리스트';
COMMENT ON COLUMN TB_CHAT_USER_LIST.flag IS '0: 입장 / 1: 퇴장';


CREATE TABLE TB_FRIENDS_LIST
(
    user_id	    VARCHAR2(32)	PRIMARY KEY,
    f_id	    VARCHAR2(32)	NOT NULL
);
COMMENT ON TABLE TB_FRIENDS_LIST IS '친구 목록';
COMMENT ON COLUMN TB_FRIENDS_LIST.user_id IS '사용자 ID';
COMMENT ON COLUMN TB_FRIENDS_LIST.f_id IS '친구 ID';


CREATE TABLE TB_USER_LOG
(
    login_date	DATE	        DEFAULT SYSDATE	    PRIMARY KEY,
    protocol	NUMBER(32)	    NOT NULL,
    user_id	    VARCHAR2(32)	NOT NULL,
    comments	VARCHAR2(256)
);
COMMENT ON TABLE TB_USER_LOG IS '사용자 로그';
COMMENT ON COLUMN TB_USER_LOG.login_date IS '로그 시간 (예. 2022-12-26 15:46:34)';
COMMENT ON COLUMN TB_USER_LOG.protocol IS '프로토콜';
COMMENT ON COLUMN TB_USER_LOG.user_id IS '사용자ID';
COMMENT ON COLUMN TB_USER_LOG.comments IS '로그 내용';

CREATE TABLE TB_CHAT_LOG
(
    chat_date	DATE	        DEFAULT SYSDATE	    PRIMARY KEY,
    protocol	NUMBER(32)	    NOT NULL,
    user_id	    VARCHAR2(32)	NOT NULL,
    comments	VARCHAR2(256)
);
COMMENT ON TABLE TB_CHAT_LOG IS '채팅 로그';
COMMENT ON COLUMN TB_CHAT_LOG.chat_date IS '로그 시간 (예. 2022-12-26 15:46:34)';
COMMENT ON COLUMN TB_CHAT_LOG.protocol IS '프로토콜';
COMMENT ON COLUMN TB_CHAT_LOG.user_id IS '사용자ID';
COMMENT ON COLUMN TB_CHAT_LOG.comments IS '로그 내용';