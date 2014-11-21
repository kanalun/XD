<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Date"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
.NCB {
	float: right;
	margin-right: 9px;
	margin-top: 1px;
}

.NCB.off {
	background:
		url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA8AAAAPCAYAAAA71pVKAAABYUlEQVR42o1Sf6sBQRSdz/V6n8eH4hvYQhHZLCX5sbIp8ZeQGEp2EwZtOW/OeMR6eLdOuzP3nnPmzlwhIuG6bqJcLstMJnO2LAv8cs198Sq63e53sVhU2WwWvV4Pvu/jcDhgtVrB8zzk83kwz7onIh3a7TZOpxPCMHzCfr9Hq9VCOp0+PwgUCgVFdaUUjsfjW9RqNbDeEJvNZjyXy2G73RryJ2w2G7rzFAlRKpXmnU4Hu93u36jX69A8KdjDbDYzikQsFnsJBmum06npXfA5eKNBENzwinjNLxYLJJNJGOfJZIL1ev2AKPE+NxwOkUqlzsK27Xmj0TDuUVyJ0f1qtQrNk0L/xDkYUkosl8uPYL/alQKXidNPpajGi6PIO+hRBetvQ6Kv/os9OI5jBP7CeDw2RNax/mFEuaGPr36PhH6/j9FohMFgYNZ6fMH8E/E+KpVKQo+fpAOfkV+utWs8WvsDm9CMlE2NWuUAAAAASUVORK5CYII=")
		no-repeat scroll 0 0 transparent;
	height: 15px;
	overflow: hidden;
	width: 15px;
}

.NCB.on {
	background:
		url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA8AAAAPCAYAAAA71pVKAAABPElEQVR42oVTS66CQBCccxmvA4eSEwhKQBOIoInyW7AxhGjY4E6OwHL6dc1DnvJAJinITHdVNzWNEIO1Xq8N0zQbhmRQ925wLqYWJywZbUeYAuLLMaK0LIu22+0kEO86+RPgw9a2bdrtdrPoRFpFdBxntdlsyPM88n1/FshDB1zMEPx4QjEMw3/QNG303HVdkBvBVeXxeKTz+fwBXdcVIDCMQYCrS5hFcRxTmqY9XsR3gfd4FEXKfVU5SRLK81xhSHwXeOWADJ7Y7/dPtHG9Xnt8W4jDOOY14nA4rNhxKoqCyrKcBci4riAIfieO3W5PpxPd73eqquormIT7bvshuVwuC7Zeov26rkcBIm6Fq0rkf4xolmUL/g41aTDkdrvR4/FQ3WCPT0MceZM/CN+jwT406KSbJIk9C6yGuT+BVVPEy3wIqgAAAABJRU5ErkJggg==")
		no-repeat scroll 0 0 transparent;
	height: 15px;
	overflow: hidden;
	width: 15px;
}
</style>
<title>简历</title>
</head>
<body>
<%=new Date()%>
<form action="ResumeServlet" method="post">
<div>求职意向：<input name="resume.jobI" value="软件工程师" readonly="readonly" /></div>
<div>姓名：<input name="name"></input></div>
<div>电话：<input name="telephone"></input></div>
<div>邮箱：<input name="email"></input></div>
<div>简历：<input type="file" name="rsfile"></input></div>
<input type="submit" value="sub" /></form>

<div class="FCB PBB GCB">
<div class="DCB" style="width: 0px;"></div>
<div class="LEB OCB"></div>
<div class="ECB">
<div class="KY MCB JCB" title="All waves you have contributed to"><span class="EY DY">By Me</span>
<div class="CCB"></div>
</div>
</div>
<div class="button NCB up cat enabled menu off" title="More actions" style="display: none;"></div>

<div class="FCB PBB GCB">
	<div class="DCB" style="width: 0px;"></div>
	<div class="LEB OCB">图标</div>
	<div class="ECB">文本
		<div class="KY MCB JCB" title="All waves you have contributed to">
			<span class="EY DY">By Me</span>
			<div class="CCB"></div>
		</div>
	</div>
	<div class="button NCB up cat enabled menu off" title="More actions" style="display: none;"></div>
</div>
</div>
</body>
</html>
