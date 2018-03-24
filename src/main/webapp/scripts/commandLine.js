function sendCommand(command) {
    var xhr = new XMLHttpRequest();
    var request = "/command";
    var http_method = "POST";
    var isAsynchr = true;
    var data;

    if (command !== null) {
        var form = document.getElementById("command");
        data = "command=" + form.value;
    }else{
        data = "command=" + command;
    }

    if (document.getElementById("typeOfTran") !== null){
        var formOfType = document.getElementById("typeOfTran");
        for (var i = 0; i < formOfType.length; i++){
            if (formOfType[i].type === "radio" && formOfType[i].checked){
                var type = formOfType[i].value;
            }
        }
        data += "&type=" + type;
    }

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
            if (this.responseText.indexOf("1") + 1){
                askType(document.getElementById("command"));
            }else{
                document.getElementById("status-command").innerHTML = this.responseText;
            }
        }
    };
}

function askType(command) {
    var div = document.getElementById("questionAboutTypeOfCommand");
    var text = document.createElement("p");
    text.innerHTML = "What is transaction:";
    var form = document.createElement("form");
    form.innerHTML = "<style>.questionAboutTypeOfCommand{\n" +
        "                position: absolute;\n" +
        "                top: 50%;\n" +
        "                right: 50%;\n" +
        "                background: cornflowerblue;\n" +
        "                border: 4px black;\n" +
        "                padding: 10px;\n" +
        "            }</style>" +
        "<input type='radio' value='income' id='income_com'/>Income<Br>" +
        "<input type='radio' value='expense' id='expense_com'/>Expense<Br>" +
        "<input type='button' value='Ok' id='buttonOfFormType'>";

    form.id = "typeOfTran";
    div.appendChild(text);
    div.appendChild(form);
    document.getElementById("buttonOfFormType").addEventListener("click", function () {
        sendCommand(command);
    }, false);
}