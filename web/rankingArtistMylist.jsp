<%@page import="jp.ac.jec.jz.gr03.entity.ArtistMylistDetail"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.ArtistMylistDetailResultSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="アーティストランキング（マイリスト登録数順）" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <p>
            アーティストランキング（マイリスト登録数順）
        </p>
        <ol>
            <%
                ArtistMylistDetailResultSet artistMylistDetails = (ArtistMylistDetailResultSet) request.getAttribute("artistMylistDetails");
                for (ArtistMylistDetail artistMylistDetail : artistMylistDetails) {
            %>
            <li>
                <a href="ArtistServlet?id=<%= artistMylistDetail.artist.artistId%>"><%= h(artistMylistDetail.artist.name)%></a>（<%= artistMylistDetail.mylistDetailCount %>）
            </li>
            <%
                }
            %>
        </ol>

    </c:param>
</c:import>