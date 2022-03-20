<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie.lang.value}" />
<fmt:setBundle basename="messages"/>
<!doctype html>
<html lang="${cookie.lang.value}">
<head>
    <title>Users</title>
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
                            "targets": [4],
                            orderable: false
                        }
                    ]
                });
            const checkbox = document.getElementById("check");
            checkbox.checked = sessionStorage.users_check === 'true';
            $('input:checkbox').on('change', function () {
                sessionStorage.setItem('users_check', document.getElementById("check").checked);
                const states = $('input:checkbox[name="activeOnly"]:checked').map(function () {
                    return this.value;
                }).get().join('|');
                table.column(3).search(states, true, false, false).draw(false);
            });

            $('.modal').on('hide.bs.modal', function () {
                $(this).find('form').each(function () {
                    $(this).find('input').each(function () {
                        $(this).removeClass('is-valid').removeClass('is-invalid')
                    })
                    $(this).removeClass('was-validated')
                })
            })

            $('#newButton').on('click', function () {
                $('#createModal').modal('toggle')
            })

            $('#dataTable tbody').on('click', '.updateButton', function () {
                const data = table.row($(this).parents('tr')).data();
                const arr = [$(this).attr('id'), data[0], data[2]]
                $('#updateModal').data('data', arr).modal('toggle')
            })

            $('#updateModal').on('show.bs.modal', function () {
                const data = $(this).data('data')
                $(this).find('#userId').val(data[0])
                const username = $(this).find('#usernameUpdate')
                username.val(data[1])
                username.removeClass('is-invalid')
                const email = $(this).find('#emailUpdate')
                email.val(data[2])
                email.removeClass('is-invalid')
            }).on('hide.bs.modal', function () {
                $(this).find('#passwordUpdate').val('')
            })

            $('.modalForm').on('submit', function (event) {
                if (!$(this)[0].checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                const username = $(this).find('.username')
                const email = $(this).find('.email')
                const password = $(this).find('.password')
                const usernameFeedback = $(this).find('.username-feedback')
                const emailFeedback = $(this).find('.email-feedback')
                const passwordFeedback = $(this).find('.password-feedback')
                const usernameValue = username.val()
                const emailValue = email.val()
                const passwordValue = password.val()

                if (usernameValue === '') {
                    usernameFeedback.text('Username cannot be blank')
                    username.removeClass('is-valid').addClass('is-invalid')
                } else if (!/^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$/.test(usernameValue)) {
                    usernameFeedback.text('Username must contain minimum 8 characters maximum 45, letters and digits that may be separated with ._ symbols.')
                    username.removeClass('is-valid').addClass('is-invalid')
                } else {
                    usernameFeedback.text('')
                    username.removeClass('is-invalid').addClass('is-valid')
                }

                if (emailValue !== '' && !/^(?=[a-zA-Z0-9._@%-]{6,255}$)[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,64}$/.test(emailValue)) {
                    emailFeedback.text('Enter email please. Example: for@example.com')
                    email.removeClass('is-valid').addClass('is-invalid')
                } else {
                    emailFeedback.text('')
                    email.removeClass('is-invalid').addClass('is-valid')
                }

                if (password.attr('required') === 'required' && passwordValue === '') {
                    passwordFeedback.text('Password cannot be blank')
                    password.removeClass('is-valid').addClass('is-invalid')
                } else if (passwordValue !== '' && !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,32}$/.test(passwordValue)) {
                    passwordFeedback.text('Password must contain minimum 8 characters maximum 32, at least one letter, one number and one special character')
                    password.removeClass('is-valid').addClass('is-invalid')
                } else {
                    passwordFeedback.text('')
                    password.removeClass('is-invalid').addClass('is-valid')
                }
                $(this).addClass('was-validated')
            })

            $('.activitiesButton').on('click', function () {
                const selector = '.activitiesModal' + $(this).attr('id')
                $(selector).modal('toggle')
            })

            $('.userTable').DataTable(
                {
                    "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
                });
        });
    </script>
</head>
<body>
<%@include file="../other/admin-header.jsp" %>
<%@include file="user-create-modal.jsp" %>
<%@include file="user-update-modal.jsp" %>
<div class="container mt-3 mb-3 shadow p-5">
    <%@include file="../other/message.jsp" %>
    <div class="row col-12 mb-3">
        <div class="form-check w-auto">
            <input id="check" type="checkbox" class="btn-check" name="activeOnly" value="ACTIVE" autocomplete="off">
            <label for="check" class="btn btn-outline-success">Active only</label>
        </div>
        <div class="w-auto">
            <button id="newButton" type="button" class="btn btn-outline-info">New user</button>
        </div>
    </div>
    <div class="row col-12">
        <table id="dataTable" class="table table-hover w-100 text-break">
            <thead>
            <tr>
                <th>Username</th>
                <th>Role</th>
                <th>Email</th>
                <th>Status</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.users}" var="user">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.role.name.toLowerCase()}</td>
                    <td>${user.email}</td>
                    <td class="text-lowercase">${user.status}</td>
                    <td class="w-25">
                        <div class="d-flex flex-row justify-content-center">
                            <div class="col-4 m-1">
                                <button id="${user.id}" type="button"
                                        class="updateButton btn btn-outline-warning rounded-0 w-100">Update
                                </button>
                            </div>
                            <div class="col-4 m-1">
                                <c:choose>
                                    <c:when test="${user.equals(sessionScope.user)}">
                                        <div class="btn disabled w-100">This user</div>
                                    </c:when>
                                    <c:when test="${user.status.name().equals('ACTIVE')}">
                                        <form action="users/ban" method="post">
                                            <input class="btn btn-outline-danger rounded-0 w-100"
                                                   type="submit" value="Ban">
                                            <input type="hidden" name="id" value="${user.id}">
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="users/unban" method="post">
                                            <input class="btn btn-outline-success rounded-0 w-100"
                                                   type="submit" value="Unban">
                                            <input type="hidden" name="id" value="${user.id}">
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-4 m-1">
                                <c:if test="${not user.role.name.equals('ADMIN')}">
                                    <button id="${user.id}" type="button"
                                            class="activitiesButton btn btn-outline-info rounded-0 w-100">Statistics
                                    </button>
                                    <%@include file="user-activities-modal.jsp" %>
                                </c:if>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>Username</th>
                <th>Role</th>
                <th>Email</th>
                <th>Status</th>
                <th></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>