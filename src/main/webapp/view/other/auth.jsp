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
            const form = document.getElementById("loginForm");
            const username = document.getElementById("username");
            const password = document.getElementById("password");
            form.addEventListener("submit", function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                checkInputs();
                form.classList.add('was-validated');
            })

            function checkInputs() {
                const usernameValue = username.value.trim();
                const passwordValue = password.value.trim();
                if (usernameValue === "") {
                    setErrorFor(username, "Username cannot be blank");
                } else if (!usernameRegexTest(usernameValue)) {
                    setErrorFor(username, "Field may only contain lowercase letters and digits. Min 5, max 45 symbols");
                } else {
                    setSuccessFor(username);
                }

                if (passwordValue === "") {
                    setErrorFor(password, "Password cannot be blank");
                } else if (!passwordRegexTest(passwordValue)) {
                    setErrorFor(password, "Field may only contain lowercase letters and digits. Min 5, max 32 symbols");
                } else {
                    setSuccessFor(password);
                }
            }

            function setErrorFor(input, message) {
                input.parentElement.getElementsByClassName("invalid-feedback")[0].innerText = message;
                input.className = "form-control is-invalid";
            }

            function setSuccessFor(input) {
                input.className = "form-control is-valid";
            }

            function usernameRegexTest(text) {
                return /^[a-z0-9]{5,45}/.test(text);
            }

            function passwordRegexTest(text) {
                return /^[a-z0-9]{5,32}/.test(text);
            }
        });
    </script>
</head>
<body>
<%@include file="../other/message.jsp" %>
<div class="container-fluid position-absolute translate-middle-y top-50 left-50 ">
    <div class="row">
        <div class="col-md-4 offset-md-4">
            <div class="login-form bg-light mt-4 p-4">
                <form id="loginForm" action="auth" method="post" class="row g-3" novalidate>
                    <h4 class="text-center">Login</h4>
                    <div class="col-12">
                        <label for="username" class="form-label">Username</label>
                        <input id="username" class="form-control" name="username" placeholder="Username" required
                               pattern="^[a-z0-9]{5,45}">
                        <div class="invalid-feedback">Error</div>
                    </div>
                    <div class="col-12">
                        <label for="password" class="form-label">Password</label>
                        <input id="password" class="form-control" type="password" name="password"
                               placeholder="Password"
                               required pattern="^[a-z0-9]{5,32}">
                        <div class="invalid-feedback">Error</div>
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

