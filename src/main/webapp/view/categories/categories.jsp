<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title>Categories</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/dataTables.bootstrap5.min.css"/>
    <script src="${pageContext.request.contextPath}/script/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/jquery-3.5.1.js"></script>
    <script src="${pageContext.request.contextPath}/script/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/dataTables.bootstrap5.min.js"></script>
    <script>
        $(document).ready(function () {
            const table = $('#dataTable').DataTable(
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
                        "emptyTable": "No categories found"
                    }
                });
            const checkbox = document.getElementById("check");
            checkbox.checked = sessionStorage.act_check === 'true';
            $('input:checkbox').on('change', function () {
                sessionStorage.setItem('act_check', document.getElementById("check").checked);
                const states = $('input:checkbox[name="openedOnly"]:checked').map(function () {
                    return this.value;
                }).get().join('|');
                table.column(1).search(states, true, false, false).draw(false);
            });
        });
    </script>
</head>
<body>
<%@include file="../other/admin-header.jsp" %>
<div class="container mt-3 mb-3">
    <%@include file="../other/message.jsp" %>
    <div class="row col-12">
        <div class="start-0">
            <label for="check">Opened only:</label>
            <input id="check" type="checkbox" name="openedOnly" value="OPENED">
        </div>
    </div>
    <div class="row col-12">
        <table id="dataTable" class="table table-hover w-100">
            <thead>
            <tr>
                <th>Category</th>
                <th>Status</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.list}" var="category">
                <tr>
                    <td>${category.name}</td>
                    <td class="text-lowercase">${category.status}</td>
                    <td class="w-25">
                        <div class="d-flex flex-row">
                            <a class="btn btn-outline-warning rounded-0 w-50 m-1"
                               href="categories/change?id=${category.id}">Update</a>
                            <c:choose>
                                <c:when test="${category.status.name().equals('OPENED')}">
                                    <form class="w-50" action="categories/close" method="post">
                                        <input class="btn btn-outline-danger rounded-0 w-100 m-1"
                                               type="submit" value="Close">
                                        <input type="hidden" name="id" value="${category.id}">
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form class="w-50" action="categories/open" method="post">
                                        <input class="btn btn-outline-success rounded-0 w-100 m-1"
                                               type="submit" value="Open">
                                        <input type="hidden" name="id" value="${category.id}">
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>Category</th>
                <th>Status</th>
                <th></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>