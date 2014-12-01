<%-- 
    Document   : artist-edit
    Created on : 2014/11/10, 11:38:05
    Author     : 12jz0112
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>アーティストプロフィール編集画面</title>
        <script src="js/jquery-1.11.1.min.js"></script>
        <link rel="stylesheet" href="css/common.css">
        <link rel="stylesheet" href="css/artist-edit.css">
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
            <div id="page_title">アーティスト情報編集</div>
            <div id="page_content">
                <form method="post" action="ArtistEdit">
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
        </div>
  <%-- <div id="confirm">
            
        </div> --%>
        <div id="footer">
            フッター
        </div>
    </body>
</html>
