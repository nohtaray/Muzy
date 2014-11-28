<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="new music" />
    <c:param name="header">
        <!-- あとでけす -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
        <!-- /あとでけす -->

        <link rel="stylesheet" type="text/css" href="css/newMusic.css">
        <script type="text/javascript" src="js/newMusic.js"></script>
    </c:param>
    <c:param name="content">

        <div class="container">
            <% if (request.getAttribute("error") != null) { %>
            <div class="has-error">
                ${error}
            </div>
            <% }%>
            <div class="row">
                <div class="col-md-7">
                    <div id="music-youtube-video-area">
                        公開する動画を一覧から選択してください。<br>
                        YouTubeで非公開の動画は表示されません。
                    </div>
                    <div id="new-music-form-area" class="hidden">
                        <form action="NewMusicServlet" method="post" class="form-horizontal">
                            <input type="hidden" name="youtubeVideoId" id="music-youtube-video-id">
                            <div class="form-group">
                                <label for="music-title" class="col-sm-2 control-label">Title</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="music-title" placeholder="Title" name="title">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="music-description" class="col-sm-2 control-label">Description</label>
                                <div class="col-sm-10">
                                    <textarea class="form-control" id="music-description" placeholder="Description" name="description"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-default">登録！</button>
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
        </div>

    </c:param>
</c:import>