package com.xd.tools.test;

import java.math.BigDecimal;
import java.lang.String;
import java.util.Date;

public class PosOrderPay
{
	/** VARCHAR2(1,0) */
	String status;
	/** VARCHAR2(1,0) */
	String transactionType;
	/** VARCHAR2(1,0) */
	String payfeeSettleFlag;
	/** DATE(7,0) */
	Date payfeeSettleTime;
	/** VARCHAR2(40,0) */
	String contactName;
	/** VARCHAR2(15,0) */
	String phone;
	/** DATE(7,0) */
	Date refundDate;
	/** NUMBER(22,10) */
	BigDecimal id;
	/** VARCHAR2(32,0) */
	String innerOrderid;
	/** VARCHAR2(32,0) */
	String orderInfoId;
	/** VARCHAR2(15,0) */
	String merchantid;
	/** VARCHAR2(8,0) */
	String terminalid;
	/** VARCHAR2(6,0) */
	String transactionNo;
	/** VARCHAR2(14,0) */
	String transactionDate;
	/** NUMBER(22,10) */
	BigDecimal payAmount;
	/** VARCHAR2(3,0) */
	String currency;
	/** VARCHAR2(19,0) */
	String cardNo;
	/** VARCHAR2(2,0) */
	String cardType;
	/** VARCHAR2(8,0) */
	String bank;
	/** VARCHAR2(12,0) */
	String bankNo;
	/** VARCHAR2(4,0) */
	String settlementDate;
	/** VARCHAR2(6,0) */
	String batchNo;
	/** VARCHAR2(1000,0) */
	String description;
	/** DATE(7,0) */
	Date createDate;
	/** NUMBER(22,10) */
	BigDecimal returnableAmt;
	/** NUMBER(22,10) */
	BigDecimal oriPayAmount;
	/** VARCHAR2(1,0) */
	String noticeStatus;
	/** DATE(7,0) */
	Date nextNoticeTime;
	/** VARCHAR2(1,0) */
	String noticeTimes;
	/** NUMBER(22,10) */
	BigDecimal payFee;
	/** NUMBER(22,10) */
	BigDecimal netAmount;
	/** VARCHAR2(32,0) */
	String refundTransNo;
	/** VARCHAR2(1,0) */
	String amtPoolStatus;
}