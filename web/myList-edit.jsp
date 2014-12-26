<%@page import="jp.ac.jec.jz.gr03.entity.MyList"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="jp.ac.jec.jz.gr03.entity.Music" %>
<%@page import="jp.ac.jec.jz.gr03.dao.entityresultset.MyListResultSet" %>
<c:import url="/layout/application.jsp">
    
    <c:param name="myList-edit" value="マイリストの編集" />
    <c:param name="header">
        <script src="js/lib/prototype.js"></script>
        
        <input type="text" name="name" id="name" />
        <input type="button" onclick="addMyList() " name="bt" value="マイリスト追加" /> 
        
        <script type="text/javascript">
            function addMyList() {
                var name = document.getElementById('name').value;
                new Ajax.Request('AddMyListServlet', {
                    method: 'post',
                    parameters: {
                        name: name
                    },
                    onSuccess: function() {
                        alert("成功");
                        showMyList();
                    },
                    onFailure: function() {
                        alert("失敗");
                    },
                    onException: function() {
                        alert("エラー");
                    }
                });
            }
            
            function showMyList() {
                new Ajax.Request('MyListServlet',{
                    method:'post',
                    parameters:{
                    
                    },
                    onSuccess : function(){
                    <%
                        //小菅さんの参考(ArtistServlet)　直す
                        MyListResultSet mylists = (MyListResultSet)request.getAttribute("mylists");
                        MyList mylist;
                        int num = 0;
                        
                        while ((mylist = mylists.readRow()) != null) {
                            out.println("<li>");
                            out.println(++num);
                            out.println(mylist.name);
                           // out.println("<a href=\"MyListServlet?id=" + mylist.user.userId + "\">" + mylist.name + "</a>");
                            out.println("</li>");
                        }
                    %>
                    }
                });
            }
        </script>
<!--select youtube_video_id, name from (mylists join mylist_details ON mylists.mylist_id = mylist_details.mylist_id) left join musics USING(music_id) where user_id = 22;
-->
        
    </c:param>
    <c:param name="content">
        
        <%-- ここにHTMLを記述 --%>
        
    </c:param>
</c:import>