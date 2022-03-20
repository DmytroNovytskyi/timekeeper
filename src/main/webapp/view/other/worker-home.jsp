<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie.lang.value}" />
<fmt:setBundle basename="messages"/>
<!doctype html>
<html lang="${cookie.lang.value}">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/style/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/script/bootstrap.min.js"></script>
    <title>Home</title>
</head>
<body>
<%@include file="../other/worker-header.jsp" %>
<%@include file="../other/message.jsp" %>
</body>
</html>
