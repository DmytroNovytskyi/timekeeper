<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/button.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/util.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/style/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/script/bootstrap.min.js"></script>
    <title>Change</title>
</head>
<body>
<%@include file="../other/admin-header.jsp" %>
<div class="container">
    <div class="center-h center-v">
        <form class="def-table" action="change" method="post">
            <div class="def-table">
                <div class="def-row">
                    <div class="def-cell bold text-big-xx def-cell">Username:</div>
                    <div class="def-cell container">
                        <input class="text-big-x width-fill" name="username" required
                               value="${requestScope.user.username}"
                               pattern="^[a-zA-Z0-9\.-]{4,45}$"
                               title="Field may contain letters, digits and .- symbols. Min 4, max 45 symbols.">
                    </div>
                </div>
                <div class="def-row">
                    <div class="def-cell bold text-big-xx def-cell">Email:</div>
                    <div class="def-cell container">
                        <input class="text-big-x width-fill" name="email"
                               value="${requestScope.user.email}"
                               pattern="[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,64}$"
                               title="Field may contain letters, digits and ._%+- symbols before @ letters, digits and .- symbols after @ and before . and only letters after . (min 2, max 255 symbols)">
                    </div>
                </div>
                <div class="def-row">
                    <div class="def-cell bold text-big-xx def-cell">New password:</div>
                    <div class="def-cell container">
                        <input class="text-big-x width-fill" name="password"
                               pattern="^[\sa-zA-Z0-9/.-]{4,32}$"
                               title="Field may be empty if no update needed or contain letters, digits and !@#$%^&* - symbols. Min 4, max 32 symbols.">
                        <input type="hidden" name="userId" value="${requestScope.user.id}">
                    </div>
                </div>
            </div>
            <div class="def-row container text-center">
                <div class="left">
                    <a class="my-button width-xx text-big-xx" btn-color="blue"
                       href="${pageContext.request.contextPath}/users">Back</a>
                </div>
                <div class="right">
                    <input class="my-button width-xx text-big-xx" btn-color="blue" type="submit" value="Save">
                </div>
            </div>
        </form>
        <c:if test="${not empty requestScope.message}">
            <div class="def-row container ">
                <div class="center-h">
                    <div class="center-h bold text-big-xx">${requestScope.message}</div>
                </div>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
