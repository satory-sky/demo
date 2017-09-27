<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Details</title>
    <!-- Bootstrap -->
    <link type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
    <link type="text/css" href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
    <%-- Javascript --%>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</head>
<body>
&nbsp<a href="${pageContext.request.contextPath}/v1/security/home">Home</a>
</br>
</br>
<b>Assertion Details:</b>
</br>
<table width="59%" border="1">
    <%
        List<String[]> data = (List<String[]>) request.getAttribute("data");
        for (String[] row : data) {
    %>
    <tr>
        <%
            for (String value : row) { %>
        <td>
            <%= value%>
        </td>
        <%
            }
        %>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>