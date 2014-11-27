<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
    </c:param>
    <c:param name="content">

        <p>
            こんにちは！Muzyです！
        </p>

        <div>
            機能リンク（デバッグ用）
            <ul>
                <li><a href="LoginWithPasswordServlet">パスワードでログイン</a></li>
                <li>パスワードで新規登録</li>
                <li><a href="LoginWithGoogleServlet">Googleで登録・ログイン</a></li>
                <li><a href="LogoutServlet">ログアウト</a></li>
            </ul>
            <ul>
                <li>アーティスト登録</li>
                <li><a href="NewMusicServlet">楽曲投稿</a></li>
                <li><a href="MusicServlet?id=1">楽曲</a></li>
            </ul>
        </div>

</c:param>
</c:import>