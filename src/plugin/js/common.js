var common = (function () {
    return {

        annotationCreateUrl: "http://localhost:8080/annotation",
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
            html += "<div id='cat-text-annotation-panel' class='panel panel-primary'>";
            html += "<div class='panel-heading'> <h4 class='panel-title'>Add annotation</h4></div>";
            html += "<div class='panel-body'>"
            html += "<p> <b>Target:</b> &nbsp;" + getSelectionText() + "</p>"
            html += "<div class='form-group'><label class='control-label'>Body:</label> <textarea id='annotation-body' class='form-control'></textarea></div> ";
            html += "<div class='form-group'> <label class='control-label'>Motivation: </label>" + common.getMotivationComboHtml() + "</div>";
            html += "<div class='checkbox'><label><input id='annotation-type' type='checkbox' /> Private </div> ";

            html += "<div class='hidden'><input id='annotation-target-selector-start' type='hidden' value='" + selection.anchorOffset + "'/> ";
            html += "<input id='annotation-target-selector-end' type='hidden' value='" + selection.focusOffset + "'/></div>"


            html += "<div class='pull-right'><button id='btnSaveTextAnnotation' class='btn  btn-sm btn-success'>Save</button></div>";
            html += "</div>"; //panel-body
            html += "</div>"; //panel
            $(".panel-body", "#cat-annotations-container").html(html);

        },
        getTextAnnotation: function () {

            var data = {};
           // var $form = $("#cat-text-annotation-panel");
            data.target = {};
            data.type = "Annotation";
            data["@context"] = "http://wwww.w3.org/ns/anno.jsonld";
            data.target.source = window.location.href;
            data.target.selector = {};
            data.private= $("#annotation-type").val();
            data.target.selector.type = "TextPositionSelector";
            data.target.selector.start = $("#annotation-target-selector-start").val();
            data.target.selector.end = $("#annotation-target-selector-end").val();
            data.body = $("#annotation-body").val();
            console.log(JSON.stringify(data));
            return data;

        },

    };
})();
