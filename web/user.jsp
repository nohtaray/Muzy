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
        <link rel="stylesheet" type="text/css" href="css/user.css">
    </c:param>
    <c:param name="content">

        <div class="row">
            <div class="col-md-8">
                <div class="clearfix">
                    <img src="<%= h(user.iconImageFile)%>" class="pull-left" width="64" height="64" id="icon-image">
                    <h2 id="user-name"><%= h(user.name)%></h2>
                </div>
                <hr>
                <div>
                    <%= br(h(user.introduction))%>
                </div>
            </div>
        </div>

    </c:param>
</c:import>