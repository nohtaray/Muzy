<%@page import="jp.ac.jec.jz.gr03.entity.User"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    User user = (User) request.getAttribute("user");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="スポンサー" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <h2>スポンサーページ</h2>
        <div>
            スポンサーの紹介。広告エリア
        </div>
        <div>
            <% if (user == null) { %>
            <small>ログインしてこのページにアクセスするとポイントが貯まります</small>
            <% } else { %>
            <small>ポイントが貯まりました。</small>
            <% }%>
        </div>
    </c:param>
</c:import>