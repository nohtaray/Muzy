<%-- 
    Document   : artist
    Created on : 2014/11/13, 10:35:35
    Author     : 12jz0112
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>アーティストプロフィール</title>
        <script src="js/jquery-1.11.1.min.js"></script>
        <link rel="stylesheet" href="css/common.css">
        <link rel="stylesheet" href="css/artist.css">
    </head>
    <body>
    <div id="header">
        <div id="header_title"><a href="index.html"><img src="images/rogo.png"></a></div>
        <div id="header_links">
        <form action="search.html" method="get">
            <input type="text" name="s" placeholder="楽曲を探す">
            <input type="submit" value="検索">
        </form>
        <a href="mypage.html">マイページ</a>
        </div>
        <hr>
    </div>
    <div id="sidebar">
        <div id="sidebar2">
              <div id="ads">
                    広告一覧
                    <a href="http://www.riken.jp" onclick="alert('2ポイント獲得しました！');"><img src="images/banner.png" width="150"></a><br>
                    <a href="http://www.riken.jp" onclick="alert('2ポイント獲得しました！');"><img src="images/banner.png" width="150"></a><br>
                    <a href="http://www.riken.jp" onclick="alert('2ポイント獲得しました！');"><img src="images/banner.png" width="150"></a><br>
                    <a href="http://www.riken.jp" onclick="alert('2ポイント獲得しました！');"><img src="images/banner.png" width="150"></a><br>
                    <a href="http://www.tokyodisneyresort.jp/top.html" onclick="alert('2ポイント獲得しました！');"><img src="images/dis.png" width="150"></a>
              </div>
        </div>
    </div>
    <div id="main">
        <div id="page_title">アーティストプロフィール</div>
        <div id="page_content">
            プロフィール&nbsp;&nbsp;&nbsp;
            <a href="#" id="ad">チケットを使ってこのアーティストを応援する</a>&nbsp;
            <a href="#" id="addto">マイリストに追加する</a><br>
            <div id="profile">
                <img id="image" width="140px" height="90px" src="images/artist.jpg">
                    こんにちは、よろしくお願いします
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
        </div>
    </div>
    <div id="footer">
        フッター
    </div>
    </body>
</html>
