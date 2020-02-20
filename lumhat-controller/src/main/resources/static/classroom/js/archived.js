// remove archived function
function removeArchived(id) {
    $("#removeArchivedIdTarget").val(id);
    $("#removeArchivedModalConfirm").modal("show");
}

// restore archived function
function restoreArchived(id) {
    $("#restoreArchivedIdTarget").val(id);
    $("#restoreModalConfirm").modal("show");
}

// restore archived class
$("#restoreArchiveConfirm").click(()=>{
    $.blockUI({baseZ: 2000,css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    $.ajax({
        url: "/classroom/archived/restore",
        type: "GET",
        data: {id:$("#restoreArchivedIdTarget").val()},
        success: function (respond) {
            $.unblockUI();
            window.location.href="/classroom"
        },
        error: function (respond) {
            console.log(respond);
            $("#restoreArchivedModalFail").modal("show");
        }
    });
});

// remove archived class
$("#removeArchiveConfirm").click(() => {
    $.blockUI({baseZ: 2000,css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    let removeIdTarget = $("#removeArchivedIdTarget").val();
    $.ajax({
        url: "/classroom/archived/remove",
        type: "GET",
        data: {id: removeIdTarget},
        success: function (respond) {
            $.unblockUI();
            window.location.href="/classroom/archived"
        },
        error: function (respond) {
            console.log("fail");
            console.log(respond);
            $("#removeArchivedModalFail").modal("show");
        }
    });
});