<div class="modal" id="createModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">New category</h4>
            </div>
            <div class="modal-body">
                <form class="row g-3 modalForm"
                      action="categories/create" method="post" novalidate>
                    <div class="col-12 mb-3">
                        <label for="nameCreate" class="form-label">Category</label>
                        <input id="nameCreate" class="form-control name" name="name"
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