<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String errorMessage = (String)request.getAttribute("javax.servlet.error.message");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="404 Not Found" />
    <c:param name="header">
        <%-- スクリプトのインポートなど。例↓
        <script type="text/javascript" src="js/template.js"></script>
        <link rel="stylesheet" type="text/css" href="css/template.css">
        --%>
    </c:param>
    <c:param name="content">
        
        <%-- ここにHTMLを記述 --%>
        <h2>404 Not Found</h2>
        <p><%= errorMessage != null ? br(h(errorMessage)) : "" %></p>
        
    </c:param>
</c:import>