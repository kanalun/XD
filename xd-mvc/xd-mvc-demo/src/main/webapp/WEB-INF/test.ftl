<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FreeMarker示例!</title>
</head>
<body>
<h2>FreeMarker25555555555555555示例!</h2>

<h3>1,优先从Action中获得数据</h3>
默认:Name:${name?if_exists}<br/>
Action中User.Name:${user.name?if_exists}<br/>
${user.birth?date?string("yyyy-MM-dd HH:mm:ss zzzz")}
<br/>
<h3>2,指定特定的"域"中获取数据</h3>
Action:${Action.name?if_exists}<br/>
Request:${Request.name?if_exists}<br/>
Session:${Session.name?if_exists}<br/>
Application:${Application.name?if_exists}<br/>
RequestParameters:${RequestParameters.name?if_exists}<br/>
Cookies:${Cookies.name?if_exists.value?if_exists}
<br/>
</body>
</html>