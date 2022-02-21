<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title>Categories</title>
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
                const arr = [$(this).attr('id'), data[0]]
                $('#updateModal').data('data', arr).modal('toggle')
            })

            $('#updateModal').on('show.bs.modal', function () {
                const data = $(this).data('data')
                $(this).find('#categoryId').val(data[0])
                const name = $(this).find('#nameUpdate')
                name.val(data[1])
                name.removeClass('is-invalid')
            }).on('hide.bs.modal', function () {
                $(this).find('#categoryId').val('')
                $(this).find('#nameUpdate').val('')
            })

            $('.modalForm').on('submit', function (event) {
                if (!$(this)[0].checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                const category = $(this).find('.name')
                const feedback = $(this).find('.invalid-feedback')
                const categoryValue = category.val()
                if (categoryValue === '') {
                    feedback.text('Category cannot be blank')
                    category.removeClass('is-valid').addClass('is-invalid')
                } else if (!/^[\sa-zA-Z0-9/.-]{8,45}$/.test(categoryValue)) {
                    feedback.text('Category must contain minimum 8 characters maximum 45, letters, digits or /.- symbols.')
                    category.removeClass('is-valid').addClass('is-invalid')
                } else {
                    category.removeClass('is-invalid').addClass('is-valid')
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
            <label for="check" class="btn btn-outline-success">Opened only</label>
        </div>
        <div class="w-auto">
            <button id="newButton" type="button" class="btn btn-outline-info">New category</button>
        </div>
    </div>
    <div class="row col-12">
        <table id="dataTable" class="table table-hover w-100 text-break">
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
                            <button id="${category.id}" type="button"
                                    class="updateButton btn btn-outline-warning rounded-0 w-50 m-1">Update
                            </button>
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