package com.allscore.test.dto;

import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;

public class NpayAgentPayDetail
{
	/** NUMBER(22,0) */
	BigDecimal systrace;
	/** VARCHAR2(50,0) */
	String applyClient;
	/** VARCHAR2(50,0) */
	String applySubClient;
	/** VARCHAR2(50,0) */
	String applyAccount;
	/** VARCHAR2(20,0) */
	String applyRrn;
	/** VARCHAR2(20,0) */
	String applyFeerrn;
	/** VARCHAR2(50,0) */
	String channel;
	/** VARCHAR2(20,0) */
	String channelType;
	/** VARCHAR2(50,0) */
	String transferTarget;
	/** VARCHAR2(50,0) */
	String targetPayeeno;
	/** VARCHAR2(100,0) */
	String targetPayeeName;
	/** VARCHAR2(200,0) */
	String channelReturn;
	/** VARCHAR2(20,0) */
	String channelRrn;
	/** VARCHAR2(30,0) */
	String transferType;
	/** NUMBER(16,2) */
	BigDecimal applyAmount;
	/** NUMBER(16,2) */
	BigDecimal applyFeeamount;
	/** VARCHAR2(30,0) */
	String businessType;
	/** CHAR(2,0) */
	String status;
	/** VARCHAR2(20,0) */
	String batchno;
	/** VARCHAR2(50,0) */
	String applyReason;
	/** DATE(7,0) */
	Date applyDate;
	/** DATE(7,0) */
	Date finishDate;
	/** DATE(7,0) */
	Date verifyDate;
	/** VARCHAR2(50,0) */
	String verifyUser;
	/** VARCHAR2(80,0) */
	String bankname;
	/** VARCHAR2(50,0) */
	String bankprovince;
	/** VARCHAR2(50,0) */
	String bankcity;
	/** VARCHAR2(200,0) */
	String branchname;
	/** CHAR(2,0) */
	String pubpriflag;
}
