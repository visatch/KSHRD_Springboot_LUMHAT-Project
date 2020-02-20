let removeId = [], quizRemoveId = 0;

// click all checkbox
function checkAllStudent() {
    $("#student input:checkbox").each(function(){
        let $this = $(this);
        if($("#checkAll").prop("checked")){
            $this.prop('checked',false);
        }else {
            $this.prop('checked',true);
        }
    });
}

$("#btnRemoveStudent").click(function(){
    $.ajax({
        type: "POST",
        url: "/classroom/delete-user/"+classId,
        data: JSON.stringify(removeId),
        headers : {'Content-Type' : 'application/json'},
        success: function (response) {
            $("#totalStudent").text(response);
            $("#student-tab").click();
        },
        error: function(response){
        }
    });
});

//Get all id of student that will remove
function removeStudent() {
    removeId = [];
    $("#student input:checkbox").each(function(){
        let $this = $(this);
        if($this.is(":checked")){
           removeId.push($this.attr("id"));
        }
    });
    if(removeId.length===0){
        warningModal(noOneSelect);
    }else {
        $("#removeStudentModal").modal('show');
    }
}

function getAllStudent() {
    $("#student-classic").empty().append(`<div class="text-center w-100 mt-5"><div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div></div>`);
    $.ajax({
        type: "GET",
        url: "/classroom/student-tab/"+classId,
        success: function (response) {
            $("#student-classic").empty().append(response);
        },
        error: function(response){
            console.log("error"+response)
        }
    });
}

function getAllQuiz(page) {
    $("#modalChoiceQuizExpire").unblock();
    $("#quiz-classic").empty().append(`<div class="text-center w-100 mt-5"><div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div></div>`);
    $.ajax({
        type: "GET",
        url: "/classroom/quiz/?page="+page+"&classId="+classId,
        success: function (response) {
            $("#quiz-classic").empty().append(response);
        },
        error: function(response){
        }
    });
}

function getHistory(page) {
    $("#history-classic").empty().append(`<div class="text-center w-100 mt-5"><div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div></div>`);
    $.ajax({
        type: "GET",
        url: "/classroom/history/?page="+page+"&classId="+classId,
        success: function (response) {
            $("#history-classic").empty().append(response);
        },
        error: function(response){
        }
    });
}

function removeQuiz(quizId) {
    quizRemoveId = quizId;
    $("#removeQuizModal").modal("show");
}

function previewListScore(quizId,topic,turnIn){
    $.blockUI({baseZ: 2000,css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    $("#listScore").empty().append(`<tr><td colspan="4" align="center"><div class="spinner-border text-default" role="status">
                                <span class="sr-only">Loading...</span>
                                </div>
                           </td></tr>`);
    $.ajax({
        method: "GET",
        url : "/classroom/find-class-by-quiz/"+quizId,
        success : function (res) {
            $("#class").text(res.name);
            $("#roomClass").text(res.room);
            $("#teacherName").text(res.teacherFirstName + ' ' + res.teacherLastName);
            $("#totalStudentResult").text(turnIn);
            $.unblockUI();
            $("#btnExport").prop('disabled', true);
            $("#modalCart").modal('show');
            $.ajax({
                method : "get",
                url : "/classroom/result-quiz/"+quizId,
                headers : {
                    "Content-type" : "Application/json",
                    "Accept" : "application/json"
                },
                success : function(res){
                    $("#quizTopic").text(topic);
                    let rank = 1;
                    if(res.length>=1){
                        $("#listScore").empty();
                        for(i in res){
                            let data = res[i];
                            let row = `<tr>
                                <th scope="row">${rank}</th>
                                <td>${data.firstName} ${data.lastName}</td>
                                <td>${data.score}/${data.fullScore}</td>
                                <td>${rank}</td>  
                            </tr>`;
                            let dateTime = data.recordDate.split('T');
                            let date = dateTime[0];
                            $("#recordDate").text(date);
                            $("#listScore").append(row);
                            rank++;
                        }
                        $("#exportPreviewReport").val(quizId);
                        $("#exportPreviewReportTopic").val(topic);
                    }
                    $("#btnExport").prop('disabled', false);
                },
                error : function(res){
                    console.log(res);
                }
            })
        },
        error : function (res) {
            console.log(res)
        }
    });

}

function loadAllClass() {
    $("#selectNameLabel").text("");
    $("#selectQuizLabel").text("");
    $("#quizTitle").removeAttr('data-error').removeAttr('data-success');
    $.blockUI({css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    $.ajax({
        type: "GET",
        url: "/classroom/get-all-class",
        success: function (response) {
            $("#selectQuizTitle").empty().append(`<option value="" selected disabled>Please select class first</option>`);
            $("#selectClassName").empty().append(` <option value="" selected disabled>Please select class</option>`);
            for(var i=0;i<response.length;i++){
                $("#selectClassName").append(`<option value="${response[i].id}">${response[i].name} (${response[i].subject})</option>`);
            }
            $("#modalFindReuseQuiz").modal('show');
            $.unblockUI();
        },
        error: function(response){
            console.log(response)
        }
    });
}

$('#selectClassName').on('change', function() {
    $("#modalFindReuseQuiz").block({css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    $.ajax({
        type: "GET",
        url: "/classroom/get-all-topic/"+this.value,
        success: function (response) {
            $("#modalFindReuseQuiz").unblock();
            $("#selectQuizTitle").empty().append(` <option value="" selected disabled>Please select quiz</option>`);
            if(response.length==1&&response[0].quizzes.length==0){
                $("#selectQuizTitle").empty().append(` <option value="" selected disabled>No Quiz</option>`);
            }else if(response.length>=1){
                for(var i=0;i<response.length;i++){
                    if(response[i].quizzes.length==0){
                        $("#selectQuizTitle").append(`<optgroup label="Topic : ${response[i].topic} (No Quiz)">`);
                    }else {
                        $("#selectQuizTitle").append(`<optgroup label="Topic : ${response[i].topic}">`);
                    }
                    for(var j=0;j<response[i].quizzes.length;j++){
                        let quizTitle = response[i].quizzes[j].title;
                        let quizId = response[i].quizzes[j].id;
                        $("#selectQuizTitle").append(`<option value="${quizId}">${quizTitle}</option>`)
                    }
                    $("#selectQuizTitle").append(`</optgroup>`);
                }
            }
        },
        error: function(response){
        }
    });
});

//Select classname
$("#selectClassName").change(()=>{
    $("#selectClassName").val() == null ? $("#selectNameLabel").text("Please select class"):$("#selectNameLabel").text("");
});

//Select quiz title
$("#selectQuizTitle").change(()=>{
    $("#selectQuizTitle").val() == null ? $("#selectQuizLabel").text("Please select class"):$("#selectQuizLabel").text("");
});

// reuse quiz button
$("#btnReuseQuiz").click(function () {
    let classId = $("#selectClassName").find(":selected").val();
    let quizId = $("#selectQuizTitle").find(":selected").val();
    if(classId !=="" && quizId !==""){
        isReuseQuiz = true;
        editDraftQuiz(quizId,null);
    }else if(classId === "" && quizId !== ""){
        $("#selectNameLabel").text("Please select class");
        $("#selectQuizLabel").text("");
    }else if(quizId === "" && classId !== "") {
        $("#selectQuizLabel").text("Please select quiz");
        $("#selectNameLabel").text("");
    }
    else{
        $("#selectNameLabel").text("Please select class");
        $("#selectQuizLabel").text("Please select quiz");
    }
});

function archiveClass(){
    // data-target="#archiveModalSuccess"
    $.ajax({
        type: "GET",
        url: "/classroom/archive-class/"+classId,
        success: function (response) {
            window.location.href = window.location.origin+"/classroom";
        },
        error: function(response){
        }
    });
}

function updateClassroom() {
    $("#modalEdit").block({css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    if(isValidInputField("classnameEdit","classnameEditLabel")&&isValidInputField("subjectEdit","subjectEditLabel")&&isValidInputField("roomEdit","roomEditLabel")){
        let classroomClass = {
            id : classId,
            name : $("#classnameEdit").val(),
            subject : $("#subjectEdit").val(),
            room : $("#roomEdit").val(),
            code : '',
            imagePath : '',
            totalStudent : '',
            totalQuiz : '',
            createdDate : '',
            teacherImage : '',
            teacherFirstName : '',
            teacherLastName : ''
        };
        $.ajax({
            type: "POST",
            url: "/classroom/update-class",
            data : JSON.stringify(classroomClass),
            headers : {'Content-Type' : 'application/json'},
            success: function (classroomClass) {
                $("#classroomSubject").text(classroomClass.subject);
                $("#classroomName").text(classroomClass.name);
                $("#modalEdit").unblock().modal('hide');
            },
            error: function(response){
            }
        });
    }
}

$("#modalCreateLink").click(()=>{
    $("#classnameEdit").val("").blur();
    $("#subjectEdit").val("").blur();
    $("#roomEdit").val("").blur();
});

//remove all history in class
$("#removeClassroomHistory").click(function () {
    $.ajax({
        type: "PUT",
        url: "/classroom/clear-history-class/"+classId,
        success: function (response) {
            $("#history-tab").click();
        },
        error: function(response){
        }
    });
});

//Remove quiz after confirm modal
$("#btnRemoveQuiz").click(function () {
    $.blockUI({baseZ: 2000,css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    $.ajax({
        type: "DELETE",
        url: "/classroom/delete-quiz/"+quizRemoveId,
        success: function (response) {
            if(totalQuiz!==0){
                totalQuiz = totalQuiz-1;
            }
            $("#totalQuiz").text(totalQuiz);
            $.unblockUI();
            $("#quiz-tab").click()
        },
        error: function(response){
        }
    });
});

//Check if haven't finish update yet
function checkBeforeClose() {
    if(isEdit){
        warningModal(mustFinishUpdate)
    }else {
        $(".modal-backdrop").remove();
        $("#preview-modal").modal('hide');
    }

}

// export report
function exportReport(quizId,topic) {
    let link = `<form id="formExport" method="post" action="/classroom/export/${quizId}/${topic}"><button id="submitExportBtn" type="submit"></button></form>`;
    $('body').append(link);
    $("#submitExportBtn").click();
    $("#formExport").remove();
}

// preview export button
$("#exportPreviewReportBtn").attr('disabled');
$(document).ready(()=>{
    let exportPreviewReportBtn = $("#exportPreviewReportBtn");
    exportPreviewReportBtn.removeAttr('disabled');
    exportPreviewReportBtn.click(()=>{
        exportReport($("#exportPreviewReport").val(),$("#exportPreviewReportTopic").val());
    });
});

// create quiz validate
let quizTitle = $("#quizTitle");
quizTitle.keyup(()=>{
    if(quizTitle.val().trim()===""){
        $("#lbQuizTitle").attr('data-error','cannot be blank').removeAttr('data-success');
        quizTitle.addClass("invalid").removeClass('valid');
        quizTitle.focus();
        $("#btnCreateQuiz").attr('disabled','true');
    }else if(quizTitle.val().trim().length>45 || quizTitle.val().trim().length<3){
        if(quizTitle.val().trim().length<3)
            $("#lbQuizTitle").attr('data-error','minimum length is 3').removeAttr('data-success');
        else
            $("#lbQuizTitle").attr('data-error','maximum length is 45').removeAttr('data-success');
        quizTitle.addClass("invalid").removeClass('valid');
        $("#btnCreateQuiz").attr('disabled','true');
    }else{
        quizTitle.addClass("valid").removeClass('invalid');
        $("#lbQuizTitle").attr('data-success','available').removeAttr('data-error');
        $("#btnCreateQuiz").removeAttr('disabled');
    }
});

function validateQuizTitleOnClick() {
    let quizTitle = $("#quizTitle"),isValid = true;
    if(quizTitle.val().trim()===""){
            $("#lbQuizTitle").attr('data-error','cannot be blank').removeAttr('data-success');
            quizTitle.addClass("invalid").removeClass('valid');
            quizTitle.focus();
            isValid = false;
    }else if(quizTitle.val().trim().length>45 || quizTitle.val().trim().length<3){
            if(quizTitle.val().trim().length<3)
                $("#lbQuizTitle").attr('data-error','minimum length is 3').removeAttr('data-success');
            else
                $("#lbQuizTitle").attr('data-error','maximum length is 45').removeAttr('data-success');
            quizTitle.addClass("invalid").removeClass('valid');
            isValid = false;
    }else{
            quizTitle.addClass("valid").removeClass('invalid');
            $("#lbQuizTitle").attr('data-success','available').removeAttr('data-error');
    }
    return isValid;
}


function newQuiz() {
    isEditQuizDraft = false;
    $("#quizTitle").val("").blur().removeClass("valid").removeClass("invalid");
    $("#txtInstruction").val("").blur().removeClass("valid").removeClass("invalid");
    $("#btnCreateQuiz").attr('disabled','true');
    $("#lbQuizTitle").removeClass("active").removeAttr('data-success').removeAttr('data-error');
    $("#modalCreateQuiz").modal("show")
}

$(document).ready(()=>{

    $("#classnameEdit").keyup(()=>{
        if(isValidInputField('classnameEdit','classnameEditLabel') &&
            isValidName("subjectEdit") &&
            isValidName("roomEdit")
        ) $("#updateClassSubmit").removeAttr('disabled');
        else $("#updateClassSubmit").attr('disabled','true');

    });

    $("#subjectEdit").keyup(()=>{
        if(isValidInputField('subjectEdit','subjectEditLabel') &&
            isValidName("classnameEdit") &&
            isValidName("roomEdit")
        ) $("#updateClassSubmit").removeAttr('disabled');
        else $("#updateClassSubmit").attr('disabled','true');

    });

    $("#roomEdit").keyup(()=>{
        if(isValidInputField('roomEdit','roomEditLabel') &&
            isValidName("classnameEdit") &&
            isValidName("subjectEdit")
        ) $("#updateClassSubmit").removeAttr('disabled');
        else $("#updateClassSubmit").attr('disabled','true');

    });
});