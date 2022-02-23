<div class="modal" id="createModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">New user</h4>
            </div>
            <div class="modal-body">
                <form class="row g-3 modalForm"
                      action="users/create" method="post" novalidate>
                    <div class="col-12 mb-3">
                        <label for="usernameCreate" class="form-label">Username</label>
                        <input id="usernameCreate" class="form-control username" name="username"
                               required pattern="^(?=[a-zA-Z0-9._]{8,45}$)(?!.*[_.]{2})[^_.].*[^_.]$">
                        <div class="username-feedback invalid-feedback"></div>
                        <label for="emailCreate" class="form-label">Email</label>
                        <input id="emailCreate" class="form-control email" name="email"
                               pattern="^(?=[a-zA-Z0-9._@%-]{6,255}$)[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,64}$">
                        <div class="email-feedback invalid-feedback"></div>
                        <label for="role" class="form-label">Role</label>
                        <select class="form-control" id="role"
                                name="roleId">
                            <c:forEach items="${requestScope.roles}" var="role">
                                <option value="${role.id}">${role.name}</option>
                            </c:forEach>
                        </select>
                        <label for="passwordUpdate" class="form-label">Password</label>
                        <input id="passwordUpdate" type="password" class="form-control password" name="password"
                               required pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,32}$">
                        <div class="password-feedback invalid-feedback"></div>
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