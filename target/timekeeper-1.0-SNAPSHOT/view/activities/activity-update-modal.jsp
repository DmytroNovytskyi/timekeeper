<div class="modal" id="updateModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><fmt:message key="admin.activities.modal.updateTitle"/></h4>
            </div>
            <div class="modal-body">
                <form class="row g-3 modalForm"
                      action="activities/update" method="post" novalidate>
                    <div class="col-12 mb-3">
                        <label for="categoryUpdate" class="form-label"><fmt:message
                                key="admin.activities.modal.category"/></label>
                        <select class="form-control" id="categoryUpdate"
                                name="categoryId">
                            <c:forEach items="${requestScope.categories}" var="category">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-12 mb-3">
                        <label for="activityUpdateName" class="form-label"><fmt:message
                                key="admin.activities.modal.activity"/></label>
                        <input id="activityUpdateName" class="form-control activityName" name="activityName"
                               required pattern="^[\sa-zA-Z0-9/.-]{8,45}$">
                        <div class="invalid-feedback"></div>
                        <input id="activityId" type="hidden" name="activityId">
                    </div>
                    <div class="col-12">
                        <button type="button"
                                class="btn col-5 btn-outline-danger float-start"
                                data-bs-dismiss="modal"><fmt:message key="admin.activities.modal.cancel"/>
                        </button>
                        <button type="submit"
                                class="btn col-5 btn-outline-success float-end"><fmt:message
                                key="admin.activities.modal.save"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>