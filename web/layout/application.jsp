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
                    <h1><a href="./"><img src="img/logo.png" Border="0" Width="200" Height="70"></a></h1>
                </div>
            </div>
        </nav>
        <header id="header">
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
                            <input type="submit" class="btn btn-info" value="検索">
                        </form>
                    </div>
                </div>

                <div>
                    <% if (userLoggedIn == null) { %>
                    <%-- ログインしてない --%>
                    <h1>未ログイン状態です</h1>
                    <ul>
                        <li><a href="LoginWithPasswordServlet">パスワードでログイン</a></li>
                        <li><a href="SignUpUserServlet">パスワードで新規登録</a></li>
                        <li><a href="LoginWithGoogleServlet">Googleで登録・ログイン</a></li>
                    </ul>
                    <% } else {%>
                    <%-- ログインしている --%>
                    <%= userLoggedIn.name%>としてログイン中
                    <ul>
                        <li><a href="MyPageServlet" class="label label-info">マイページ</a>
                        <li><a href="LogoutServlet">ログアウト</a></li>
                    </ul>
                    <% }%>
                </div>
                <ul>
                    <li><a href="SponsorServlet">スポンサー</a></li>
                    <li><a href="LatestMusicServlet">最新楽曲</a></li>
                </ul>
            </div>
        </header>

        <div id="main" class="container">
            ${param.content}
        </div><!-- /#main -->
    </body>
</html>
