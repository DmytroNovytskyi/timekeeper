<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid shadow">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/home">Timekeeper</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page"
                       href="${pageContext.request.contextPath}/home">Home</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        Menu
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/activities">Activities</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/categories">Categories</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/users">Users</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/requests">Requests</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="navbar-nav col-1 ms-auto mb-2 mb-lg-0 me-5">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        ${sessionScope.user.username}
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="userDropdown">
                        <li>
                            <form action="${pageContext.request.contextPath}/quit" method="post">
                                <input class="dropdown-item" type="submit" value="Quit">
                            </form>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
