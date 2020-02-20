//Create a new card question
function createNewQuestion(data) {
    let answerHtml = "";
    $("#addQuestion").remove();
    //add answer text area
    for (let i = 0; i < data.answer_id.length; i++) {
        answerHtml = answerHtml +
            '<div class="input-group mb-3">' + ' <div class="input-group-prepend">' +
            '<div class="input-group-text">' +
            '<input class="form-check-input" type="checkbox" value="" id="' + data.select_id[i] + '" >' +
            '<label class="form-check-label" for="' + data.select_id[i] + '" ></label></div></div>' +
            '<textarea name="" class="form-control font-size-1rem" placeholder="'+pleaseInputAnswerText+' '+(i+1)+'" id="' + data.answer_id[i] + '" rows="1" style="min-height: 40.25px"></textarea></div>'+
            '<p class="errorMsg" id="' + data.error_answer[i] + '"style="display: none">Please Input Answer '+(i+1)+'</p>';
    }
    let question = `<div class="card mb-4">
                        <div class="text-left d-flex m-2" id="addQuestion">
                            <div class="d-flex">
                                <div class="d-flex flex-column text-center justify-content-center">
                                <button class="btn btn-default btn-sm m-0" onclick="addQuestion()"><i class="fas fa-plus mr-2"></i>${addQuestionText}</button></div>
                            </div>
                            <textarea id="currentIns" class="ml-2 font-size-1rem" rows="1">${instructionText} : ${allInstuctions[allInstuctions.length - 1].title}</textarea>
                        </div>
                        <div class="p-3 w-100">
                            <div class="d-flex">
                              <div class="mr-3 ">
                                <p class="question-test-center-vertical font-size-1rem">${noQuestion}</p>
                              </div>
                              <div class="w-100">
                                <textarea name="font-size-1rem" placeholder="${pleaseInputQuestionText}" class="p-2 fs-16" id="${data.question_id}" rows="1" style="width: 100%; min-height: 40.25px"></textarea>
                              </div>
                            </div>
                             <p class="errorMsg" id="${data.error_question}" style="margin-bottom: 0">Please Input Question</p>
                        <div class="mt-2 px-3">
                              ${answerHtml}
                        </div>
                    </div>`;
    $("#quiz").prepend(question);
    resizeTextArea(data.question_id);
    //set timeout to resize textarea
    setTimeout(function () {
        resizeTextArea('currentIns');
    },0,setTimeout(function () {
        $('#currentIns').prop('disabled',true)
    },100));
    for (let i = 0; i < data.answer_id.length; i++) {
        resizeTextArea(data.answer_id[i]);
    }
}

//Disable question after create new question
function disableQuestion(data) {
    if(data!==undefined) {
        $("#" + data.question_id).attr("disabled", "true");
        for (var i = 0; i < data.answer_id.length; i++) {
            if (data.answer_id[i] !== undefined) {
                $("#" + data.answer_id[i]).attr("disabled", "true");
                $("#" + data.select_id[i]).attr("disabled", "true");
            }
        }
    }
}

//Enable question when edit 
function enableQuestion(data) {
    $("#" + data.instruction_id).removeAttr('disabled');
    $("#" + data.question_id).removeAttr('disabled');
    for (let i = 0; i < data.answer_id.length; i++) {
        $("#" + data.answer_id[i]).removeAttr('disabled');
        $("#" + data.select_id[i]).removeAttr('disabled');
    }
    isEdit = true;
}

//add default card when create new instruction
function addDefaultCard() {
    $("#quiz").empty();
    let defaultCard = `<div class="card mb-4" id="card">
                            <div class="text-left d-flex m-2" id="addQuestion">
                                <div class="d-flex">
                                    <div class="d-flex flex-column text-center justify-content-center">
                                    <button class="btn btn-default btn-sm m-0" onclick="addQuestion()"><i class="fas fa-plus mr-2"></i>${addQuestionText}</button></div>
                                </div>
                                <textarea id="currentIns" class="ml-2 font-size-1rem" rows="1">${instructionText} : ${allInstuctions[allInstuctions.length - 1].title}</textarea>
                            </div>
                            <div class="p-3 w-100">
                                <!-- Question -->
                                <div class="d-flex">
                                  <div class="mr-3 ">
                                    <p class="question-test-center-vertical font-size-1rem">1</p>
                                  </div>
                                  <div class="w-100">
                                    <textarea  placeholder="${pleaseInputQuestionText}" class="w-100 p-2 fs-16" id="question_${question_num}" rows="1" style="min-height: 40.25px"></textarea>
                                  </div>
                                </div>
                                <p class="errorMsg" id="errorMsg_q_${question_num}" style="margin-bottom: 0">Please Input Question</p>
                                <!-- Answer 1 -->
                                <div class="my-2 px-3">
                                  <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                      <div class="input-group-text">
                                        <input class="form-check-input" type="checkbox" value="" id="select1_${question_num}">
                                        <label class="form-check-label" for="select1_${question_num}"></label>
                                      </div>
                                    </div>
                                    <textarea name="" placeholder="${pleaseInputAnswerText} 1" class="form-control font-size-1rem" id="answer1_${question_num}" rows="1" style="min-height: 40.25px"></textarea>
                                  </div>
                                  <p class="errorMsg" id="errorMsg_a1_${question_num}" style="margin-bottom: 0">Please Input Answer 1</p>
                                  <!-- Answer 2 -->
                                  <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                      <div class="input-group-text">
                                        <input class="form-check-input" type="checkbox" value="select1" id="select2_${question_num}">
                                        <label class="form-check-label" for="select2_${question_num}"></label>
                                      </div>
                                    </div>
                                    <textarea name="" placeholder="${pleaseInputAnswerText} 2" class="form-control font-size-1rem" id="answer2_${question_num}" rows="1" style="min-height: 40.25px"></textarea>
                                  </div>
                                  <p class="errorMsg" id="errorMsg_a2_${question_num}" style="margin-bottom: 0">Please Input Answer 2</p>
                                  <!-- Answer 3 -->
                                  <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                      <div class="input-group-text">
                                        <input class="form-check-input" type="checkbox" value="" id="select3_${question_num}">
                                        <label class="form-check-label" for="select3_${question_num}"></label>
                                      </div>
                                    </div>
                                     <textarea name="" placeholder="${pleaseInputAnswerText} 3" class="form-control font-size-1rem" id="answer3_${question_num}" rows="1" style="min-height: 40.25px"></textarea>
                                  </div>
                                  <p class="errorMsg" id="errorMsg_a3_${question_num}" style="margin-bottom: 0">Please Input Answer 3</p>
                                  <!-- Answer 4 -->
                                  <div class="input-group">
                                    <div class="input-group-prepend">
                                      <div class="input-group-text">
                                        <input class="form-check-input" type="checkbox" value="" id="select4_${question_num}">
                                        <label class="form-check-label" for="select4_${question_num}"></label>
                                      </div>
                                    </div>
                                     <textarea name="" class="form-control font-size-1rem"  placeholder="${pleaseInputAnswerText} 4"  id="answer4_${question_num}" rows="1" style="min-height: 40.25px"></textarea>
                                  </div>
                                  <p class="errorMsg" id="errorMsg_a4_${question_num}" style="margin-bottom: 0">Please Input Answer 4</p>
                            </div>
                        </div>
                    </div>`;
    $("#quiz").prepend(defaultCard);
    let ids = ['question_' + question_num, 'answer1_' + question_num, 'answer2_' + question_num, 'answer3_' + question_num, 'answer4_' + question_num];
    for (let i = 0; i < ids.length; i++) {
        resizeTextArea(ids[i]);
    }
    let timer = 200;
    setTimeout(function () {
        resizeTextArea('currentIns');
    }, timer, setTimeout(function () {
        $('#currentIns').prop('disabled',true);
        },timer)
    );


}

//Preview the quiz before publish or save as draft
function preview() {
    $("#question-container").empty();
    let answerHtml = "";
    if(allInstuctions.length>=1){
        $("#btnPublish").prop("disabled",false);
        $("#btnDraft").prop("disabled",false);
        for (let i = 0; i < allInstuctions.length; i++) {
            let instruction = `<div class="accordion md-accordion" id="inst${allInstuctions[i].id}">
                                    <div class="card">
                                        <div class="card-header d-flex" id="headingOne1">
                                            <h5 class="mb-0 d-flex" style="width:90%"></i>
                                                <span>${i+1}.</span>
                                                <textarea class="instruction border-0 w-100" id="instruction_${allInstuctions[i].id}" onclick='updateInstructionTitle("${allInstuctions[i].id}")' rows="1" readonly>${allInstuctions[i].title}</textarea>
                                                <span>(${allInstuctions[i].questions.length})</span>
                                            </h5>
                                            <a data-toggle="collapse" class="collapsed d-flex text-center justify-content-center flex-column " aria-expanded="false" style="width: 10%;" data-parent="#inst${allInstuctions[i].id}" href="#collapse${i}">
                                            <i class="fas fa-angle-down rotate-icon clr-ico"></i></a>    
                                        </div>
                                    <div id="collapse${i}" class="collapse show" data-parent="#inst${allInstuctions[i].id}">
                                <div class="card-body pt-0" id="card-body-${allInstuctions[i].id}"></div></div></div>`;
            $("#question-container").append(instruction);
            if(allInstuctions[i].questions.length>=1){
                for (let j = 0; j < allInstuctions[i].questions.length; j++) {
                    let data = getComponentId("preview_"+allInstuctions[i].questions[j].id);
                    for (let k = 0; k < allInstuctions[i].questions[j].answers.length; k++) {
                        let select = '';
                        if (allInstuctions[i].questions[j].answers[k].isCorrect === true) {
                            select = `<input class="form-check-input" type="checkbox" id="${data.select_id[k]}" checked>`;
                        } else {
                            select = `<input class="form-check-input" type="checkbox" id="${data.select_id[k]}">`;
                        }
                        answerHtml = answerHtml +
                            `<div class="input-group"> <div class="input-group-prepend">
                                 <div class="input-group-text">${select}
                                 <label class="form-check-label" for="${data.select_id[k]}" ></label></div></div>
                                 <textarea class="mb-2 p-2" style="width: 93%" rows="1" id="${data.answer_id[k]}">${allInstuctions[i].questions[j].answers[k].option}</textarea>
                                 <p class="errorMsg mb-0" id="${data.error_answer[k]}" style="display: none">Please Input Answer ${k+1}</p>`;
                    }
                    let question = `
                        <div class="card mb-4">
                            <div class="p-3 w-100 no-padding-t-b">
                            <div class="d-flex">
                            <div class="mr-3 ">
                                <p class="question-test-center-vertical">${j+1}</p>
                            </div>
                            <div class="w-100 d-flex">
                                <textarea class="w-100 p-2" id="${data.question_id}" value="${allInstuctions[i].questions[j].title}" rows="1">${allInstuctions[i].questions[j].title}</textarea>
                                <div class="btn-group dropright d-block text-right">
                                <i class="fas fa-ellipsis-v p-2 float-right" id="btn_modify_1" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false"></i>
                                <div class="dropdown-menu">
                                <a class="dropdown-item" onclick="edit(${allInstuctions[i].id},${allInstuctions[i].questions[j].id})"
                                    href="#">${editText}</a>
                                <a class="dropdown-item" onclick="removeQuestion(${allInstuctions[i].id},${allInstuctions[i].questions[j].id})" id="remove_1" href="#">${removeText}</a>
                                </div>
                            </div></div></div>
                            <p class="errorMsg" id="${data.error_question}" style="margin-bottom: 0">Please Input Question</p>
                            <div class="my-2 px-3">
                            ${answerHtml}
                            </div>
                            <div class="text-right d-none mr-3 w-100" id="update_${allInstuctions[i].questions[j].id}">
                                  <button class="btn btn-default btn-sm mr-0 waves-effect waves-light" onclick="saveQuestion(${allInstuctions[i].id},${allInstuctions[i].questions[j].id})">${saveText}</button>
                            </div>
                            </div>`;
                    answerHtml = "";
                    $("#card-body-" + allInstuctions[i].id).append(question);
                }
            }  else{
                $("#card-body-" + allInstuctions[i].id).append(`<h4 class="text-center">${noQuestionText}</h4>`);
            }
        }
        PreviewLoopResize();
        //check if instruction has no question
        if((allInstuctions.length===1)){
            if(allInstuctions[allInstuctions.length-1].questions.length<1){
                $("#btnPublish").prop("disabled",true);
                $("#btnDraft").prop("disabled",true);
            }else{
                $("#btnPublish").prop("disabled",false);
                $("#btnDraft").prop("disabled",false);
            }
        }

        for (let j = 0; j < allInstuctions.length; j++) {
            setTimeout(function(){
                resizeTextArea('instruction_'+allInstuctions[j].id)
            },300);
        }
        // Dynamic Height of Texarea
        // Using setTimeout to wait for element to finish loaded and scrollHeight is set
        // Document Ready is not working in this case
        let timer = 1000;
        let data1;
        for (let k = 0; k < allInstuctions.length; k++) {
            setTimeout(function () {
                resizeTextArea('instruction_'+allInstuctions[k].id)
            },timer);
            for (let l = 0; l < allInstuctions[k].questions.length; l++) {
                data1 = getComponentId("preview_"+allInstuctions[k].questions[l].id);
                setTimeout(function () {
                    resizeTextArea(data1.question_id);
                },timer);
                for (let m = 0; m < allInstuctions[k].questions[l].answers.length; m++) {
                        setTimeout(function () {
                            resizeTextArea(data1.answer_id[m])
                        },timer);
                }
                disableQuestion(data1)
            }
        }
    }
    else{
        $("#btnPublish").prop("disabled",true);
        $("#btnDraft").prop("disabled",true);
        $("#question-container").append(`<h1 class="text-center p-10">${noQuizText}</h1>`);
    }
}

//Resize all textarea
function PreviewLoopResize(){
    let arrayIDs = [];
    for (let k = 0; k < allInstuctions.length; k++) {
        arrayIDs.push('instruction_'+allInstuctions[k].id);
        for (let l = 0; l < allInstuctions[k].questions.length; l++) {
            let data = getComponentId("preview_"+allInstuctions[k].questions[l].id);
            arrayIDs.push(data.question_id);
            for (let m = 0; m < allInstuctions[k].questions[l].answers.length; m++) {
                arrayIDs.push(data.answer_id[m]);
            }
        }
    }
    for (let i of arrayIDs){
        setTimeout(function () {
            resizeTextArea(i);
        },1000)
    }
    setTimeout(function () {
        if(typeof data !== 'undefined'){
            disableQuestion(data)
        }
    },1500);
}

let observe;
if (window.attachEvent) {
    observe = function (element, event, handler) {
        element.attachEvent('on'+event, handler);
    };
}
else {
    observe = function (element, event, handler) {
        if(!(element==null)){
            element.addEventListener(event, handler, false);
        }
    };
}

//resize text area
function resizeTextArea (id) {
    let text = document.getElementById(id);
    function resize () {
        text.style.height = 'auto';
        if (text.scrollHeight === 0){
            text.style.height = 35+'px';
        } else {
            text.style.height = text.scrollHeight+'px';
        }
    }
    /* 0-timeout to get the already changed text */
    function delayedResize () {
        window.setTimeout(resize, 0);
    }
    observe(text, 'load', resize);
    observe(text, 'change',  resize);
    observe(text, 'cut',     delayedResize);
    observe(text, 'paste',   delayedResize);
    observe(text, 'drop',    delayedResize);
    observe(text, 'keydown', delayedResize);
    if(!(text==null)){
        text.focus();
        // text.select();
        resize();
    }
}

//Rerange the number of question
function rerangeQuizNumber(totalQuestion) {
   let allQuestion = totalQuestion+1;
    let allQuiz = document.getElementById("quiz").childNodes;
    allQuiz.forEach(function (item, index) {
        item.childNodes.forEach(function (subItem, i) {
            if (subItem.className == "p-3" || subItem.className == "p-3 w-100") {
                subItem.childNodes.forEach(function (subItem1, i) {
                    if (subItem1.className == 'd-flex') {
                        subItem1.childNodes.forEach(function (subItem2, i) {

                            if (subItem2.className == "mr-3 ") {
                                subItem2.childNodes.forEach(function (subItem3, i) {
                                    if (subItem3.className == "question-test-center-vertical") {
                                        subItem3.innerHTML = allQuestion;
                                        allQuestion = allQuestion - 1;
                                    }
                                })
                            }
                        })
                    }
                })
            }
        })
    });
}

function updateInstructionTitle(ins_id){
    if(!isEdit){
        let instruction = $("#txtTitleIns");
        let btnSave =  $("#btnSaveIns");
        let insId = $("#instruction_"+ins_id);
        $("#modal-edit-instruction").modal("show");
        btnSave.off();
        instruction.val(insId.val()).trigger('change');
        setTimeout(function () {
            resizeTextArea('txtTitleIns')
        },200);
        instruction.blur();
        instruction.focus();
        btnSave.click(function(e){
            if (instruction.val().trim() !== "") {
                $('#modal-edit-instruction').modal("show");
                $("#btnSaveIns").attr("data-dismiss", "modal");
                for(var i=0; i<allInstuctions.length;i++){
                    if(allInstuctions[i].id === parseInt(ins_id)){
                        allInstuctions[i].title = instruction.val();
                        $("#currentIns").html(`${instructionText} : ${instruction.val()}`);
                        setTimeout(function () {
                            resizeTextArea('currentIns');
                        },200);
                        //Update instruction title
                        sql = sql + `update lh_instruction set title = '${instruction.val()}' where id = ${ins_id};`;
                        preview()
                    }
                }
                isEdit = false;
            } else {
                $("#btnSaveIns").removeAttr("data-dismiss")
            }
        })
    }else {
        warningModal(mustFinishUpdate)
    }

}

//Change from select option topic to topic textfield
$('#topicQuiz').change(function(){
    let value = $(this).val();
    if(value==='0'){
        isAddTopic = true;
        $("#selectTopic").hide();
        $("#topic").append(`<div class="input-field md-form mt-0" id="frmAddTopic">
                                    <i id="closeAddTopic" onclick="closeAddTopic()" class="postfix fas fa-times fa-1x"></i>
                                    <input type="text" id="txtTopic" class="form-control fs-18" onkeyup="validateTopicOnKeyUp()" placeholder="${addTopicText}">
                                    <label id="lbTopic" for="txtTopic" class="ml-0 mt-2 text-danger clr-modal-label active"></label>
                              </div>`);
        $("#txtTopic").focus();
        $("#btnCreateQuiz").attr('disabled','true');
    }
});

//Close add topic textfield and change to select option topic
function closeAddTopic() {
    $("#selectTopic").show();
    $("#topicQuiz").val($("#topicQuiz option:first").val());
    $("#frmAddTopic").remove();
    $("#btnCreateQuiz").removeAttr('disabled');
    isAddTopic = false;
}

function validateTopicOnKeyUp(){
    let topic = $("#txtTopic");
    if(topic.val().trim()===""){
        $("#lbTopic").attr('data-error','cannot be blank').removeAttr('data-success');
        topic.addClass("invalid").removeClass('valid');
        topic.focus();
        $("#btnCreateQuiz").attr('disabled','true');
    }else if(topic.val().trim().length>20 || topic.val().trim().length<3){
        if(topic.val().trim().length<3)
            $("#lbTopic").attr('data-error','minimum length is 3').removeAttr('data-success');
        else
            $("#lbTopic").attr('data-error','maximum length is 20').removeAttr('data-success');
        topic.addClass("invalid").removeClass('valid');
        $("#btnCreateQuiz").attr('disabled','true');
    }else{
        topic.addClass("valid").removeClass('invalid');
        $("#lbTopic").attr('data-success','available').removeAttr('data-error');
        $("#btnCreateQuiz").removeAttr('disabled');
    }
}

function validateTopicOnClick(){
    let topic = $("#txtTopic"),isValid = true;
    if(topic.val().trim()===""){
        $("#lbTopic").attr('data-error','cannot be blank').removeAttr('data-success');
        topic.addClass("invalid").removeClass('valid');
        topic.focus();
        isValid = false;
    }else if(topic.val().trim().length>20 || topic.val().trim().length<3){
        if(topic.val().trim().length<3)
            $("#lbTopic").attr('data-error','minimum length is 3').removeAttr('data-success');
        else
            $("#lbTopic").attr('data-error','maximum length is 20').removeAttr('data-success');
        topic.addClass("invalid").removeClass('valid');
        isValid = false;
    }else{
        topic.addClass("valid").removeClass('invalid');
        $("#lbTopic").attr('data-success','available').removeAttr('data-error');
    }
    return isValid;
}






