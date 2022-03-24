<div class="modal" id="createModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><fmt:message key="admin.categories.modal.createTitle"/></h4>
            </div>
            <div class="modal-body">
                <form class="row g-3 modalForm"
                      action="categories/create" method="post" novalidate>
                    <div class="col-12 mb-3">
                        <label for="nameCreate" class="form-label"><fmt:message
                                key="admin.categories.modal.category"/></label>
                        <input id="nameCreate" class="form-control name" name="name"
                               required pattern="^[\sa-zA-Z0-9/.-]{8,45}$">
                        <div class="invalid-feedback"></div>
                    </div>
                    <div class="col-12">
                        <button type="button"
                                class="btn col-5 btn-outline-danger float-start"
                                data-bs-dismiss="modal"><fmt:message key="admin.categories.modal.cancel"/>
                        </button>
                        <button type="submit"
                                class="btn col-5 btn-outline-success float-end"><fmt:message
                                key="admin.categories.modal.create"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>