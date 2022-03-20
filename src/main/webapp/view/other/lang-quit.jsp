<%@ page contentType="text/html;charset=UTF-8" %>
<ul class="navbar-nav col-auto ms-auto mb-2 mb-lg-0 me-5">
    <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="langDropdown" role="button"
           data-bs-toggle="dropdown" aria-expanded="false">
            <c:choose>
                <c:when test="${cookie.lang.value == 'en'}">English</c:when>
                <c:otherwise>Українська</c:otherwise>
            </c:choose>
        </a>
        <ul class="dropdown-menu" aria-labelledby="langDropdown">
            <li>
                <form action="" method="post">
                    <c:choose>
                        <c:when test="${cookie.lang.value == 'en'}">
                            <input class="dropdown-item" type="submit" name="lang" value="Українська">
                        </c:when>
                        <c:otherwise>
                            <input class="dropdown-item" type="submit" name="lang" value="English">
                        </c:otherwise>
                    </c:choose>
                </form>
            </li>
        </ul>
    </li>
    <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
           data-bs-toggle="dropdown" aria-expanded="false">
            ${sessionScope.user.username}
        </a>
        <ul class="dropdown-menu" aria-labelledby="userDropdown">
            <li>
                <form action="${pageContext.request.contextPath}/quit" method="post">
                    <input class="dropdown-item" type="submit" value=<fmt:message key="admin.header.logout"/>>
                </form>
            </li>
        </ul>
    </li>
</ul>