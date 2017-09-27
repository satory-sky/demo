<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Options</title>
    <!-- Bootstrap -->
    <link type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
    <link type="text/css" href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" />
</head>
<body>
<form>
    &nbsp<a href="${pageContext.request.contextPath}/v1/security/home">Home</a>
    </br>
    </br>
    PF Host:<br>
    <input type="text" name="pf_host" value=<%=request.getSession().getAttribute("pf_host") != null ? request.getSession().getAttribute("pf_host") : ""%>>
    <br>
    PF port:<br>
    <input type="text" name="pf_port" value=<%=request.getSession().getAttribute("pf_port") != null ? request.getSession().getAttribute("pf_port") : ""%>>
    <br>
    Local Cookie Path:<br>
    <input type="text" name="local_cookie_path" value=<%=request.getSession().getAttribute("local_cookie_path") != null ? request.getSession().getAttribute("local_cookie_path") : ""%>>
    <br>
    <br>
    <input type="submit" value="Save settings">
</form>
</body>
</html>
