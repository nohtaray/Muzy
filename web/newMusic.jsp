<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">\
    <c:param name="title" value="new music" />
    <c:param name="header">
        <script type="text/javascript" src="js/newMovie.js"></script>

        <!-- あとでけす -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
    </c:param>
    <c:param name="content">

        <div class="container">
            <div class="row">
                <div class="col-md-7">
                    <div id="music-youtube-video-area">
                        公開する動画を一覧から選択してください。<br>
                        YouTubeで非公開の動画は表示されません。
                    </div>
                    <div id="new-music-form-area" class="hidden">
                        <form action="NewMusicServlet" method="post">
                            <input type="hidden" name="youtubeVideoId" id="music-youtube-video-id">
                            <input type="text" name="title" id="music-title">
                            <textarea name="description" id="music-description"></textarea>
                            <input type="submit" value="登録！">
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
        </div>

    </c:param>
</c:import>