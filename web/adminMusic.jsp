<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Artist"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Music"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet"%>
<c:import url="/layout/admin.jsp">
    <c:param name="title" value="楽曲管理" />
    <c:param name="header">
        <script src="js/adminMusic.js"></script>
    </c:param>
    <c:param name="content">

        <h3>楽曲一覧</h3>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>楽曲ID</th>
                    <th>アーティスト</th>
                    <th>楽曲ページ</th>
                    <th>投稿日時</th>
                    <th>更新日時</th>
                    <th>削除</th>
                </tr>
            </thead>
            <tbody>
                <%
                    MusicResultSet musics = (MusicResultSet) request.getAttribute("musics");
                    Music music = new Music();
                    while ((music = musics.readRow()) != null) {
                %>
                <tr>
                    <td>
                        <%= music.musicId%>
                    </td>
                    <td>
                        <a href="ArtistServlet?id=<%= music.artist.artistId%>"><%= h(music.artist.name)%></a>
                    </td>
                    <td>
                        <a href="MusicServlet?id=<%= music.musicId%>"><%= h(music.title)%></a>
                    </td>
                    <td>
                        <%= h(dateToString(music.createdAt))%>
                    </td>
                    <td>
                        <%= h(dateToString(music.updatedAt))%>
                    </td>
                    <td>
                        <button class="delete-button btn btn-danger" data-music-id="<%= music.musicId%>">削除</button>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

    </c:param>
</c:import>