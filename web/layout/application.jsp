<%-- 
    Document   : application
    Created on : 2014/11/08, 16:39:27
    Author     : yada

    全画面共通の部分
--%>
<%@page import="jp.ac.jec.jz.gr03.util.Flash"%>
<%@page import="jp.ac.jec.jz.gr03.entity.User"%>
<%@page import="jp.ac.jec.jz.gr03.util.Authorizer"%>
<%
    Authorizer auth = new Authorizer(session);
    User userLoggedIn = auth.getUserLoggedInAs();
    Flash flash = Flash.get(session);
%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${param.title}</title>
        <script type="text/javascript" src="js/lib/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="js/lib/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/common.js"></script>
        <script type="text/javascript" src="js/application.js"></script>
        <link rel="stylesheet" type="text/css" href="css/lib/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/lib/bootstrap-theme.min.css">
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
                <div align="right">
                    <% if (userLoggedIn == null) { %>
                    <%-- ログインしてない --%>

                    <div id="login-box" class="btn-group">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            ログイン・新規登録
                            <span class="caret"></span>
                            <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="LoginWithGoogleServlet">ログイン・新規登録</a></li>
                            <li><a href="LoginWithPasswordServlet">メールアドレスでログイン</a></li>
                            <li><a href="SignUpUserServlet">メールアドレスで新規登録</a></li>
                        </ul>
                    </div>

                    <% } else {%>
                    <%-- ログインしている --%>
                    <%= userLoggedIn.name%>としてログイン中
                    <ul>
                        <li><a href="MyPageServlet" class="label label-info">マイページ</a>
                        <li><a href="LogoutServlet">ログアウト</a></li>
                    </ul>
                    <% }%>
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
                <% if (userLoggedIn == null) { %>
                <%-- ログインしてない --%>
                <h1>未ログイン状態です</h1>
                <% } else {%>
                <%-- ログインしている --%>
                <%= userLoggedIn.name%>としてログイン中
                <% }%>
                <ul>
                    <li><a href="SponsorServlet">スポンサー</a></li>
                    <li><a href="LatestMusicServlet">最新楽曲</a></li>
                </ul>
            </div>
        </header>

        <div class="container">
            <%
                if (!flash.success.isEmpty()) {
                    String message;
                    while ((message = flash.success.poll()) != null) {
            %>
            <div class="alert alert-success">
                <a class="close" data-dismiss="alert">×</a>
                <%= br(h(message))%>
            </div>
            <%
                    }
                }
                if (!flash.info.isEmpty()) {
                    String message;
                    while ((message = flash.info.poll()) != null) {
            %>
            <div class="alert alert-info">
                <a class="close" data-dismiss="alert">×</a>
                <%= br(h(message))%>
            </div>
            <%
                    }
                }
                if (!flash.warning.isEmpty()) {
                    String message;
                    while ((message = flash.warning.poll()) != null) {
            %>
            <div class="alert alert-warning">
                <a class="close" data-dismiss="alert">×</a>
                <%= br(h(message))%>
            </div>
            <%
                    }
                }
                if (!flash.danger.isEmpty()) {
                    String message;
                    while ((message = flash.danger.poll()) != null) {
            %>
            <div class="alert alert-danger">
                <a class="close" data-dismiss="alert">×</a>
                <%= br(h(message))%>
            </div>
            <%
                    }
                }
            %>
            <div id="main">
                ${param.content}
            </div>
        </div><!-- /#main -->
    </body>
</html>
