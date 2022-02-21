<button type="button" class="btn btn-outline-warning rounded-0 w-50 m-1" data-bs-toggle="modal"
        data-bs-target="#update${activity.id}">Update
</button>
<div class="modal" id="update${activity.id}" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Update</h4>
            </div>
            <div class="modal-body">
                <form class="row g-3" action="activities/change" method="post">
                    <div class="col-12 mb-3">
                        <label for="categoryLabel" class="form-label">Category</label>
                        <select class="form-select" id="categoryLabel" name="category">
                            <option value="${activity.category.id}">${activity.category.name}</option>
                            ${requestScope.catetegories.remove(activity.category)}
                            <c:forEach items="${requestScope.categories}" var="category">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                            ${requestScope.catetegories.add(activity.category)}
                        </select>
                    </div>
                    <div class="col-12 mb-3">
                        <label for="activity" class="form-label">Activity</label>
                        <input id="activity" class="form-control" name="activity"
                               value="${activity.name}" required
                               pattern="^[a-z0-9]{5,45}">
                        <div class="invalid-feedback">Error</div>
                    </div>
                    <div class="col-12">
                        <button type="submit" class="btn btn-outline-primary ps-4 pe-4 float-start">Save</button>
                        <button type="submit" class="btn btn-outline-primary ps-4 pe-4 float-end">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
