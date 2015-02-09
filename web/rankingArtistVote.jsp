<%@page import="jp.ac.jec.jz.gr03.entity.ArtistVote"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.ArtistVoteResultSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                ArtistVoteResultSet artistVotes = (ArtistVoteResultSet) request.getAttribute("artistVotes");
                for (ArtistVote artistVote : artistVotes) {
            %>
            <li>
                <a href="ArtistServlet?id=<%= artistVote.artist.artistId%>"><%= h(artistVote.artist.name)%></a>（<%= artistVote.spentTicketsSum%>tickets）
            </li>
            <%
                }
            %>
        </ol>

    </c:param>
</c:import>