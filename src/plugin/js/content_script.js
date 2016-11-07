$(document).ready(function () {
    console.log("cat is started...")
    $('img').imgAreaSelect({
        handles: true,
        onSelectEnd: function (e,selection) {
            $("#cat-annotations-container .panel-body").append("<div id='cat-image-preview'>")
        },
        onSelectChange: preview
    });

})

var isCatActive=false;

function preview(img, selection) {
    var scaleX = 100 / (selection.width || 1);
    var scaleY = 100 / (selection.height || 1);
if($("#cat-annotations-container .panel-body #cat-image-preview").length==0) {
    $("#cat-annotations-container .panel-body").append("<div id='cat-image-preview' style='float: left; overflow-y:auto;   position: relative; overflow: hidden; width: 100px; height: 100px;' />")
}
    $("#cat-image-preview").html("<img src='"+img.src+"' />");


var imgWidth=$(img).width();
    var imgHeight=$(img).height();

    $('#cat-annotations-container .panel-body #cat-image-preview > img').css({
        width: Math.round(scaleX * imgWidth) + 'px',
        height: Math.round(scaleY * imgHeight) + 'px',
        marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',
        marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'
    });
}

function addAnnotationsContainer() {
    isCatActive=true;
    if ($("#cat-annotations-container").length > 0) {
        $("#cat-annotations-container").removeClass("hide");
        return;
    }
    $("body").append("<div id='cat-annotations-container'/>")
    var $catContainer = $("#cat-annotations-container");
    $catContainer.css({
        "position": "fixed",
        "width": "300px",
        "height": "100%",
        "right": "0",
        "top": "0",
        "z-index": "100000000000",
        "background-color": "rgb(255, 255, 255)",
        "border-left": "3px solid #000"
    });
    $catContainer.html("<div class='panel panel-default' ><div class='panel-heading'><h4 class='panel-title'>Cat Data Annotator</h4></div> <div class='panel-body'>" +
        "<div class='pull-right'><button id='closeAnnotator' class='btn btn-sm btn-danger'>Close</button></div>" +
        " <div id='cat-info' style='font-size:12px;width:200px' class='alert alert-warning'>W3 Web Annotator</div><div class='clearfix'></div><hr/>" +
        "<div id='cat-container-body' style='height: 300px; overflow-y: auto'></div>"+
        "</div></div>")
}
function getHeaderHtml(){
    return "<div class='panel panel-default' ><div class='panel-heading'><h4 class='panel-title'>Cat Data Annotator</h4></div> <div class='panel-body'>" +
        "<div class='pull-right'><button id='closeAnnotator' class='btn btn-sm btn-danger'>Close</button></div>" +
        "</div></div>";
}

function removeAnnotationsContainer() {
    $("#cat-annotations-container").addClass("hide");
    isCatActive=false;
}
function addAnnotations(data){
    if(data==null || data==undefined) return;
    var $container=$("#cat-container-body");
    var html="<div class='row'>";
    $(data).each(function(i,e){
        //text selecton
       if(e.target.selector.type=="TextPositionSelector"){
           html+="<div class='col-sm-12'>Type : TextSelection </div>";
           html+="<div class='col-sm-12'>Selected Text : "+e.target.selector.value+" </div>";
           html+="<div class='col-sm-12'>Annotation : "+e.body[0].items[0].value+" </div>";
           html+="<div class='col-sm-12'>Purpose : "+e.body[0].items[0].purpose+" </div>";
           html+="<div class='col-sm-12'><hr /></div>"
       }
        //image
    });
    html+="</div>"; 
    $container.html(html);

}
/** click event **/

window.addEventListener("click", notifyExtension);

function notifyExtension(e) {
    if (e.target.id == "closeAnnotator") {
        console.log("closing annotator")
        removeAnnotationsContainer();
        return;
    }
    else if (e.target.id == "btnCloseTextAnnotation") {
        console.log("closing annotatation form")
        $("#cat-container-body").html('');
        return;
    }
    else if(e.target.id=='btnSaveTextAnnotation'){
        console.log("saving text-annotation");
        chrome.runtime.sendMessage({"action":"saveTextAnnotation",data:common.getTextAnnotation()});
    }

}

/** end of click event **/

//** text select event **/

window.addEventListener("mouseup", selectHandler);

function selectHandler(e) {
    console.log($(e.target).parents("#cat-annotations-container").length)
    if(isCatActive==false && $(e.target).parents("#cat-annotations-container").length===1) return;
    var slct = window.getSelection();

    if (slct.anchorOffset != slct.focusOffset) {
        console.log("text selected: ", getSelectionText())
        if($(e.target).parents("#cat-annotations-container").length===0)
         common.textAnnotationForm(slct);
    }
}

function getSelectionText() {
    var text = "";
    if (window.getSelection) {
        text = window.getSelection().toString();
    } else if (document.selection && document.selection.type != "Control") {
        text = document.selection.createRange().text;
    }
    return text;
}



//** end text select event **/

chrome.runtime.onMessage.addListener(handleMessage);

function handleMessage(message) {
    console.log("handing message", message)
    if (message.action == "showAnnotations") {
        addAnnotationsContainer();
        if(message.data){
            addAnnotations(message.data);
        }

    } else if (message.action == "hideAnnotations") {
        removeAnnotationsContainer();
    }else if(message.action=="addTextSelectionForm"){
        common.textAnnotationForm(message.selection);
    }
}