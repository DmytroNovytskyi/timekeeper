<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title>Requests</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/dataTables.bootstrap5.min.css"/>
    <script src="${pageContext.request.contextPath}/script/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/jquery-3.5.1.js"></script>
    <script src="${pageContext.request.contextPath}/script/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/dataTables.bootstrap5.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#dataTable').DataTable(
                {
                    stateSave: true,
                    "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
                    "columnDefs": [
                        {
                            "targets": [4],
                            orderable: false
                        }
                    ],
                    "language": {
                        "emptyTable": "No requests found"
                    }
                });
        });
    </script>
</head>
<body>
<%@include file="../other/worker-header.jsp" %>
<div class="container mt-3 mb-3">
    <%@include file="../other/message.jsp" %>
    <div class="row col-12">
        <table id="dataTable" class="table table-hover w-100">
            <thead>
            <tr>
                <th>User</th>
                <th>Category</th>
                <th>Activity</th>
                <th>Type</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.list}" var="userHasActivity">
                <tr>
                    <td>${userHasActivity.user.username}</td>
                    <td>${userHasActivity.activity.category.name}</td>
                    <td>${userHasActivity.activity.name}</td>
                    <td>
                        <c:choose>
                            <c:when test="${userHasActivity.status.name().equals('PENDING_ASSIGN')}">
                                assign
                            </c:when>
                            <c:otherwise>
                                abort
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="w-25">
                        <div class="d-flex flex-row">
                            <form class="w-100 d-inline-flex" action="requests/process" method="post">
                                <input class="btn btn-outline-danger rounded-0 w-100 m-1" type="submit" value="Cancel">
                                <input type="hidden" name="id" value="${userHasActivity.id}">
                                <c:choose>
                                    <c:when test="${userHasActivity.status.name().equals('PENDING_ASSIGN')}">
                                        <input type="hidden" name="type" value="assign">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="type" value="abort">
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>User</th>
                <th>Category</th>
                <th>Activity</th>
                <th>Type</th>
                <th></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>