<%@page import="jp.ac.jec.jz.gr03.entity.Artist"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="アーティスト登録" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        アーティスト登録
        <form method="post" action="">
            名前：<input type="text" name="name"><br>
            アーティスト用プロフィール：<br>
            <textarea name="introduction"></textarea><br>
            <input type="submit" value="更新">
        </form>
    </c:param>
</c:import>
