<%@page import="jp.ac.jec.jz.gr03.entity.Music"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    Music music = (Music) request.getAttribute("music");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="楽曲編集" />
    <c:param name="header">
        <link rel="stylesheet" type="text/css" href="css/editMusic.css">
        <script type="text/javascript" src="js/editMusic.js"></script>
    </c:param>
    <c:param name="content">

        <div class="row">
            <div class="col-sm-7">
                <h3>楽曲情報編集</h3>
                <hr>
                <div id="music-youtube-video-area" class="text-center">
                    <iframe width="540" height="330" frameborder="0" src="http://www.youtube.com/embed/<%= h(music.youtubeVideoId)%>?rel=0" allowfullscreen></iframe>
                </div>
                <form action="EditMusicServlet" method="post" class="form-horizontal">
                    <input type="hidden" name="id" value="<%= music.musicId%>">
                    <div class="form-group row">
                        <label for="music-title" class="control-label col-sm-3">Title</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="music-title" placeholder="Title" name="title" value="<%= h(music.title)%>" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="music-description" class="control-label col-sm-3">Description</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" id="music-description" placeholder="Description" name="description"rows="5"><%= h(music.description)%></textarea>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-9 col-sm-offset-3 clearfix">
                            <div class="pull-right">
                                <button class="btn btn-danger" id="delete-button" data-music-id="<%= music.musicId%>">削除</button>
                                <button class="btn btn-primary" type="submit">更新</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </c:param>
</c:import>