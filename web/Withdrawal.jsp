<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="name" value="taikai" />
    <c:param name="title" value="退会" />
    <c:param name="content">
		
		<h1>退会しました</h1>
		<% request.setCharacterEncoding("UTF-8");%>
	</c:param>
</c:import>