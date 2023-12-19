<!-- Page Heading -->
<div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">Index (Quick Links)</h1>

</div>

<div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h2 class="h3 mb-0 text-gray-800">Parks:</h2>
</div>

<!-- Content Row -->
<div class="row">

    
    {#each data.parks as park}
    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card border-left-info shadow h-100 py-2">
            <a class="nav-link" href="/park/{park.id}">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="h5 mb-0 font-weight-bold text-gray-800">{park.name}</div>
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1"></div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-car fa-2x text-gray-300"></i>
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
            <a class="nav-link" href="#" data-toggle="modal" data-target="#addParkModal">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="h5 mb-0 font-weight-bold text-gray-800">Add Park</div>
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
            <a class="nav-link" href="#" data-toggle="modal" data-target="#remParkModal">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="h5 mb-0 font-weight-bold text-gray-800">Remove Park</div>
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

<!--Add Park Modal-->
<div class="modal fade" id="addParkModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="addPark" method="POST" action="?/create">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Give us info about the park!</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group row">
                        <label for="name" class="col-sm-2 col-form-label">Name</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="name" placeholder="" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="address" class="col-sm-2 col-form-label">Address</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="address" placeholder="" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="latitude" class="col-sm-2 col-form-label">Latitude</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" name="latitude" placeholder="">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="longitude" class="col-sm-2 col-form-label">Longitude</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" name="longitude" placeholder="">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="entranceFee" class="col-sm-2 col-form-label">Fee</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" name="entranceFee" placeholder="">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="maxLotation" class="col-sm-2 col-form-label">Max Capacity</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" name="maxLotation" placeholder="">
                        </div>
                    </div>


                </div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Add</button>
                    <!--<input type="submit" class="btn btn-primary" value="Add"> -->
                    <!--<a class="btn btn-primary" href="#.html">Add</a> onclick="$('#addParkModal').modal('hide');" -->
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Remove Park Modal-->
<div class="modal fade" id="remParkModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Are you sure you want to remove this park?</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">This action cannot be reverted..</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                <a class="btn btn-primary" href="parks.html">Remove</a>
            </div>
        </div>
    </div>
</div>

<script>
	/** @type {import('./$types').PageData} */
	export let data;
    console.log(data.parks);
</script>