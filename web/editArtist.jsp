<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String name = (String) request.getAttribute("name");
    String introduction = (String) request.getAttribute("introduction");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        アーティスト情報編集

        <form method="post" action="EditArtistServlet">
            <input type="text" name="name" value="<%= name%>"><br>
            <textarea placeholder="アーティストプロフィール" name="introduction"><%= introduction%></textarea><br>
            <input type="submit" value="更新">
        </form>

        <a href="WithdrawArtistServlet">アーティスト登録解除</a>
    </c:param>
</c:import>
