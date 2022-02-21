<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <title>Error</title>
</head>
<body>
<div class="container-fluid position-absolute translate-middle-y top-50 left-50">
    <div class="row justify-content-center">
        <div class="container col-md-8 col-md-offset-4 shadow p-5">
            <c:if test="${requestScope['javax.servlet.error.status_code'] != 500}">
                <div class="row-fluid text-center"><h3 class="fw-bold">Error Details</h3></div>
                <div class="row">
                    <h4 class="fst-italic w-auto fw-bold">Status Code:</h4>
                    <h4 class="w-auto">${requestScope['javax.servlet.error.status_code']}}</h4>
                    <hr>
                </div>
            </c:if>
            <c:if test="${requestScope['javax.servlet.error.status_code'] == 500}">
                <div class="row-fluid text-center"><h3 class="fw-bold">Exception Details</h3></div>
                <div class="row">
                    <h4 class="fst-italic w-auto fw-bold">Servlet Name:</h4>
                    <h4 class="w-auto">${requestScope['javax.servlet.error.servlet_name']}</h4>
                    <hr>
                </div>
                <div class="row">
                    <h4 class="fst-italic w-auto fw-bold ">Exception Name and Message:</h4>
                    <h4 class="w-auto">${requestScope['javax.servlet.error.exception']}</h4>
                    <hr>
                </div>
            </c:if>
            <div class="row">
                <h4 class="fst-italic w-auto fw-bold">Requested URI:</h4>
                <h4 class="w-auto">${requestScope['javax.servlet.error.request_uri']}</h4>
            </div>
            <div class="row-fluid text-center mt-3">
                <a class="btn btn-outline-dark w-25" href="${pageContext.request.contextPath}/home">Home</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>