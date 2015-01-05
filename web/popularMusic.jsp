<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.PopularMusicResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.PopularMusic" %>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="楽曲ランキング（広告ポイント順）" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <p>
            楽曲ランキング（広告ポイント順）
        </p>
        <ol>
            <%
                PopularMusicResultSet popularMusics = (PopularMusicResultSet) request.getAttribute("popularMusics");
                for (PopularMusic pm : popularMusics) {
            %>
            <li>
                <a href="MusicServlet?id=<%= pm.music.musicId%>"><%= pm.music.title%></a>（<%= pm.spentPointsSum %>pts）
            </li>
            <%
                }
            %>
        </ol>

    </c:param>
</c:import>