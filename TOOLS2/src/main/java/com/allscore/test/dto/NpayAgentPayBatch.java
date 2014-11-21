package com.allscore.test.dto;

import java.math.BigDecimal;
import java.lang.String;
import java.util.Date;

public class NpayAgentPayBatch
{
	/** VARCHAR2(20,0) */
	String batchno;

	/** VARCHAR2(50,0) */
	String applyClient;
	/** VARCHAR2(50,0) */
	String applySubClient;
	/** NUMBER(22,0) */
	BigDecimal records;
	/** NUMBER(22,0) */
	BigDecimal successRecord;
	/** NUMBER(22,0) */
	BigDecimal failRecord;
	/** NUMBER(16,2) */
	BigDecimal successAmount;
	/** NUMBER(16,2) */
	BigDecimal failAmount;
	/** NUMBER(16,2) */
	BigDecimal totalPayamount;
	/** CHAR(2,0) */
	String status;
	/** VARCHAR2(30,0) */
	String transferType;
	/** VARCHAR2(30,0) */
	String businessType;
	/** VARCHAR2(30,0) */
	String trashStaff;
	/** DATE(7,0) */
	Date trashDate;
	/** VARCHAR2(30,0) */
	String makeBatchStaff;
	/** DATE(7,0) */
	Date createDate;
	/** DATE(7,0) */
	Date handleDate;
	/** DATE(7,0) */
	Date transferDate;
	/** VARCHAR2(30,0) */
	String applyName;
	/** VARCHAR2(50,0) */
	String channel;
	/** VARCHAR2(20,0) */
	String channelType;
	/** VARCHAR2(30,0) */
	String payAccount;
	/** NUMBER(22,0) */
	BigDecimal notifySyscount;
}
