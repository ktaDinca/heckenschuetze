
<%@ include file="/WEB-INF/taglibs.jsp" %>

<script type="text/javascript">

    function saveDepartment() {

        var name = $('#editDeptmodal .modal-body #dname').val();
        var manager_id = $('#editDeptModal .modal-body #managers option:selected').val();
        var division_id = $('#editDeptModal .modal-body #divisions option:selected').val();
        var deptno = $('#editDeptModal .modal-body #depno').val();

        $.ajax({
            type: "post",
            url: "<spring:url value="/admin/departments/edit" />",
            data: {
                id: deptno,
                name: name,
                manager_id: manager_id,
                division_id: division_id
            },
            success: function (data) {
                if (data.message == 'success') {
                    $('#editDeptModal').modal('hide');
                    refreshDepartmentsTable();
                }
            }
        });
    }

    function createPopulateEditDeptModalFunction(department) {
        return function() {

            loadAllDepartmentManagers();
            loadAllDivisions();

            $('#editDeptModal .modal-body #depno').val(department.id);
            $('#editDeptModal .modal-header p').text('Edit ' + department.name);
            $('#editDeptModal .modal-body #dname').val(department.name);
        };
    }

    function loadAllDepartmentManagers() {
        $.ajax({
            type : "get",
            url : "<spring:url value="/admin/employees/dept-managers" />",
            success: function (data) {
                var deptManagerSelect = $('#editDeptModal .modal-body #managers');
                deptManagerSelect.empty();

                deptManagerSelect.append('<option value="none"> select </option>');
                for (var i = 0; i < data.deptManagers.length; i ++) {
                    deptManagerSelect.append('<option value="' + data.deptManagers[i].id + '">' + data.deptManagers[i].firstname + ' ' + data.deptManagers[i].lastname + '</option>');
                }
            }
        });
    }

    function loadAllDivisions() {
        $.ajax({
            type: "get",
            url : "<spring:url value="/admin/divisions" />",
            success : function(data) {
                var divisionsSelect = $('#editDeptModal .modal-body #divisions');
                divisionsSelect.empty();

                divisionsSelect.append('<option value="none"> select </option>');
                for (var i = 0; i < data.divisions.length; i ++) {
                    divisionsSelect.append('<option value="' + data.divisions[i].id + '">' + data.divisions[i].name + '</option>');
                }
            }
        });
    }

    function refreshDepartmentsTable() {
        $('#departments-table > tbody').empty();
        loadAllDepartments();
    }

    function loadAllDepartments() {

        $.ajax({
            type : "get",
            url : "<spring:url value="admin/departments" />",
            success : function(data) {
                console.log(data);

                var table = $('#departments-table');
                var crtDept;
                for (var i = 0; i < data.departments.length; i ++) {
                    var line = '';
                    crtDept = null;
                    if (typeof data.departments[i] !== 'object') {
                        if (departmentsMap[data.departments[i]] != null) {
                            crtDept = departmentsMap[data.departments[i]];
                        }
                        else {
                            console.log('skipping ' + data.departments[i]);
                        }
                    }
                    else {
                        crtDept = data.departments[i];
                    }

                    if (crtDept != null) {
                        line += '<tr>';
                        line += '<td>' + crtDept.name + '</td>';
                        line +=  '<td>' + crtDept.division.name + '</td>';

                        if (crtDept.manager == null) {
                            line += '<td>' + '-' + '</td>';
                        }
                        else {
                            line += '<td>' + crtDept.manager.firstname + ' ' + crtDept.manager.lastname  + '</td>';
                        }

                        line += '<td>' + '<a id="edit_dep_' + i + '"' + 'data-toggle="modal" href="#editDeptModal">' + 'edit' + '</a>' +
                                '<td>' + '<a id="remove_dep_' + i + '"' + ">" + 'delete' + '</a>' +
                                '</tr>';

                        var editLink = $('#edit_dep_' + i);
                        editLink.click(createPopulateEditDeptModalFunction(crtDept));
                    }
                    table.append(line);
                }
            }
        });
    }

    $(document).ready(function() {
        refreshDepartmentsTable();
    });

    function addDepartmentClickHandler() {
        clearEditDeptModal();
        loadAllDepartmentManagers();
        loadAllDivisions();
    }

    function clearEditDeptModal() {
        $('#editDeptModal .modal-body #depno').val('');
        $('#editDeptModal .modal-header p').text('');
        $('#editDeptModal .modal-body #dname').val('');

    }
</script>


<h2>Would you like to add a new department?</h2>
<a class="btn btn-default" data-toggle="modal" id="addDepartment" href="#editDeptModal" onclick="addDepartmentClickHandler()">Add new department</a>

<table id="departments-table" class="table table-responsive">
    <thead>
    <tr>
        <th>Department</th>
        <th>Division</th>
        <th>Manager</th>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
    </tr>
    </thead>
</table>

<div class="modal fade in" id="editDeptModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <p></p>
            </div>
            <div class="modal-body">
                <input type="hidden" name="depno" id="depno">

                <label class="control-label" for="dname">Departament</label>
                <input type="text" name="dname" id="dname" class="form-control input-lg">

                <label class="control-label" for="divisions">Division</label>
                <select id="divisions">
                    <option value="none"> select </option>
                </select>

                <label class="control-label" for="managers">Manager</label>
                <select id="managers">
                    <option value="none"> select </option>
                </select>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-default" id="saveDepartment" onclick="saveDepartment()">Save</button>
            </div>
        </div>
    </div>
</div>



