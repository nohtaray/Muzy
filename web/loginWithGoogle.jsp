<%@page import="jp.ac.jec.jz.gr03.util.GoogleProxy"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="ログイン" />
    <c:param name="header">
        <script type="text/javascript" src="js/loginWithGoogle.js"></script>
    </c:param>
    <c:param name="content">
        <span id="signinButton">
            <span
                class="g-signin"
                data-accesstype="offline"
                data-approvalprompt="force"
                data-callback="googleLoginCallback"
                data-clientid="<%= h(GoogleProxy.CLIENT_ID) %>"
                data-cookiepolicy="single_host_origin"
                data-redirecturi="postmessage"
                data-requestvisibleactions="http://schemas.google.com/AddActivity"
                data-scope="https://www.googleapis.com/auth/plus.login profile email https://www.googleapis.com/auth/youtube.readonly"
            ></span>
        </span>
        
        <script type="text/javascript">
            (function() {
                var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
                po.src = 'https://apis.google.com/js/client:plusone.js';
                var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
            })();
        </script>
    </c:param>
</c:import>