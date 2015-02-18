<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.ArtistResultSet" %>
<%@page import="jp.ac.jec.jz.gr03.entity.Artist" %>
<c:import url="/layout/admin.jsp">
    <c:param name="title" value="アーティスト管理" />
    <c:param name="header">
        <script type="text/javascript" src="js/adminArtist.js"></script>
    </c:param>
    <c:param name="content">

        <h3>アーティスト一覧</h3>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>アーティストID</th>
                    <th>アーティストページ</th>
                    <th>ユーザID</th>
                    <th>登録日時</th>
                    <th>更新日時</th>
                    <th>削除</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArtistResultSet artists = (ArtistResultSet) request.getAttribute("artists");
                    for (Artist artist : artists) {
                %>
                <tr>
                    <td>
                        <%= artist.artistId%>
                    </td>
                    <td>
                        <a href="ArtistServlet?id=<%= artist.artistId%>"><%= h(artist.name)%></a>
                    </td>
                    <td>
                        <%= artist.user.userId%>
                    </td>
                    <td>
                        <%= h(dateToString(artist.createdAt))%>
                    </td>
                    <td>
                        <%= h(dateToString(artist.updatedAt))%>
                    </td>
                    <td>
                        <button class="delete-button btn btn-danger" data-artist-id="<%= artist.artistId%>">削除</button>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

    </c:param>
</c:import>