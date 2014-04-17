<%@ include file="/WEB-INF/taglibs.jsp" %>

<link rel="stylesheet" href="<spring:url value="/resources/css/tab-employees-content.css" />" />

<script type="text/javascript">

    function saveEmployee() {

        var empno = $('#editModal .modal-body #empno').val();
        var username = $('#editModal .modal-body #username').val();
        var lastname = $('#editModal .modal-body #lastname').val();
        var firstname = $('#editModal .modal-body #firstname').val();
        var email = $('#editModal .modal-body #email').val();

        var password = $('#editModal .modal-body #password').val();

        var job = $('#editModal .modal-body #jobs option:selected').text();
        var dept_id= $('#editModal .modal-body #departments option:selected').val();

        $.ajax({
            type : "post",
            url : "<spring:url value="/admin/employees/edit" />",
            data: {
                id : empno,
                username : username,
                lastname : lastname,
                firstname : firstname,
                password : password,
                job : job,
                dept : dept_id,
                email : email
            },
            success : function (data) {
                if (data.message == 'success') {
                    $('#editModal').modal('hide');
                    refreshEmployeeTable();
                    clearEmployeeModal();
                }
            }
        });
    }

    function createRemoveEmployeeFunction(empid) {
        return function(){
            $('#removeEmployeeModal .modal-header p').text('Are you sure you want to remove this record?');
            $('#removeEmployeeModal .modal-header #empid').val(empid);
        };
    }

    function removeEmployee() {
        var empid = $('#removeEmployeeModal .modal-header #empid').val();

        $.ajax({
            type: "post",
            url: "<spring:url value="/admin/employees/delete" />",
            data: {
                id : empid
            },
            success: function() {
                $('#removeEmployeeModal').modal('hide');
                refreshEmployeeTable();
            }
        });
    }

    // when the edit button is pressed
    function createPopulateEditModalFunction(employee) {
        return function () {

            // load the departments needed in the departments select
            loadDepartments();

            $('#editModal .modal-body #empno').val(employee.id);
            $('#editModal .modal-header p').text("Edit " + employee.username);
            $('#editModal .modal-body #username').val(employee.username);
            $('#editModal .modal-body #lastname').val(employee.lastname);
            $('#editModal .modal-body #firstname').val(employee.firstname);
            $('#editModal .modal-body #email').val(employee.email);

        };
    }

    function refreshEmployeeTable() {
        $('#employees-table > tbody').empty();
        loadAllEmployees();
    }

    // populate the departments select inside the employee modal.
    function loadDepartments() {
        $.ajax({
            type: "get",
            url : "<spring:url value="admin/departments" />",
            success : function(data) {
                var departmentsSelect = $('#editModal .modal-body #departments');
                departmentsSelect.empty();

                departmentsSelect.append('<option value="none"> select </option>');
                for (var i = 0; i < data.departments.length; i ++) {
                    departmentsSelect.append('<option value="' + data.departments[i].id + '">' + data.departments[i].name + '</option>');
                }
            }
        });
    }

    function loadAllEmployees() {
        $.ajax({
            type: "get",
            contentType: "application/json",
            url: "<spring:url value="/admin/employees" />",
            success: function(data) {
                var table = $("#employees-table");

                console.log(data);

                for (var i = 0; i < data.employees.length; i ++) {
                    var line = '<tr>' +
                            '<td>' + data.employees[i].id+ '</td>' +
                            '<td>' + data.employees[i].username + '</td>' +
                            '<td>' + data.employees[i].firstname + '</td>' +
                            '<td>' + data.employees[i].lastname + '</td>' +
                            '<td>' + data.employees[i].email + '</td>' +
                            '<td>' + data.employees[i].job + '</td>';

                    if (typeof data.employees[i].department !== 'object') {
                        if (departmentsMap[data.employees[i].department] != null) {
                            line += '<td>' + (departmentsMap[data.employees[i].department]).name + '</td>';
                        }
                        else {
                            line += '<td>' + 'todo: error' + '</td>';
                        }
                    }
                    else {
                        departmentsMap[data.employees[i].department.json_id] = data.employees[i].department;
                        line += '<td>' + data.employees[i].department.name + '</td>';
                    }
                    line +=
                            '<td>' + '<a id="edit_' + i + '"' + ' data-toggle="modal" href="#editModal">edit</a>' + '</td>' +
                            '<td>' + '<a id="remove_' + i + '"' + ' data-toggle="modal" href="#removeEmployeeModal">remove</a>' + '</td>' +
                            '</tr>';
                    table.append(line);

                    // when the edit button is pressed.
                    var editLink = $('#edit_' + i);
                    editLink.click(createPopulateEditModalFunction(data.employees[i]));

                    var removeLink = $('#remove_' + i);
                    removeLink.click(createRemoveEmployeeFunction(data.employees[i].id));
                }
            }
        });
    }

    $(document).ready(function() {
        refreshEmployeeTable();
    });

    function clearEmployeeModal() {
        $('#editModal .modal-body #empno').val('');
        $('#editModal .modal-body #username').val('');
        $('#editModal .modal-body #lastname').val('');
        $('#editModal .modal-body #firstname').val('');
        $('#editModal .modal-body #email').val('');

        $('#editModal .modal-body #password').val('');

        $('#editModal .modal-body #jobs option:selected').removeAttr("selected");
        $('#editModal .modal-body #departments option:selected').removeAttr("selected");
    }

</script>

<h2>Would you like to add a new employee?</h2>
<a class="btn btn-default" data-toggle="modal" id="addEmployee" href="#editModal" onclick="clearEmployeeModal(); loadDepartments()">Add new employee</a>

<table id="employees-table" class="table table-responsive">
    <thead>
    <tr>
        <th>#Id</th>
        <th>Username</th>
        <th>Firstname</th>
        <th>Lasname</th>
        <th>Email</th>
        <th>Job</th>
        <th>Department</th>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
    </tr>
    </thead>
</table>

<div class="modal fade in" id="editModal" tab-index="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <p>Add a new employee</p>
            </div>
            <div class="modal-body">

                <input type="hidden" name="empno" id="empno" value="">

                <label class="control-label" for="username">username</label>
                <input type="text" name="username" id="username" class="form-control input-lg" value="">

                <label class="control-label">password</label>
                <input type="password" name="password" id="password" class="form-control input-lg" value="">

                <label class="control-label">first name</label>
                <input type="text" name="firstname" id="firstname" class="form-control input-lg" value="">

                <label class="control-label">last name</label>
                <input type="text" name="lastname" id="lastname" class="form-control input-lg" value="">

                <label class="control-label">email</label>
                <input type="text" name="email" id="email" class="form-control input-lg" value="">

                <label class="control-label">job</label>
                <select id="jobs">
                    <option value="none"> select </option>
                    <c:forEach items="${jobs}" var="job">
                        <option value="${job}">${job}</option>
                    </c:forEach>
                </select>

                <label class="control-label">department</label>
                <select id="departments">
                    <option value="none"> select </option>
                </select>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-default" id="saveEmployee" onclick="saveEmployee()">save</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade in" id="removeEmployeeModal" tab-index="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <p></p>
                <input type="hidden" name="empid" id="empid">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-default" id="deleteEmployee" onclick="removeEmployee()">Confirm</button>
            </div>
        </div>
    </div>
</div>