// SideNav Button Initialization
$(".button-collapse").sideNav();
// SideNav Scrollbar Initialization
var sideNavScrollbar = document.querySelector('.custom-scrollbar');
var ps = new PerfectScrollbar(sideNavScrollbar);

// copy text
$(document).ready(()=>{
    var clipboard = new ClipboardJS('.view-code-lg');

    var clipboard1 = new ClipboardJS('.class_code');

    clipboard.on('success', function(e) {
        notify("Copied");
    });
    clipboard.on('error', function(e) {
        console.log(e);
    });

    clipboard1.on('success', function(e) {
        notify("Copied");
    });

    clipboard1.on('error', function(e) {
        console.log(e);
    });
});

// notification
function notify(message){
    var text = `
        <div id="toast" class="toast-container">
            <div class="toast-head">
                <div class="toast-body text-center" id="toast_body">
                    ${message}
                </div>
            </div>
        </div>
    `;
    $('body').append(text);
}

//------------ Add button Dropdown ----------------
$(document).ready(function() {
    $('.mdb-select').materialSelect();
});


$(document).ready(function () {

    $("#confirm_cropped_image").attr('disabled','true');
    $(".cr-slider-wrap").hide();
    //Crop image before upload
    $("#change_cover").click(function () {
        $(".clr-upload-place").hide();
        $("#cropped_image").hide();
        $(".upload-result").hide();
        $("#upload").click()
    });

    $("#upload").click(()=>{
        $("#cropModal").modal("show")
    });

    $("#upload").click(()=>{
        $("#cropModal").modal("show")
    })

    $("#upload").change(function () {

        $(".clr-upload-place").show();
        $(".upload-result").show();
        $("#cropped_image").hide();
        $("#confirm_cropped_image").attr('disabled','true');
    });

    $(".upload-result").click(function () {
        $("#cropped_image").show();
        $(".clr-upload-place").hide();
        $(".upload-result").hide();
        $("#confirm_cropped_image").removeAttr('disabled');
    });

    $("#confirm_cropped_image").click(function () {
        $.blockUI({baseZ: 2000,css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
        var img = $("#cropped_image").attr("src");
        $("#class_cover").attr("src", img);
        $.ajax({
            url:"/classroom/change-cover-class/"+classId,
            type:"POST",
            data:JSON.stringify(img),
            contentType:"application/json",
            success:function (data) {
                $.unblockUI();
            },
            error:function (error) {
                console.log(error);
            }
        })
    });
});

// click all checkbox
$("#all").click(function () {
    var isCheck = $("#materialChecked").prop("checked");
    if (isCheck == false) {
        $(".form-check-joinCode").not("#materialChecked").prop('checked', true)
    } else {
        $(".form-check-joinCode").not("#materialChecked").prop('checked', false)
    }

});

$(function () {
    $('.material-tooltip-email').tooltip({
        template: '<div class="tooltip md-tooltip-email"><div class="tooltip-arrow md-arrow"></div><div class="tooltip-inner md-inner-email"></div></div>'
    });
});

$(document).ready(()=>{

    validateJoinClass();

    $("#createClassSubmit").attr('disabled');

    $("#modalCreateLink").click(()=>{
        $("#classname").val("").blur();
        $("#subject").val("").blur();
        $("#room").val("").blur();
    });

    $("#classname").keyup(()=>{
        if(isValidInputField('classname','classnameLabel') &&
            isValidName("subject") &&
            isValidName("room")
        ) $("#createClassSubmit").removeAttr('disabled');
        else $("#createClassSubmit").attr('disabled','true');

    });

    $("#subject").keyup(()=>{
        if(isValidInputField('subject','subjectLabel') &&
            isValidName("classname") &&
            isValidName("room")
        ) $("#createClassSubmit").removeAttr('disabled');
        else $("#createClassSubmit").attr('disabled','true');

    });

    $("#room").keyup(()=>{
        if(isValidInputField('room','roomLabel') &&
            isValidName("subject") &&
            isValidName("classname")
        ) $("#createClassSubmit").removeAttr('disabled');
        else $("#createClassSubmit").attr('disabled','true');

    });

    $(".modal-content").click(()=>{

        $("#classnameLabel").addClass('active');
        $("#subjectLabel").addClass('active');
        $("#roomLabel").addClass('active');

        if(isValidName("classname"))
            $("#classname").addClass("valid").removeClass("invalid");
        else
            $("#classname").removeClass("valid").addClass("invalid");
        if(isValidName("subject"))
            $("#subject").addClass("valid").removeClass("invalid");
        else
            $("#subject").removeClass("valid").addClass("invalid");
        if(isValidName("room"))
            $("#room").addClass("valid").removeClass("invalid");
        else
            $("#room").removeClass("valid").addClass("invalid");
    });

});

// validate join class modal
function validateJoinClass() {
    const codeLength = 6;
    const specialCharacter = /[^a-zA-Z0-9]/g;

    let joinCode = $("#joinClassCode");
    let joinCodeLabel = $("#joinClassCodeLabel");
    let joinSubmit = $("#joinClassCodeSubmit");
    let isAvailable = false;

    joinCode.keyup(()=>{

        if(joinCode.val().match(specialCharacter)){

            joinCodeLabel.attr('data-error','class code cannot be symbol');
            joinCode.addClass("invalid");
            isAvailable = false;

        }else{

            if(joinCode.val().length === codeLength){
                joinCodeLabel.attr('data-success','available');
                joinCode.removeClass("invalid").addClass("valid");
                isAvailable = true;

            }else if(joinCode.val().length < codeLength){
                joinCodeLabel.attr('data-error','class code length must be at least 6 digits').removeAttr('data-success');
                joinCode.addClass("invalid").removeClass("valid");
                isAvailable = false;

            }else if(joinCode.val()===''){
                joinCode.removeClass("invalid");
            }
            else{
                joinCodeLabel.attr('data-error','class code maximum length is 6 digits').removeAttr('data-success');
                joinCode.addClass("invalid").removeClass("valid");
                isAvailable = false;
            }

            if(isAvailable)
                joinSubmit.removeAttr('disabled');
            else
                joinSubmit.attr('disabled','true');

        }

    });


    $(".modal").click(()=>{

        joinCodeLabel.addClass('active');
        if(isAvailable) {
            joinCode.addClass("valid").removeClass("invalid");
        }
        else
            joinCode.removeClass("valid").addClass("invalid");
    });

    $("#modalJoinLink").click(()=>{
        joinCode.val("").blur()
            .removeClass("valid")
            .removeClass("invalid");
        joinSubmit.attr('disabled','true');
        joinCodeLabel.removeClass("active")
            .removeAttr('data-success')
            .removeAttr('data-error');
    });

    joinSubmit.click((e)=>{
        if(!isAvailable){
            joinCode.removeClass("valid");
            joinCodeLabel.removeClass("active");
            e.preventDefault();

        }
    });
}

function isValidClassname(s) {
    return /\p{L}|\p{Nd}/u.test(s);
}

// validate input field
function isValidInputField(input, label) {
    let whitespace = /^\s+$/;
    let inputField = $("#"+input);
    let messageLabel = $("#"+label);
    if(inputField.val().trim()===""){
        messageLabel.attr('data-error','cannot be blank').removeAttr('data-success');
        inputField.addClass("invalid").removeClass('valid');
        inputField.focus();
        return false;
    }else {
        if(!isValidClassname(inputField.val().trim())){
            messageLabel.attr('data-error','cannot be symbol').removeAttr('data-success');
            inputField.addClass("invalid").removeClass('valid');

        }else if(inputField.val().charAt(0).match(whitespace)){
            inputField.addClass("invalid").removeClass('valid');
            messageLabel.attr('data-error','first letter cannot be whitespace').removeAttr('data-success');

        }else if(inputField.val().trim().length < 3){
            inputField.addClass("invalid").removeClass('valid');
            messageLabel.attr('data-error','please input at least 3 characters').removeAttr('data-success');
        }
        else if(inputField.val().trim().length > 50){
            inputField.addClass("invalid").removeClass('valid');
            messageLabel.attr('data-error','maximum is 50 characters').removeAttr('data-success');
        }
        else{
            inputField.addClass("valid").removeClass('invalid');
            messageLabel.attr('data-success','available').removeAttr('data-error');
            return true;

        }
        return false;
    }


}

// valid input field
function isValidName(input) {
    let whitespace = /^\s+$/;
    let inputField = $("#"+input);

    if(!isValidClassname(inputField.val().trim())) return false;
    if(inputField.val().charAt(0).match(whitespace)) return false;
    return !(inputField.val().trim().length < 3 || inputField.val().trim().length === 0 || inputField.val().trim().length > 50);

}

// clear modal input value when fade out
function clearModalInputValue(startModalId,inputId,LabelId,SubmitButtonId) {
    $("#"+startModalId).click(()=>{
        $("#"+inputId).val("").blur().removeClass("valid").removeClass("invalid");
        $("#"+SubmitButtonId).attr('disabled','true');
        $("#"+LabelId).removeClass("active").removeAttr('data-success').removeAttr('data-error');
    });
}


// join and create class
$(document).ready(()=>{

    $("#createClassSubmit").click(()=>{
        createClass();
    });

    $("#joinClassCodeSubmit").click(()=>{
        joinClass();
    });

    function createClass() {
        $("#modalCreate").block({css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
        let data = {
            name : $("#classname").val(),
            subject : $("#subject").val(),
            room : $("#room").val()
        };

        $.ajax({
            url : "/classroom/create",
            type: "POST",
            data: data,
            success: function (respond) {
                $("#modalCreate").unblock();
                if(respond===true){
                    $("#modalCreate").modal("hide");
                    $("#createModalSuccess").modal("show");
                }else{
                    $("#createModalFail").modal("show");
                }
                window.location.href = window.location.origin+"/classroom";
            },
            error: function (respond) {
                $("#modalCreate").unblock();
                console.log("ERROR: "+respond);
                $("#createModalFail").modal("show");
            }
        });
    }

    function joinClass() {
        $("#modalJoin").block({css: { border: 'none',backgroundColor: 'transparent'}, message: '<div class="spinner-border text-default" role="status"><span class="sr-only">Loading...</span></div>'});
        $.ajax({
            url : "/classroom/join",
            type: "POST",
            data: {code : $("#joinClassCode").val()},
            success: function (respond) {
                $("#modalJoin").unblock();
                if(respond != "0"){
                    $("#modalJoin").modal("hide");
                    $("#joinModalSuccess").modal("show");
                    window.location.href = "/classroom/student/"+respond;
                }else{
                    $("#modalJoin").modal("show");
                    $("#joinModalFail").modal("show");
                }
            },
            error: function (respond) {
                console.log("ERROR: "+respond);
                $("#modalJoin").modal("show");
                $("#joinModalFail").modal("show");
            }
        });
    }
});

//change language to khmer in classroom
$("#classroom_langkm").click(function () {
    $.ajax({
        url:"/?lang=km",
        method:"get",
        success:function () {
            location.reload();
        }
    })
});

//change language to english in classroom
$("#classroom_langen").click(function () {
    $.ajax({
        url:"/?lang=en",
        method:"get",
        success:function () {
            location.reload();
        }
    })
});