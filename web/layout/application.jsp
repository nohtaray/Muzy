<%-- 
    Document   : application
    Created on : 2014/11/08, 16:39:27
    Author     : yada

    全画面共通の部分
--%>
<%@page import="jp.ac.jec.jz.gr03.entity.User"%>
<%@page import="jp.ac.jec.jz.gr03.util.Authorizer"%>
<%
	Authorizer auth = new Authorizer(session);
	User userLoggedIn = auth.getUserLoggedInAs();
%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${param.title}</title>
        <script type="text/javascript" src="js/lib/jquery-2.1.1.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/application.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
        <link rel="stylesheet" type="text/css" href="css/common.css">
        <link rel="stylesheet" type="text/css" href="css/application.css">
        ${param.header}
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="./">Muzy</a>
                </div>
            </div>
        </nav>
        <header id="header">
            <h1><a href="./"><img src="img/muzy.png" Border="0" Width="200" Height="80"></a></h1>
            <div>
                <div>
                    <div>
                        <form id="search-form" method="GET">
							<label class="radio-inline">
								<input type="radio" name="type" class="search-form-type" value="music" checked /> 楽曲を検索
							</label>
							<label class="radio-inline">
								<input type="radio" name="type" class="search-form-type" value="artist" /> アーティストを検索
							</label>
							<label class="radio-inline">
								<input type="radio" name="type" class="search-form-type" value="tag" /> タグで検索
							</label>
							<input type="text" id="search-form-text" placeholder="キーワードを入力">
							<input type="submit" class="btn btn-danger" value="検索">
						</form>
					</div>
				</div>

				<div>
					<% if (userLoggedIn == null) { %>
					<%-- ログインしてない --%>
					未ログイン
					<ul>
						<li><a href="LoginWithPasswordServlet">パスワードでログイン</a></li>
						<li><a href="SignUpUserServlet">パスワードで新規登録</a></li>
						<li><a href="LoginWithGoogleServlet">Googleで登録・ログイン</a></li>
					</ul>
					<% } else {%>
					<%-- ログインしている --%>
					<%= userLoggedIn.name%>としてログイン中
					<ul>
						<li><a href="MyPageServlet">マイページ</a></li>
						<li><a href="LogoutServlet">ログアウト</a></li>
					</ul>
					<% }%>
				</div>
			</div>
		</header>

		<nav id="nav">
			<ul>
				<li><a href="SponsorServlet">スポンサー</a></li>
				<li><a href="RankingMusicServlet">楽曲ランキング</a></li>
				<li><a href="RankingArtistServlet">アーティストランキング</a></li>
				<li><a href="LatestMusicServlet">最新楽曲</a></li>
			</ul>
			開発用サイトマップ（あとで消す）
			<ul>
				<li><a href="MusicServlet?id=1">楽曲</a></li>
				<li><a href="ArtistServlet?id=1">アーティスト</a></li>
				<li><a href="LatestMusicServlet">最新楽曲</a></li>
				<li><a href="PopularMusicServlet">注目楽曲</a></li>
				<li><a href="PopularArtistServlet">注目アーティスト</a></li>
				<li><a href="SponsorServlet">スポンサー</a></li>
			</ul>
			<div>
				未ログイン
				<ul>
					<li><a href="LoginWithPasswordServlet">パスワードでログイン</a></li>
					<li><a href="SignUpUserServlet">パスワードで新規登録</a></li>
					<li><a href="LoginWithGoogleServlet">Googleで登録・ログイン</a></li>
				</ul>
			</div>
			<div>
				ログイン済み
				<ul>
					<li><a href="MyPageServlet">マイページ</a></li>
					<li><a href="EditUserServlet">ユーザ情報編集</a></li>
					<li><a href="LogoutServlet">ログアウト</a></li>
					<li><a href="WithdrawUserServlet">退会</a></li>
					<li><a href="PasswordServlet">パスワード登録・変更</a></li>
					<li><a href="MyListServlet">マイリスト</a></li>
					<li><a href="RankingMusicServlet">楽曲ランキング</a></li>
					<li><a href="RankingArtistServlet">アーティストランキング</a></li>
				</ul>
				<div>
					非アーティスト
					<ul>
						<li><a href="SignUpArtistServlet">アーティスト登録</a></li>
					</ul>    
				</div>
				<div>
					アーティスト
					<ul>
						<li><a href="MyMusicServlet">投稿した楽曲</a></li>
						<li><a href="NewMusicServlet">楽曲投稿</a></li>
						<li><a href="EditArtistServlet">アーティスト情報編集</a></li>
						<li><a href="WithdrawArtistServlet">アーティスト登録解除</a></li>
						<li><a href="EditMusicServlet?id=1">楽曲編集</a></li>
					</ul>
				</div>
				<div>
					管理者
					<ul>
						<li><a href="AdminServlet">管理ページ</a></li>
					</ul>
				</div>
			</div>
		</nav>
		<div id="main">
			<div id="content">
				${param.content}
			</div>
		</div><!-- /#main -->
	</body>
</html>
