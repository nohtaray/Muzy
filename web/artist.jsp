<%@page import="jp.ac.jec.jz.gr03.entity.Music"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Message"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MessageResultSet"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Artist"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    Artist artist = (Artist) request.getAttribute("artist");
    MessageResultSet messages = (MessageResultSet) request.getAttribute("messages");
    MusicResultSet musics = (MusicResultSet) request.getAttribute("musics");
    String pageTitle = artist.name + " さんのページ";
%>
<c:import url="/layout/application.jsp">
    <%--
        "<%= で始まった場合、 %>" で終わらなければいけない
        value=" <%= 1 %> " はOK
        value="<%= 1 %> " はエラーになる
    --%>
    <c:param name="title" value="<%= pageTitle%>" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        <h1><%= artist.name%> さんのページ</h1>
        プロフィール&nbsp;&nbsp;&nbsp;
        <a href="#" id="ad">チケットを使ってこのアーティストを応援する</a>&nbsp;
        <a href="#" id="addto">マイリストに追加する</a><br>
        <div id="profile">
            <img id="image" src="img/ajax-loader.gif">
            <%= artist.introduction%>
        </div>

        <form method="post" action="keizibanwrite">
            <input type="text" placeholder="コメントを入力"><input type="button" value="コメントする" onclick="alert('投稿しました！');">
        </form>
        <div id="messages">
            <% for (Message message : messages) {%>
            <div class="message">
                <div class="message-header">
                    <%= message.messageId%>. <%= message.user.name%>
                </div>
                <div class="message-body">
                    <%= message.content%>
                </div>
                <div class="message-footer">
                    <%= message.createdAt%>
                </div>
            </div>
            <% }%>
        </div>

        <div id="musics">
            投稿楽曲一覧
            <ul>
                <% for (Music music : musics) { %>
                <li>
                    <a href="MusicServlet?id=<%= music.musicId %>"><%= music.title %><br>
                        <img src="http://img.youtube.com/vi/<%= music.youtubeVideoId %>/1.jpg"></a>
                </li>
                <% } %>
            </ul>
        </div>
    </c:param>
</c:import>
