<!-- Page Heading -->
<div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">Index (Quick Links)</h1>

</div>

<div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">{data.park.name}</h1>
    <a href="/park/{data.park.id}" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
        <i class="fa-solid fa-arrow-left"></i> Back to Park Settings
    </a>
    
</div>

<div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h2 class="h3 mb-0 text-gray-800">Sensors:</h2>
</div>

<!-- Content Row -->
<div class="row">
    
    {#each data.sensors as sensor}
    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card border-left-info shadow h-100 py-2">
            <a class="nav-link" href="#">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="h5 mb-0 font-weight-bold text-gray-800">{sensor.id}</div>
                            <div class="text-xs font-weight-bold text-danger text-uppercase mb-1">{sensor.type}</div>
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">{sensor.address}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fa-solid fa-registered" style="color: #f8e45c;"></i>
                        </div>
                    </div>
                </div>
            </a>
        </div>
    </div>
    {/each}

</div>

<div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h2 class="h3 mb-0 text-gray-800">Actions:</h2>
</div>

<div class="row">
    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card border-left-info shadow h-100 py-2">
            <a class="nav-link" href="#" data-toggle="modal" data-target="#addSensorModal">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="h5 mb-0 font-weight-bold text-gray-800">Add Sensor</div>
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                            </div>

                        </div>
                        <div class="col-auto">
                            <i class="fas fa-plus fa-2x text-gray-300"></i>
                            <i class="fas fa-car fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </a>
        </div>
    </div>

    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card border-left-info shadow h-100 py-2">
            <a class="nav-link" href="#" data-toggle="modal" data-target="">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="h5 mb-0 font-weight-bold text-gray-800">Remove Sensor</div>
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                            </div>

                        </div>
                        <div class="col-auto">
                            <i class="fas fa-minus fa-2x text-gray-300"></i>
                            <i class="fas fa-car fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </a>
        </div>
    </div>
</div>

<!--Add Sensor Modal-->
<div class="modal fade" id="addSensorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="addSensor" method="POST" action="/sensors">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Give us info about the Sensor!</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group row">
                        <label for="address" class="col-sm-2 col-form-label">Type</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="address" placeholder="" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="type" class="col-sm-2 col-form-label">Type</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="type" placeholder="" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="park" class="col-sm-2 col-form-label">ParkId</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" name="park" value={data.park.id} readonly>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Add</button>
                </div>
            </form>
        </div>
    </div>
</div>



<script>
	/** @type {import('./$types').PageData} */
	export let data;
    console.log(data.sensors);
</script>