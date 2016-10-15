
$(document).ready(function () {
    console.log("cat is started...")

})
function addAnnotationsContainer(){
    $("body").append("<div id='cat-annotations-container'/>;")
    var $catContainer=$("#cat-annotations-container");
    $catContainer.css({
        "position":"fixed",
        "width":"300px",
        "height":"100%",
        "right":"0",
        "top":"0",
        "background-color":"#cccccc"
    });

}


window.addEventListener("click", notifyExtension);

function notifyExtension(e) {

    chrome.runtime.sendMessage({"url": "asd"});
    console.log("sending message")
}

chrome.runtime.onMessage.addListener(handleMessage);

function handleMessage(message){
  console.log("handing message",message)
    if(message.action=="showAnnotations"){
        addAnnotationsContainer();
    }
}