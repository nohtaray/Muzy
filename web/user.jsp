<%@page import="jp.ac.jec.jz.gr03.entity.User"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    User user = (User) request.getAttribute("user");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title">
        <%= h(user.name) + " さんのプロフィール"%>
    </c:param>
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <%= h(user.name) + " さんのプロフィール"%>
        <dl>
            <dt>name</dt><dd><%= h(user.name)%></dd>
            <dt>introduction</dt><dd><%= br(h(user.introduction))%></dd>
        </dl>

    </c:param>
</c:import>