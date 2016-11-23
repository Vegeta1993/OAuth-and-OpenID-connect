<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>logout</title>
</head>
<body>
	<%
	session.removeAttribute("realm");
	session.removeAttribute("email");
	session.removeAttribute("at");
	session.removeAttribute("user");
	session.removeAttribute("uid");
	session.invalidate();
	Cookie[] cookies = request.getCookies();
	for (Cookie cookie : cookies) {
		cookie.setMaxAge(0);
		cookie.setValue(null);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
	rd.forward(request, response);
	%>
</body>
</html>