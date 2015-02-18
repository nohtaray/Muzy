<%@page import="jp.ac.jec.jz.gr03.entity.Artist"%>
<%@page import="jp.ac.jec.jz.gr03.entity.User"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    User user = (User) request.getAttribute("user");
    Artist artist = (Artist) request.getAttribute("artist");
    boolean isArtist = artist != null;
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="マイページ" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <h2>マイページ</h2>

        <div class="row">
            <div class="col-md-5">
                <div class="list-group">
                    <a href="UserServlet?id=<%= user.userId%>" class="list-group-item"><b>あなたの公開プロフィール</b></a>
                    <a href="EditUserServlet" class="list-group-item"><b>登録情報を編集する</b></a>
                    <a href="BuyTicketServlet" class="list-group-item"><b>チケットを購入する</b></a>
                    <a href="PointHistoryServlet" class="list-group-item"><b>ポイントの獲得・利用履歴</b></a>
                    <a href="MyListServlet" class="list-group-item"><b>マイリスト</b></a>
                </div>
                    
                <div class="list-group">
                <% if (isArtist) {%>
                    <a href="MyMusicServlet" class="list-group-item"><b>投稿した楽曲</b></a>
                    <a href="NewMusicServlet" class="list-group-item"><b>楽曲を投稿する</b></a>
                    <a href="EditArtistServlet" class="list-group-item"><b>アーティスト情報を編集する</b></a>
                <% } else { %>
                    <a href="SignUpArtistServlet" class="list-group-item"><b>アーティストとして登録する</b></a>
                <% }%>
                </div>
            </div>
        </div>

    </c:param>
</c:import>