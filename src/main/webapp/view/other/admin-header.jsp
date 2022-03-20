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
                       href="${pageContext.request.contextPath}/home"><fmt:message key="admin.header.homepage"/></a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false"><fmt:message key="admin.header.menu"/>
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/activities"><fmt:message
                                key="admin.header.activities"/></a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/categories"><fmt:message
                                key="admin.header.categories"/></a>
                        </li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/users"><fmt:message
                                key="admin.header.users"/></a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/requests"><fmt:message
                                key="admin.header.requests"/></a>
                        </li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/monitoring"><fmt:message
                                key="admin.header.monitoring"/></a>
                        </li>
                    </ul>
                </li>
            </ul>
            <%@include file="../other/lang-quit.jsp" %>
        </div>
    </div>
</nav>
