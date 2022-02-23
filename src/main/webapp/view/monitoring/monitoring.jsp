<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html lang="en">
<head>
    <title>Monitoring</title>
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
    <script>
        $(document).ready(function () {
            const table = $('#dataTable').DataTable(
                {

                    "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
                    "language": {
                        "emptyTable": "Nothing to monitor"
                    },
                    "columnDefs": [
                        {
                            targets: [3],
                            type: 'datetime'
                        }
                    ],
                    initComplete: function () {
                        selectProcess(this, '#userSelect', 0)
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
                            if (col.type === "datetime") {
                                const cDate = moment(data[i], 'DD-MM-YY hh:mm:ss')
                                valid = cDate.isValid() && !(min == null || cDate.isBefore(min))
                            }
                        });
                    } else if (!min.isValid() && max.isValid()) {
                        $.each(settings.aoColumns, function (i, col) {
                            if (col.type === "datetime") {
                                const cDate = moment(data[i], 'DD-MM-YY hh:mm:ss')
                                valid = cDate.isValid() && !(max == null || cDate.isAfter(max))
                            }
                        });
                    } else if (min.isValid() && max.isValid()) {
                        $.each(settings.aoColumns, function (i, col) {
                            if (col.type === "datetime") {
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
<%@include file="../other/admin-header.jsp" %>
<div class="container mt-3 mb-3 shadow p-5">
    <%@include file="../other/message.jsp" %>
    <div class="row mb-5">
        <div class="col-6 row">
            <div class="col-4">
                <label for="userSelect">User</label>
                <select id="userSelect" class="js-example-basic-single" multiple="multiple">
                    <option disabled>Chose user...</option>
                </select>
            </div>
            <div class="col-4">
                <label for="activitySelect">Activity</label>
                <select id="activitySelect" class="js-example-basic-single" multiple="multiple">
                    <option disabled>Chose activity...</option>
                </select>
            </div>
            <div class="col-4">
                <label for="statusSelect">Status</label>
                <select id="statusSelect" class="js-example-basic-single" multiple="multiple">
                    <option disabled>Chose status...</option>
                </select>
            </div>
        </div>
        <div class="col-6 row">
            <div class="col-6">
                <label for="min">Min start time</label>
                <input id="min" type="datetime-local" class="rounded border-1 h5 fw-normal">
            </div>
            <div class="col-6">
                <label for="max">Max start time</label>
                <input id="max" type="datetime-local" class="rounded border-1 h5 fw-normal">
            </div>
        </div>
    </div>
    <div class="row col-12">
        <table id="dataTable" class="table table-hover w-100 text-break">
            <thead>
            <tr>
                <th>User</th>
                <th>Activity</th>
                <th>Status</th>
                <th>Start time</th>
                <th>End time</th>
                <th>Time spent</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.list}" var="userHasActivity">
                <tr>
                    <td>${userHasActivity.user.username}</td>
                    <td>${userHasActivity.activity.name}</td>
                    <td class="text-lowercase">${userHasActivity.status}</td>
                    <td><fmt:formatDate value="${userHasActivity.startTime}" pattern="dd-MM-yy HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${userHasActivity.endTime}" pattern="dd-MM-yy HH:mm:ss"/></td>
                    <td>${userHasActivity.timeSpent}</td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>User</th>
                <th>Activity</th>
                <th>Status</th>
                <th>Start time</th>
                <th>End time</th>
                <th>Time spent</th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>