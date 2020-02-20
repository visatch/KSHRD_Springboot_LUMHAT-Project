$("#allEnrollClass").change(function () {
    getStudentHistory(0);
});

function getStudentHistory(page) {
    let classId = $("#allEnrollClass").val();
    if(page===0){
        $.blockUI({baseZ: 2000,css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    }else {
        $("#tbEnrollHistory").empty().append(`<tr>
                                            <td colspan="5" align="center">
                                                <div class="spinner-border text-default" role="status">
                                                    <span class="sr-only">Loading...</span>
                                                </div>
                                            </td>
                                           </tr>`);
    }
    $.ajax({
        method : "GET",
        url : "/classroom/student-history?classId="+classId+"&page="+page,
        success : function (res) {
            $.unblockUI();
            $("#history-blog").empty().append(res);
        },
        error : function (res) {
            console.log(res)
        }
    });
}

//Clear all enroll history
$("#btnRemoveAllEnrollHistory").click(function () {
    $.blockUI({baseZ: 2000,css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    $.ajax({
        method : "PUT",
        url : "/classroom/clear-all-enroll-history",
        success : function (res) {
            $.unblockUI();
            window.location.href = window.location.origin+"/classroom/student-history";
        },
        error : function (res) {
            console.log(res)
        }
    });
});