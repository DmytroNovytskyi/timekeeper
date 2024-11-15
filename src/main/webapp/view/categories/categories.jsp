<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="messages"/>
<!doctype html>
<html lang="${cookie.lang.value}">
<head>
    <title><fmt:message key="admin.categories.title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/dataTables.bootstrap5.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/util.css">
    <script src="${pageContext.request.contextPath}/script/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/jquery-3.5.1.js"></script>
    <script src="${pageContext.request.contextPath}/script/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/dataTables.bootstrap5.min.js"></script>
    <script>
        $(document).ready(function () {
            const table = $('#dataTable').DataTable(
                {
                    stateSave: true,
                    // 'stateSaveParams': function(settings, data) {
                    //     data.columns.forEach(function(column) {
                    //         delete column.visible;
                    //     });
                    // },
                    "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "<fmt:message key="datatable.all"/>"]],
                    "columnDefs": [
                        {
                            "targets": [4],
                            orderable: false
                        },
                        {
                            "targets": [0, 1],
                            "visible": false
                        }
                        <%--                        <c:choose>--%>
                        <%--                        <c:when test="${cookie.lang.value.equals('ua')}">--%>
                        <%--                        {--%>
                        <%--                            "targets": 0,--%>
                        <%--                            "visible": false--%>
                        <%--                        }--%>
                        <%--                        </c:when>--%>
                        <%--                        <c:otherwise>--%>
                        <%--                        {--%>
                        <%--                            "targets": 1,--%>
                        <%--                            "visible": false--%>
                        <%--                        }--%>
                        <%--                        </c:otherwise>--%>
                        <%--                        </c:choose>--%>
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
            checkbox.checked = sessionStorage.cat_check === 'true';
            $('input:checkbox').on('change', function () {
                sessionStorage.setItem('cat_check', document.getElementById("check").checked);
                const states = $('input:checkbox[name="openedOnly"]:checked').map(function () {
                    return this.value;
                }).get().join('|');
                table.column(1).search(states, true, false, false).draw(false);
            })

            $('#newButton').on('click', function () {
                $('#createModal').modal('toggle')
            })

            $('#dataTable tbody').on('click', '.updateButton', function () {
                const data = table.row($(this).parents('tr')).data();
                const arr = [$(this).attr('id'), data[0], data[1]]
                $('#updateModal').data('data', arr).modal('toggle')
            })

            $('#updateModal').on('show.bs.modal', function () {
                const data = $(this).data('data')
                $(this).find('#categoryId').val(data[0])
                const enName = $(this).find('#enNameUpdate')
                enName.val(data[1])
                enName.removeClass('is-invalid')
                const uaName = $(this).find('#uaNameUpdate')
                uaName.val(data[2])
                uaName.removeClass('is-invalid')
            }).on('hide.bs.modal', function () {
                $(this).find('#categoryId').val('')
                $(this).find('#enNameUpdate').val('')
                $(this).find('#uaNameUpdate').val('')
            })

            $('.modalForm').on('submit', function (event) {
                if (!$(this)[0].checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                const enCategory = $(this).find('.enName')
                const enFeedback = $(this).find('.enFeedback')
                const enCategoryValue = enCategory.val()
                const uaCategory = $(this).find('.uaName')
                const uaFeedback = $(this).find('.uaFeedback')
                const uaCategoryValue = uaCategory.val()
                if (enCategoryValue === '') {
                    enFeedback.text('<fmt:message key="inputError.enCategory.blank"/>')
                    enCategory.removeClass('is-valid').addClass('is-invalid')
                } else if (!/^[\sa-zA-Z0-9\/.-]{8,45}$/.test(enCategoryValue)) {
                    enFeedback.text('<fmt:message key="inputError.enCategory.regex"/>')
                    enCategory.removeClass('is-valid').addClass('is-invalid')
                } else {
                    enFeedback.text('')
                    enCategory.removeClass('is-invalid').addClass('is-valid')
                }
                if (!/^(|[\sА-ЩЬЮЯҐЄІЇа-щьюяґєії0-9\/.-]{8,45})$/.test(uaCategoryValue)) {
                    uaFeedback.text('<fmt:message key="inputError.uaCategory.regex"/>')
                    uaCategory.removeClass('is-valid').addClass('is-invalid')
                } else {
                    uaFeedback.text('')
                    uaCategory.removeClass('is-invalid').addClass('is-valid')
                }
                $(this).addClass('was-validated')
            })
        });
    </script>
</head>
<body>
<%@include file="../other/admin-header.jsp" %>
<%@include file="category-create-modal.jsp" %>
<%@include file="category-update-modal.jsp" %>
<div class="container mt-3 mb-3 shadow p-5">
    <%@include file="../other/message.jsp" %>
    <div class="row col-12 mb-3">
        <div class="form-check w-auto">
            <input id="check" type="checkbox" class="btn-check" name="openedOnly" value="OPENED" autocomplete="off">
            <label for="check" class="btn btn-outline-success"><fmt:message key="admin.categories.openedOnly"/></label>
        </div>
        <div class="w-auto">
            <button id="newButton" type="button" class="btn btn-outline-info"><fmt:message
                    key="admin.categories.newCategory"/></button>
        </div>
    </div>
    <div class="row col-12">
        <table id="dataTable" class="table table-hover w-100 text-break">
            <thead>
            <tr>
                <th></th>
                <th></th>
                <th><fmt:message key="admin.categories.category"/></th>
                <th><fmt:message key="admin.categories.status"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.list}" var="category">
                <tr>
                    <td>${category.langName.get('en')}</td>
                    <td>${category.langName.get('ua')}</td>
                    <c:choose>
                        <c:when test="${cookie.lang.value.equals('ua') && !category.langName.get('ua').equals('')}">
                            <td>${category.langName.get('ua')}</td>
                        </c:when>
                        <c:otherwise>
                            <td>${category.langName.get('en')}</td>
                        </c:otherwise>
                    </c:choose>
                    <td class="text-lowercase">${category.status}</td>
                    <td class="w-25">
                        <div class="d-flex flex-row">
                            <button id="${category.id}" type="button"
                                    class="updateButton btn btn-outline-warning rounded-0 w-50 m-1"><fmt:message
                                    key="admin.categories.update"/>
                            </button>
                            <c:choose>
                                <c:when test="${category.status.name().equals('OPENED')}">
                                    <form class="w-50" action="categories/close" method="post">
                                        <input class="btn btn-outline-danger rounded-0 w-100 m-1"
                                               type="submit" value=<fmt:message key="admin.categories.close"/>>
                                        <input type="hidden" name="id" value="${category.id}">
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form class="w-50" action="categories/open" method="post">
                                        <input class="btn btn-outline-success rounded-0 w-100 m-1"
                                               type="submit" value=<fmt:message key="admin.categories.open"/>>
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
                <th></th>
                <th></th>
                <th><fmt:message key="admin.categories.category"/></th>
                <th><fmt:message key="admin.categories.status"/></th>
                <th></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>