<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Music"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    MusicResultSet musics = (MusicResultSet) request.getAttribute("musics");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="投稿楽曲" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <h3>投稿楽曲一覧</h3>
        <div class="row">
            <div class="col-sm-7">
                <% for (Music music : musics) {%>
                <div class="music thumbnail media clearfix">
                    <a class="pull-left" href="MusicServlet?id=<%= music.musicId%>">
                        <img class="media-object" src="http://img.youtube.com/vi/<%= h(music.youtubeVideoId)%>/1.jpg">
                    </a>
                    <div class="media-body">
                        <h4 class="media-heading"><a href="MusicServlet?id=<%= music.musicId%>"><%= h(truncate(music.title, 100))%></a></h4>
                            <%= h(truncate(music.description, 200))%>
                    </div>
                    <div class="pull-right">
                        <a class="btn btn-info" href="EditMusicServlet?id=<%= music.musicId%>">編集</a>
                    </div>
                </div>
                <% }%>
            </div>
        </div>

    </c:param>
</c:import>