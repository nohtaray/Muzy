<%@page import="jp.ac.jec.jz.gr03.entity.User"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    User user = (User) request.getAttribute("user");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        <form method="post" action="EditUserServlet">
        <input type="text" name="name" value="<%= user.name %>"><br>
        <input type="text" name="email" value="<%= user.email %>"><br>
        <textarea placeholder="自己紹介文" name="introduction"><%= user.introduction %></textarea><br>
        <input type="submit" value="更新">
        </form>
        
        <a href="WithdrawUserServlet">退会する</a>
    </c:param>
</c:import>