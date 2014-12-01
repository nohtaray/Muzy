<%-- 
    Document   : user-edit
    Created on : 2014/11/10, 9:53:59
    Author     : 12jz0112
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ユーザ情報編集画面</title>
        <script src="js/jquery-1.11.1.min.js"></script>
        <link rel="stylesheet" href="css/common.css">
        <link rel="stylesheet" href="css/user-edit.css">
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
	        <a href="http://wwwriken.jp" onclick="alert('2ポイント獲得しました！');"><img src="images/banner.png" width="150"></a><br>
	        <a href="http://www.riken.jp" onclick="alert('2ポイント獲得しました！');"><img src="images/banner.png" width="150"></a><br>
	        <a href="http://www.riken.jp" onclick="alert('2ポイント獲得しました！');"><img src="images/banner.png" width="150"></a><br>
	        <a href="http://www.riken.jp" onclick="alert('2ポイント獲得しました！');"><img src="images/banner.png" width="150"></a><br>
	        <a href="http://www.tokyodisneyresort.jp/top.html" onclick="alert('2ポイント獲得しました！');"><img src="images/dis.png" width="150"></a>
	  </div>
  	</div>
  </div>
  <div id="main">
    <div id="page_title">ページタイトル</div>
    <div id="page_content">
        <form method="post" action="UserEdit">
        <input type="text" name="name" value="${name}"><br>
        <input type="text" name="email" value="${email}"><br>
        <input type="text" name="newpass1" placeholder="新しいパスワード"><br>
        <input type="text" name="newpass2" placeholder="もう1度入力してください"><br>
        <textarea placeholder="自己紹介文" name="introduction">${introduction}</textarea>
        <br>
        <br>
        　　サムネイル画像:<br>
        &nbsp;&nbsp;&nbsp;<input type="file" />
        <br><br><br><br>
        <input type="password" name="nowpass" placeholder="現在のパスワード" required><br>
        &nbsp;&nbsp;&nbsp;<input type="submit" value="更新">
        </form>
    </div>
  </div>
  <div id="footer">
    フッター
  </div>   
    </body>
</html>
