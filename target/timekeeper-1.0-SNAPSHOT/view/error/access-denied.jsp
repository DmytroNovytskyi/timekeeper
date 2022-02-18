<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title>Access denied</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/script/bootstrap.min.js"></script>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.user.role.name.equals('ADMIN')}">
        <%@include file="../other/admin-header.jsp" %>
    </c:when>
    <c:otherwise>
        <%@include file="../other/worker-header.jsp" %>
    </c:otherwise>
</c:choose>
<div class="container-fluid position-absolute translate-middle-y top-50 left-50 ">
    <div class="row">
        <div class="text-center">
            <h1 class="text-danger fw-bold" style="text-shadow: 10px 3px 10px #5C5C5C;">Access denied!</h1>
        </div>
    </div>
</div>
</body>
</html>
