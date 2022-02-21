<div class="modal" id="createModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">New activity</h4>
            </div>
            <div class="modal-body">
                <form class="row g-3 modalForm"
                      action="activities/create" method="post" novalidate>
                    <div class="col-12 mb-3">
                        <label for="categoryCreate" class="form-label">Category</label>
                        <select class="form-control" id="categoryCreate"
                                name="categoryId">
                            <c:forEach items="${requestScope.categories}" var="category">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-12 mb-3">
                        <label for="activityCreateName" class="form-label">Activity</label>
                        <input id="activityCreateName" class="form-control activityName" name="activityName"
                               required pattern="^[\sa-zA-Z0-9/.-]{8,45}$">
                        <div class="invalid-feedback"></div>
                    </div>
                    <div class="col-12">
                        <button type="button"
                                class="btn col-5 btn-outline-danger float-start"
                                data-bs-dismiss="modal">Cancel
                        </button>
                        <button type="submit"
                                class="btn col-5 btn-outline-success float-end">Create
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>