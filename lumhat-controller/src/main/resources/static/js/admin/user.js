function editRole(element) {
    userId = $(element).parent().parent().children().eq(0).text();
    firstName = $(element).parent().parent().children().eq(1).text();
    lastName = $(element).parent().parent().children().eq(2).text();
    role = $(element).parent().parent().children().eq(3).text();
    $(".txtId").val(userId);
    $("#exampleModalLabel").text('Choose new role for user "'+firstName+' '+lastName+'"');

    $("#cboRole option").each(function () {
        if ($(this).text() == role) {
            $(this).removeAttr("selected", "selected")
            $(this).attr("selected", "selected");
        } else {
            $(this).removeAttr("selected", "selected")
        }
    });
}

$(function (element) {
    $(document).on("submit","#formUpdate",function (event) {
        event.preventDefault();
        var data={
            id:0,
            userId:$("#txtId").val(),
            roleId:$("#cboRole").val()
        };
        $.ajax({
            url:"/admin/updateRole",
            type:"POST",
            data:JSON.stringify(data),
            contentType: "application/json",
            beforeSend:function(){
                $("#basicExampleModal").modal("hide");
                $('#loading').show();
            },
            success:function (data) {
                getData("/admin/findAllUser","#tblUser")
                $("#cboRole").removeAttr('selected');
                $("#basicExampleModal").modal("hide");
            },
            error:function (data) {
                console.log("Error=> "+data);
            }
        });
    })
    // $("#formUpdate").submit(function (event) {
    //
    //
    // });
})

function getData(dataUrl, table) {
    $('#loading').show();
    $.ajax({
        url: dataUrl,
        type: "GET",
        beforeSend: function(){
            $('#loading').show();
        },
        success: function (data) {
            $(table).html(data);
            $('#loading').hide();
        },
        error: function (data) {
            console.log("Error : " + data);
        }
    });
}
