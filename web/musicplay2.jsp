<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
        <%-- スクリプトのインポートなど。例↓
        <script type="text/javascript" src="js/template.js"></script>
        <link rel="stylesheet" type="text/css" href="css/template.css">
        --%>
        <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
		
        <script>
            function HeaderClick() {
                target = document.getElementById("ContentsPanel");
                if (target.style.display === "none") {
                    target.style.display = "block";
                } else {
                    target.style.display = "none";
                }
            }

            function funcSignUpTags() {
                $.ajax({
                    type: 'POST',
                    dataType: 'text',
                    url: 'SignUpTagsServlet', //ここにServletのアドレス
                    //ここがServletに渡される値
                    //複数渡せる左が変数で右が値
                    data: {
                        tagname: $('#tagname').val(),
						//score: $('#score').val(),
                        musicid: $('#musicid').val()
                    }
                }).done(function(data) {
                    // ajax ok
                    //追加表示処理
                    $tag = $('#tag');
                    $newtag = $('#tagname');
                    $('<span>' + " " + $newtag.val() + " " + '</span>').appendTo($tag);
					alert("成功");
                }).fail(function(data) {
                    // ajax error
                    //追加できませんでした的な通知
					alert("失敗");
                }).always(function(data) {
                    // ajax complete
                });
			}
				//------------ここまで実行可能確認------------------
				
			function funcReview() {
                $.ajax({
                    type: 'POST',
                    dataType: 'text',
                    url: 'AddReviewServlet', //ここにServletのアドレス
                    //ここがServletに渡される値
                    //複数渡せる左が変数で右が値
                    data: {
                        review: $('#review').val(),
                        musicid: $('#musicid').val()
                    }
                }).done(function(data) {
                    // ajax ok
                    //追加表示処理
                    $reviewarea = $('#reviewarea');
                    $newreview = $('#review');
                    $('<br /><br /><span>' + $newreview.val() + '</span>').appendTo($reviewarea);
					$('<br /><input type="button" onclick="evaluationCommentGood()" value="良い" /><input type="button" onclick="evaluationCommentBad()" value="悪い" /><input type="button" onclick="deleteComment()" value="削除" />').appendTo($reviewarea);
					alert("成功");
                }).fail(function(data) {
                    // ajax error
                    //追加できませんでした的な通知
					alert("失敗");
                }).always(function(data) {
                    // ajax complete
                });
            }
			
			function evaluationCommentGood() {
                $.ajax({
                    type     : 'POST',
                    dataType : 'text',
                    url      : 'EvaluationCommentsServlet', //ここにServletのアドレス
                    //ここがServletに渡される値
                    //複数渡せる左が変数で右が値
                    data     : {
                                 commentid: 3, //仮
                                 eva: 1
                               }
                }).done(function() {
                    alert("良い成功");
                }).fail(function() {
                    alert("良い失敗");
                }).always(function() {
                });
            }
            function evaluationCommentBad() {
                $.ajax({
                    type     : 'POST',
                    dataType : 'text',
                    url      : 'EvaluationCommentsServlet', //ここにServletのアドレス
                    //ここがServletに渡される値
                    //複数渡せる左が変数で右が値
                    data     : {
                                 commentid: 3, //仮
                                 eva: -1
                               }
                }).done(function() {
                    alert("悪い成功");
                }).fail(function() {
                    alert("悪い失敗");
                }).always(function() {
                });
            }
            
            function deleteComment() {
                $.ajax({
                    type     : 'POST',
                    dataType : 'text',
                    url      : 'DeleteCommentServlet', //ここにServletのアドレス
                    //ここがServletに渡される値
                    //複数渡せる左が変数で右が値
                    data     : {
                                 commentid: 3 //仮
                               }
                }).done(function() {
                    alert("削除成功");
                }).fail(function() {
                    alert("削除失敗");
                }).always(function() {
                });
            }

        </script>
    </c:param>
    <c:param name="content">

        <%--既存登録タグ(暫定的)--%>
        <div id="tag">
            <span>xyz</span>
        </div>

        <%--タグ追加スペース--%>
		<input type="button" onclick="HeaderClick();" value="タグ追加">
		<div>
            <div id="ContentsPanel" style="width:780px;border:1px solid #008d18; display:none">
                <div id="tagfiled">
                    <input type="text" id="tagname" placeholder="タグ名" required>
					<!--number形だと動かない？サイズは後-->
					<input type="number" id="score"   placeholder="評価値" required>
                </div>
                <input type="button" onclick="funcSignUpTags();" value="新規タグを登録する">
            </div>
            <input type="hidden" id="musicid" value="1">
        </div>
		
		レビュー<br />
		<div>
			<textarea id="review" cols="30" rows="5"></textarea>
			<input type="button" onclick="funcReview();" value="レビュー書き込み">
		</div>
		
		<br /><br /><br />
		<h2>レビュー一覧</h2>
		<div id="reviewarea">
			<span>良い曲ですね</span>
			<%--ユーザーIDとかも表示？--%>
			<br />
			<input type="button" onclick="evaluationCommentGood()" value="良い" />
			<input type="button" onclick="evaluationCommentBad()" value="悪い" />
			<input type="button" onclick="deleteComment()" value="削除" />
		</div>
    </c:param>
</c:import>