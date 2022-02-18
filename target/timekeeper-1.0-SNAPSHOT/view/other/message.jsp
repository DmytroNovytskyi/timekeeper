<c:if test="${not empty requestScope.successMessage}">
    <div class="row col-12">
        <div class="alert alert-success alert-dismissible fade show">
            <strong>Success!</strong> ${requestScope.successMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </div>
</c:if>
<c:if test="${not empty requestScope.warningMessage}">
    <div class="row col-12">
        <div class="alert alert-warning alert-dismissible fade show">
            <strong>Warning!</strong> ${requestScope.warningMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </div>
</c:if>
<c:if test="${not empty requestScope.errorMessage}">
    <div class="row col-12">
        <div class="alert alert-danger alert-dismissible fade show">
            <strong>Error!</strong> ${requestScope.errorMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </div>
</c:if>
