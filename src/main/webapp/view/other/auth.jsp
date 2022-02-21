<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <title>Authorization</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/util.css">
    <script src="${pageContext.request.contextPath}/script/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/jquery-3.5.1.js"></script>
    <script>
        $(document).ready(function () {
            $('#loginForm').on('submit', function (event) {
                if (!$(this)[0].checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                const username = $(this).find('#username')
                const password = $(this).find('#password')
                const usernameFeedback = $(this).find('#usernameFeedback')
                const passwordFeedback = $(this).find('#passwordFeedback')
                const usernameValue = username.val()
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

                if (passwordValue === '') {
                    passwordFeedback.text('Password cannot be blank')
                    password.removeClass('is-valid').addClass('is-invalid')
                } else if (!/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,32}$/.test(passwordValue)) {
                    passwordFeedback.text('Password must contain minimum 8 characters maximum 32, at least one letter, one number and one special character')
                    password.removeClass('is-valid').addClass('is-invalid')
                } else {
                    passwordFeedback.text('')
                    password.removeClass('is-invalid').addClass('is-valid')
                }
                $(this).addClass('was-validated')
            })
        });
    </script>
</head>
<body>
<%@include file="../other/message.jsp" %>
<div class="container-fluid position-absolute translate-middle-y top-50 left-50">
    <div class="row">
        <div class="col-md-4 offset-md-4 shadow ps-3 pb-4 pe-3">
            <div class="login-form bg-light mt-4 p-4">
                <form id="loginForm" action="auth" method="post" class="row g-3" novalidate>
                    <h4 class="text-center">Login</h4>
                    <div class="col-12">
                        <label for="username" class="form-label">Username</label>
                        <input id="username" class="form-control" name="username" placeholder="Username" required
                               pattern="^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$">
                        <div id="usernameFeedback" class="invalid-feedback"></div>
                        <label for="password" class="form-label">Password</label>
                        <input id="password" class="form-control" type="password" name="password"
                               placeholder="Password"
                               required pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,32}$">
                        <div id="passwordFeedback" class="invalid-feedback"></div>
                    </div>
                    <div class="col-12 end-0">
                        <button type="submit" class="btn btn-outline-success ps-4 pe-4 float-end">Sign in</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

