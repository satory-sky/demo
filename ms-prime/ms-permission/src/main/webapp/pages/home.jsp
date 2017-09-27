<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <!-- Bootstrap -->
    <link type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
    <link type="text/css" href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
</head>
&nbsp<a href="${pageContext.request.contextPath}/v1/security/options">Options</a>
</br>
</br>
<b>SSO Link:&nbsp&nbsp</b>${ssoSpInitializationUrl}
</br>
&nbsp<a href="${ssoSpInitializationUrl}">SSO SP initialization</a>
</br>
</br>
<b>SSO Link:&nbsp&nbsp</b>${ssoIdpInitializationUrl}
</br>
&nbsp<a href="${ssoIdpInitializationUrl}">SSO IDP initialization</a>
</body>
</html>
