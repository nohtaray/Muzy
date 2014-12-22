ex<%-- 
    Document   : musicplay
    Created on : 2014/11/20, 9:39:56
    Author     : 12jz0129
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 
        <script src="/js/jquery-1.11.1.js"></script>   
        <script type="text/javascript"><!--
 
            function funcSignUpTags() {
                $.ajax({
                    type     : 'POST',
                    dataType : 'text',
                    url      : 'SignUpTagsServlet', //ここにServletのアドレス
                    //ここがServletに渡される値
                    //複数渡せる左が変数で右が値
                    data     : {
                                 tagname : $('#newtag').val(),
                                 musicid : $('#music').val()
                               }
                }).done(function(data) {
                    // ajax ok
                    //追加表示処理
                    $tag = $('#tag');
                    $newtag = $('#newtag');
                    $('<span>' + $newtag.val() + '</span>').appendTo($tag);
                }).fail(function(data) {
                    // ajax error
                    //追加できませんでした的な通知
					$tag = $('#tag');
                    $newtag = $('#newtag');
                    $('<span>' + $newtag.val() + '</span>').appendTo($tag);
                }).always(function(data) {
                    // ajax complete
                });
 
            }
            
            function funcDeleteTags() {
                $.ajax({
                    type     : 'POST',
                    dataType : 'text',
                    url      : 'DeleteTagsServlet', //ここにServletのアドレス
                    //ここがServletに渡される値
                    //複数渡せる左が変数で右が値
                    data     : {
                                 tagname : $('#tags').val(),
                                 musicid : $('#music').val()
                               }
                }).done(function(data) {
                    // ajax ok
                    //削除されればおｋ
                    //aタグではないので処理をどうするか
                    $(a).parent().remove();
                }).fail(function(data) {
                    // ajax error
                    //追加できませんでした的な通知
                }).always(function(data) {
                    // ajax complete
                });
            }
            
            function funcEvaluationTags() {
                $.ajax({
                    type     : 'POST',
                    dataType : 'text',
                    url      : 'EvaluationTagsServlet', //ここにServletのアドレス
                    //ここがServletに渡される値
                    //複数渡せる左が変数で右が値
                    data     : {
                                 tagname : $('#tags').val(),
                                 musicid : $('#music').val()
                               }
                }).done(function(data) {
                    // ajax ok
                    //削除されればおｋ
                    
                    $(a).parent().remove();
                }).fail(function(data) {
                    // ajax error
                    //追加できませんでした的な通知
                }).always(function(data) {
                    // ajax complete
                });
            }
			//タグ追加のフィールドを表示する
 			function HeaderClick() {
     			target = document.getElementById("ContentsPanel");
     			if (target.style.display === "none") {
        			target.style.display = "block";
      			} else {
        			target.style.display = "none";
      			}
    		}
        --></script>
 
    </head>
    <body>
 
        <div>
            <div id="tag">
				<span>xyz</span>
            </div>
 			
            <input type="button" onclick="HeaderClick();" value="タグ追加">
  			
            <div id="ContentsPanel" style="width:240px;border:1px solid #008d18; display:none">
				 <input type="text" id="newtag">
				 <input type="button" onclick="funcSignUpTags();" value="追加">
			</div>
            <div id="music">
                1
            </div>
        </div>
 
    </body>
</html>
