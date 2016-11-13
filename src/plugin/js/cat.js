var cat = (function () {
    var serverUrl="http://localhost:1111";
    var authServerUrl="http://localhost:8080";
    return {

        getAnnotationCount: function () {
            chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                $("#info").html("Retriving count...");
                console.log("cat.js: Retrieving Count");
                cat.post("/annotation/countById", {"id": tabs[0].url}, function (json) {
                    $("#info").addClass("hide");
                    $("#btnShowAnnotations").text("Show Annotations (" + json + ")");
                    $("#btnShowAnnotations").removeClass("hide");

                })
            });

        },
        showAnnotations: function () {

                chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                    console.log("current tab url:"+tabs[0].url);
                    cat.post("/annotation/listById", {"id": tabs[0].url}, function (json) {
                        chrome.tabs.sendMessage(tabs[0].id, {
                            "sender": "cat",
                            "action": "showAnnotations",
                            "data":json
                        }, function (response) {
                        });
                    });

                    });

        },
//ŞK01 B
        login: function () {

            chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                console.log("cat.js Login");
                console.log("current tab url:"+tabs[0].url);
                user = {};
                user.nickname = $("#username").val();
                user.password = $("#password").val();
                alert("nickname " + user.nickname +  " password " + user.password) ;

/*
                cat.post("/user/authenticate", {"user": user}, function (json) {
                    chrome.tabs.sendMessage(tabs[0].id, {
                        "sender": "cat",
                        "action": "login",
                        "data":json
                    }, function (response) {
                    });
                });
*/
            });


        },
//ŞK01 E
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
            cat.post("/annotation", annotation, function (json) {
                //get annotations
                console.log("show annotations after save");
                cat.showAnnotations();
            })

        },
        post: function (url, data, success,isAuthRequest) {
            var server=serverUrl;
            if(isAuthRequest && isAuthRequest===true){
                server=authServerUrl;
            }

           // console.log(JSON.stringify(data));
            $.ajax({
                type: "POST",
                url: server+url,
                data: JSON.stringify(data),
                crossDomain: true,
                cache:false,
                success: function (json) {
                    console.log("success",json)
                    if (success)
                        success(json);
                },
                error: function (json) {
                   console.log(json)
                    //$("#cat-info").removeClass("hide").html(JSON.parse(json.responseText).message);

                },
                complete:function (json) {
                    console.log("post completed",json)
                },
                //dataType: "json",
                contentType: "application/json; charset=utf-8"
            });
        },
        get: function (url,data,success){
            $("#info").html($.stringify(data));
            $.ajax({
                type: "GET",
                cache:false,
                url: serverUrl+url+"?"+$.stringify(data),
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

    } else if (message.action == "saveImageAnnotation") {
        console.log("adkhdh");
        console.log(message.data);
        cat.saveAnnotation(message.data);
    }


}