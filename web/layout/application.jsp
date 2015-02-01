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
        <nav id="header" class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="./"></a>
                </div>
                <div class="pull-right">
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
                    <%= userLoggedIn.name%> さん
                    <a href="MyPageServlet" class="label label-info">マイページ</a>
                    <a href="LogoutServlet">ログアウト</a>
                    <% }%>
                </div>
            </div>
        </nav>

        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                        <form id="search-form" method="GET" class="form-horizontal">
                            <div class="form-group">
                                <div class="col-sm-10">
                                    <label class="radio-inline">
                                        <input type="radio" name="type" class="search-form-type" value="music" checked /> 楽曲
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="type" class="search-form-type" value="artist" /> アーティスト
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="type" class="search-form-type" value="tag" /> タグ
                                    </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-9">
                                    <input type="text" id="search-form-text" placeholder="キーワードを入力" class="form-control">
                                </div>
                                <div class="col-sm-2">
                                    <input type="submit" class="btn btn-primary" value="検索">
                                </div>
                            </div>
                        </form>
                </div>
                <div id="sponsors" class="col-sm-6">
                    <ul class="list-inline pull-right">
                        <li><a href="SponsorServlet"><img src="img/sponsor/062580.jpg" width="230" height="165" alt="Sponsor ATreat" border="0"></a></li>
                        <li><a href="SponsorServlet"><img src="img/sponsor/081510.jpg" width="230" height="165" alt="Sponsor ATreat" border="0"></a></li>
                    </ul>
                </div>
            </div>
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
            </div><!-- /#main -->
        </div>
    </body>
</html>
