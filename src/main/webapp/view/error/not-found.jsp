<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!doctype html>
<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <title>Oops!</title>
</head>
<body>
<div class="container-fluid position-absolute translate-middle-y top-50 left-50">
    <div class="row justify-content-center">
        <div class="col-md-6 col-md-offset-4 shadow p-5 text-center">
            <div class="row-fluid">
                <img class="w-auto" src="${pageContext.request.contextPath}/images/404.png" alt="404">
            </div>
            <div class="row">
                <h1 class="fst-italic fw-bold">Page not found</h1>
                <hr>
            </div>
            <div class="row-fluid mt-3">
                <a class="btn btn-outline-dark w-25  fs-6" href="${pageContext.request.contextPath}/home">Home</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
