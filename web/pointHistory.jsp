<%@page import="jp.ac.jec.jz.gr03.entity.Point"%>
<%@page import="jp.ac.jec.jz.gr03.entity.PointGetHistory"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.PointGetHistoryResultSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    Point point = (Point) request.getAttribute("point");
    PointGetHistoryResultSet histories = (PointGetHistoryResultSet) request.getAttribute("histories");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
        <%-- スクリプトのインポートなど。例↓
        <script type="text/javascript" src="js/template.js"></script>
        <link rel="stylesheet" type="text/css" href="css/template.css">
        --%>
    </c:param>
    <c:param name="content">

        所有ポイント：<%= point.pointCount%>
        <table border="1">
            <caption>ポイント獲得・利用履歴</caption>
            <thead>
            <th>日時</th>
            <th>ポイント</th>
            <th>詳細</th>
        </thead>
        <tbody>
            <% for (PointGetHistory history : histories) {%>
            <tr>
                <td><%= history.createdAt%></td>
                <td><%= history.gotPoints%></td>
                <td><%= history.description%></td>
            </tr>
            <% }%>
        </tbody>
    </table>

</c:param>
</c:import>