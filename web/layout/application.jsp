<%-- 
    Document   : application
    Created on : 2014/11/08, 16:39:27
    Author     : yada

    全画面共通の部分
--%>
<%@page import="jp.ac.jec.jz.gr03.util.GoogleProxy"%>
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
        <script type="text/javascript" src="js/loginWithGoogle.js"></script>
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
                    <% if (userLoggedIn == null) {%>
                    <%-- ログインしてない --%>
                    <div id="login-box" class="btn-group">
                        <span id="signinButton">
                            <span
                                class="g-signin"
                                data-accesstype="offline"
                                data-approvalprompt="force"
                                data-callback="googleLoginCallback"
                                data-clientid="<%= h(GoogleProxy.CLIENT_ID)%>"
                                data-cookiepolicy="single_host_origin"
                                data-redirecturi="postmessage"
                                data-requestvisibleactions="http://schemas.google.com/AddActivity"
                                data-scope="https://www.googleapis.com/auth/plus.login profile email https://www.googleapis.com/auth/youtube.readonly"
                                ></span>
                        </span>
                    </div>
                    <% } else {%>
                    <%-- ログインしている --%>
                    <ul class="list-unstyled" id="common-menu-list">
                        <li><a href="MyPageServlet" class="label label-default">マイページ</a></li>
                        <li><a href="LogoutServlet" class="label label-default">ログアウト</a></li>
                    </ul>
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
                    <div class="row">
                    <ul class="list-inline">
                        <li class="col-xs-6"><a href="SponsorServlet"><img src="img/sponsor/01.png" alt="Sponsor" border="0"></a></li>
                        <li class="col-xs-6"><a href="SponsorServlet"><img src="img/sponsor/02.png" alt="Sponsor" border="0"></a></li>
                    </ul>
                    </div>
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

        <script type="text/javascript">
            (function () {
                var po = document.createElement('script');
                po.type = 'text/javascript';
                po.async = true;
                po.src = 'https://apis.google.com/js/client:plusone.js';
                var s = document.getElementsByTagName('script')[0];
                s.parentNode.insertBefore(po, s);
            })();
        </script>
    </body>
</html>
