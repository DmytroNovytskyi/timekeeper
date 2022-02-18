<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/button.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/util.css">
    <title>No Categories</title>
</head>
<body>
<div class="container">
    <div class="def-table center-h center-v">
        <div class="def-row">
            <div class="text-center bold text-large-xx">No categories were found!</div>
        </div>
        <div class="def-row container">
            <div class="center-h">
                <a class="my-button text-big-xxx" btn-color="blue" href="${pageContext.request.contextPath}/categories/new">Create category</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
