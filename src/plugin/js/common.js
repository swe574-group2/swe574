var common = (function () {


    return {
        getpluginHeaderHtml: function () {
            return "<div class='pull-right'><button id='closeAnnotator' class='btn btn-sm btn-danger'>Close</button></div></div class='clearfix'></div><hr/>"
        },
        catContainerSelector: "#cat-annotations-container",
        getMotivationComboHtml: function () {
            var html = "<select  id='annotation-motivation'>";
            html += "<option value='assessing'>Assesing</option>";
            html += "<option value='bookmarking'>Book Marking</option>";
            html += "<option value='classifying'>Classifying</option>";
            html += "<option value='commenting'>Commenting</option>";
            html += "<option value='describing'>Describing</option>";
            html += "<option value='editing'>Editing</option>";
            html += "<option value='highligthing'>Highligthing</option>";
            html += "<option value='identifying'>Identifying</option>";
            html += "<option value='linking'>Linking</option>";
            html += "<option value='moderating'>Moderating</option>";
            html += "<option value='questioning'>Questioning</option>";
            html += "<option value='replying'>Replying</option>";
            html += "<option value='tagging'>Tagging</option>";
            html += "</select>"

            return html;
        },


        post: function (url, data, success) {
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                crossDomain: true,
                success: function (json) {
                    if (success)
                        success(json);
                },
                error: function (responseData, textStatus, errorThrown) {
                    console.log(responseData, textStatus, errorThrown)
                },
                dataType: "json",
                //contentType: "application/json"
            });
        },
        textAnnotationForm: function (selection) {

            var html = "";
            console.log("text selection form", selection, getSelectionText())
            html += "<div id='cat-text-annotation-panel' style='overflow: auto' class='panel panel-primary'>";
            html += "<div class='panel-heading'> <h4 class='panel-title'>Add annotation</h4></div>";
            html += "<div class='panel-body'>"
            html += "<p> <b>Target:</b> &nbsp;" + getSelectionText() + "</p>"
            html += "<div class='form-group'><label class='control-label'>Body:</label> <textarea id='annotation-body' class='form-control'></textarea></div> ";
            html += "<div class='form-group'> <label class='control-label'>Motivation: </label>" + common.getMotivationComboHtml() + "</div>";
            html += "<div class='checkbox'><label><input id='annotation-type' type='checkbox' /> Private </div> ";

            html += "<div class='hidden'><input id='annotation-target-selector-start' type='hidden' value='" + selection.anchorOffset + "'/> ";
            html += "<input id='annotation-target-selector-end' type='hidden' value='" + selection.focusOffset + "'/>"
            html += "<input id='annotation-target-selector-value' type='hidden' value='" + getSelectionText() + "'/>"
            html += "<input id='annotation-creator-name' type='hidden' value='admin'>";
            html += "<input id='annotation-creator-nickName' type='hidden' value='admin'> </div>";


            html += "<div class='pull-right'><button id='btnCloseTextAnnotation' class='btn  btn-sm btn-primary'>Close</button> &nbsp;<button id='btnSaveTextAnnotation' class='btn  btn-sm btn-success'>Save</button></div>";
            html += "</div>"; //panel-body
            html += "</div>"; //panel
            $("#cat-container-body").html(html);

        },

        imageAnnotationForm: function (img,selection){
            var html="";
            html += "<div id='cat-image-annotation-panel' style='overflow: auto' class='panel panel-primary'>";
            html += "<div class='panel-heading'> <h4 class='panel-title'>Add annotation</h4></div>";
            html += "<div class='panel-body'>"
            html+=  "<div id='cat-image-preview' style='float: left; overflow-y:auto;   position: relative; overflow: hidden; width: 100px; height: 100px;'> </div><div class='clearfix'/>";
            html += "<p> <b>Target:</b> &nbsp;" + img.src + "["+selection.x1+","+selection.y1+","+selection.width+","+selection.height+"]</p>"
            html += "<div class='form-group'><label class='control-label'>Body:</label> <textarea id='annotation-body' class='form-control'></textarea></div> ";
            html += "<div class='form-group'> <label class='control-label'>Motivation: </label>" + common.getMotivationComboHtml() + "</div>";
            html += "<div class='checkbox'><label><input id='annotation-type' type='checkbox' /> Private </div> ";

            html += "<input id='annotation-target-selector-value' type='hidden' value='xywh="  +selection.x1+","+selection.y1+","+selection.width+","+selection.height + "'/>"
            html += "<input id='annotation-creator-name' type='hidden' value='admin'>";
            html += "<input id='annotation-creator-nickName' type='hidden' value='admin'> </div>";
            html+= "<input id='annotation-source-format' type='hidden' value='"+common.getImageMimeType(img.src)+"'/>"
            html+= "<input id='annotation-source' type='hidden' value='"+img.src+"'/>"

            html += "<div class='pull-right'><button id='btnCloseTextAnnotation' class='btn  btn-sm btn-primary'>Close</button> &nbsp;<button id='btnSaveImageAnnotation' class='btn  btn-sm btn-success'>Save</button></div>";
            html += "</div>"; //panel-body
            html += "</div>"; //panel
            $("#cat-container-body").html(html);

        },

        getTextAnnotation: function () {


            // var $form = $("#cat-text-annotation-panel");
            var data = {
                "@context": "http://www.w3.org/ns/anno.jsonld",
                "type": "Annotation",
                "id": window.location.href,
                "private": $("#annotation-type").prop("checked"),
                "motivation": $("#annotation-motivation").find('option:selected').val(),
                "target": {
                    "source": window.location.href,
                    "selector": {
                        "type": "TextPositionSelector",
                        "start": $("#annotation-target-selector-start").val(),
                        "end": $("#annotation-target-selector-end").val(),
                        "value": $("#annotation-target-selector-value").val()
                    }
                },
                "body": {
                        "value": $("#annotation-body").val(),
                        "purpose": $("#annotation-motivation").find('option:selected').val(),
                        "source":{
                            "format":"text",
                            "language":"en",
                            "creator": {
                                "name": $("#annotation-creator-name").val(),
                                "nickname": $("#annotation-creator-nickName").val()
                            },
                        }
                    },

                "generator": {
                    "name": "CatPlugin",
                    "homePage": "www.catplugin.net"
                }
            };

            console.log(JSON.stringify(data));
            return data;

        },
        getImageAnnotation:function () {

                // var $form = $("#cat-text-annotation-panel");
                var data = {
                    "@context": "http://www.w3.org/ns/anno.jsonld",
                    "type": "Annotation",
                    "id": window.location.href,
                    "private": $("#annotation-type").prop("checked"),
                    "motivation": $("#annotation-motivation").find('option:selected').val(),
                    "target": {
                        "source":  $("#annotation-source").val(),
                        "selector": {
                            "type": "FragmentSelector",
                            "conformsTo":"http://www.w3.org/TR/media-frags/",
                            "value": $("#annotation-target-selector-value").val()
                        }
                    },
                    "body": {
                            "value": $("#annotation-body").val(),
                            "purpose": $("#annotation-motivation").find('option:selected').val(),
                            "source":{
                                "format":"text",
                                "language":"en",
                                "creator": {
                                    "name": $("#annotation-creator-name").val(),
                                    "nickname": $("#annotation-creator-nickName").val()
                                }
                            }
                    },

                    "generator": {
                        "name": "CatPlugin",
                        "homePage": "www.catplugin.net"
                    }
                };

                console.log(JSON.stringify(data));
                return data;

        },
        getImageMimeType:function(src){
            if(src.endsWith("jpg")){
                return "image/jpeg";
            }else if(src.endsWith("png")){
                return "image/png";
            }
            else if(src.endsWith("gif")){
                return "image/gif"
            } else if(src.endsWith("bmp")){
                return "image/bmp"
            }else{
                return "";
            }

        }

    };
})();
