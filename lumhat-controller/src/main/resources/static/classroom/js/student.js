//Get all quiz that assigned
function getStudentQuiz(page) {
    $("#quiz-classic").empty().append(`<div class="text-center w-100 mt-5"><div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div></div>`);
    $.ajax({
        type: "GET",
        url: "/classroom/student/quiztab/?page="+page+"&classId="+classId,
        success: function (res) {
            $("#quiz-classic").empty().append(res);
        },
        error: function (err) {
            console.log("error"+err)
        }
    })
}

//Get all student in class
function getStudentList() {
    $("#student-classic").empty().append(`<div class="text-center w-100 mt-5"><div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div></div>`);
    $.ajax({
        type: "GET",
        url: '/classroom/student/stutab/'+classId,
        success: function (res) {
            $("#student-classic").empty().append(res);
        },
        error: function (err) {
            console.log("error"+err)
        }
    })
}

//get student history
function getStudentHistory(page) {
    $("#history-classic").empty().append(`<div class="text-center w-100 mt-5"><div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div></div>`);
    $.ajax({
        type: "GET",
        url: `/classroom/student/histab/?userId=${userId}&classId=${classId}&page=`+page,
        success: function (res) {
            $("#history-classic").empty().append(res);
        },
        error: function (err) {
            console.log("error"+err)
        }
    })
}

//remove all history of student in class
$("#removeStudentHistory").click(function () {
    // #removeModalSuccess
    $.ajax({
        type: "PUT",
        url: "/classroom/clear-history-student/"+classId,
        success: function (response) {
            getStudentHistory(1);
        },
        error: function(response){
        }
    });
});

// leave class
function leaveClass() {

    $.ajax({
        url: "/classroom/student/leave",
        type: "POST",
        headers : {
            "Content-type" : "Application/json",
            "Accept" : "application/json"
        },
        data:JSON.stringify(classId),
        success: function (response) {
            if(response)
                window.location.href = window.location.origin+"/classroom/";
                // $("#leaveModalSuccess").modal("show");
            else
                $("#leaveModalFail").modal("show");
        },
        error: function (response) {
            $("#leaveModalFail").modal("show");
        }
    });


}