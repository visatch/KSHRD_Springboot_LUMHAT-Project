$(document).ready(function () {

    $("#btn-delete").click(function () {
        $.ajax({
            url: '/profile/delete',
            type: 'GET',
            beforeSend: function () {
                $('#loading').show()
            },
            success: function(result) {
                // Do something with the result
                removeHistoryFromUI()
                $('#loading').hide()
            }
        })
    })



});


function removeHistoryFromUI() {
    $("#history-blog").empty();
    $("#history-blog").append('<h2 th:if="${histories.isEmpty()}" id="no-record-label">No History Record!</h2>\n')
}

function doQuizAgain(btn) {
    console.log($(btn).attr("id"));
    $('#centralModalInfo').modal('show');


    $("#done-do-quiz").click(function () {
        $('#loading').show();
        window.location.href = "/do-quiz?quiz_id="+$(btn).attr("id");
    })

}