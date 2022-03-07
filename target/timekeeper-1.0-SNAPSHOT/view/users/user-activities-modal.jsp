<div class="modal activitiesModal${user.id}" role="dialog">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">${user.username} activities</h4>
            </div>
            <div class="modal-body">
                <table class="userTable table table-hover w-100 text-break">
                    <thead>
                    <tr>
                        <th>Category</th>
                        <th>Activity</th>
                        <th>Total time spent</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.uha}" var="userHasActivity">
                        <c:if test="${userHasActivity.user.id == user.id}">
                            <tr>
                                <td>${userHasActivity.activity.category.name}</td>
                                <td>${userHasActivity.activity.name}</td>
                                <td>${userHasActivity.timeSpent}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>