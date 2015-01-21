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
        <%-- スクリプトのインポートなど。例↓
        <script type="text/javascript" src="js/template.js"></script>
        <link rel="stylesheet" type="text/css" href="css/template.css">
        --%>
    </c:param>
    <c:param name="content">

		<h2>マイページ</h2>

		name: <%= user.name%>
		<div class="row">
			<div class="col-md-4">
				<div class="list-group">
					<a href="UserServlet?id=<%= user.userId%>" class="list-group-item"><b>あなたの公開プロフィール</b></a>
					<a href="EditUserServlet" class="list-group-item"><b>ユーザ情報編集</b></a>
					<a href="MyListServlet" class="list-group-item"><b>マイリスト</b></a>
					<a href="BuyTicketServlet" class="list-group-item"><b>チケット購入</b></a>
					<a href="PointHistoryServlet" class="list-group-item"><b>ポイント獲得・利用履歴</b></a>
					<a href="TicketHistoryServlet" class="list-group-item"><b>チケット獲得・利用履歴</b></a>
				</div>
			</div>
		</div>
		<% if (isArtist) {%>
		<div>
			アーティスト登録があります
			（<a href="ArtistServlet?id=<%= artist.artistId%>"><%= artist.name%></a>）
			<div class="row">
				<div class="col-md-4">
					<div class="list-group">
						<a href="MyMusicServlet" class="list-group-item"><b>投稿した楽曲</b></a>
						<a href="NewMusicServlet" class="list-group-item"><b>楽曲投稿</b></a>
						<a href="EditArtistServlet" class="list-group-item"><b>アーティスト情報編集</b></a>
					</div>
				</div>
			</div>
		</div>
		<% } else { %>
		<ul>
			<li><a href="SignUpArtistServlet">アーティスト登録</a></li>
		</ul>
		<% }%>

    </c:param>
</c:import>