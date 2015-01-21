<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String error = (String) request.getAttribute("error");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="new music" />
    <c:param name="header">
        <link rel="stylesheet" type="text/css" href="css/newMusic.css">
        <script type="text/javascript" src="js/newMusic.js"></script>
    </c:param>
    <c:param name="content">

        <div class="row">
            <div class="col-md-7">
                <% if (error != null) {%>
                <div class="alert alert-danger">
                    <a class="close" data-dismiss="alert">×</a>
                    エラー：<%= error%>
                </div>
                <% }%>
                <div id="music-youtube-video-area" class="text-center">
                    公開する動画を一覧から選択してください。<br>
                    YouTubeで非公開の動画は表示されません。
                </div>
                <div id="new-music-form-area" class="hidden">
                    <form action="NewMusicServlet" method="post" class="form-horizontal">
                        <input type="hidden" name="youtubeVideoId" id="music-youtube-video-id">
                        <div class="form-group row">
                            <label for="music-title" class="col-sm-2 control-label">タイトル</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="music-title" placeholder="タイトル" name="title" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="music-description" class="col-sm-2 control-label">説明</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" id="music-description" placeholder="説明" name="description"></textarea>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="music-tags" class="col-sm-2 control-label">タグ</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" id="music-tags" placeholder="タグ（改行で区切ります）" name="tags"></textarea>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-primary">投稿</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-md-5">

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-title">
                            YouTubeにアップロードした動画
                        </div>
                    </div>
                    <div class="panel-body">
                        <div id="new-music-youtube-videos">
                            <div id="new-music-youtube-loading">
                                <img src="img/ajax-loader.gif">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </c:param>
</c:import>