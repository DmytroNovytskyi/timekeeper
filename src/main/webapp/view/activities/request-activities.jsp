<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie.lang.value}" />
<fmt:setBundle basename="messages"/>
<!doctype html>
<html lang="${cookie.lang.value}">
<head>
    <title><fmt:message key="worker.activities.request.title"/></title>
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
                    "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "<fmt:message key="datatable.all"/>"]],
                    "columnDefs": [
                        {
                            "targets": [2],
                            orderable: false
                        }
                    ],
                    "language": {
                        "emptyTable": "<fmt:message key="datatable.emptyTable"/>",
                        "info": "<fmt:message key="datatable.info"/>",
                        "infoEmpty": "<fmt:message key="datatable.infoEmpty"/>",
                        "infoFiltered": "<fmt:message key="datatable.infoFiltered"/>",
                        "infoPostFix": "<fmt:message key="datatable.infoPostFix"/>",
                        "thousands": "<fmt:message key="datatable.thousands"/>",
                        "lengthMenu": "<fmt:message key="datatable.lengthMenu"/>",
                        "loadingRecords": "<fmt:message key="datatable.loadingRecords"/>",
                        "processing": "<fmt:message key="datatable.processing"/>",
                        "search": "<fmt:message key="datatable.search"/>",
                        "zeroRecords": "<fmt:message key="datatable.zeroRecords"/>",
                        "paginate": {
                            "first": "<fmt:message key="datatable.first"/>",
                            "last": "<fmt:message key="datatable.last"/>",
                            "next": "<fmt:message key="datatable.next"/>",
                            "previous": "<fmt:message key="datatable.previous"/>"
                        },
                        "aria": {
                            "sortAscending": "<fmt:message key="datatable.sortAscending"/>",
                            "sortDescending": "<fmt:message key="datatable.sortDescending"/>"
                        }
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
                <th><fmt:message key="worker.activities.request.category"/></th>
                <th><fmt:message key="worker.activities.request.activity"/></th>
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
                                   type="submit" value="<fmt:message key="worker.activities.request.request"/>">
                            <input type="hidden" name="id" value="${activity.id}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th><fmt:message key="worker.activities.request.category"/></th>
                <th><fmt:message key="worker.activities.request.activity"/></th>
                <th></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>