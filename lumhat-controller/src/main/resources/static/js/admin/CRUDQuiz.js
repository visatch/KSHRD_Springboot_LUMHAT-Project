
//contains all instruction and every questions in each instruction
var allInstuctions=new Array();

//count total answer on add question page
var count=0;

//currently view quiz id
var quizViewID=0;
$(function(){

    //add question and instruction to allInstruction
    $("#form-add-question").submit(function (e) {
        e.preventDefault();
        if (allInstuctions.length==0){
            allInstuctions[0]={
                id:0,
                title:$("#question-instruction").val(),
                quiz:{},
                questions:new Array()
            }
        }
        for(var ins=0;ins<allInstuctions.length;ins++){
                if (allInstuctions[ins].title==($("#question-instruction").val())) {
                allInstuctions[ins].questions[allInstuctions[ins].questions.length]={
                    id:0,
                    title:$("#question-input").val(),
                    instruction:{
                        id:0,
                        title:$("#question-instruction").val(),
                        quiz:{},
                        questions:[]
                    },
                    answers:[
                        answer={
                            id:1,
                            option:$("#answer-option1").val(),
                            isCorrect:$("#answer-iscorrect1").prop('checked'),
                            question:{}
                        },
                        answer={
                            id:2,
                            option:$("#answer-option2").val(),
                            isCorrect:$("#answer-iscorrect2").prop('checked'),
                            question:{}
                        },
                        answer={
                            id:3,
                            option:$("#answer-option3").val(),
                            isCorrect:$("#answer-iscorrect3").prop('checked'),
                            question:{}
                        },
                        answer={
                            id:4,
                            option:$("#answer-option4").val(),
                            isCorrect:$("#answer-iscorrect4").prop('checked'),
                            question:{}
                        }
                    ]
                }
            }else{
                if(ins==allInstuctions.length-1) {
                    allInstuctions[allInstuctions.length] = {
                        id: 0,
                        title: $("#question-instruction").val(),
                        quiz: {},
                        questions:new Array()
                    }
                    var allInstructionsLenght=allInstuctions.length;
                    allInstuctions[allInstructionsLenght-1].questions[allInstuctions[allInstructionsLenght-1].questions.length]={
                        id:0,
                        title:$("#question-input").val(),
                        instruction:{
                            id:0,
                            title:$("#question-instruction").val(),
                            quiz:{},
                            questions:[]
                        },
                        answers:[
                            answer={
                                id:1,
                                option:$("#answer-option1").val(),
                                isCorrect:$("#answer-iscorrect1").prop('checked'),
                                question:{}
                            },
                            answer={
                                id:2,
                                option:$("#answer-option2").val(),
                                isCorrect:$("#answer-iscorrect2").prop('checked'),
                                question:{}
                            },
                            answer={
                                id:3,
                                option:$("#answer-option3").val(),
                                isCorrect:$("#answer-iscorrect3").prop('checked'),
                                question:{}
                            },
                            answer={
                                id:4,
                                option:$("#answer-option4").val(),
                                isCorrect:$("#answer-iscorrect4").prop('checked'),
                                question:{}
                            }
                        ]
                    }
                    break;
                }

            }
        }

        disableAddQuestionOption();
        clearAddQuestionInputForm();
        count++;
        $("#question-count").text(""+count);
    })
})

function clearAddQuestionInputForm() {
    $("#question-input").val("");
    $("#answer-option1").val("");
    $("#answer-option2").val("");
    $("#answer-option3").val("");
    $("#answer-option4").val("");
    $("#answer-iscorrect1").prop("checked",false);
    $("#answer-iscorrect2").prop("checked",false);
    $("#answer-iscorrect3").prop("checked",false);
    $("#answer-iscorrect4").prop("checked",false);
    $("#question-input").focus();
}

function disableAddQuestionOption() {
    $("#select-sub-major").attr("disabled",true);
    $("#select-subject").attr("disabled",true);
    $("#quiz-level").attr("disabled",true);
    $("#quiz-title").attr("disabled",true);
    $("#quiz-duration").attr("disabled",true);
}


function saveQuestionsToDatabase() {
    window.onbeforeunload=function(){
        return null;
    }
    $("#checking").show();
    var data={
        id:0,
        title:$("#quiz-title").val(),
        duration:$("#quiz-duration").val(),
        status:'',
        user:{},
        subMajor:{
            id:$('#select-subject').val(),
            name:'',
            major:{},
            subjects:[]
        },
        level: {
            id: $('#quiz-level').val(),
            name: $('#quiz-level option:selected').text()
        },
        instructions:allInstuctions
    };
    $.ajax({
        url:"/admin/add-questions",
        type:"POST",
        data:JSON.stringify(data),
        contentType:"application/json",
        success:function (data) {
            $("#checking").hide();
            alert("Success");
            $("#checking").show();
            window.location.href = data;
        },
        error:function (error) {
            alert(error);
        }

    })
}

function viewQuestions(){
    $("#questions-update-delete").empty();
    var countQuestion=1;
    for(ins=0;ins<allInstuctions.length;ins++){
        $("#questions-update-delete").append(
            "<div class='question-update-delete'>"+
            "<p id='instruction ins"+ins+"' class='text-left instructions'>"+allInstuctions[ins].title+"</p>"+
            "<div class='question-by-instruction' id='question-by-instruction"+ins+"'>"+
            "</div></div>"
        )
        for(qus=0;qus<allInstuctions[ins].questions.length;qus++){
            console.log("Questions:"+qus);
            $("#question-by-instruction"+ins).append(
                "<div class='questions-container' id='question-number"+qus+"-ins"+ins+"'"+
                "</div>"
            )
            $("#question-number"+qus+"-ins"+ins).append(
                "<div class='question-container text-left'><p>"+"<span>"+countQuestion+". </span>"+""+allInstuctions[ins].questions[qus].title+"</p></div>"+
                "<div class='btn-modal-container'>"+
                "<button class='btn-danger' id='"+ins+"btn-delete-qus"+qus+"' onclick='deleteQuestion(this)'>Delete</button>"+
                "<button class='btn-default' id='"+ins+"btn-update-qus"+qus+"' data-toggle='modal' data-target='#updateQuestionModal' onclick='updateQuestion(this)'>Update</button>"+
                "</div>"
            )
            countQuestion++;
        }

    }
}

function deleteQuestion(btnDelete) {
    var btnDeleteID=btnDelete.id;
    var insID="";
    var qusID="";

    for (var i=0;i<btnDeleteID.indexOf("btn");i++){
        insID+=btnDeleteID.charAt(i);
    }

    for (var i=btnDeleteID.lastIndexOf("s");i<btnDeleteID.length;i++){
        qusID+=btnDeleteID.charAt(i+1);
    }
    allInstuctions[insID].questions.splice(qusID,1);
    if (allInstuctions[insID].questions.length==0){
        allInstuctions.splice(insID,1);
    }
    viewQuestions();
    count--;
    $("#question-count").text(""+count);

}

function updateQuestion(btnUpdate){
    console.log(btnUpdate.id);
    var btnUpdateID=btnUpdate.id;
    var insUpdateID="";
    var quzUpdateID="";
    for (var i=0;i<btnUpdateID.indexOf("btn");i++){
        insUpdateID+=btnUpdateID.charAt(i);
    }

    for (var i=btnUpdateID.lastIndexOf("s");i<btnUpdateID.length;i++){
        quzUpdateID+=btnUpdateID.charAt(i+1);
    }

    var questionUpdate=allInstuctions[insUpdateID].questions[quzUpdateID];
    var questionUpdateTitle=questionUpdate.title;
    var answersUpdate=new Array();
    for(var i=0;i<questionUpdate.answers.length;i++){
        answersUpdate[i]=questionUpdate.answers[i];
    }

    $("#question-update-modal-body").empty();
    $("#question-update-modal-body").append(
        "<form>" +
        "                            <div class=\"form-group\">\n" +
        "                                <label for=\"question-update-input\">Question</label>\n" +
        "                                <textarea class=\"form-control\" id=\"question-update-input\" rows=\"3\" required>"+questionUpdateTitle+"</textarea>\n" +
        "                            </div>\n" +
        "\n" +
        "                            <div class=\"form-group\">\n" +
        "                                <label for=\"answer-update-option1\">Answer 1</label>\n" +
        "                                <div class=\"input-group\">\n" +
        "                                    <input type=\"text\" class=\"form-control\" id=\"answer-update-option1\"\n" +
        "                                           placeholder=\"Your answer here\" value='"+answersUpdate[0].option+"' required>\n" +
        "                                    <div class=\"input-group-text input-group-append\">\n" +
        "\n" +
        "                                        <input type=\"checkbox\" aria-label=\"Checkbox for following text input\" id=\"answer-update-iscorrect1\" class=\"correctAnswerCheckbox\">\n" +
        "                                        <label for=\"answer-update-iscorrect1\">correct</label>\n" +
        "                                    </div>\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "\n" +
        "                            <div class=\"form-group\">\n" +
        "                                <label for=\"answer-update-option2\">Answer 2</label>\n" +
        "                                <div class=\"input-group\">\n" +
        "                                    <input type=\"text\" class=\"form-control\" id=\"answer-update-option2\"\n" +
        "                                           placeholder=\"Your answer here\" value='"+answersUpdate[1].option+"' required>\n" +
        "                                    <div class=\"input-group-text input-group-append\">\n" +
        "\n" +
        "                                        <input type=\"checkbox\" aria-label=\"Checkbox for following text input\" id=\"answer-update-iscorrect2\" class=\"correctAnswerCheckbox\">\n" +
        "                                        <label for=\"answer-update-iscorrect2\">correct</label>\n" +
        "                                    </div>\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "                            <div class=\"form-group\">\n" +
        "                                <label for=\"answer-update-option3\">Answer 3</label>\n" +
        "                                <div class=\"input-group\">\n" +
        "                                    <input type=\"text\" class=\"form-control\" id=\"answer-update-option3\"\n" +
        "                                           value='"+answersUpdate[2].option+"' placeholder=\"Your answer here\">\n" +
        "                                    <div class=\"input-group-text input-group-append\">\n" +
        "                                        <input type=\"checkbox\" aria-label=\"Checkbox for following text input\" id=\"answer-update-iscorrect3\" class=\"correctAnswerCheckbox\">\n" +
        "                                        <label for=\"answer-update-iscorrect3\">correct</label>\n" +
        "                                    </div>\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "                            <div class=\"form-group\">\n" +
        "                                <label for=\"answer-update-option4\">Answer 4</label>\n" +
        "                                <div class=\"input-group\">\n" +
        "                                    <input type=\"text\" class=\"form-control\" id=\"answer-update-option4\"\n" +
        "                                           value='"+answersUpdate[3].option+"' placeholder=\"Your answer here\">\n" +
        "                                    <div class=\"input-group-text input-group-append\">\n" +
        "                                        <input type=\"checkbox\" aria-label=\"Checkbox for following text input\" id=\"answer-update-iscorrect4\" class=\"correctAnswerCheckbox\">\n" +
        "                                        <label for=\"answer-update-iscorrect4\">correct</label>\n" +
        "                                    </div>\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "<div class=\"text-center\">\n" +
        "                                <button type='button' onclick='updateQuestionbyID("+insUpdateID+","+quzUpdateID+")' class=\"btn btn-default\" data-dismiss=\"modal\">Save</button>\n" +
        "                            </div>"+
        "                        </form>"
    )
    
    for (var i=0;i<answersUpdate.length;i++){
        if (answersUpdate[i].isCorrect){
            $("#answer-update-iscorrect"+(i+1)).prop("checked",true);
        }
    }
}

function updateQuestionbyID(insID,qusID) {
    allInstuctions[insID].questions[qusID]= {
        id: 0,
        title: $("#question-update-input").val(),
        instruction: {
            id: 0,
            title: allInstuctions[insID].title,
            quiz: {},
            questions: []
        },
        answers: [
            answer = {
                id: 1,
                option: $("#answer-update-option1").val(),
                isCorrect: $("#answer-update-iscorrect1").prop('checked'),
                question: {}
            },
            answer = {
                id: 2,
                option: $("#answer-update-option2").val(),
                isCorrect: $("#answer-update-iscorrect2").prop('checked'),
                question: {}
            },
            answer = {
                id: 3,
                option: $("#answer-update-option3").val(),
                isCorrect: $("#answer-update-iscorrect3").prop('checked'),
                question: {}
            },
            answer = {
                id: 4,
                option: $("#answer-update-option4").val(),
                isCorrect: $("#answer-update-iscorrect4").prop('checked'),
                question: {}
            }
        ]
    }
    viewQuestions();
}

function deleteQuestionByID(btnDelete) {

    var result=confirm("A question is about to be deleted, are you sure?")

    if(result){
        $("#checkingViewQuiz").show();
        var btnDeleteID=btnDelete.id;
        var questionID="";
        for (var i=btnDeleteID.lastIndexOf('s');i<btnDeleteID.length;i++){
            questionID+=btnDeleteID.charAt(i+1);
        }

        var quizID="";
        for(var i=0;i<btnDeleteID.indexOf("btnDel");i++){
            quizID+=btnDeleteID.charAt(i);
        }

        $.ajax({
            url:"/admin/quiz/view-quiz/delete-question/"+quizID+"/"+questionID,
            type:"GET",
            contentType:"application/json",
            success:function (data) {
                $("#viewQuiz").html(data);
                var questiontext=$(".question-view");
                for (var i=0;i<questiontext.length;i++){
                    questiontext[i].innerText=i+1+". "+questiontext[i].innerText;
                }
                $("#checkingViewQuiz").hide();
            },
            error:function (error) {
                alert(error);
            }

        })
    }

}

function deleteQuizByID(btnDeleteQuiz){

    var result=confirm("A quiz is about to be deleted, are you sure?")

    if (result){
        $("#checkingViewQuiz").show();
        window.onbeforeunload=function(){
            return null;
        }
        var btnDeleteQuizID=btnDeleteQuiz.id;

        var quizID="";
        for (var i=btnDeleteQuizID.lastIndexOf("z");i<btnDeleteQuizID.length;i++){
            quizID+=btnDeleteQuizID.charAt(i+1);
        }
        $.ajax({
            url:"/admin/quiz/view-quiz/delete-quiz/"+quizID,
            type:"POST",
            contentType:"application/json",
            success:function (data) {
                console.log("Successful")
                window.location.href = data;
            },
            error:function (error) {
                alert(error);
            }

        })
    }
}

function updateQuizView(btnUpdateQuizView) {
    var btnUpdateQuizID=btnUpdateQuizView.id;

    // get question ID from btnUpdate
    var qusID="";
    for(var i=btnUpdateQuizID.lastIndexOf("s");i<btnUpdateQuizID.length;i++){
        qusID+=btnUpdateQuizID.charAt(i+1);
    }

    // get questionContainer using qusID
    var questionContainer=$("#container-qus"+qusID);

    //get quesiton text from container
    var questionTextTemp=questionContainer.children()[0].innerText;
    var questionText="";
    for (var i=questionTextTemp.indexOf(" ");i<questionTextTemp.length;i++){
        questionText+=questionTextTemp.charAt(i+1);
    }

    var answerText=new Array();
    var answerChecked=new Array();
    for (var i=1;i<questionContainer.children().length;i++){
        answerText[i-1]=questionContainer.children().eq(i).children().eq(1).children().eq(0).text();
        answerChecked[i-1]=questionContainer.children().eq(i).children().eq(0).children().eq(0).children().eq(0).prop('checked');

    }

    if (answerText.length<4){
        for (var i=answerText.length;i<4;i++){
            answerText[i]="";
            answerChecked[i]=false;
        }
    }


    $("#question-update-modal-body").empty();
    $("#question-update-modal-body").append(
        "<form>" +
        "                            <div class=\"form-group\">\n" +
        "                                <label for=\"question-update-input\">Question</label>\n" +
        "                                <textarea class=\"form-control\" id=\"question-update-input\" rows=\"3\" required>"+questionText+"</textarea>\n" +
        "                            </div>\n" +
        "\n" +
        "                            <div class=\"form-group\">\n" +
        "                                <label for=\"answer-update-option1\">Answer 1</label>\n" +
        "                                <div class=\"input-group\">\n" +
        "                                    <input type=\"text\" class=\"form-control\" id=\"answer-update-option1\"\n" +
        "                                           placeholder=\"Your answer here\" value='"+answerText[0]+"' required>\n" +
        "                                    <div class=\"input-group-text input-group-append\">\n" +
        "\n" +
        "                                        <input type=\"checkbox\" aria-label=\"Checkbox for following text input\" id=\"answer-update-iscorrect1\" class=\"correctAnswerCheckbox\">\n" +
        "                                        <label for=\"answer-update-iscorrect1\">correct</label>\n" +
        "                                    </div>\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "\n" +
        "                            <div class=\"form-group\">\n" +
        "                                <label for=\"answer-update-option2\">Answer 2</label>\n" +
        "                                <div class=\"input-group\">\n" +
        "                                    <input type=\"text\" class=\"form-control\" id=\"answer-update-option2\"\n" +
        "                                           placeholder=\"Your answer here\" value='"+answerText[1]+"' required>\n" +
        "                                    <div class=\"input-group-text input-group-append\">\n" +
        "\n" +
        "                                        <input type=\"checkbox\" aria-label=\"Checkbox for following text input\" id=\"answer-update-iscorrect2\" class=\"correctAnswerCheckbox\">\n" +
        "                                        <label for=\"answer-update-iscorrect2\">correct</label>\n" +
        "                                    </div>\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "                            <div class=\"form-group\">\n" +
        "                                <label for=\"answer-update-option3\">Answer 3</label>\n" +
        "                                <div class=\"input-group\">\n" +
        "                                    <input type=\"text\" class=\"form-control\" id=\"answer-update-option3\"\n" +
        "                                           value='"+answerText[2]+"' placeholder=\"Your answer here\">\n" +
        "                                    <div class=\"input-group-text input-group-append\">\n" +
        "                                        <input type=\"checkbox\" aria-label=\"Checkbox for following text input\" id=\"answer-update-iscorrect3\" class=\"correctAnswerCheckbox\">\n" +
        "                                        <label for=\"answer-update-iscorrect3\">correct</label>\n" +
        "                                    </div>\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "                            <div class=\"form-group\">\n" +
        "                                <label for=\"answer-update-option4\">Answer 4</label>\n" +
        "                                <div class=\"input-group\">\n" +
        "                                    <input type=\"text\" class=\"form-control\" id=\"answer-update-option4\"\n" +
        "                                           value='"+answerText[3]+"' placeholder=\"Your answer here\">\n" +
        "                                    <div class=\"input-group-text input-group-append\">\n" +
        "                                        <input type=\"checkbox\" aria-label=\"Checkbox for following text input\" id=\"answer-update-iscorrect4\" class=\"correctAnswerCheckbox\">\n" +
        "                                        <label for=\"answer-update-iscorrect4\">correct</label>\n" +
        "                                    </div>\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "<div class=\"text-center\">\n" +
        "<button type='button' class='btn btn-info' data-dismiss='modal'>Cancel</button>"+
        "                                <button type='button' onclick='updateQuestionViewByID("+qusID+")' class=\"btn btn-success\" data-dismiss=\"modal\">Save</button>\n" +
        "                            </div>"+
        "                        </form>"
    )
    for (var i=0;i<answerChecked.length;i++){
        if (answerChecked[i]){
            $("#answer-update-iscorrect"+(i+1)).prop("checked",true);
        }
    }
}

function updateQuestionViewByID(qusID) {
    $("#checkingViewQuiz").show();
    var question= {
        id: qusID,
        title: $("#question-update-input").val(),
        instruction: {
            id: 0,
            title:'',
            quiz: {},
            questions: []
        },
        answers: [
            answer = {
                id: 1,
                option: $("#answer-update-option1").val(),
                isCorrect: $("#answer-update-iscorrect1").prop('checked'),
                question: {}
            },
            answer = {
                id: 2,
                option: $("#answer-update-option2").val(),
                isCorrect: $("#answer-update-iscorrect2").prop('checked'),
                question: {}
            },
            answer = {
                id: 3,
                option: $("#answer-update-option3").val(),
                isCorrect: $("#answer-update-iscorrect3").prop('checked'),
                question: {}
            },
            answer = {
                id: 4,
                option: $("#answer-update-option4").val(),
                isCorrect: $("#answer-update-iscorrect4").prop('checked'),
                question: {}
            }
        ]
    }
    $.ajax({
        url:"/admin/quiz/view-quiz/update-question/"+quizViewID,
        type:"POST",
        data:JSON.stringify(question),
        contentType:"application/json",
        success:function (data) {
            $("#checkingViewQuiz").hide();
            console.log("Successful")
            $("#viewQuiz").html(data);
            var questiontext=$(".question-view");
            for (var i=0;i<questiontext.length;i++){
                questiontext[i].innerText=i+1+". "+questiontext[i].innerText;
            }
        },
        error:function (error) {
            alert(error);
        }

    })
}

function insertQuestion(){
    $("#checkingViewQuiz").show();
    var question= {
        id:'',
        title: $("#question-insert-input").val(),
        instruction: {
            id: $("#instruction-insert").val(),
            title:'',
            quiz: {},
            questions: []
        },
        answers: [
            answer = {
                id: 1,
                option: $("#answer-insert-option1").val(),
                isCorrect: $("#answer-insert-iscorrect1").prop('checked'),
                question: {}
            },
            answer = {
                id: 2,
                option: $("#answer-insert-option2").val(),
                isCorrect: $("#answer-insert-iscorrect2").prop('checked'),
                question: {}
            },
            answer = {
                id: 3,
                option: $("#answer-insert-option3").val(),
                isCorrect: $("#answer-insert-iscorrect3").prop('checked'),
                question: {}
            },
            answer = {
                id: 4,
                option: $("#answer-insert-option4").val(),
                isCorrect: $("#answer-insert-iscorrect4").prop('checked'),
                question: {}
            }
        ]
    }

    $.ajax({
        url:"/admin/quiz/view-quiz/insert-question/"+quizViewID,
        type:"POST",
        data:JSON.stringify(question),
        contentType:"application/json",
        success:function (data) {
            $("#checkingViewQuiz").hide();
            console.log("Successful")
            $("#viewQuiz").html(data);
            var questiontext=$(".question-view");
            for (var i=0;i<questiontext.length;i++){
                questiontext[i].innerText=i+1+". "+questiontext[i].innerText;
            }
        },
        error:function (error) {
            alert(error);
        }

    })

    console.log(question);
}
function clearInsertQuestionForm() {
    $("#question-insert-input").val("");
    $("#answer-insert-option1").val("");
    $("#answer-insert-option2").val("");
    $("#answer-insert-option3").val("");
    $("#answer-insert-option4").val("");
    $("#answer-insert-iscorrect1").prop("checked",false);
    $("#answer-insert-iscorrect2").prop("checked",false);
    $("#answer-insert-iscorrect3").prop("checked",false);
    $("#answer-insert-iscorrect4").prop("checked",false);
    $("#question-input").focus();
}

