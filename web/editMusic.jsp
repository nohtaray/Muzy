<%@page import="jp.ac.jec.jz.gr03.entity.Music"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    Music music = (Music) request.getAttribute("music");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="楽曲編集" />
    <c:param name="header">
        <script type="text/javascript" src="js/editMusic.js"></script>
    </c:param>
    <c:param name="content">

        <div id="music-youtube-video-area">
            <iframe width="540" height="330" frameborder="0" src="http://www.youtube.com/embed/<%= music.youtubeVideoId%>?rel=0" allowfullscreen></iframe>
        </div>
        <form action="EditMusicServlet" method="post">
            <input type="hidden" name="id" value="<%= music.musicId%>">
            <div class="form-group">
                <label for="music-title" class="control-label">Title</label>
                <div>
                    <input type="text" class="form-control" id="music-title" placeholder="Title" name="title" value="<%= music.title%>" required>
                </div>
            </div>
            <div class="form-group">
                <label for="music-description" class="control-label">Description</label>
                <div>
                    <textarea class="form-control" id="music-description" placeholder="Description" name="description"><%= music.description%></textarea>
                </div>
            </div>
            <div class="form-group">
                <button type="submit">更新！</button>
            </div>
        </form>

        <div>
            <button id="delete-button" data-music-id="<%= music.musicId %>">この楽曲を削除する</button>
        </div>
    </c:param>
</c:import>