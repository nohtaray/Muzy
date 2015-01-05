<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.PopularArtistResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.PopularArtist" %>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="アーティストランキング（投票数順）" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <p>
            アーティストランキング（投票数順）
        </p>
        <ol>
            <%
                PopularArtistResultSet popularArtists = (PopularArtistResultSet) request.getAttribute("popularArtists");
                for (PopularArtist pa : popularArtists) {
            %>
            <li>
                <a href="ArtistServlet?id=<%= pa.artist.artistId%>"><%= pa.artist.name%></a>（<%= pa.spentTicketsSum %>tickets）
            </li>
            <%
                }
            %>
        </ol>

    </c:param>
</c:import>