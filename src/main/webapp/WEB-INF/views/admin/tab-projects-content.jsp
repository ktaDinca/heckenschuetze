
<%@ include file="/WEB-INF/taglibs.jsp" %>

<script type="text/javascript">

    function loadProjects() {
        $.ajax({
            type: "get",
            url: "<spring:url value="/intervals/admin/projects" />",
            success: function(data) {
                if (data.message == 'success') {
                    var table = $('#projects-table');

                    for (var i = 0; i < data.projects.length; ++i) {
                        var line = '';
                        line += '<tr>';
                        line += '<td>' + data.projects[i].id + '</td>';
                        line += '<td>' + data.projects[i].name + '</td>';

                        if (data.projects[i].client != null) {
                            line += '<td>' + data.projects[i].client.name + '</td>';
                        }
                        else {
                            line += '<td>' + '-' + '</td>';
                        }

                        line += '<td>' + '<a href="#editProjectModal" data-toggle="modal" id="edit_project_' + i + '">' + 'edit' + '</a>' + '</td>';
                        line += '<td>' + '<a href="#removeProjectModal" data-toggle="modal" id="remove_project_' + i + '">' + 'remove' + '</a>' + '</td>';

                        table.append(line);

                        var editLink = $('#edit_project_' + i);
                        editLink.click(createPopulateEditProjectModalFunction(data.projects[i]));

                        var removeLink = $('#remove_project_' + i);
                        removeLink.click(createRemoveProjectFunction(data.projects[i].id));
                    }
                }
            }
        });
    }

    function createPopulateEditProjectModalFunction(project){
        return function() {

            loadAndPopulateClients();

            $('#editProjectModal .modal-header p').text("Editing " + project.name);
            $('#editProjectModal .modal-body #projectId').val(project.id);
            $('#editProjectModal .modal-body #projectName').val(project.name);
        };
    }

    function createRemoveProjectFunction(id) {
        return function() {
            $('#removeProjectModal .modal-body #projId').val(id);
            $('#removeProjectModal .modal-body p').text("Are you sure you want to remove this Project?");
        };
    }

    function removeProject() {
        var id =   $('#removeProjectModal .modal-body #projId').val();

        $.ajax({
            type: "post",
            url: "<spring:url value="/intervals/admin/projects/remove" />",
            data: {
                id : id
            },
            success: function(data) {
                if (data.message == 'success') {
                    $('#removeProjectModal').modal('hide');
                    refreshProjectsTable();
                }
            }
        });
    }

    function refreshProjectsTable() {
        $('#projects-table > tbody').empty();
        loadProjects();
    }

    $(document).ready(function() {
        refreshProjectsTable();
    });

    function loadAndPopulateClients() {
        $.ajax({
            type: "get",
            url: "<spring:url value="/intervals/admin/clients" />",
            success: function(data) {
                if (data.message == 'success') {
                    var clientsSelect = $('#editProjectModal .modal-body #clients');
                    clientsSelect.empty();
                    clientsSelect.append('<option value="none"> select </option>');

                    for (var i = 0; i < data.clients.length; i ++) {
                        clientsSelect.append('<option value="' + data.clients[i].id + '">' + data.clients[i].name + '</option>');
                    }
                }
             }
        });
    }

    function clearProjectModal() {
        $('#editProjectModal .modal-header p').text('');
        $('#editProjectModal .modal-body #projectId').val('');
        $('#editProjectModal .modal-body #projectName').val('');
    }

    function saveProject() {
        var id = $('#editProjectModal .modal-body #projectId').val();
        var name = $('#editProjectModal .modal-body #projectName').val();
        var clientId = $('#editProjectModal .modal-body #clients option:selected').val();

        $.ajax({
            type: "post",
            url: "<spring:url value="/intervals/admin/projects/edit" />",
            data: {
                id: id,
                name: name,
                clientId: clientId
            },
            success: function(data) {
                if (data.message == 'success') {
                    $('#editProjectModal').modal('hide');
                    refreshProjectsTable();
                }
            }
        });
    }

</script>

<h2>Would you like to add a new Project?</h2>
<a href="#editProjectModal" class="btn btn-default" id="addProject" onclick="clearProjectModal(); loadAndPopulateClients();" data-toggle="modal">Add Project</a>

<table class="table table-responsive" id="projects-table">
    <thead>
    <tr>
        <th>#Id</th>
        <th>Nume</th>
        <th>Client</th>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
    </tr>
    </thead>
</table>

<div class="modal fade in" id="editProjectModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <p></p>
            </div>
            <div class="modal-body">
                <input type="hidden" id="projectId">
                <label for="projectName">Name</label>
                <input type="text" id="projectName">

                <label for="clients">Client</label>
                <select id="clients">
                    <option value="none"> select </option>
                </select>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-default" id="saveProject" onclick="saveProject()">save</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade in" id="removeProjectModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <p></p>
                <input type="hidden" name="projId" id="projId">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-default" onclick="removeProject()">Confirm</button>
            </div>
        </div>
    </div>
</div>

