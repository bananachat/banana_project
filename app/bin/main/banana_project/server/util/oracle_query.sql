-- 2023-01-03 오라클 쿼리문
-- 수정 전

CREATE TABLE TB_USER
(
	user_id	VARCHAR2(32)	NOT NULL	PRIMARY KEY,
	user_pw	VARCHAR2(256)	NOT NULL,
	user_name	VARCHAR2(32)	NOT NULL,
	user_hp	VARCHAR2(32)	UNIQUE,
	user_nickname		VARCHAR2(32),
	fail_cnt	NUMBER(32)	NOT NULL DEFAULT 0,
	ins_date	DATE	DEFAULT	SYSDATE,
	upd_date	DATE	DEFAULT	SYSDATE,
	login_date	DATE	DEFAULT	SYSDATE
);


CREATE TABLE TB_CHAT_LIST
(
	chat_no	NUMBER(32)	NOT NULL	PRIMARY KEY,
	chat_title	VARCHAR2(32)	DEFAULT	'새 채팅'
);


CREATE TABLE TB_CHAT_CONTENTS
(
	chat_content	VARCHAR2(512),
	chat_date	DATE	DEFAULT SYSDATE,
	user_id	VARCHAR2(32)	NOT NULL,
	chat_no	NUMBER(32)	NOT NULL
);


CREATE TABLE TB_FRIENDS_LIST
(
	user_id	NUMBER(32)	NOT NULL,
	f_id	VARCHAR2(32)	NOT NULL
);


CREATE TABLE TB_USER_LOG
(
	login_date	DATE	DEFAULT SYSDATE	PRIMARY KEY	NOT NULL,
	protocol	NUMBER(32)	NOT NULL,
	comments	VARCHAR2(256),
	user_id	VARCHAR2(32)	NOT NULL
);


CREATE TABLE TB_CHAT_LOG
(
	chat_date	DATE	DEFAULT SYSDATE	PRIMARY KEY	NOT NULL,
	protocol	NUMBER(32)	NOT NULL,
	user_id	VARCHAR2(32)	NOT NULL,
	comments	VARCHAR2(256)
);


CREATE TABLE TB_CHAT_USER_LIST(
	chat_no	NUMBER(32)	NOT NULL,
	user_id	VARCHAR2(32)	NOT NULL,
	flag NUMBER(2) NOT NULL
);