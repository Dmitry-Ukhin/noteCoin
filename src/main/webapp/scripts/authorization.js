function startAuth() {

    let REQUEST = "/auth";
    let HTTP_METHOD = "GET";
    const ASYN = false;
    let xhr = new XMLHttpRequest();

    if (xhr !== null) {
        xhr.open(HTTP_METHOD, REQUEST, ASYN);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        if (xhr.readyState === 1) {
            xhr.send();
        } else {
            console.error("readyState != 1");
        }
    }
    let responseText = xhr.responseText;

    console.log("Resp: " + responseText);

    let elementAuth = document.getElementById("div-authorization");
    if (null !== elementAuth) {
        elementAuth.innerHTML = responseText;
    } else {
        console.log("couldn't found element");
    }
}