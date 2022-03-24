<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="messages"/>
<!doctype html>
<html lang="${cookie.lang.value}">
<head>
    <title><fmt:message key="admin.activities.title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/dataTables.bootstrap5.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/util.css">
    <%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap-icons.css"/>--%>
    <script src="${pageContext.request.contextPath}/script/jquery-3.5.1.js"></script>
    <script src="${pageContext.request.contextPath}/script/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/dataTables.bootstrap5.min.js"></script>
    <script>
        $(document).ready(function () {
            const table = $('#dataTable').DataTable(
                {
                    stateSave: true,
                    "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "<fmt:message key="datatable.all"/>"]],
                    "columnDefs": [
                        {
                            "targets": [4],
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

            const checkbox = document.getElementById("check");
            checkbox.checked = sessionStorage.act_check === 'true';
            $('input:checkbox').on('change', function () {
                sessionStorage.setItem('act_check', document.getElementById("check").checked);
                const states = $('input:checkbox[name="openedOnly"]:checked').map(function () {
                    return this.value;
                }).get().join('|');
                table.column(2).search(states, true, false, false).draw(false);
            });

            $('#newButton').on('click', function () {
                if (${empty requestScope.categories}) {
                    printCategoriesCheckMessage()
                } else {
                    $('#createModal').modal('toggle')
                }
            })

            function printCategoriesCheckMessage() {
                $('#messageRow').append('<div class="row col-12"> <div class="alert alert-danger'
                    + ' alert-dismissible fade show"> <strong><fmt:message key="message.errorTitle"/></strong> <div id="errorMessage">'
                    + '<fmt:message key="admin.activities.error.noCategories"/></div> <button type="button"'
                    + ' class="btn-close" data-bs-dismiss="alert"></button> </div> </div>')
            }

            $('#dataTable tbody').on('click', '.updateButton', function () {
                if (${empty requestScope.categories}) {
                    printCategoriesCheckMessage()
                } else {
                    const data = table.row($(this).parents('tr')).data();
                    const arr = [$(this).attr('id'), data[0], data[1]]
                    $('#updateModal').data('data', arr).modal('toggle')
                }
            })

            $('#updateModal').on('show.bs.modal', function () {
                const data = $(this).data('data')
                $(this).find('#activityId').val(data[0])
                $(this).find('#categoryUpdate').children('option').each(function () {
                    if ($(this).html() === data[1]) {
                        $(this).attr('selected', 'selected')
                    }
                })
                const activityName = $(this).find('#activityUpdateName')
                activityName.val(data[2])
                activityName.removeClass('is-invalid')
            }).on('hide.bs.modal', function () {
                $(this).find('#activityId').val('')
                $("#categoryUpdate option[selected]").removeAttr('selected')
                $(this).find('#activityUpdateName').val('')
            })

            $('.modalForm').on('submit', function (event) {
                if (!$(this)[0].checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                const activity = $(this).find('.activityName')
                const feedback = $(this).find('.invalid-feedback')
                const activityValue = activity.val()
                if (activityValue === '') {
                    feedback.text('<fmt:message key="inputError.activity.blank"/>')
                    activity.removeClass('is-valid').addClass('is-invalid')
                } else if (!/^[\sa-zA-Z0-9/.-]{8,45}$/.test(activityValue)) {
                    feedback.text('<fmt:message key="inputError.activity.regex"/>')
                    activity.removeClass('is-valid').addClass('is-invalid')
                } else {
                    activity.removeClass('is-invalid').addClass('is-valid')
                }
                $(this).addClass('was-validated')
            })
        });
    </script>
</head>
<body>
<%@include file="../other/admin-header.jsp" %>
<%@include file="activity-create-modal.jsp" %>
<%@include file="activity-update-modal.jsp" %>
<div class="container mt-3 mb-3 shadow p-5">
    <div id="messageRow" class="row col-12">
        <%@include file="../other/message.jsp" %>
    </div>
    <div class="row mb-3">
        <div class="form-check w-auto">
            <input id="check" type="checkbox" class="btn-check" name="openedOnly" value="OPENED" autocomplete="off">
            <label for="check" class="btn btn-outline-success"><fmt:message key="admin.activities.openedOnly"/></label>
        </div>
        <div class="w-auto">
            <button id="newButton" type="button" class="btn btn-outline-info"><fmt:message
                    key="admin.activities.newActivity"/></button>
        </div>
    </div>
    <div class="row col-12">
        <table id="dataTable" class="table table-hover w-100 text-break">
            <thead>
            <tr>
                <th><fmt:message key="admin.activities.category"/></th>
                <th><fmt:message key="admin.activities.activity"/></th>
                <th><fmt:message key="admin.activities.status"/></th>
                <th><fmt:message key="admin.activities.workers"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.activities}" var="activity">
                <tr>
                    <td>${activity.category.name}</td>
                    <td>${activity.name}</td>
                    <td class="text-lowercase">${activity.status}</td>
                    <td>${activity.userCount}</td>
                    <td class="w-25">
                        <div class="d-flex flex-row">
                                <%--                            <button id="${activity.id}" type="button"--%>
                                <%--                                    class="updateButton btn btn-outline-warning border-0 fs-5 bg-transparent shadow-none">--%>
                                <%--                                <i style="font-size: 1.5rem">Edit <i class="bi bi-pencil-square"></i></i>--%>
                                <%--                            </button>--%>
                            <button id="${activity.id}" type="button"
                                    class="updateButton btn btn-outline-warning rounded-0 w-50 m-1"><fmt:message
                                    key="admin.activities.update"/>
                            </button>
                            <c:choose>
                                <c:when test="${activity.status.name().equals('OPENED')}">
                                    <form class="w-50" action="activities/close" method="post">
                                        <input class="btn btn-outline-danger rounded-0 w-100 m-1"
                                               type="submit" value=<fmt:message key="admin.activities.close"/>>
                                            <%--                                        <button type="submit" value="Close"--%>
                                            <%--                                                class="btn btn-outline-danger border-0 fs-5 bg-transparent shadow-none">--%>
                                            <%--                                            <i style="font-size: 1.5rem">Close <i class="bi bi-x-circle"></i></i>--%>
                                            <%--                                        </button>--%>
                                        <input type="hidden" name="id" value="${activity.id}">
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form class="w-50" action="activities/open" method="post">
                                            <%--                                        <button type="submit" value="Open"--%>
                                            <%--                                                class="btn btn-outline-success border-0 fs-5 bg-transparent shadow-none">--%>
                                            <%--                                            <i style="font-size: 1.5rem">Open <i class="bi bi-check-circle"></i></i>--%>
                                            <%--                                        </button>--%>
                                        <input class="btn btn-outline-success rounded-0 w-100 m-1"
                                               type="submit" value=<fmt:message key="admin.activities.open"/>>
                                        <input type="hidden" name="id" value="${activity.id}">
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
                <th><fmt:message key="admin.activities.category"/></th>
                <th><fmt:message key="admin.activities.activity"/></th>
                <th><fmt:message key="admin.activities.status"/></th>
                <th><fmt:message key="admin.activities.workers"/></th>
                <th></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>