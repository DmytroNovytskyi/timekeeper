<c:if test="${not empty requestScope.successMessage}">
    <div class="row col-12">
        <div class="alert alert-success alert-dismissible fade show">
            <strong><fmt:message key="message.successTitle"/></strong>
            <div id="successMessage">${requestScope.successMessage}</div>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </div>
</c:if>
<c:if test="${not empty requestScope.warningMessage}">
    <div class="row col-12">
        <div class="alert alert-warning alert-dismissible fade show">
            <strong><fmt:message key="message.warningTitle"/></strong>
            <div id="warningMessage"> ${requestScope.warningMessage}</div>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </div>
</c:if>
<c:if test="${not empty requestScope.errorMessage}">
    <div class="row col-12">
        <div class="alert alert-danger alert-dismissible fade show">
            <strong><fmt:message key="message.errorTitle"/></strong>
            <div id="errorMessage"> ${requestScope.errorMessage}</div>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </div>
</c:if>
