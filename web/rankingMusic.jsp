<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="楽曲ランキング" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        
        <p>
            楽曲ランキング
        </p>
        <ul>
            <li><a href="RankingMusicAdvertisementServlet">広告ポイント数</a></li>
            <li><a href="RankingMusicViewServlet">再生数</a></li>
            <li><a href="RankingMusicMylistServlet">マイリスト登録数</a></li>
        </ul>
        
    </c:param>
</c:import>