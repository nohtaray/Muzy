<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%!
private static DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

String h(String src) {
    return StringEscapeUtils.escapeHtml4(src);
}
String truncate(String src, int maxLength) {
    if (src.length() <= maxLength) {
        return src;
    } else {
        return src.substring(0, maxLength) + "...";
    }
}
String dateToString(Date date) {
    return df.format(date);
}
String br(String src) {
    return src.replaceAll("(\\r\\n|\\r|\\n)", "<br>");
}
%>