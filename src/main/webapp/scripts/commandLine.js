function sendCommand() {
    var xhr = new XMLHttpRequest();
    var request = "/command";
    var http_method = "POST";
    var isAsynchr = true;
    var data;

    var form = document.getElementById("command");
    data = "command=" + form.value;

    /*
    Send request
     */
    xhr.open(http_method, request, isAsynchr);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    if (xhr.readyState === 1) {
        xhr.send(data);
    }else{
        alert("readyState != 1");
    }

    /*
    Get response and change DOM
     */
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("p1").innerHTML = this.responseText;
        }
    };
}