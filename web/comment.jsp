<%-- 
    Document   : comment
    Created on : 2014/12/17, 13:52:44
    Author     : 12jz0121
--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ページのタイトル" />
    <c:param name="header">
        <%-- スクリプトのインポートなど。例↓
        <script type="text/javascript" src="js/template.js"></script>
        <link rel="stylesheet" type="text/css" href="css/template.css">
        --%>
        <script src="js/prototype.js"></script>
        
    </c:param>
    <c:param name="content">
        
        <%-- ここにHTMLを記述 --%>
        <input type="button" onclick="evaluationCommentGood()" value="良い" />
        <input type="button" onclick="evaluationCommentBad()" value="悪い" />
        <input type="button" onclick="deleteComment()" value="削除" />
        
        <script type="text/javascript">
            function evaluationCommentGood() {
                new Ajax.Request('EvaluationCommentsServlet', {
                    method: 'post',
                    parameters: {
                        commentid: 1, //仮
                        eva: 1
                    },
                    onSuccess: function() {
                        alert("良い成功");
                    },
                    onFailure: function() {
                        alert("良い失敗");
                    },
                    onException: function() {
                        alert("エラー");
                    }
                });
            }
            
            function evaluationCommentBad() {
                new Ajax.Request('EvaluationCommentsServlet', {
                    method: 'post',
                    parameters: {
                        commentid: 1, //仮
                        eva: -1
                    },
                    onSuccess: function() {
                        alert("悪い成功");
                    },
                    onFailure: function() {
                        alert("悪い失敗");
                    },
                    onException: function() {
                        alert("エラー");
                    }
                });
            }
            
            function deleteComment() {
                new Ajax.Request('DeleteCommentServlet', {
                    method: 'post',
                    parameters: {
                        commentid: 2 //仮
                    },
                    onSuccess: function() {
                        alert("削除成功");
                        //再表示処理したい
                    },
                    onFailure: function() {
                        alert("削除失敗");
                    }
                });
            }
        
            
        </script>
    </c:param>
</c:import>