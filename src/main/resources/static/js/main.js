
function validateJson() {
    var stringValue = document.getElementById('normalJsonString').value;

    $('#jsonResult').val('');

    $.ajax({
        type: "POST",
        url: "/json/validate",
        data: {
            "normalJsonString":stringValue
        },
        success:function (result) {
            $('#jsonResult').val(result.isValidJson);

            if(result.success) {
                $('#jsonResult').css("color","green").css("font-weight","bold");
            }
            else  {
                $('#jsonResult').css("color","red").css("font-weight","bold");
            }

        },
        error:function (result) {
            $('#jsonResult').html(result.isValidJson);
            $('#jsonResult').css("color","red");
        }
    });

}

function beautifyJson(){

    var stringValue = document.getElementById('normalJsonString').value;

    $('#jsonResult').val('');

    $.ajax({
        type:"POST",
        url:"/json/beautify",
        data:{
            "normalJsonString":stringValue
        },
        success:function (result) {

            if (result.success){
                $('#jsonResult').val(result.formattedJsonString).css("color","").css("font-weight","normal");
            }else{
                $('#jsonResult').val(result.formattedJsonString).css("color","red").css("font-weight","bold");
            }

        },
        error:function (result) {
            $('#jsonResult').html(result.formattedJsonString);
        }
    });
}


function minifyJson(){

    var stringValue = document.getElementById('normalJsonString').value;

    $('#jsonResult').val('');

    $.ajax({
        type:"POST",
        url:"/json/minify",
        data:{
            "normalJsonString":stringValue
        },
        success:function (result) {
            if (result.success){
                $('#jsonResult').val(result.formattedJsonString).css("color","").css("font-weight","normal");
            }else {
                $('#jsonResult').val(result.formattedJsonString).css("color","red").css("font-weight","bold");
            }
        },
        error:function (result) {
            $('#jsonResult').val(result.formattedJsonString).css("color","red").css("font-weight","bold");
        }
    })
}


function convertToXml(){

    var stringValue = document.getElementById('normalJsonString').value;

    $('#jsonResult').val('');

    $.ajax({
        type:"POST",
        url:"/json/convert/xml",
        data:{
            "normalJsonString":stringValue,
        },
        success:function (result) {

            if (result.success){
                $('#jsonResult').val(result.xmlData).css("color","").css("font-weight","normal");
            }else{
                $('#jsonResult').val(result.xmlData).css("color","red").css("font-weight","bold");
            }
        },
        error:function () {
            $('#jsonResult').val(result.xmlData).css("color","red").css("font-weight","bold");
        }
    });
}


function convertToCsv(){

    var stringValue = document.getElementById('normalJsonString').value;

    $('#jsonResult').val('');

    $.ajax({
        type:"POST",
        url:"/json/convert/csv",
        data:{
            "normalJsonString":stringValue,
        },
        success:function (result) {

            if (result.success){
                $('#jsonResult').val(result.csvData).css("color","").css("font-weight","normal");
            }else{
                $('#jsonResult').val(result.csvData).css("color","red").css("font-weight","bold");
            }
        },
        error:function () {
            $('#jsonResult').val(result.csvData).css("color","red").css("font-weight","bold");
        }
    });
}

function fireDownload(){
    var text = document.getElementById("jsonResult").value;
    var filename = "download.txt";
    if (typeof(Storage) !== "undefined") {
        sessionStorage.setItem("text", text);
        sessionStorage.setItem("filename", filename);
    }else{

    }
    window.location = "/download";
}

function validateUserCred() {

    var username =document.getElementById("username").value;
    var password =document.getElementById("password").value;

    if (username != null && password != null){
        console.log(""+username+" "+password);

        $.ajax({
            type:"POST",
            url:"/json/validateUser",
            data:{
                "username":username,
                "password":password,
            },
            success:function (result) {
                console.log(""+username+"2 "+password);
                if (result.success){
                    if (typeof(Storage) !== "undefined") {
                        var text =sessionStorage.getItem("text");
                        var filename =sessionStorage.getItem("filename");
                        download(filename,text);
                        window.location="/index";
                    }
                }else{
                    window.location="/download";
                }
            },
            error:function () {
                console.log(""+username+" 3"+password);
                $('#jsonResult').val(result.csvData).css("color","red").css("font-weight","bold");
            }
        });
    }
}

function download(filname,text) {

    console.log("dfff")
    var element = document.createElement('a');

    element.setAttribute('href','data:text/plain;charset=utf-8,' + encodeURIComponent(text));

    element.setAttribute('download',filname);

    element.style.display = 'none';

    document.body.appendChild(element);

    element.click();
    document.body.removeChild(element);
}

