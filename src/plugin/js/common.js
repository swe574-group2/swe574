var common = (function () {
    
    
    return {
        getpluginHeaderHtml:function(){
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
                contentType: "application/json"
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
        getTextAnnotation: function () {

            var data = {};
           // var $form = $("#cat-text-annotation-panel");
            data.target = {};
            data.type = "Annotation";
            data.id = window.location.href
            data["@context"] = "http://wwww.w3.org/ns/anno.jsonld";
            data.target.source =  window.location.href
            data.target.selector = {};
            data.private= $("#annotation-type").val();
            data.target.selector.type = "TextPositionSelector";
            data.target.selector.start = $("#annotation-target-selector-start").val();
            data.target.selector.end = $("#annotation-target-selector-end").val();
            data.motivation = $("#annotation-motivation").find('option:selected').val();
            data.target.selector.value=$("#annotation-target-selector-value").val();

            data.creator = {};
            data.creator.name = $("#annotation-creator-name").val();
            data.creator.nickname = $("#annotation-creator-nickName").val();

            data.generator = {};
            data.generator.name = "CatPlugin";
            data.generator.homePage = "www.catplugin.net";

            data.body = {};
            data.body.value = $("#annotation-body").val();
            data.body.purpose = $("#annotation-motivation").find('option:selected').val();
            data.body.annotationSource = "catplugin";


            console.log(JSON.stringify(data));
            return data;

        },

    };
})();
