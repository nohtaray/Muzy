<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="${title}" />
    <c:param name="header">
        
    </c:param>
    <c:param name="content">
        
        ${title}<br>
        
        <iframe width="576" height="360" src="//www.youtube.com/embed/${youtubeVideoId}?autoplay=1&controls=2&rel=0&showinfo=0" frameborder="0" allowfullscreen></iframe>
        <br>
        ${description}
        
    </c:param>
</c:import>