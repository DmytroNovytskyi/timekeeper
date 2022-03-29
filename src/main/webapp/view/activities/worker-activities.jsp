<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="messages"/>
<!doctype html>
<html lang="${cookie.lang.value}">
<head>
    <title><fmt:message key="worker.activities.title"/></title>
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
                    "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "<fmt:message key="datatable.all"/>"]],
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

            $('#dataTable tbody').on('click', '.descriptionButton', function () {
                $('#descriptionModal').data('description', $(this).attr('value')).modal('toggle')
            })

            $('#descriptionModal').on('show.bs.modal', function () {
                let description = $(this).data('description')
                if(description === ''){
                    description = '<fmt:message key="message.noDescription"/>'
                }
                $(this).find('.modal-body').text(description)
            })

        })
    </script>
</head>
<body>
<%@include file="../other/worker-header.jsp" %>
<%@include file="description-modal.jsp" %>
<div class="container mt-3 mb-3">
    <%@include file="../other/message.jsp" %>
    <div class="row col-12">
        <table id="dataTable" class="table table-hover w-100">
            <thead>
            <tr>
                <th><fmt:message key="worker.activities.category"/></th>
                <th><fmt:message key="worker.activities.activity"/></th>
                <th><fmt:message key="worker.activities.status"/></th>
                <th><fmt:message key="worker.activities.startTime"/></th>
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
                            <td><fmt:message key="worker.activities.assigned"/></td>
                            <td>-</td>
                            <td class="w-25">
                                <div class="d-flex flex-row">
                                    <div class="m-1">
                                        <button type="button" class="descriptionButton btn btn-outline-info"
                                                style="border-radius: 20px; width: 40px; height: 40px" value="${userHasActivity.activity.description}">i
                                        </button>
                                    </div>
                                    <form class="w-50 d-inline-flex" action="activities/process" method="post">
                                        <input class="btn btn-outline-success rounded-0 w-100 m-1" type="submit" value=
                                            <fmt:message key="worker.activities.start"/>>
                                        <input type="hidden" name="action" value="start">
                                        <input type="hidden" name="id" value="${userHasActivity.id}">
                                    </form>
                                    <form class="w-50 d-inline-flex" action="activities/process" method="post">
                                        <input class="btn btn-outline-danger rounded-0 w-100 m-1" type="submit" value=
                                            <fmt:message key="worker.activities.abort"/>>
                                        <input type="hidden" name="action" value="abort">
                                        <input type="hidden" name="id" value="${userHasActivity.id}">
                                    </form>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td><fmt:message key="worker.activities.inProgress"/></td>
                            <td><fmt:formatDate value="${userHasActivity.startTime}" pattern="dd-MM-yy HH:mm:ss"/></td>
                            <td class="w-25">
                                <div class="d-flex flex-row">
                                    <div class="m-1">
                                        <button type="button" class="descriptionButton btn btn-outline-info"
                                                style="border-radius: 20px; width: 40px; height: 40px" value="${userHasActivity.activity.description}">i
                                        </button>
                                    </div>
                                    <form class="w-50 d-inline-flex" action="activities/process" method="post">
                                        <input class="btn btn-outline-success rounded-0 w-100 m-1"
                                               type="submit" value=<fmt:message key="worker.activities.end"/>>
                                        <input type="hidden" name="action" value="end">
                                        <input type="hidden" name="id" value="${userHasActivity.id}">
                                    </form>
                                    <form class="w-50 d-inline-flex" action="activities/process" method="post">
                                        <input class="btn btn-outline-danger rounded-0 w-100 m-1"
                                               type="submit" value=<fmt:message key="worker.activities.abort"/>>
                                        <input type="hidden" name="action" value="abort">
                                        <input type="hidden" name="id" value="${userHasActivity.id}">
                                    </form>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th><fmt:message key="worker.activities.category"/></th>
                <th><fmt:message key="worker.activities.activity"/></th>
                <th><fmt:message key="worker.activities.status"/></th>
                <th><fmt:message key="worker.activities.startTime"/></th>
                <th></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>
