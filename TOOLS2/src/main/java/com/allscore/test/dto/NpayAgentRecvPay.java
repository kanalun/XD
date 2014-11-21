package com.allscore.test.dto;

import java.math.BigDecimal;
import java.lang.String;
import java.util.Date;

public class NpayAgentRecvPay
{
	/** VARCHAR2(30,0) */
	String payno;
	/** VARCHAR2(20,0) */
	String tranno;
	/** VARCHAR2(10,0) */
	String payType;
	/** VARCHAR2(50,0) */
	String finalType;
	/** CHAR(1,0) */
	String payStstus;
	/** DATE(7,0) */
	Date applyDate;
	/** DATE(7,0) */
	Date transferDate;
	/** VARCHAR2(30,0) */
	String cardNo;
	/** VARCHAR2(30,0) */
	String seriesNo;
	/** NUMBER(16,2) */
	BigDecimal payAmt;
	/** VARCHAR2(20,0) */
	String reqno;
	/** VARCHAR2(30,0) */
	String channel;
	/** VARCHAR2(50,0) */
	String transferTarget;
	/** VARCHAR2(25,0) */
	String bankTraceDate;
	/** VARCHAR2(50,0) */
	String bankTraceNo;
}
