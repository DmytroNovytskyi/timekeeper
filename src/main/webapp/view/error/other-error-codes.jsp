<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/util.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/button.css">
    <title>Error</title>
</head>
<body>
<div class="container">
    <div class="def-table center-h center-v text-center bold ">
        <div class="def-row">
            <div class="text-large-xx">Oops! Something went wrong.</div>
        </div>
        <div class="def-row">
            <div class="text-large-xx">Error code: ${requestScope['javax.servlet.error.status_code']}</div>
        </div>
        <div class="def-row">
            <div class="center-h text-big-xxx">
                <a class="my-button width-xx" btn-color="blue"
                   href="${pageContext.request.contextPath}/home">Home</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
