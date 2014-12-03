<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
        <link rel="stylesheet" type="text/css" href="css/artist.css">
    </c:param>
    <c:param name="content">
        <h1>${name} さんのページ</h1>
        プロフィール&nbsp;&nbsp;&nbsp;
        <a href="#" id="ad">チケットを使ってこのアーティストを応援する</a>&nbsp;
        <a href="#" id="addto">マイリストに追加する</a><br>
        <div id="profile">
            <img id="image" width="140" height="90" src="images/artist.jpg">
            ${introduction}
        </div>
        
        <div id="keiziban">
            <div class="comment">
                <a href="user.html">三戸さん：</a><br>
                1get
            </div>
            <div class="comment">
                <a href="user.html">五井さん：</a><br>
                NetBeansまじでうざいんだけどｗ
            </div>
            <div class="comment">
                <a href="user.html">マウンテンさん：</a><br>
                .netでJavaはできないらしい。。。まじオワコン
            </div>
            <div class="comment">
                <a href="user.html">鈴木良房：</a><br>
                一応できるけど面倒くさいし、それでJavaやってる人は情弱
            </div>
            <div class="comment">
                <a href="user.html">新名翔太：</a><br>
                ↑だまれ
            </div>
            <form method="post" action="keizibanwrite">
                <input type="text" placeholder="コメントを入力"><input type="button" value="コメントする" onclick="alert('投稿しました！');">
            </form>
        </div>
        <div id="songlist">
            楽曲一覧
            <ul>
                <li>
                    <a href="song.html">楽曲1<br>
                        <img src="http://img.youtube.com/vi/KLVOwmAf7ok/1.jpg"></a>
                </li>
                <li>
                    <a href="song.html">楽曲2<br>
                        <img src="http://img.youtube.com/vi/KLVOwmAf7ok/1.jpg"></a>
                </li>
            </ul>
        </div>
    </c:param>
</c:import>
