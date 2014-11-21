package com.allscore.test.dto;

import java.math.BigDecimal;
import java.lang.String;
import java.util.Date;

public class NpayAgentRecvDetail
{
	/** VARCHAR2(20,0) */
	String tranno;
	/** VARCHAR2(20,0) */
	String tranType;
	/** DATE(7,0) */
	Date createDate;
	/** DATE(7,0) */
	Date payDate;
	/** VARCHAR2(200,0) */
	String title;
	/** VARCHAR2(20,0) */
	String extorderid;
	/** VARCHAR2(30,0) */
	String draweeAccount;
	/** VARCHAR2(30,0) */
	String payeeAccount;
	/** NUMBER(16,2) */
	BigDecimal tranamt;
	/** NUMBER(16,2) */
	BigDecimal tranFee;
	/** VARCHAR2(200,0) */
	String notifyUrl;
	/** VARCHAR2(5,0) */
	String tranStatus;
	/** VARCHAR2(200,0) */
	String remark;
	/** VARCHAR2(250,0) */
	String extRemark;
	/** VARCHAR2(10,0) */
	String extOrderdate;
	/** VARCHAR2(30,0) */
	String extMethod;
	/** VARCHAR2(50,0) */
	String client;
	/** VARCHAR2(50,0) */
	String channel;
	/** VARCHAR2(50,0) */
	String transferTarget;
	/** VARCHAR2(20,0) */
	String oriTranno;
	/** VARCHAR2(20,0) */
	String oriOrderid;
	/** DATE(7,0) */
	Date checkDate;
	/** DATE(7,0) */
	Date confirmDate;
	/** DATE(7,0) */
	Date finishDate;
	/** VARCHAR2(20,0) */
	String checker;
	/** VARCHAR2(20,0) */
	String confirmer;
}
