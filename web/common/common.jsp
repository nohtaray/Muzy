<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%!
String h(String src) {
    return StringEscapeUtils.escapeHtml4(src);
}
%>