<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie.lang.value}" />
<fmt:setBundle basename="messages"/>
<!doctype html>
<html lang="${cookie.lang.value}">
<head>
    <title><fmt:message key="admin.monitoring.title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/dataTables.bootstrap5.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/select2.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/util.css">
    <script src="${pageContext.request.contextPath}/script/jquery-3.5.1.js"></script>
    <script src="${pageContext.request.contextPath}/script/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/dataTables.bootstrap5.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/select2.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/moment.js"></script>
    <script src="${pageContext.request.contextPath}/script/date-euro.js"></script>
    <script>
        $(document).ready(function () {
            const table = $('#dataTable').DataTable(
                {

                    "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "<fmt:message key="datatable.all"/>"]],
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
                    },
                    "columnDefs": [
                        {
                            targets: [3, 4, 6],
                            type: 'date-euro'
                        }
                    ],
                    initComplete: function () {
                        selectProcess(this, '#firstSelect', 0)
                        selectProcess(this, '#activitySelect', 1)
                        selectProcess(this, '#statusSelect', 2)
                    }
                });

            $('.select2').val(null).trigger('change')

            $('#min, #max').on('change', function () {
                table.draw()
            })

            $.fn.dataTable.ext.search.push(
                function (settings, data) {
                    let valid
                    let min = moment($("#min").val())
                    let max = moment($("#max").val())
                    if (min.isValid() && !max.isValid()) {
                        $.each(settings.aoColumns, function (i, col) {
                            if (col.type === "date-euro" && i === 6) {
                                const cDate = moment(data[i], 'DD-MM-YY hh:mm:ss')
                                valid = cDate.isValid() && !(min == null || cDate.isBefore(min))
                            }
                        });
                    } else if (!min.isValid() && max.isValid()) {
                        $.each(settings.aoColumns, function (i, col) {
                            if (col.type === "date-euro" && i === 6) {
                                const cDate = moment(data[i], 'DD-MM-YY hh:mm:ss')
                                valid = cDate.isValid() && !(max == null || cDate.isAfter(max))
                            }
                        });
                    } else if (min.isValid() && max.isValid()) {
                        $.each(settings.aoColumns, function (i, col) {
                            if (col.type === "date-euro" && i === 6) {
                                const cDate = moment(data[i], 'DD-MM-YY hh:mm:ss')
                                valid = cDate.isValid() && !(min == null || max == null || cDate.isBefore(min) || cDate.isAfter(max))
                            }
                        });
                    } else {
                        valid = true
                    }
                    return valid;
                })

            function selectProcess(table, selectId, columnId) {
                const activitySelect = $(selectId)
                table.api().column(columnId).data().unique().sort().each(function (d) {
                    activitySelect.append('<option value="' + d + '">' + d + '</option>')
                })
                activitySelect.select2().on('change', function () {
                    let data = $.map($(this).select2('data'), function (value) {
                        return value.text ? '^' + $.fn.dataTable.util.escapeRegex(value.text) + '$' : null;
                    });
                    if (data.length === 0) {
                        data = [""];
                    }
                    const val = data.join('|');
                    table.api().column(columnId).search(val ? val : '', true, false).draw();
                });
            }
        });
    </script>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.user.role.name.equals('ADMIN')}">
        <%@include file="../other/admin-header.jsp" %>
    </c:when>
    <c:otherwise>
        <%@include file="../other/worker-header.jsp" %>
    </c:otherwise>
</c:choose>
<div class="container mt-3 mb-3 shadow p-5">
    <%@include file="../other/message.jsp" %>
    <div class="row mb-5">
        <div class="col-6 row">
            <div class="col-4">
                <c:choose>
                    <c:when test="${sessionScope.user.role.name.equals('ADMIN')}">
                        <label for="firstSelect"><fmt:message key="admin.monitoring.user"/></label>
                        <select id="firstSelect" class="js-example-basic-single" multiple="multiple">
                            <option disabled><fmt:message key="admin.monitoring.chooseUser"/></option>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <label for="firstSelect">Category</label>
                        <select id="firstSelect" class="js-example-basic-single" multiple="multiple">
                            <option disabled>Chose category...</option>
                        </select>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-4">
                <label for="activitySelect"><fmt:message key="admin.monitoring.activity"/></label>
                <select id="activitySelect" class="js-example-basic-single" multiple="multiple">
                    <option disabled><fmt:message key="admin.monitoring.chooseActivity"/></option>
                </select>
            </div>
            <div class="col-4">
                <label for="statusSelect"><fmt:message key="admin.monitoring.status"/></label>
                <select id="statusSelect" class="js-example-basic-single" multiple="multiple">
                    <option disabled><fmt:message key="admin.monitoring.chooseStatus"/></option>
                </select>
            </div>
        </div>
        <div class="col-6 row">
            <div class="col-6">
                <label for="min"><fmt:message key="admin.monitoring.after"/></label>
                <input id="min" type="datetime-local" class="rounded border-1 h5 fw-normal">
            </div>
            <div class="col-6">
                <label for="max"><fmt:message key="admin.monitoring.before"/></label>
                <input id="max" type="datetime-local" class="rounded border-1 h5 fw-normal">
            </div>
        </div>
    </div>
    <div class="row col-12">
        <table id="dataTable" class="table table-hover w-100 text-break">
            <thead>
            <tr>
                <c:choose>
                    <c:when test="${sessionScope.user.role.name.equals('ADMIN')}">
                        <th><fmt:message key="admin.monitoring.user"/></th>
                    </c:when>
                    <c:otherwise>
                        <th>Category</th>
                    </c:otherwise>
                </c:choose>
                <th><fmt:message key="admin.monitoring.activity"/></th>
                <th><fmt:message key="admin.monitoring.status"/></th>
                <th><fmt:message key="admin.monitoring.startTime"/></th>
                <th><fmt:message key="admin.monitoring.endTime"/></th>
                <th><fmt:message key="admin.monitoring.timeSpent"/></th>
                <th><fmt:message key="admin.monitoring.creationDate"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.list}" var="userHasActivity">
                <tr>
                    <c:choose>
                        <c:when test="${sessionScope.user.role.name.equals('ADMIN')}">
                            <td>${userHasActivity.user.username}</td>
                        </c:when>
                        <c:otherwise>
                            <td>${userHasActivity.activity.category.name}</td>
                        </c:otherwise>
                    </c:choose>
                    <td>${userHasActivity.activity.name}</td>
                    <td class="text-lowercase">${userHasActivity.status}</td>
                    <td><fmt:formatDate value="${userHasActivity.startTime}" pattern="dd-MM-yy HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${userHasActivity.endTime}" pattern="dd-MM-yy HH:mm:ss"/></td>
                    <td>${userHasActivity.timeSpent}</td>
                    <td><fmt:formatDate value="${userHasActivity.creationDate}" pattern="dd-MM-yy HH:mm:ss"/></td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <c:choose>
                    <c:when test="${sessionScope.user.role.name.equals('ADMIN')}">
                        <th><fmt:message key="admin.monitoring.user"/></th>
                    </c:when>
                    <c:otherwise>
                        <th>Category</th>
                    </c:otherwise>
                </c:choose>
                <th><fmt:message key="admin.monitoring.activity"/></th>
                <th><fmt:message key="admin.monitoring.status"/></th>
                <th><fmt:message key="admin.monitoring.startTime"/></th>
                <th><fmt:message key="admin.monitoring.endTime"/></th>
                <th><fmt:message key="admin.monitoring.timeSpent"/></th>
                <th><fmt:message key="admin.monitoring.creationDate"/></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>