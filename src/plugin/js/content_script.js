$(document).ready(function () {

    originalHtml=$("body").html();
    init();

})

var groups=[];
var loggedIn=false;
var username="";
var originalHtml="";
function init(){
    console.log("cat is started...")
    chrome.runtime.sendMessage({"action":"getUser"});

    $('img').imgAreaSelect({
        handles: true,
        onSelectEnd: function (e,selection) {
            if(loggedIn==false) {
                showAlert("Please login to add annotation");
                return;
            }

            $("#cat-annotations-container .panel-body").eq(1).append("<div id='cat-image-preview'>")
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
    $("#cat-image-preview").html("<img class='hide' src='"+img.src+"' />");


    var imgWidth=$(img).width();
    var imgHeight=$(img).height();

    $('#cat-annotations-container .panel-body #cat-image-preview > img').css({
        width: Math.round(scaleX * imgWidth) + 'px',
        height: Math.round(scaleY * imgHeight) + 'px',
        marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',
        marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'
    });
    $('#cat-annotations-container .panel-body #cat-image-preview > img').removeClass("hide");
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
        "<div id='cat-container-body' style='height: 300px; padding: 10px; overflow-y: auto'></div>"+
        "</div></div>")

    if(loggedIn==true){
        showAlert("Welcome "+username+"!  Note: To add annotation please clik clear highlights button.");
    }

}
function getHeaderHtml(){
    return "<div class='panel panel-default' ><div class='panel-heading'><h4 class='panel-title'>Cat Data Annotator</h4></div> <div class='panel-body'>" +
        "<div class='pull-right'><button id='closeAnnotator' class='btn btn-sm btn-danger'>Close</button></div>" +
        "</div></div>";
}

function removeAnnotationsContainer() {
    $("#cat-annotations-container").addClass("hide");
    isCatActive=false;
    $("body").html(originalHtml);
}
function addAnnotations(data){
    groups=[];
    console.log("annotations to show:",data);
    data=data.content;
    if(data==null || data==undefined) return;

    var $container=$("#cat-container-body");
    $("img","body").each(function (i,e) {
        var inst=$(e).imgAreaSelect({instance:true});
        if(inst!=undefined){
            //inst.addClass("hide");
        }
    });
    var html="<div class='row'>";
    $(data).each(function(i,e){
        //text selecton
       if(e.target.selector.type=="TextPositionSelector"){
           html+="<div id='cat-annotation-"+i+"'>"
           html+="<div class='col-sm-12'>\"<u style='color:#2aabd2'>"+e.body.value+"</u>\"<span style='font-size:9px'> by "+e.creator.name+","+new Date(e.created)+" </span></div>";
       }//image
       else if(e.target.selector.type==="FragmentSelector"){
           html+="<div id='cat-annotation-"+i+"'>"
           html+="<div class='col-sm-12'>\"<u style='color:#2aabd2'>"+e.body.value+"</u>\"<span style='font-size:9px'> by "+e.creator.name+","+new Date(e.created)+" </span></div>";

       }
        html+="<div class='col-sm-12'><span style='border:1px solid #2aabd2; border-radius: 7px; padding: 1px; color:#2aabd2;'>" + e.motivation + "</span></div>";
        html+="<div class='col-sm-12'><hr /></div></div>";
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
            var added2=false;
            if(index==0){
                groups.push({id:groupId,annotations:[e]});
                added2=true;
            }
            else{
                var a2=false;
                $(groups).each(function(i2,e2){
                    if(a2==false) {
                        var ann = e2.annotations[0];
                        var target1=ann.target.selector.value.split("#")[0];
                        var target2=e.target.selector.value.split("#")[0];
                        if (target1== target2
                        ) {
                            a2 = true;
                            groups[i2].annotations.push(e);
                            added2 = true;
                            //return true;
                        }
                    }
                });

            }
            if(added2 ==false){
                groupId++;
                groups.push({id:groupId,annotations:[e]});
            }

            index++;
            //highlightImage(e.target.source,e.target.selector,i);
        }

    });
    $container.html(html);
    console.log("groups",groups);
    $(groups).each(function (i,e){
        var ann=e.annotations[0];
       if(ann.target.selector.type=="TextPositionSelector") {
           var found = false;
           $(".highlighted").each(function (i3, e3) {
               if ($(e3).text().startsWith(ann.target.selector.value)) {
                   found = true;
               }
           });
           if (found == false) {
               var added = new Array();
               $(tempNodes).each(function (i2, e2) {
                   try {
                       if (e2.nodeValue.indexOf(ann.target.selector.value) !== -1 && added.indexOf(ann.target.selector.value) == -1) {
                           var nodeValue=e2.nodeValue.toString();
                           var html = "<span data-index='" + i + "' style='border:1px solid red;' class='highlighted'>" + ann.target.selector.value + " <span class='badge'>" + e.annotations.length + "</span></span>";
                           var el=$(":contains('"+ann.target.selector.value+"')","body").eq(0);
                           console.log("debug22");
                           console.log($(el).html());
                           $(el).html(el.html().replace(ann.target.selector.value,html));
                           added.push(ann.target.selector.value);
                       }
                   }
                   catch (ex) {
                       console.log("debug3");
                        console.log("ex:",ex)
                   }
               });
           }
           found=false;
       }
       else if(ann.target.selector.type=="FragmentSelector"){
           highlightImage(ann.target.selector.value.split("#")[0],ann.target.selector,i);
       }
    });

    $(".imgareaselect-border4").click(function (e) {
        showSelectedAnnotationGroup($(this));
    });


    $("span.highlighted").on("click",function(){

        showSelectedAnnotationGroup(this);

    });


}

function showSelectedAnnotationGroup(elm) {
    var index=parseInt($(elm).data("index"));
    console.log("aslkdj",elm);
    $(groups).each(function(i2,e2){

        if(i2==index){
            var html="<div class='row'>";
            $(e2.annotations).each(function(i,e) {
                if(e.target.selector.type=="TextPositionSelector"){
                    html+="<div id='cat-annotation-"+i+"'>"
                    html+="<div class='col-sm-12'>\"<u style='color:#2aabd2'>"+e.body.value+"</u>\"<span style='font-size:9px'> by "+e.creator.name+","+new Date(e.created)+" </span></div>";
                    html+="<div class='col-sm-12'><hr /></div></div>"

                }
                //image
                else if(e.target.selector.type==="FragmentSelector"){
                    html+="<div id='cat-annotation-"+i+"'>"
                    html+="<div class='col-sm-12'>\"<u style='color:#2aabd2'>"+e.body.value+"</u>\"<span style='font-size:9px'> by "+e.creator.name+","+new Date(e.created)+" </span></div>";
                    html+="<div class='col-sm-12'><a class='annotation-highlight' data-groupId='"+index+"' data-id='"+i+"'>  Highlight</a> </div>";
                    html+="<div class='col-sm-12'><hr /></div></div>"
                }
            });
            html+="</div>";
            var $container=$("#cat-container-body");
            $container.html(html);
        }
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
function highlightImage(src,selector,index){
    console.log("highlighting image:",src,selector);
   $("img","body").each(function (i,e) {
        if(src.endsWith($(e).attr("src")))  {
            var slct=$(e).imgAreaSelect( {instance:true});
            slct.cancelSelection();

            var arr= selector.value.split("#")[1].replace("xywh=","").split(",");

            var options={movable:false,resizable:false,handles:false,x1:parseInt(arr[0]),  y1:parseInt(arr[1]),
                x2: parseInt(arr[0])+parseInt(arr[2]),  y2:parseInt(arr[1])+parseInt(arr[3]),
                index:index
            };

            slct.setOptions(options);
            slct.update(false);


        }
    });
    //init();

}

/** click event **/

window.addEventListener("click", notifyExtension);

function notifyExtension(e) {
    console.log(e.target);
    if (e.target.id == "closeAnnotator") {
        console.log("closing annotator")
        removeAnnotationsContainer();
        $("img","body").each(function (i,e) {
            $(e).imgAreaSelect({remove:true});
        });
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
        $("img","body").each(function (i,e) {
            $(e).imgAreaSelect({remove:true});
        });
        $("body").html(originalHtml);

        addAnnotationsContainer();
        init();
        return;
    }

    else if(e.target.id=='btnSaveTextAnnotation'){
        console.log("saving text-annotation");
        if(loggedIn==false){
            showAlert("Please login to add annotation");
            username="";
            return;
        }
        chrome.runtime.sendMessage({"action":"saveTextAnnotation",data:common.getTextAnnotation()});
    }
    else if(e.target.id=='btnSaveImageAnnotation'){
        console.log("saving image annotation");
        if(loggedIn==false){
            showAlert("Please login to add annotation");
            username="";
            return;
        }
        chrome.runtime.sendMessage({"action":"saveImageAnnotation",data:common.getImageAnnotation()});
    }
    else if($(e.target).hasClass("annotation-highlight") ){
        var groupId=$(e.target).data("groupid");
        var index=$(e.target).data("id");
        var ann=groups[groupId].annotations[index];
        highlightImage(ann.target.selector.value.split("#")[0],ann.target.selector,groupId);

    }
}

/** end of click event **/

//** text select event **/

window.addEventListener("mouseup", selectHandler);

function selectHandler(e) {
    if(loggedIn==false) {
        showAlert("Please login to add annotation");
        return;
    }
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
function showAlert(message){
    $("#cat-info").html(message);
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

function showSearchResults(data,search){
    addAnnotationsContainer();
    var html="<div class='row'>";
    html+="<p style='margin-left: 15px'>Search results for <i style='color:#d43f3a'>'"+search+"'</i></p>";
    $(data).each(function(i,e){
        //text selecton
        if(e.target.selector.type=="TextPositionSelector"){
            html+="<div id='cat-annotation-"+i+"'>"
            html+="<div class='col-sm-12'>\"<u style='color:#2aabd2'>"+e.body.value+"</u>\"<span style='font-size:9px'> by "+e.creator.name+","+new Date(e.created)+" </span></div>";
            html+="<div class='col-sm-12'><hr /></div></div>"

        }
        //image
        else if(e.target.selector.type==="FragmentSelector"){
            html+="<div id='cat-annotation-"+i+"'>"
            html+="<div class='col-sm-12'>\"<u style='color:#2aabd2'>"+e.body.value+"</u>\"<span style='font-size:9px'> by "+e.creator.name+","+new Date(e.created)+" </span></div>";
            html+="<div class='col-sm-12'><hr /></div></div>"
        }

    });
    html+="</div>";
    $("#cat-container-body").html(html);

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
    }else if(message.action=="userLoggedin"){
        if(message.data.loggedIn==true) {
            loggedIn = true;
            showAlert("Welcome " + message.data.username + "! Note: To add annotation please clik clear highlights button.")
            username=message.data.username;
        }else{
            showAlert("Please login to add annotation");
            username="";
            loggedIn=false;
        }

    }
    else if(message.action=="showSearchResults"){
        showSearchResults(message.data.items,message.data.search);
    }
//ŞK01 B
    else if(message.action=="login"){
        console.log("content_script handlemessage: login");
        //
    }
//ŞK01 E
}