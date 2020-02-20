function alertSave() {
    swal({
        title: "Saved!",
        text: "You have saved data to database",
        icon: "success",
        button: false,
        timer: 2000,
    }).then(
        function (dismiss) {
            if (dismiss === 'timer') {
                //console.log('I was closed by the timer')
            }
        }
    )
}
function alertDelete() {
    swal({
        title: "Deleted!",
        text: "Your imaginary file has been deleted.",
        icon: "success",
        button: false,
        timer: 1000,
    }).then(
        function (dismiss) {
            if (dismiss === 'timer') {
                //console.log('I was closed by the timer')
            }
        }
    )
}

function validateSelect(select,msg) {
    var a=$(select).val();
    if(a==null) {
        alert(msg);
        return false;
    }else{
        return true;
    }
    return true;
}

// Retrieve data function-------------------------
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
// Add to Major-------------------------------------------
$(function () {
    getData("/admin/major/findAll", "#tblMajor");
    $("#majorAdd").submit(function (e) {
        e.preventDefault();
        var data = {
            id: 0,
            name: $("#name").val(),
            subMajors: []
        }
        $.ajax({
            url: "/admin/addMajor",
            type: "POST",
            data: JSON.stringify(data),
            contentType: "application/json",
            success: function () {
                getData("/admin/major/findAll", "#tblMajor");
                $("#name").val("")
                getData("/admin/cbMajor/findAll", ".comboMajorId");
                alertSave();
            },
            error: function (data) {
                console.log(data);
            }
        })
    })
});

function updateMajor(element) {
    var id = $(element).parent().parent().children().eq(0).text();
    var major = $(element).parent().parent().children().eq(1).text();
    $("#txtUpdateMajor").val(major);
    $("#id").val(id);
}

// Update Major--------------------------------------------------
$(function () {
    $("#updateMajor").submit(function (e) {
        e.preventDefault();
        var data = {
            id: $("#id").val(),
            name: $("#txtUpdateMajor").val(),
            subMajors: []
        }
        console.log(data);
        $.ajax({
            url: "/admin/updateMajor",
            type: "POST",
            data: JSON.stringify(data),
            contentType: "application/json",
            success: function (data) {
                getData("/admin/major/findAll", "#tblMajor");
                getData("/admin/subMajor/findAll", "#tblSubMajor");
                getData("/admin/cbMajor/findAll", ".comboMajorId");
                $("#majorModal").modal('hide');
            },
            error: function (data) {
                console.log(data);
            }
        })
    })

});
// DELETE Major--------------------------------------------------
$(function () {
    $(document).on("click", "#btnMajorRemove", function () {
        var yes = confirm("Do you really want to delete?");
        if (yes == true) {
            $.ajax({
                type: "DELETE",
                url: $(this).attr('data-href'),
                success: function (data) {
                    getData("/admin/major/findAll", "#tblMajor");
                    getData("/admin/subMajor/findAll", "#tblSubMajor");
                    getData("/admin/cbMajor/findAll", ".comboMajorId");
                    alertDelete();
                },
                error: function (e) {
                    console.log(e);
                }
            });
        }
    })
})

// SUB-MAJOR
//Add subMajor---------------------------------------------------
$(function () {
    getData("/admin/subMajor/findAll", "#tblSubMajor");
    $("#subMajorAdd").submit(function (e) {
        e.preventDefault();
        if(validateSelect("#majorId","Please Choose Major")==true) {
            var data = {
                major_id: $("#majorId").val(),
                sub_major_name: $("#subMajorName").val()
            }
            console.log(data);
            $.ajax({
                url: "/admin/addSubMajor",
                type: "POST",
                data: JSON.stringify(data),
                contentType: "application/json",
                success: function () {
                    getData("/admin/subMajor/findAll", "#tblSubMajor");
                    $("#subMajorName").val("")
                    getData("/admin/cbSubMajor/findAll", ".comboSubMajor");
                    alertSave();
                },
                error: function (data) {
                    console.log(data);
                }
            })
        }else {

        }
    })
});

// Update Sub Major-----------------------------------------------------------
var id;
function updateSubMajor(element) {
    id = $(element).parent().parent().children().eq(0).text();
    var subMajor = $(element).parent().parent().children().eq(1).text();
    var major_name = $(element).parent().parent().children().eq(2).text();

    console.log(id)

    $("#txtUpdateSubMajor").val(subMajor);
    $("#major.id").val(major_name);

    $("select option").each(function () {
        if ($(this).text() == major_name) {
            $(this).attr("selected", "selected");
        } else {
            $(this).removeAttr("selected", "selected")
        }
    })
}

$(function () {
    $("#subMajorUpdate").submit(function (e) {
        console.log("clicked")
        e.preventDefault();

        var data = {
            id: id,
            major_id: $("#comboMajorId").val(),
            sub_major_name: $("#txtUpdateSubMajor").val()
        }

        console.log(data);
        $.ajax({
            url: "/admin/updateSubMajor",
            type: "POST",
            data: JSON.stringify(data),
            contentType: "application/json",
            success: function () {
                getData("/admin/subMajor/findAll", "#tblSubMajor");
                $("#subMajorModal").modal('hide');
                getData("/admin/subject/findAll", "#tblSubject");
                getData("/admin/cbSubMajor/findAll", ".comboSubMajor");
            },
            error: function (data) {
                console.log(data);
            }
        })
    })
});

// DELETE SubMajor-----------------------------------------------------
$(function () {
    $(document).on("click", "#btnRemoveSubMajor", function () {
        var yes = confirm("Do you really want to delete?");
        if (yes == true) {
            $.ajax({
                type: "DELETE",
                url: $(this).attr('data-href'),
                success: function () {
                    getData("/admin/subMajor/findAll", "#tblSubMajor");
                    getData("/admin/subject/findAll", "#tblSubject");
                    getData("/admin/cbSubMajor/findAll", ".comboSubMajor");
                    alertDelete();
                },
                error: function (e) {
                    console.log(e);
                }
            });
        }
    })

})

// Add Subject-------------------------------------------------------------
$(function () {
    getData("/admin/subject/findAll", "#tblSubject");
    $("#subjectAdd").submit(function (e) {
        e.preventDefault();
        if(validateSelect("#subMajorId","Please Choose Sub-Major")==true) {
            var data = {
                parent_id: $("#subMajorId").val(),
                sub_major_name: $("#subjectName").val()
            }
            $.ajax({
                url: "/admin/addSubject",
                type: "POST",
                data: JSON.stringify(data),
                contentType: "application/json",
                success: function () {
                    getData("/admin/subject/findAll", "#tblSubject");
                    $("#subjectName").val("")
                    alertSave();
                },
                error: function (data) {
                    console.log(data);
                }
            });
        }else {

        }
    })
});

// DELETE SUBJECT-------------------------------------------------
$(function () {
    $(document).on("click", "#btnRemoveSubject", function () {
        var yes = confirm("Do you really want to delete?");
        if (yes == true) {
            $.ajax({
                type: "DELETE",
                url: $(this).attr('data-href'),
                success: function () {
                    getData("/admin/subject/findAll", "#tblSubject");
                    alertDelete();
                },
                error: function (e) {
                    console.log(e);
                }
            });
        }
    })
})


// Update Subject-----------------------------------------------------------
var id;
function updateSubject(element) {
    id = $(element).parent().parent().children().eq(0).text();
    var subject = $(element).parent().parent().children().eq(1).text();
    var sub_major_name = $(element).parent().parent().children().eq(2).text();

    console.log(id)
    console.log(sub_major_name)

    $("#txtUpdateSubject").val(subject);
    $("#subMajor.id").val(sub_major_name);

    $("select option").each(function () {
        if ($(this).text() == sub_major_name) {
            $(this).attr("selected", "selected");
        } else {
            $(this).removeAttr("selected", "selected")
        }
    })
}

$(function () {
    $("#subjectUpdate").submit(function (e) {
        console.log("clicked")
        e.preventDefault();

        var data = {
            id: id,
            parent_id: $("#comboSubMajorId").val(),
            sub_major_name: $("#txtUpdateSubject").val()
        }

        console.log(data);
        $.ajax({
            url: "/admin/updateSubject",
            type: "POST",
            data: JSON.stringify(data),
            contentType: "application/json",
            success: function () {
                getData("/admin/subject/findAll", "#tblSubject");
                $("#subjectModal").modal('hide');
                console.log("update successfully")
            },
            error: function (data) {
                console.log(data);
            }
        })
    })
});

