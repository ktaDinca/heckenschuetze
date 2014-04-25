<%@ include file="/WEB-INF/taglibs.jsp" %>

<script type="text/javascript">

    function saveDivision() {
        var divid = $('#editDivisionModal .modal-body #divno').val();
        var divname = $('#editDivisionModal .modal-body #divname').val();
        var manager_id = $('#editDivisionModal .modal-body #divmanagers option:selected').val();

        $.ajax({
            type: "post",
            url: "<spring:url value="/intervals/admin/divisions/edit" />",
            data: {
                id: divid,
                name: divname,
                manager_id : manager_id
            },
            success: function(data) {
                if (data.message == 'success') {
                    $('#editDivisionModal').modal('hide');
                    refreshDivisionsTable();
                }
            }
        });

    }

    function loadAllDivManagers() {
        $.ajax({
            type: "get",
            url: "<spring:url value="/intervals/admin/employees/division-managers" />",
            success: function(data) {
                var divManagerSelect = $('#editDivisionModal .modal-body #divmanagers');
                divManagerSelect.empty();

                divManagerSelect.append('<option value="none"> select </option>');
                for (var i = 0; i < data.divisionManagers.length; i ++) {
                    divManagerSelect.append('<option value="' + data.divisionManagers[i].id + '">' + data.divisionManagers[i].firstname + ' ' + data.divisionManagers[i].lastname + '</option>');
                }
            }
        });
    }

    function createPopulateEditDivisionModalFunction(division) {
        return function() {

            loadAllDivManagers();

            $('#editDivisionModal .modal-header p').text('Edit ' + division.name);
            $('#editDivisionModal .modal-body #divno').val(division.id);
            $('#editDivisionModal .modal-body #divname').val(division.name);
        };
    }

    function clearDivisionsModal() {
        $('#editDivisionModal .modal-header p').text('');
        $('#editDivisionModal .modal-body #divno').val('');
        $('#editDivisionModal .modal-body #divname').val('');

//        $('#editDivisionModal .modal-body #divmanagers option:selected').removeAttribute("selected");
    }

    function createRemoveDivisionFunction(divid) {
        return function() {
            $('#removeDivisionsModal .modal-body p').text('Are you sure you want to remove this division?');
            $('#removeDivisionsModal .modal-body #divid').val(divid);
        };
    }

    function removeDivision() {
        var divid = $('#removeDivisionsModal .modal-body #divid').val();

        $.ajax({
            type: "post",
            url: "<spring:url value="/intervals/admin/divisions/remove" />",
            data: {
                id : divid
            },
            success: function(data) {
                if (data.message == 'success') {
                    $('#removeDivisionsModal').modal('hide');
                    refreshDivisionsTable();
                }
            }
        });
    }

    function loadDivisions() {
        $.ajax({
            type: "get",
            url : "<spring:url value="/intervals/admin/divisions" />",
            success : function(data) {
                console.log(data);

                var table = $('#divisions-table');
                for (var i = 0; i < data.divisions.length; i ++) {
                    var line = '<tr>' +
                            '<td>' + data.divisions[i].id + '</td>' +
                            '<td>' + data.divisions[i].name + '</td>';

                    if (data.divisions[i].manager == null) {
                        line += '<td>' + '-' + '</td>';
                    }
                    else {
                        line += '<td>' + data.divisions[i].manager.firstname + ' ' + data.divisions[i].manager.lastname  + '</td>';
                    }

                    line += '<td>' + '<a id="edit_div_' + i + '"' + 'data-toggle="modal" href="#editDivisionModal">' + 'edit' + '</a>' + '</td>' +
                            '<td>' + '<a id="remove_div_' + i + '"' + 'data-toggle="modal" href="#removeDivisionsModal" >' + 'delete' + '</a>' + '</td>' +
                            '</tr>';

                    table.append(line);

                    var editLink = $('#edit_div_' + i);
                    editLink.click(createPopulateEditDivisionModalFunction(data.divisions[i]));

                    var deleteLink = $('#remove_div_' + i);
                    deleteLink.click(createRemoveDivisionFunction(data.divisions[i].id));
                }
            }
        });
    }

    function refreshDivisionsTable() {
        $('#divisions-table > tbody').empty();
        loadDivisions();
    }

    $(document).ready(function() {
        refreshDivisionsTable();
    });

    function addDivisionClickHandler() {
        clearDivisionsModal();
        loadAllDivManagers();
    }

</script>

<h2>Would you like to add a new division?</h2>
<a class="btn btn-default" data-toggle="modal" id="addDivision" href="#editDivisionModal" onclick="addDivisionClickHandler()">Add new division</a>

<table id="divisions-table" class="table table-responsive">
    <thead>
        <tr>
            <th>#Id</th>
            <th>Name</th>
            <th>Manager</th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
        </tr>
    </thead>
</table>

<div class="modal fade in" id="editDivisionModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <p></p>
            </div>
            <div class="modal-body">
                <input type="hidden" name="divno" id="divno">

                <label class="control-label" for="divname">Name</label>
                <input type="text" name="divname" id="divname" class="form-control input-lg">

                <label class="control-label" for="divmanagers">Manager</label>
                <select id="divmanagers">
                    <option value="none"> select </option>
                </select>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-default" id="saveDivision" onclick="saveDivision()">Save</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade in" id="removeDivisionsModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <p></p>
                <input type="hidden" name="divid" id="divid">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-default" id="deleteDivision" onclick="removeDivision()">Confirm</button>
            </div>
        </div>
    </div>
</div>
