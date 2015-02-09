<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String errorMessage = (String)request.getAttribute("javax.servlet.error.message");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="403 Forbidden" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        
        <h2>403 Forbidden</h2>
        <p><%= errorMessage != null ? br(h(errorMessage)) : "" %></p>
        
    </c:param>
</c:import>