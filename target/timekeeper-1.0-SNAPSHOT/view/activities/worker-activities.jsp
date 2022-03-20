<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie.lang.value}" />
<fmt:setBundle basename="messages"/>
<!doctype html>
<html lang="${cookie.lang.value}">
<head>
    <title>Activities</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/dataTables.bootstrap5.min.css"/>
    <script src="${pageContext.request.contextPath}/script/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/jquery-3.5.1.js"></script>
    <script src="${pageContext.request.contextPath}/script/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/dataTables.bootstrap5.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/date-euro.js"></script>
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
                        },
                        {
                            targets: [3],
                            type: 'date-euro'
                        }
                    ],
                    "language": {
                        "emptyTable": "No active activities found"
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
                <th>Status</th>
                <th>Start time</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.list}" var="userHasActivity">
                <tr>
                    <td>${userHasActivity.activity.category.name}</td>
                    <td>${userHasActivity.activity.name}</td>
                    <c:choose>
                        <c:when test="${userHasActivity.status.name().equals('ASSIGNED')}">
                            <td>Assigned</td>
                            <td>-</td>
                            <td class="w-25">
                                <form action="activities/process" method="post">
                                    <div class="d-flex flex-row">
                                        <input class="btn btn-outline-success rounded-0 w-50 m-1"
                                               type="submit" name="action" value="Start">
                                        <input class="btn btn-outline-danger rounded-0 w-50 m-1"
                                               type="submit" name="action" value="Abort">
                                    </div>
                                    <input type="hidden" name="id" value="${userHasActivity.id}">
                                </form>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>In progress</td>
                            <td><fmt:formatDate value="${userHasActivity.startTime}" pattern="dd-MM-yy HH:mm:ss"/></td>
                            <td class="w-25">
                                <form action="activities/process" method="post">
                                    <div class="d-flex flex-row">
                                        <input class="btn btn-outline-success rounded-0 w-50 m-1"
                                               type="submit" name="action" value="End">
                                        <input class="btn btn-outline-danger rounded-0 w-50 m-1"
                                               type="submit" name="action" value="Abort">
                                        <input type="hidden" name="id" value="${userHasActivity.id}">
                                    </div>
                                </form>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>Category</th>
                <th>Activity</th>
                <th>Status</th>
                <th>Start time</th>
                <th></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>
