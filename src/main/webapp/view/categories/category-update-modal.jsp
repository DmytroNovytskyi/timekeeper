<%@ page contentType="text/html;charset=UTF-8" %>
<div class="modal" id="updateModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><fmt:message key="admin.categories.modal.updateTitle"/></h4>
            </div>
            <div class="modal-body">
                <form class="row g-3 modalForm"
                      action="categories/update" method="post" novalidate>
                    <div class="col-12 mb-3">
                        <label for="enNameUpdate" class="form-label"><fmt:message
                                key="admin.categories.modal.enCategory"/></label>
                        <input id="enNameUpdate" class="form-control enName" name="enName"
                               required pattern="^[\sa-zA-Z0-9\/.-]{8,45}$">
                        <div class="invalid-feedback enFeedback"></div>
                        <input id="categoryId" type="hidden" name="id">
                    </div>
                    <div class="col-12 mb-3">
                        <label for="uaNameUpdate" class="form-label"><fmt:message
                                key="admin.categories.modal.uaCategory"/></label>
                        <input id="uaNameUpdate" class="form-control uaName" name="uaName"
                               pattern="^[\sА-ЩЬЮЯҐЄІЇа-щьюяґєії0-9\/.-]{8,45}$">
                        <div class="invalid-feedback uaFeedback"></div>
                    </div>
                    <div class="col-12">
                        <button type="button"
                                class="btn col-5 btn-outline-danger float-start"
                                data-bs-dismiss="modal"><fmt:message key="admin.categories.modal.cancel"/>
                        </button>
                        <button type="submit"
                                class="btn col-5 btn-outline-success float-end"><fmt:message
                                key="admin.categories.modal.save"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>