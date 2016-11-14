$(document).ready(function () {

    originalHtml=$("body").html();
    init();

})
var originalHtml="";
function init(){
    console.log("cat is started...")
    $('img').imgAreaSelect({
        handles: true,
        onSelectEnd: function (e,selection) {
            $("#cat-annotations-container .panel-body").append("<div id='cat-image-preview'>")
        },
        onSelectChange: preview
    });
}

var isCatActive=false;

function preview(img, selection) {
    common.imageAnnotationForm(img,selection);

    var scaleX = 100 / (selection.width || 1);
    var scaleY = 100 / (selection.height || 1);
/*if($("#cat-annotations-container .panel-body #cat-image-preview").length==0) {
    $("#cat-annotations-container .panel-body").append("<div id='cat-image-preview' style='float: left; overflow-y:auto;   position: relative; overflow: hidden; width: 100px; height: 100px;' />")
}*/
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
function hightlightAnnotation (i){
    var selector="#cat-annotation-"+i;
    $(selector).css({"border":"1px solid red"});
}
function unhightlightAnnotation (i){
    var selector="#cat-annotation-"+i;
    $(selector).css({"border":"none"});
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
           html+="<div id='cat-annotation-"+i+"'>"
           html+="<div class='col-sm-12'>Type : TextSelection </div>";
           html+="<div class='col-sm-12'>Selected Text : "+e.target.selector.value+" </div>";
           html+="<div class='col-sm-12'>Annotation : "+e.body.value+" </div>";
           html+="<div class='col-sm-12'>Purpose : "+e.body.purpose+" </div>";
           html+="<div class='col-sm-12'><hr /></div></div>"

       }
       //image
       else if(e.target.selector.type==="FragmentSelector"){
           html+="<div id='cat-annotation-"+i+"'>"
           html+="<div class='col-sm-12'>Type : FragmentSelector </div>";
           html+="<div class='col-sm-12'>Selected Image : "+e.target.source+" </div>";
           html+="<div class='col-sm-12'>Annotation : "+e.body.value+" </div>";
           html+="<div class='col-sm-12'>Purpose : "+e.body.purpose+" </div>";
           html+="<div class='col-sm-12'><hr /></div></div>"
       }

    });
    html+="</div>"; 
    $container.html(html);
    $(data).each(function(i,e){
        //text selection
        if(e.target.selector.type=="TextPositionSelector"){

            highlightText(e.target.selector,i);
        }
        //image
        else if(e.target.selector.type==="FragmentSelector"){
            highlightImage(e.target.source,e.target.selector,i);
        }

    });
    $("span.highlighted").on("mouseenter",function(){
        var i=$(this).data("index");
        var selector="#cat-annotation-"+i;
        $(selector).css({"color":"red"});
    });
    $("span.highlighted").on("mouseleave",function(){
        var i=$(this).data("index");
        var selector="#cat-annotation-"+i;
        $(selector).css({"color":"inherit"});
    });

}

//** hightlighting **//
function highlightText(selector,i){
    var found=false;
    $(".highlighted").each(function(i,e){
       if($(e).text()==selector.value) {
          found=true;
       }
    });
    if(found==true) return;
    console.log("highlighting text:",selector);
    //$("body").html(originalHtml);
    $("body").html($("body").html().replace(selector.value,"<span data-index='"+i+"' style='border:1px solid red;' class='highlighted'>"+selector.value+"</span>"))
    init();

}
function highlightImage(src,selector,i){
    console.log("highlighting image:",src,selector);
   $("img","body").each(function (i,e) {
        console.log($(e).attr("src"));
        if(src.endsWith($(e).attr("src")))  {
            var arr= selector.value.replace("xywh=","").split(",");
            console.log(arr);
            $(e).imgAreaSelect( {x1:parseInt(arr[0]),  y1:parseInt(arr[1]), x2: parseInt(arr[0])+parseInt(arr[2]),  y2:parseInt(arr[1])+parseInt(arr[3])});
        }
    });
    //init();

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
    else if(e.target.id=='btnSaveImageAnnotation'){
        console.log("saving image annotation");
        chrome.runtime.sendMessage({"action":"saveImageAnnotation",data:common.getImageAnnotation()});
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
    console.log("handing message 111  ", message)
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
//ŞK01 B
    else if(message.action=="login"){
        console.log("content_script handlemessage: login");
        //
    }
//ŞK01 E
}