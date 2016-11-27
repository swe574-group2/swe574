$(document).ready(function () {

    originalHtml=$("body").html();
    init();

})

var groups=[];


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

    $("body").append("<div id='cat-annotations-container'></div>");
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
        "<button id='clearHighlights' class='btn btn-sm btn-danger'>Clear Highlights</button></div>" +
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
    gorups=[];
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

    var mainDiv = document.body;
    // create the treewalker which will accept all textNodes
    var treeWalker = document.createTreeWalker(mainDiv,NodeFilter.SHOW_TEXT,null,false);

    var textNodeList = [];
    while(treeWalker.nextNode()) {
        if(treeWalker.currentNode.nodeValue.length>0)
        textNodeList.push(treeWalker.currentNode)
    };

    var index=0;
    var groupId=0;
    var tempNodes=[];
    $(textNodeList).each(function(i,e){
        tempNodes.push(e);
    })


    while (groups.length) { groups.pop(); }
    $(data).each(function(i,e){
        //text selection
        if(e.target.selector.type=="TextPositionSelector"){
            var added=false;
            if(index==0){
                groups.push({id:groupId,annotations:[e]});
                added=true;
            }
            else{
                var a=false;
                $(groups).each(function(i2,e2){
                    if(a==false) {
                        var ann = e2.annotations[0];
                        if (ann.target.selector.value == e.target.selector.value
                            && ann.target.selector.start == e.target.selector.start
                            && ann.target.selector.end == e.target.selector.end
                        ) {
                            a = true;
                            groups[i2].annotations.push(e);
                            added = true;
                            //return true;
                        }
                    }
                });

            }
            if(added ==false){
                groupId++;
                groups.push({id:groupId,annotations:[e]});
            }

            index++;
            // highlightText(e.target.selector,i) ;

        }
        //image
        else if(e.target.selector.type==="FragmentSelector"){
            highlightImage(e.target.source,e.target.selector,i);
        }

    });
    $container.html(html);
    console.log(groups);
    $(groups).each(function (i,e){
        var ann=e.annotations[0];
       // highlightText(e)
        var found=false;
        $(".highlighted").each(function(i3,e3){


            if($(e3).text().startsWith(ann.target.selector.value)) {
                found=true;
            }
        });
        if(found==false) {
            $(tempNodes).each(function (i2, e2) {
                try {
                    if (tempNodes[i2].nodeValue.substr(ann.target.selector.start, ann.target.selector.value.length) === ann.target.selector.value) {
                        var html = e2.nodeValue.replace(ann.target.selector.value, "<span data-index='" + i + "' style='border:1px solid red;' class='highlighted'>" + ann.target.selector.value + " <span class='badge'>" + e.annotations.length + "</span></span>")
                        $("body").html($("body").html().replace(ann.target.selector.value, html));
                    }
                }
                catch (e) {

                }
            });
        }
    });



    $("span.highlighted").on("click",function(){
        var index=parseInt($(this).data("index"));
        $(groups).each(function(i2,e2){

            if(i2==index){
                var html="<div class='row'>";
                $(e2.annotations).each(function(i,e) {
                    html += "<div id='cat-annotation-" + i + "'>"
                    html += "<div class='col-sm-12'>Type : TextSelection </div>";
                    html += "<div class='col-sm-12'>Selected Text : " + e.target.selector.value + " </div>";
                    html += "<div class='col-sm-12'>Annotation : " + e.body.value + " </div>";
                    html += "<div class='col-sm-12'>Purpose : " + e.body.purpose + " </div>";
                    html += "<div class='col-sm-12'><hr /></div></div>"
                });
                html+="</div>";
                var $container=$("#cat-container-body");
                $container.html(html);
            }
        });


    });


}

//** hightlighting **//
function highlightText(ann){
    var found=false;
    var selector=ann.annotations[0].target.selector;
    var k=ann.id;
    $(".highlighted").each(function(i,e){


       if($(e).text()==selector.value) {
          found=true;
       }
    });
    if(found==true) return;
    //$("body").html(originalHtml);
    $("body").html($("body").html().replace(selector.value,"<span data-index='"+k+"' style='border:1px solid red;' class='highlighted'>"+selector.value+ " <span class='badge'>" + ann.annotations.length + "</span></span>"))
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
    else if(e.target.id=="clearHighlights"){
        /*$(".highlighted").each(function (i,e) {
            var index=parseInt($(e).data("index"));
            $(e).replaceWith(groups[index].annotations[0].target.selector.value);
        });*/
        $("body").html(originalHtml);
        addAnnotationsContainer();
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
    console.log(groups);
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