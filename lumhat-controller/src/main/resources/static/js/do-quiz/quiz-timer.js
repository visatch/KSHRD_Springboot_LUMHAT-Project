var minute = 0;
var second = 0;


function initTimer() {
    minute = duration / 60;
    start();
}

function start() {
    stopwatch = setInterval(runTimer, 1000);
}

function stopTimer() {
    clearInterval(stopwatch);
}

function runTimer() {

    second--;
    if (second <= 0){
        second = 59;
        minute--;
        if (minute < 0){
            // TODO TIME UP !
            minute = 0;
            second = 0;
            stopTimer();

            $('#time-up-modal').modal('show');

        }
    }

    setTimerView(minute,second)
}

function setTimerView(minute, second){
    var min = (minute ? (minute > 9 ? minute : "0" + minute) : "00");
    var sec = (second > 9 ? second : "0" + second);
    $('#minute').text(min);
    $('#second').text(sec)
}

function calculateFinishTimer(){
    var fullTime = duration;
    var durationTimer = (minute*60)+second;
    return fullTime - durationTimer;
}


