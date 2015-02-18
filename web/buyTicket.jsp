<%@page import="jp.ac.jec.jz.gr03.entity.Point"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    Point point = (Point) request.getAttribute("point");
    Integer price = (Integer) request.getAttribute("price");
%>
<c:import url="/layout/application.jsp">
    <c:param name="title" value="チケット購入" />
    <c:param name="header">
        <link rel="stylesheet" type="text/css" href="css/buyTicket.css">
        <script type="text/javascript" src="js/buyTicket.js"></script>
    </c:param>
    <c:param name="content">

        ポイントと引き換えに投票チケットを購入します。
        <div>
            保有チケット：<%= point.voteTicketCount%>枚<br>
            保有ポイント：<%= point.pointCount%>
        </div>
        <div>
            <ul class="list-unstyled" id="buy-buttons">
                <%-- この単位以外のリクエストを飛ばしてもエラーにしない想定 --%>
                <%
                    int[] qts = {1, 2, 5, 10, 30};
                    for (int qt : qts) {
                        int requires = price * qt;
                        boolean canPay = requires <= point.pointCount;
                %>
                <li><a class="buy-button btn btn-<%= canPay ? "info" : "default"%>" data-qt="<%= qt%>" data-requires="<%= requires%>" <%= canPay ? "" : "disabled"%>><%= qt%>枚</a>(<%= requires%>ポイント)</li>
                    <%
                        }
                    %>
            </ul>
        </div>

    </c:param>
</c:import>