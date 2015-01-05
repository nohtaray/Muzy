<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="アーティストランキング" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        
        <p>
            アーティストランキング
        </p>
        <ul>
            <li><a href="RankingArtistVoteServlet">投票数</a></li>
        </ul>
        
    </c:param>
</c:import>