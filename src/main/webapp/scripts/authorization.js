function logIn() {
    var request = "/user";
    var http_method = "POST";
    var isAsynchr = true;

    var elem = document.getElementById("userName");
    if (null != elem){
        alert("not null");
    }else{
        alert("elem null");
    }

    elem = document.getElementById("userName");
    var name = elem.value;
    document.getElementById("userName").value = "";
    elem = document.getElementById("userPass");
    var pass = elem.value;
    document.getElementById("userPass").value = "";

    var data = "name=" + name + "&key=" + pass;
    console.log(data);

    var xhr = new XMLHttpRequest();
    xhr.open(http_method, request, isAsynchr);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    if (xhr.readyState === 1) {
        xhr.send(data);
    }else{
        alert("readyState != 1");
    }

    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            if (xhr.responseText.indexOf("200") + 1){
                document.querySelector("#div-authorization").remove();
                document.querySelector("#globalShadow").remove();
            }else{
                var elem = document.getElementById("userName");
                elem.style.borderBottom = "1px solid #c80000";
                elem = document.getElementById("userPass");
                elem.style.borderBottom = "1px solid #c80000";
                alert("wrong name or password");
            }
        }
    };
}

function signUp() {
    var request = "/sign_up";
    var http_method = "POST";
    var isAsynchr = true;

    var elem;

    elem = document.getElementById("userName");
    var name = elem.value;
    document.getElementById("userName").value = "";
    elem = document.getElementById("userPass");
    var pass = elem.value;
    document.getElementById("userPass").value = "";

    var data = "name=" + name + "&key=" + pass;
    console.log(data);

    var xhr = new XMLHttpRequest();
    xhr.open(http_method, request, isAsynchr);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    if (xhr.readyState === 1) {
        xhr.send(data);
    }else{
        alert("readyState != 1");
    }

    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            if (xhr.responseText.indexOf("success") + 1){
                document.querySelector("#div-authorization").remove();
                document.querySelector("#globalShadow").remove();
            }else{
                alert("something went wrong");
            }
        }
    };
}