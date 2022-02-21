<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title>Activities</title>
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
                            "targets": [2],
                            orderable: false
                        }
                    ],
                    "language": {
                        "emptyTable": "No free activities found"
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
                <th>Category</th>
                <th>Activity</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.list}" var="activity">
                <tr>
                    <td>${activity.category.name}</td>
                    <td>${activity.name}</td>
                    <td class="w-25">
                        <form class="w-100" action="request" method="post">
                            <input class="btn btn-outline-success rounded-0 w-100 m-1"
                                   type="submit" value="Request">
                            <input type="hidden" name="id" value="${activity.id}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>Category</th>
                <th>Activity</th>
                <th></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>