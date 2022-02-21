<div class="modal" id="updateModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Update user</h4>
            </div>
            <div class="modal-body">
                <form class="row g-3 modalForm"
                      action="users/update" method="post" novalidate>
                    <div class="col-12 mb-3">
                        <label for="usernameUpdate" class="form-label">Username</label>
                        <input id="usernameUpdate" class="form-control username" name="username"
                               required pattern="^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$">
                        <div class="username-feedback invalid-feedback"></div>
                        <label for="emailUpdate" class="form-label">Email</label>
                        <input id="emailUpdate" class="form-control email" name="email"
                               pattern="^(?=[a-zA-Z0-9._@%-]{6,255}$)[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,64}$">
                        <div class="email-feedback invalid-feedback"></div>
                        <label for="passwordUpdate" class="form-label">Password</label>
                        <input id="passwordUpdate" type="password" class="form-control password" name="password"
                               pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,32}$">
                        <div class="password-feedback invalid-feedback"></div>
                        <input id="userId" type="hidden" name="userId">
                    </div>
                    <div class="col-12">
                        <button type="button"
                                class="btn col-5 btn-outline-danger float-start"
                                data-bs-dismiss="modal">Cancel
                        </button>
                        <button type="submit"
                                class="btn col-5 btn-outline-success float-end">Save
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>