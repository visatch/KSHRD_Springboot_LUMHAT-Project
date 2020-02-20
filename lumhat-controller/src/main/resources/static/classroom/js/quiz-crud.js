let question_num = 1, isAddInstruction = false, isPublishQuiz = false;
//Contains all query string when edit quiz
let sql='';
let isReuseQuiz = false, isEditQuizDraft = false;
// Contains all instruction and every questions in each instruction
let allInstuctions = [];
let isEdit = false, isAddTopic = false;
let instructionTitle = "";
let instructionID = 0;
let noQuestion = 1;
let quizInfo = {};

//Get all id in card by question number 
function getComponentId(question_num) {
    return {
        question_num: question_num,
        question_id: "question_" + question_num,
        btn_update_id: 'update_' + question_num,
        answer_id: ["answer1_" + question_num, "answer2_" + question_num, "answer3_" + question_num, "answer4_" + question_num],
        select_id: ["select1_" + question_num, "select2_" + question_num, "select3_" + question_num, "select4_" + question_num],
        error_question: "errorMsg_q_" + question_num,
        error_answer: ["errorMsg_a1_" + question_num, "errorMsg_a2_" + question_num, "errorMsg_a3_" + question_num, "errorMsg_a4_" + question_num]
    };
}

//Get all data of question by component 
function getQuestion(componentId) {
    let question;
    let answer = [];
    let select = [];
    question = $("#" + componentId.question_id).val();
    for (let i = 0; i < 4; i++) {
        answer[i] = $("#" + componentId.answer_id[i]).val();
        select[i] = $("#" + componentId.select_id[i]).prop('checked');
    }
    return {
        question_id: componentId.question_id.split('_')[1],
        question: question,
        answer: answer,
        select: select,
        error_question: componentId.error_question,
        error_answer: componentId.error_answer,
    };
}

function addQuestion() {
    let data = getComponentId(question_num);
    let dataValidate = getQuestion(data);
    if (validate(dataValidate)) {
        //Add Data
        addData(dataValidate);
        //Disable Question
        disableQuestion(data);
        question_num = question_num + 1;
        noQuestion = noQuestion + 1;
        createNewQuestion(getComponentId(question_num));
        rerangeQuizNumber(allInstuctions[allInstuctions.length-1].questions.length)
    }
}

//Validate data before push to array
function validate(dataValidate) {
    let isCorrect = true,isCorrectAnswerCount = 0;
    //show error answer
    for (let i = 0; i < dataValidate.answer.length; i++) {
        let errorAnswerId = $("#" + dataValidate.error_answer[i]);
        if(dataValidate.answer[i]!=undefined){
            if (dataValidate.answer[i].trim() == "") {
                errorAnswerId.attr("style", "display: block");
                errorAnswerId.prev().removeClass("mb-3");
                errorAnswerId.addClass('mb-3');
                isCorrect = false;
            } else {
                errorAnswerId.attr("style", "display: none");
                errorAnswerId.removeClass('mb-3');
                errorAnswerId.prev().addClass('mb-3');
                isCorrectAnswerCount++;
            }
        }
    }
    if(isCorrectAnswerCount>=2){
        //Clear error
        for (let j = 0; j < dataValidate.answer.length; j++) {
            let errorAnswerId = $("#" + dataValidate.error_answer[j]);
            errorAnswerId.attr("style", "display: none");
            errorAnswerId.removeClass('mb-3');
            errorAnswerId.prev().addClass('mb-3');
        }
        isCorrect = true;
    }

    //show question error
    if (dataValidate.question.trim() === "") {
        $("#" + dataValidate.error_question).attr("style", "display: block");
        isCorrect = false;
    } else {
        $("#" + dataValidate.error_question).attr("style", "display: none");
    }

    //show error when no select true answer
    if (isCorrect) {
        let selectCount = 0;
        for (let i = 0; i < dataValidate.select.length; i++) {
            if (dataValidate.select[i] === true && dataValidate.answer[i].trim() !== "") {
                selectCount = selectCount + 1;
            }
        }
        if (selectCount < 1) {
            isCorrect = false;
            warningModal(mustCheckCorrectAnswer);
        }
    }
    return isCorrect;
}

//Validate edit data
function validateEditData(dataValidate) {
    let isCorrect = true;
    //show error answer
    for (let i = 0; i < dataValidate.answer.length; i++) {
        let errorAnswerId = $("#" + dataValidate.error_answer[i]);
        if(dataValidate.answer[i]!=undefined){
            if (dataValidate.answer[i].trim() == "") {
                errorAnswerId.attr("style", "display: block");
                errorAnswerId.prev().removeClass("mb-3");
                errorAnswerId.addClass('mb-3');
                isCorrect = false;
            } else {
                errorAnswerId.attr("style", "display: none");
                errorAnswerId.removeClass('mb-3');
                errorAnswerId.prev().addClass('mb-3');
            }
        }
    }
    //show question error
    if (dataValidate.question.trim() === "") {
        $("#" + dataValidate.error_question).attr("style", "display: block");
        isCorrect = false;
    } else {
        $("#" + dataValidate.error_question).attr("style", "display: none");
    }

    //show error when no select true answer
    if (isCorrect) {
        let selectCount = 0;
        for (let i = 0; i < dataValidate.select.length; i++) {
            if (dataValidate.select[i] === true && dataValidate.answer[i].trim() !== "") {
                selectCount = selectCount + 1;
            }
        }
        if (selectCount < 1) {
            isCorrect = false;
            warningModal(mustCheckCorrectAnswer);
        }
    }
    return isCorrect;
}

//Add data to array
function addData(data) {
    // Add data to array
    let allInstructionsLength = allInstuctions.length;
    //add correct answers to array
    let answers = [];
    for(let i=0;i<4;i++){
        if(data.answer[i].trim()!==""){
            let answer = {
                id: i+1,
                option: data.answer[i].replace(/^\s+|\s+$/g, ''),
                isCorrect: data.select[i],
                question: {}
            };
            answers.push(answer)
        }
    }
    allInstuctions[allInstructionsLength - 1].questions[allInstuctions[allInstructionsLength - 1].questions.length] = {
        id: data.question_id,
        title: data.question.replace(/^\s+|\s+$/g, ''),//remove line of space
        instruction: {
            id: 0,
            title: data.question,
            quiz: {},
            questions: []
        },
        answers: answers
    };
    //Show total count of instruction
    if (allInstuctions[allInstructionsLength - 1].questions.length >= 1) {
        $("#totalInstruction").text(allInstuctions.length);
    }
}

function addInstruction(btnAdd) {
    let instruction = $("#txtInstruction");
    instruction.height(35);
    if (instruction.val() !== "") {
        $('#quiz-modal').modal("show");
        instructionTitle = instruction.val().replace(/^\s+|\s+$/g, '');
        allInstuctions[allInstuctions.length] = {
            id: instructionID + 1,
            title: instructionTitle,
            quiz: {},
            questions: []
        };
        addDefaultCard();
        $("#modal-instruction").modal('hide');
        noQuestion = 1;
        instructionID = instructionID + 1;
        isAddInstruction = true;
    } else {
        instruction.focus();
    }
    instruction.val('');
}

//Check if instruction have no question ort not
function checkInstruction() {
    if(allInstuctions.length>0){
        if (allInstuctions[allInstuctions.length - 1].questions.length === 0) {
            warningModal(eachInsMustHaveOneQ)
        } else {
            $('#modal-instruction').modal("show");
        }
    }else {
        $('#modal-instruction').modal("show");
    }
    
}

//Enable question to edit
function edit(ins_id, q_id) {
    if(!isEdit){
        let componentId = getComponentId("preview_" + q_id);
        enableQuestion(componentId);
        $("#update_" + q_id).removeClass('d-none').addClass('d-block');
        isEdit = true;
    }else{
        warningModal(mustFinishUpdate);
    }
    
}

//Save question after finish edit
function saveQuestion(ins_id, q_id) {
    let componentId = getComponentId("preview_" + q_id);
    let question = getQuestion(componentId);
    if (validateEditData(question)) {
        $("#update_" + q_id).removeClass('d-block').addClass('d-none');
        disableQuestion(componentId);
        updateQuestion(question, ins_id, q_id)
    }
}

//Remove question from array by question and instruction id
function removeQuestion(ins_id, q_id) {
    if(!isEdit){
        for (let i = 0; i < allInstuctions.length; i++) {
            if (allInstuctions[i].id == ins_id) {
                if (allInstuctions[i].questions.length > 1) {
                    for (let j = 0; j < allInstuctions[i].questions.length; j++) {
                        if (allInstuctions[i].questions[j].id == q_id) {
                            allInstuctions[i].questions.splice(j, 1);
                        }
                    }
                    if(i===allInstuctions.length-1){
                        removeQuestionCard(q_id);
                        rerangeQuizNumber(allInstuctions[i].questions.length)
                    }
                } else if (allInstuctions[i].questions.length === 1) {
                    if (allInstuctions.length > 1) {
                        if(i===allInstuctions.length-1){
                            // $("#quiz").empty().append(`<h3 class="text-center p-10">Current Instruction Was Remove</h3>`)
                            allInstuctions.splice(i, 1);
                            addDefaultCard();
                        }else {
                            allInstuctions.splice(i, 1);
                        }
                    } else if (allInstuctions.length === 1) {
                        $("#quiz").empty().append(`<h1 class="text-center p-10">${noQuizText}</h1>`);
                        allInstuctions = [];
                    }
                }
            }
        }
        $("#totalInstruction").text(allInstuctions.length);
        isEdit = false;
        preview();
    }else {
        warningModal(mustFinishUpdate);
    }

}

//Update question in array by question and instruction id
function updateQuestion(question, ins_id, q_id) {
    if (validateEditData(question)) {
        //Update array
        for (let i = 0; i < allInstuctions.length; i++) {
            if (allInstuctions[i].id === ins_id) {
                for (let j = 0; j < allInstuctions[i].questions.length; j++) {
                    if (allInstuctions[i].questions[j].id == q_id) {
                        let answers = [];
                        for(let l=0;l<4;l++){
                            if(allInstuctions[i].questions[j].answers[l]!=undefined){
                                let answer = {
                                    id: allInstuctions[i].questions[j].answers[l].id,
                                    option: question.answer[l],
                                    isCorrect: question.select[l],
                                    question: {}
                                };
                                answers.push(answer)
                            }
                        }
                        //Set new data for question in same instruction
                        allInstuctions[i].questions[j] = {
                            id: q_id,
                            title: question.question,
                            instruction: {
                                id: 0,
                                title: question.instruction,
                                quiz: {},
                                questions: []
                            },
                            answers: answers
                        };
                        isEdit = false;
                        updateQuestionCard(q_id, allInstuctions[i].questions[j])
                    }
                }
            }
        }
    }
}

//remove question card in view after remove from array
function removeQuestionCard(q_id){
    let componentId = getComponentId(q_id);
    let card = $("#"+componentId.answer_id).parentsUntil('.card').parent()[0];
    if( card !== undefined){
        card.remove();
    }
}

//update question card in view after remove from array
function updateQuestionCard(q_id,question){
    let componentId = getComponentId(q_id);
    $("#"+componentId.question_id).val(question.title);
    for(let i=0; i<question.answers.length; i++){
        $("#"+componentId.answer_id[i]).val(question.answers[i].option);
        if (question.answers[i].isCorrect) {
            $("#"+componentId.select_id[i]).attr('checked','checked')
        } else {
            $("#"+componentId.select_id[i]).prop('checked', false);
        }
    }
}

//Create quiz information
function createQuiz() {
    let quizTitle = $(`#quizTitle`).val().trim();
    if(isAddTopic){
        let topic = $("#txtTopic").val();
        if(validateTopicOnClick()&&validateQuizTitleOnClick()){
            if(!isReuseQuiz){
                $("#btnCreateQuiz").attr("data-target", "#modal-instruction").attr("data-dismiss", "modal");
                $("#quizTitle").val('');
                closeAddTopic();
            }else {
                $("#btnCreateQuiz").removeAttr('data-target');
                addDefaultCard();
                $("#modalCreateQuiz").modal('hide');
                $("#quiz-modal").modal('show');
                closeAddTopic();
                isReuseQuiz = false;
            }
            $("#q_title").text(' : ' + quizTitle);
            $("#q_topic").text(' :'+topic);
            quizInfo = {
                quizTitle : quizTitle,
                duration : '' ,
                quizTopic: topic,
                quizTopicId : 0,
                expireQuiz : ''
            };
        }
    }else {
        let topic = $("#topicQuiz option:selected").text();
        $("#q_title").text(' : ' + quizTitle);
        $("#q_topic").text(' : '+topic);
        quizInfo = {
            quizTitle : quizTitle,
            duration : '',
            quizTopic: topic,
            quizTopicId : $("#topicQuiz option:selected").val(),
            expireQuiz : ''
        };
        $("#quizPreviewTitle").text(" : "+quizInfo.quizTitle);
        if(!isReuseQuiz){
                $("#btnCreateQuiz").attr("data-target", "#modal-instruction").attr("data-dismiss", "modal");
                $("#quizTitle").val('');
                closeAddTopic();
        }else {
                $("#quiz").empty();
                $("#btnCreateQuiz").removeAttr('data-target');
                $("#modalCreateQuiz").modal('hide');
                $("#quiz-modal").modal('show');
                addDefaultCard();
                $("#quizTitle").val('');
                closeAddTopic();
                isReuseQuiz = false;
        }
    }
}

//Validate Information Of Quiz
function validateQuiz() {
    let isValid = true;
    //Check quiz title
    if(!validateQuizTitleOnClick()){
        isValid = false;
    }
    //Check topic
    if(!validateTopicOnClick()){
        isValid = false;
    }
    return isValid;
}

//Draft quiz
$("#btnDraft").click(function () {
    if(isEditQuizDraft){
        editQuestionsToDatabase(true)
    }else {
        saveQuestionsToDatabase(true);
    }
    isEditQuizDraft = false;
});

//Choose expire date before publish
$("#btnComfirmQuizExpire").click(function () {
    let expireQuiz = $("#selectExpire option:selected").val();
    let durationQuiz = $("#selectDuration option:selected").val();
    if(expireQuiz===""||durationQuiz===""){
        let selectExpire = $("#selectExpire");
        selectExpire.focus();
        if($("#selectDuration").val()===null) {
            $("#selectDurationLabel").text("Please select duration.");
        }else{
            $("#selectDurationLabel").text("");
        }
        if(selectExpire.val()===null) {
            $("#selectExpireLabel").text("Please select expire date.");
        }else{
            $("#selectExpireLabel").text("");
        }

    }else {
        quizInfo.expireQuiz = expireQuiz;
        quizInfo.duration = durationQuiz;
        quizInfo.quizTopicId = quizInfo.quizTopicId;
        quizInfo.quizTopic = quizInfo.quizTopic;
        if(isPublishQuiz){
            publishQuiz(editQuizId,quizInfo.duration,quizInfo.expireQuiz);
            // editQuestionsToDatabase(false);
        }else {
            if(isEditQuizDraft){
                editQuestionsToDatabase(false);
                isEditQuizDraft = false;
            }else {
                saveQuestionsToDatabase(false);
            }
        }
    }
});

function saveQuestionsToDatabase(isDraft) {
    //Block ui while request ajax
    if(!isDraft){
        $("#modalChoiceQuizExpire").block({css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    }else {
        $.blockUI({baseZ: 2000,css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    }
    let data={
        id:1,
        title:quizInfo.quizTitle,
        duration:quizInfo.duration,
        dateExpire : '',
        hourExpire : quizInfo.expireQuiz,
        createdDate : '',
        isDraft : isDraft,
        status:'',
        user:{},
        classId : classId,
        topic : {
            id : quizInfo.quizTopicId,
            topic : quizInfo.quizTopic
        },
        instructions:allInstuctions
    };
    $.ajax({
        url:"/classroom/add-questions",
        type:"POST",
        data:JSON.stringify(data),
        contentType:"application/json",
        success:function (data) {
            window.location.href = window.location.origin+"/classroom/teacher/" + classId;
        },
        error:function (error) {
           console.log(error)
        }
    })
}

//When user cancel create quiz
$('#quiz-modal').on('hidden.bs.modal', function () {
    // clear all instruction
    allInstuctions = [];
    isEditQuizDraft = false;
});

//When user cancel add instruction
$('#modal-instruction').on('hidden.bs.modal', function () {
    $("#btnCreateQuiz").removeAttr('data-target').removeAttr('data-dismiss');
    if(isReuseQuiz){
        allInstuctions = [];
        isReuseQuiz = false;
    }
});

//When cancel create quiz
$('#modalCreateQuiz').on('hidden.bs.modal', function () {
    if(isReuseQuiz){
        allInstuctions = [];
        isReuseQuiz = false;
    }
});

//preview quiz after assign to student
function previewAssignedQuiz(quizId,isDraft) {
    $("body").block({css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    $.ajax({
        method : "get",
        url : "/classroom/preview-quiz/?quizId="+quizId,
        headers : {
            "Content-type" : "Application/json",
            "Accept" : "application/json"
        },
        success : function(res){
            $("#quizAssignedTitle").text(" : "+res.title);
            $("#previewAssignedQuiz").empty().append(res);
            $("#preview-assigned-quiz-modal").modal("show");
            $("body").unblock();
        },
        error : function(res){
            console.log(res);
        }
    });
}

//Request that save as draft for edit to publish
function editDraftQuiz(quizId,isDraft){
    let url = "/classroom/edit-quiz/?quizId="+quizId+"&isDraft="+isDraft;
    if(isDraft==null){
        //Show loading
        $("#modalFindReuseQuiz").block({css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
        url = "/classroom/edit-quiz/?quizId="+quizId;
    }else {
        $.blockUI({baseZ: 2000,css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    }
     $.ajax({
        method : "get",
        url : url,
        headers : {
            "Content-type" : "Application/json",
            "Accept" : "application/json"
        },
        success : function(res){
            $.unblockUI();
            $("#modalFindReuseQuiz").unblock();
            if(isReuseQuiz){
                $("#modalCreateQuiz").modal('show');
            }
            $("#modalFindReuseQuiz").modal('hide');
            allInstuctions = res.instructions;
            quizInfo = {
                quizTitle : res.title,
                duration : '' ,
                quizTopic:  res.topic.topic,
                quizTopicId : res.topic.id,
                expireQuiz : ''
            };
            $("#quizPreviewTitle").text(" : "+res.title);
            editQuizId = res.id;
            $("#q_title").text(' : ' + res.title);
            $("#q_topic").text(' : '+ res.topic.topic);
            $("#totalInstruction").text(allInstuctions.length);
            if(!isReuseQuiz){
                $("#quiz").empty();
                addDefaultCard();
                $("#quiz-modal").modal('show');
                isEditQuizDraft = true;
            }
        },
        error : function(res){
            console.log(res);
        }
    })
}

//Edit draft quiz
function editQuestionsToDatabase(isDraft) {
    //Block ui while request ajax
    if(!isDraft){
        $("#modalChoiceQuizExpire").block({css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    }else {
        $.blockUI({baseZ: 2000,css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    }
    let data={
        id:editQuizId,
        classId : classId,
        title : quizInfo.quizTitle,
        duration : quizInfo.duration,
        hourExpire : quizInfo.expireQuiz,
        isDraft : isDraft,
        dateExpire : '',
        status:'',
        user:{},
        topic : {
            id : quizInfo.quizTopicId,
            topic : quizInfo.quizTopic,
        },
        instructions:allInstuctions
    };
    $.ajax({
        url:"/classroom/edit-questions",
        type:"POST",
        data:JSON.stringify(data),
        contentType:"application/json",
        success:function (data) {
            window.location.href =  window.location.origin+"/classroom/teacher/" + classId;
        },

        error:function (error) {
            console.log(error);
        }

    })
}

//Create warning modal
function warningModal(msg) {
    $('body').append(`<div class="modal fade" id="warningModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-notify modal-danger" role="document">
                        
                                    <div class="modal-content">
                                        <!--Header-->
                                        <div class="modal-header clr-code-modal-header">
                        
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true" class="h2">&times;</span>
                                            </button>
                                        </div>
                        
                                        <!--Body-->
                                        <div class="modal-body clr-modal-body">
                                            <div class="text-center">
                                                <i class="far fa-times-circle fa-4x mb-3 animated rotateIn"></i>
                                                <h4>${msg}</h4>
                                            </div>
                                        </div>
                        
                                        <!--Footer-->
                                        <div class="modal-footer clr-modal-footer justify-content-center">
                                            <button onclick="removeModal()" class="btn clr-btn-ok mt-1" data-dismiss="modal"><i class="fas fa-check"></i>&nbsp;
                                                ${okText}</button>
                                        </div>
                        
                                    </div>
                        
                                </div>
                     </div>`);
    $("#warningModal").modal('show');
}

//event when modal is closing (clear backdrop modal)
function removeModal(){
    $("#warningModal").remove();
    $(".modal-backdrop").remove();
}

// event when key up on instruction textarea
function validInstruction(idTextArea, idLabel, idButton) {
    $("#"+idLabel).attr('disabled','true');
    if($("#"+idTextArea).val().length > 250){
        $("#"+idLabel).text(" *Maximum Length is 250");
        $("#"+idButton).attr('disabled','true');
    }else{
        $("#"+idLabel).text("");
        $("#"+idButton).removeAttr('disabled');
    }
}

//Change draft ti publish quiz
function publishDraftQuiz(quizId){
    editQuizId = quizId;
    isPublishQuiz = true;
    $("#modalChoiceQuizExpire").modal('show');
}

//publish quiz direct without edit
function publishQuiz(quizId,duration,hourExpire) {
    $.blockUI({baseZ: 2000,css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
    $.ajax({
        method : "get",
        url : "/classroom/publish-quiz/?quizId="+quizId+"&duration="+duration+"&hourExpire="+hourExpire,
        success : function(res){
            window.location.href =  window.location.origin+"/classroom/teacher/" + classId;
        },
        error : function(res){
            console.log(res);
        }
    });
}
