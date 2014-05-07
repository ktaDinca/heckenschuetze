
<%@ include file="/WEB-INF/taglibs.jsp" %>

<script>

    function addClientClickHandler() {
        $('#editClientModal .modal-header p').text("Add new Client");
        $('#editClientModal .modal-body #client_name').val('');
    }

    function refreshClientsTable() {
        $('#clients-table > tbody').empty();
        loadClients();
    }

    function createPopulateEditClientModalFunction(client) {
        return function() {
            $('#editClientModal .modal-body #clientid').val(client.id);
            $('#editClientModal .modal-body #client_name').val(client.name);
        };
    }

    function loadClients() {
        $.ajax({
            type: "get",
            url : "<spring:url value="/intervals/admin/clients" />",
            success: function(data) {
                console.log(data);
                var table = $('#clients-table');
                for (var i = 0; i < data.clients.length; i ++) {
                    var line = '';
                    line += '<tr>';
                    line += '<td>' + data.clients[i].id + '</td>';
                    line += '<td>' + data.clients[i].name + '</td>';
                    line += '<td>' + '<a href="#editClientModal" data-toggle="modal" id="edit_client_' + i + '">' + 'edit' + '</a>' + '</td>';
                    line += '<td>' + '<a href="#removeClientModal" data-toggle="modal" id="remove_client_' + i + '">' + 'remove' + '</a>' + '</td>';
                    line += '</tr>';

                    table.append(line);

                    var editLink = $('#edit_client_' + i);
                    editLink.click(createPopulateEditClientModalFunction(data.clients[i]));

                    var deleteLink = $('#remove_client_' + i);
                    deleteLink.click(createRemoveClientFunction(data.clients[i].id));
                    deleteLink.css("color", "red");
                }
            }
        });
    }

    function saveClient() {
        var client_id = $('#editClientModal .modal-body #clientid').val();
        var client_name = $('#editClientModal .modal-body #client_name').val();

        $.ajax({
            type: "post",
            url: "<spring:url value="/intervals/admin/clients/edit" />",
            data: {
                id: client_id,
                name: client_name
            },
            success: function(data) {
                if (data.message == 'success') {
                    $('#editClientModal').modal('hide');
                    refreshClientsTable();
                }
            }
        });
    }

    function createRemoveClientFunction(id) {
        return function() {
            $('#removeClientModal .modal-body p').text("Are you sure you want to delete this Client?");
            $('#removeClientModal .modal-body #clid').val(id);
        };
    }

    function removeClient() {
        var client_id = $('#removeClientModal .modal-body #clid').val();
        $.ajax({
            type: "post",
            url: "<spring:url value="/intervals/admin/clients/remove" />",
            data: {
                id: client_id
            },
            success: function (data) {
                if (data.message == 'success') {
                    $('#removeClientModal').modal('hide');
                    refreshClientsTable();
                }
            }
        });
    }

</script>

<h2>How about adding a new Client?</h2>
<a id="addClient" onclick="addClientClickHandler()" href="#editClientModal" class="btn btn-default" data-toggle="modal">Add Client</a>

<table class="table table-responsive" id="clients-table">
    <thead>
        <tr>
            <th>#Id</th>
            <th>Name</th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
        </tr>
    </thead>
</table>

<div class="modal fade in" id="editClientModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <p></p>
            </div>
            <div class="modal-body">
                <input type="hidden" id="clientid">
                <label for="client_name">Name</label>
                <input type="text" id="client_name">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-default" id="saveClient" onclick="saveClient()">Save</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade in" id="removeClientModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <p></p>
                <input type="hidden" name="clientId" id="clid">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-default" onclick="removeClient()">Confirm</button>
            </div>
        </div>
    </div>
</div>