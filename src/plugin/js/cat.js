var cat;
cat = (function () {
    var serverUrl = "http://localhost:8080";
    //var authServerUrl = "http://localhost:8080";
    var userName = "";
    var count=0;
    return {

        getAnnotationCount: function () {
            chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                $("#info").html("Retriving count...");
                console.log("cat.js: Retrieving Count");
                cat.post("/source/count", {"targetSource": tabs[0].url}, function (json) {
                    $("#info").addClass("hide");
                    $("#btnShowAnnotations").text("Show Annotations (" + json + ")");
                    $("#btnShowAnnotations").removeClass("hide");
                    count=json;
                })
            });

        },
        showAnnotations: function () {
            chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                console.log("current tab url:" + tabs[0].url);
                cat.post("/source", {"targetSource": tabs[0].url,pageNumber:1,pageSize:count+1}, function (json) {
                    chrome.tabs.sendMessage(tabs[0].id, {
                        "sender": "cat",
                        "action": "showAnnotations",
                        "data": json
                    }, function (response) {
                    });
                });

            });

        },
        register: function () {
            var nickname = $("#nickName").val();
            var email = $("#email").val();
            var password = $("#password").val();
            var registerInputData =
            {
                "name": nickname,
                "nickname": nickname,
                "userType": "ROLE_MEMBER",
                "password": password
            };
            cat.post("/register", registerInputData, function (json) {
                window.location = "index.html";
                console.log("registration succeeded");
            }, true);
        },
//ŞK01 B
        login: function () {
            chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                user = {};
                user.nickname = $("#username").val();
                user.password = $("#password").val();
                var nickpass = user.nickname + ":" + user.password;
                console.log("nickpass: " + nickpass);
                console.log("btoa-nickpass: " + btoa(nickpass));
                cat.get("/user", null, function (json) {
                    console.log(json);
                    userName = json.principal.name;
                    var storing = browser.storage.local.set({"principal": json.principal});
                    storing.then(function () {
                        setTimeout(function () {
                            document.location.href = "manage.html"
                        }, 300);

                    }, function () {
                        $("#lblloginMessage").text("storage error");
                        $("#loginInfoMessage").removeClass("hide");
                    });


                }, function (responseData, textStatus, errorThrown) {
                    /*window.location = 'http://github.com';*/
                    $("#lblloginMessage").text("check your username/password");
                    $("#loginInfoMessage").removeClass("hide");
                }, false, {
                    /*"Authorization": "Basic " + btoa(user.nickname + ":" + user.password)*/
                    "Authorization": "Basic " + btoa(nickpass)
                }, true);
            })
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
        post: function (url, data, success, isAuthRequest) {
            var server = serverUrl;

            var urll = server + url;
            console.log(urll);
            console.log(JSON.stringify(data));
            $.ajax({
                type: "POST",
                url: server + url,
                data: JSON.stringify(data),
                crossDomain: true,
                cache: false,
                success: function (json) {
                    console.log("success", json)
                    if (success)
                        success(json);
                },
                error: function (json) {
                    console.log(json)
                    //$("#cat-info").removeClass("hide").html(JSON.parse(json.responseText).message);

                },
                complete: function (json) {
                    console.log("post completed", json)
                },
                //dataType: "json",
                contentType: "application/json; charset=utf-8"
            });
        },

        get: function (url, data, success, failure, async, headers, isAuthRequest) {
            if (data) {
                $("#info").html($.stringify(data));
            }
            var server = serverUrl;

            var lastUrl = server + url + ((data) ? "?" + $.stringify(data) : "");
            console.log(lastUrl);
            $.ajax({
                type: "GET",
                cache: false,
                url: lastUrl,
                crossDomain: true,
                async: async,
                headers: headers,
                success: function (json) {
                    if (success)
                        success(json);
                },
                error: function (responseData, textStatus, errorThrown) {
                    if (failure)
                        failure(responseData, textStatus, errorThrown);
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
