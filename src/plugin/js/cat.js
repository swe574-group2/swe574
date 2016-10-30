var cat = (function () {
    return {
        getAnnotationCount: function () {
            chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                $("#info").html("Retriving count...");
                cat.post("http://localhost:8080/annotation/count", {context: tabs[0].url}, function (json) {
                    $("#info").addClass("hide");
                    $("#btnShowAnnotations").text("Show Annotations (" + json + ")");
                    $("#btnShowAnnotations").removeClass("hide");

                })
            });

        },
        showAnnotations: function () {
            chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {

                chrome.tabs.sendMessage(tabs[0].id, {
                    "sender": "cat",
                    "action": "showAnnotations"
                }, function (response) {
                });
            });
        },
        addTextSelectionForm: function (selection) {
            chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                chrome.tabs.sendMessage(tabs[0].id, {
                    "sender": "cat",
                    "action": "addTextSelectionForm",
                    "data": selection
                }, function (response) {
                });
            });
        },
        saveAnnotation: function (annotation) {
            console.log(annotation);
            cat.post("http://localhost:8080/annotation", annotation, function (json) {

            })

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
        }
    }
})();
chrome.runtime.onMessage.addListener(handleMessage);

function handleMessage(message) {
    console.log("cat recieved message", message)
    /*chrome.tabs.getSelected(null, function(tab) {
     chrome.tabs.sendMessage(tab[0].id, {message: message});
     });*/
    if (message.action == "textSelected") {
        cat.addTextSelectionForm(message.selection);
    } else if (message.action == "saveTextAnnotation") {
        console.log(message.data);
        cat.saveAnnotation(message.data);

    }


}