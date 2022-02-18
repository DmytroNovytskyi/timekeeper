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
                    <div class="def-cell bold text-big-xx def-cell">Category:</div>
                    <div class="def-cell container">
                        <select class="text-big-x width-fill" name="categoryId">
                            <option value="${requestScope.activity.category.id}">${requestScope.activity.category.name}</option>
                            ${requestScope.categories.remove(requestScope.activity.category)}
                            <c:forEach items="${requestScope.categories}" var="category">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="def-row">
                    <div class="def-cell bold text-big-xx def-cell">Activity:</div>
                    <div class="def-cell container">
                        <input class="text-big-x width-fill" name="activityName" required
                               value="${requestScope.activity.name}"
                               pattern="^[\sa-zA-Z0-9/.-]{8,45}$"
                               title="Field may contain letters, digits, space and / . - symbols. Min 8, max 45 symbols.">
                        <input type="hidden" name="activityId" value="${requestScope.activity.id}">
                    </div>
                </div>
            </div>
            <div class="def-row container text-center">
                <div class="left">
                    <a class="my-button width-xx text-big-xx" btn-color="blue"
                       href="${pageContext.request.contextPath}/activities">Back</a>
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
