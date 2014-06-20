<h1 style="margin-top: 100px">asdsd</h1>

<h2>Tab templates</h2>

<button class="btn btn-info" onclick="openAddTemplateModal()">Add new template</button>

<div class="container-fluid" id="templateContainer"></div>

</div>

<div>
    <div class="modal fade in" id="addTemplateModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <p>Add new flight template</p>

                    <p>This template will be used to generate flights.</p>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Departuring Airport</label>
                        <select id="departuringAirport" class="form-control"></select>
                    </div>

                    <div class="form-group">
                        <label>Arrival Airport</label>
                        <select id="arrivalAirport" class="form-control"></select>
                    </div>

                    <div class="form-group">
                        <label>Plane</label>
                        <select id="planeTypes" class="form-control"></select>
                    </div>

                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label>Departure time:</label>

                                    <div class="input-append bootstrap-timepicker">
                                        <input id="departureTimepicker" type="text" class="input-small">
                                        <span class="add-on"><i class="icon-time"></i></span>
                                    </div>
                                </div>
                            </div>

                            <div class="col-xs-6">
                                <div class="form-group">
                                    <label>Arrival time:</label>

                                    <div class="input-append bootstrap-timepicker">
                                        <input id="arrivalTimepicker" type="text" class="input-small">
                                        <span class="add-on"><i class="icon-time"></i></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Which days of the week?</label>

                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-xs-3">
                                    <input type="checkbox" id="monday">
                                    <label>Monday</label>
                                </div>
                                <div class="col-xs-3">
                                    <input type="checkbox" id="tuesday">
                                    <label>Tuesday</label>
                                </div>
                                <div class="col-xs-3">
                                    <input type="checkbox" id="wednesday">
                                    <label>Wednesday</label>
                                </div>
                                <div class="col-xs-3">
                                    <input type="checkbox" id="thursday">
                                    <label>Thursday</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <input type="checkbox" id="friday">
                                        <label>Friday</label>
                                    </div>
                                </div>
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <input type="checkbox" id="saturday">
                                        <label>Saturday</label>
                                    </div>
                                </div>
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <input type="checkbox" id="sunday">
                                        <label>Sunday</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button class="btn btn-primary" onclick="saveTemplate()">Save template</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade in" id="generateFlightsModal" aria-hidden="true" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

            </div>
            <div class="modal-body">
                <input type="hidden" id="templateId">
                <p id="source"></p>
                <p id="destination"></p>
                <p id="aircraft"></p>
                <p id="days"></p>
                <p id="departureTime"></p>
                <p id="arrivalTime"></p>
                <div class="container-fluid" id="datepickersHolder">
                    <div class="row">
                        <div class="col-xs-6">
                            <div id="startDate"></div>
                            <input type="hidden" id="secretStartDate">
                        </div>
                        <div class="col-xs-6">
                            <div id="endDate"></div>
                            <input type="hidden" id="secretEndDate">
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-info" id="generateFlightsButton">Generate Flights!</button>
            </div>
        </div>
    </div>
</div>