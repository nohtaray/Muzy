<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
    </c:param>
    <c:param name="content">
        <div id="page_title">アーティスト情報編集</div>
        <div id="page_content">
            <form method="post" action="EditArtistServlet">
                <input type="text" name="name" value="${name}"><br>
                <textarea placeholder="アーティストプロフィール" name="introduction">${introduction}</textarea>
                <br>
                <br>
                サムネイル画像:<br>
                &nbsp;&nbsp;&nbsp;<input type="file" />
                <%-- <div id="msg"></div> --%>
                <br><br><br><br>

                &nbsp;&nbsp;&nbsp;<input type="submit" value="更新">
            </form>
        </div>
    </c:param>
</c:import>
