<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/button.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/util.css">
    <title>Error</title>
</head>
<body>
<div class="container">
    <div class="def-table center-h center-v">
        <c:if test="${requestScope['javax.servlet.error.status_code'] != 500}">
            <div class="def-row container">
                <div class="center-h bold text-large-x">Error Details</div>
            </div>
            <div class="def-row container">
                <div class="def-cell left text-big-xx">
                    <div class="bold">Status Code:</div>
                    <div>${requestScope['javax.servlet.error.status_code']}</div>
                </div>
            </div>
        </c:if>
        <c:if test="${requestScope['javax.servlet.error.status_code'] == 500}">
            <div class="def-row container">
                <div class="center-h bold text-large-x">Exception Details</div>
            </div>
            <div class="def-row container text-big-xx">
                <div class="bold">Servlet Name:</div>
                <div>${requestScope['javax.servlet.error.servlet_name']}</div>
            </div>
            <div class="def-row container text-big-xx">
                <div class="bold">Exception Name and Message:</div>
                <div>${requestScope['javax.servlet.error.exception']}</div>
            </div>
        </c:if>
        <div class="def-row container text-big-xx">
            <div class="bold">Requested URI:</div>
            <div>${requestScope['javax.servlet.error.request_uri']}</div>
        </div>
        <div class="def-row container">
            <div class="center-h">
                <a class="my-button width-xx text-big-xxx" btn-color="blue" href="${pageContext.request.contextPath}/home">Home</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>