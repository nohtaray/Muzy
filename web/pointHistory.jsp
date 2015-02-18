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
    <c:param name="title" value="ポイント獲得・利用履歴" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <div class="row">
            <div class="col-sm-7">
                <div class="clearfix">
                    <h3>ポイント獲得・利用履歴</h3>
                    <div class="pull-right">
                        所有ポイント：<%= point.pointCount%>
                    </div>
                </div>
                <hr>
            </div>

            <div class="col-sm-6">
                <table class="table table-hover table-bordered">
                    <thead>
                        <tr>
                            <th class="text-center">ポイント</th>
                            <th class="text-center">詳細</th>
                            <th class="text-center">日時</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (PointGetHistory history : histories) {%>
                        <tr>
                            <td class="text-right"><%= history.gotPoints%></td>
                            <td><%= h(history.description)%></td>
                            <td><%= dateToString(history.createdAt)%></td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </div>
        </div>
    </c:param>
</c:import>