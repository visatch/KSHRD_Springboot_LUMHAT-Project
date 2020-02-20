window.onbeforeunload=function(){
    return "Data will be lost if you leave the page, are you sure?";
}
$(document).ready(function () {
    // add event to select sub major when user select a sub major
    $('.select-sub-major').on('change',function (e) {
        var optionSubMajorSelected=$("s.select-sub-major:selected",this);
        var valueSubMajorSelected=this.value;
        addSubject(valueSubMajorSelected);
    })

    $('.view-quiz-select-sub-major').on('change',function (e) {
        var optionSubjectSelected=$("s.view-quiz-select-sub-major:selected",this);
        var valueSubjectSelected=this.value;
        addQuizByLevel(valueSubjectSelected);
    })

    $('#btnAdd').click(function() {
        checked = $("input[type=checkbox]:checked").length;

        if (!checked) {
            alert("You must check at least one checkbox.");
            return false;
        }
    })

    $('#myModal').modal(options)
    $('#updateQuestionModal').modal(options);

})

// Add subject to combo box selection
function addSubject(subMajorID) {
    // loop to get sub major
    for (i=0;i<major.length;i++){
        // loop to get subject from submajor
        for(j=0;j<major[i].subMajors.length;j++){
            // check selected sub major to get subject
            if(major[i].subMajors[j].id==subMajorID){
                $(".select-subject").empty();
                if(major[i].subMajors[j].subjects.length>0){
                    // loop to add subject to selector
                    for(k=0;k<major[i].subMajors[j].subjects.length;k++) {
                        $(".select-subject").append($("<option></option>").attr("value", major[i].subMajors[j].subjects[k].id).text(major[i].subMajors[j].subjects[k].name))
                    }
                }
                else{
                    $(".select-subject").append($("<option></option>").attr("value","").attr("disabled",true).text("None"));
                }
            }
        }
    }
}

// Function is used to add quiz by subject and sorted by level
function addQuizByLevel(subjectID) {
    $("#basic-quiz").empty();
    $("#meduim-quiz").empty();
    $("#advanced-quiz").empty();
    for (i=0;i<major.length;i++){
        // loop to get subject from submajor
        for(j=0;j<major[i].subMajors.length;j++){
            // check selected sub major to get subject
            for(k=0;k<major[i].subMajors[j].subjects.length;k++){
                // loop to get all quiz in a subject
                for (l=0;l<major[i].subMajors[j].subjects[k].quizList.length;l++){
                    // check for subject to get quiz by subject id
                    if(major[i].subMajors[j].subjects[k].quizList[l].subMajor.id==subjectID){
                        $("#select-quiz").text("Select Quiz");
                        if(major[i].subMajors[j].subjects[k].quizList[l].level.id==1){
                            $('#basic-quiz').append($("<option></option>")
                                .attr("value", major[i].subMajors[j].subjects[k].quizList[l].id)
                                .text("Quiz "+major[i].subMajors[j].subjects[k].quizList[l].title))
                        }else if(major[i].subMajors[j].subjects[k].quizList[l].level.id==2){
                            $('#meduim-quiz').append($("<option></option>")
                                .attr("value", major[i].subMajors[j].subjects[k].quizList[l].id)
                                .text("Quiz "+major[i].subMajors[j].subjects[k].quizList[l].title))
                        }else{
                            $('#advanced-quiz').append($("<option></option>")
                                .attr("value", major[i].subMajors[j].subjects[k].quizList[l].id)
                                .text("Quiz "+major[i].subMajors[j].subjects[k].quizList[l].title))
                        }
                    }
                    else{
                        $("#select-quiz").text("Select Quiz");
                    }

                }
            }
        }
    }
}

function viewQuiz(selectedObject) {
    quizViewID=selectedObject.value;
    $("#checkingViewQuiz").show();
    var quiz;
    var data={
        id:selectedObject.value
    }
    $.ajax({
        url:"/admin/quiz/view-quiz/"+selectedObject.value,
        type:"GET",
        contentType:"application/json",
        success:function (data) {
            console.log("Successful")
            $("#viewQuiz").html(data);
            var questiontext=$(".question-view");
            for (var i=0;i<questiontext.length;i++){
                questiontext[i].innerText=i+1+". "+questiontext[i].innerText;
            }
            quiz=data;
            $("#checkingViewQuiz").hide();
            insertInstructionToInsertModal();
        },
        error:function (error) {
            alert(error);
        }

    })

}

function insertInstructionToInsertModal(){
    var instructions=$(".instruction-title");
    var instructionTitles=new Array();
    var instructionIDs=new Array();
    $("#instruction-insert").empty();
    for (var i=0;i<instructions.length;i++){

        instructionTitles[i]=instructions[i].innerText;

        instructionIDs[i]="";

        for (var j=instructions[i].id.lastIndexOf("n");j<instructions[i].id.length;j++){
            instructionIDs[i]+=instructions[i].id.charAt(j+1);
        }

        $("#instruction-insert").append(
            "<option value='"+instructionIDs[i]+"'>"+instructionTitles[i]+"</option>"
        )
    }

}

