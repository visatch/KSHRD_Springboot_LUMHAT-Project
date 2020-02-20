//filter history by class id
$("#filterClass").change(function () {
    getHistory(0);
});

//filter history by pagination
function getHistory(page) {
    let classId = $("#filterClass").val();
    if(page===0){
        $.blockUI({baseZ: 2000,css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    }else {
        $("#tbTeacherHistory").empty().append(`<tr>
                                            <td colspan="4" align="center">
                                                <div class="spinner-border text-default" role="status">
                                                    <span class="sr-only">Loading...</span>
                                                </div>
                                            </td>
                                           </tr>`);
    }
    $.ajax({
        method : "GET",
        url : "/classroom/teaching-history?classId="+classId+"&page="+page,
        success : function (res) {
            $.unblockUI();
            $("#history-blog").empty().append(res);
        },
        error : function (res) {
            console.log(res)
        }
    });
}

//Clear all history in class
$("#removeAllClassroomHistory").click(function () {
    $.blockUI({baseZ: 2000,css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    $.ajax({
        method : "PUT",
        url : "/classroom/clear-all-class-history",
        success : function (res) {
            $.unblockUI();
            window.location.href = window.location.origin+"/classroom/teaching-history";
        },
        error : function (res) {
            console.log(res)
        }
    });
});