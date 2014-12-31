<%-- 
    Document   : application
    Created on : 2014/11/08, 16:39:27
    Author     : yada

    全画面共通の部分
--%>

<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${param.title}</title>
        <script type="text/javascript" src="js/lib/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="js/application.js"></script>
        <link rel="stylesheet" type="text/css" href="css/application.css">
        ${param.header}
    </head>
    <body>
        <header id="header">
            <h1><a href="./">Muzy</a></h1>
            
            <div>
                <form action="SearchMusicServlet" method="GET">
                    <input type="text" name="q" placeholder="楽曲を検索">
                    <input type="submit" value="検索">
                </form>
            </div>
            <div>
                <form action="SearchArtistServlet" method="GET">
                    <input type="text" name="q" placeholder="アーティストを検索">
                    <input type="submit" value="検索">
                </form>
            </div>
            <div>
                <form action="SearchMusicServlet" method="GET">
                    <input type="text" name="t" placeholder="タグで検索">
                    <input type="submit" value="検索">
                </form>
            </div>
        </header>
        
        <nav id="nav">
            開発用サイトマップ
            <ul>
                <li><a href="MusicServlet?id=1">楽曲</a></li>
                <li><a href="ArtistServlet?id=1">アーティスト</a></li>
                <li><a href="LatestMusicServlet">最新楽曲</a></li>
                <li><a href="PopularMusicServlet">注目楽曲</a></li>
                <li><a href="PopularArtistServlet">注目アーティスト</a></li>

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
                    <li>退会</li>
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
                        <li><a href="NewMusicServlet">楽曲投稿</a></li>
                        <li><a href="EditArtistServlet">アーティスト情報編集</a></li>
                        <li><a href="ArtistStopServlet">アーティスト登録解除</a></li>
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
