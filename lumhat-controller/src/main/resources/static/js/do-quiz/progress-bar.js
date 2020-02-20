$(document).ready(function () {
    $('#centralModalInfo').modal('show');
    // Initial in Progress bar and count
    setProgressBar(getPercentage(countActiveQuestion()["active"],countActiveQuestion()["count"]));
    setProgressCount(countActiveQuestion()["active"],countActiveQuestion()["count"]);

    $('.form-check-input').click(function(){
        setProgressBar(getPercentage(countActiveQuestion()['active'],countActiveQuestion()['count']));
        setProgressCount(countActiveQuestion()["active"],countActiveQuestion()['count']);
    });

});



// Check all Question then return questions active and count
function countActiveQuestion() {
    // language=JQuery-CSS
    var questions = $("#question-container").children("#question-blog").children(".question");
    var questionLength = parseInt(questions.length);
    var questionActive = 0;

    for (var i = 0; i < questionLength; i++ ) {
        var answersLength = (questions[i].children).length;
        for (var a = 1; a < answersLength; a++) {
            var isActive = questions[i].children[a].children[0]["checked"];
                if (isActive){
                    questionActive++;
                }else{
                    questions[i].children[a].children[0]["checked"] = false;
                }
        }
    }

    var result = {};
    result["active"] = questionActive;
    result["count"] = questionLength;
    return result;
}


// Calculate then return Percentage of Progress bar
function getPercentage(questionActive, questionCount) {
    return (questionActive / questionCount)*100
}

// Set Progress Count UI
function setProgressCount(questionActive, questionCount) {
    // language=JQuery-CSS
    $("#questionActive").text(questionActive);
    $("#questionCount").text(questionCount);
}

// Set Progress Bar UI
function setProgressBar(percentage) {
    $(".progress-bar").css("width", percentage + "%");
}
