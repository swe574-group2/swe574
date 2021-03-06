var cat;

cat = (function () {
    var serverUrl = "http://ec2-35-165-112-121.us-west-2.compute.amazonaws.com:8080/";
    //var authServerUrl = "http://localhost:8080";


    return {
        username : "",
        count:0,
        password:"",
        getAnnotationCount: function (completed) {
            chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                $("#info").html("Retriving count...");
                console.log("cat.js: Retrieving Count");
                var url = tabs[0].url;
                if (url.split("#.").length > 0)
                    url = url.split("#.")[0];
                cat.post("/source/count", {"targetSource": url}, function (json) {
                    console.log("cat.js: Retrieved Count: " + json);
                    $("#info").addClass("hide");
                    $("#btnShowAnnotations").text("Show Annotations (" + json + ")");
                    $("#btnShowAnnotations").removeClass("hide");
                    cat.count=json;
                    if (completed) {
                        completed();
                    }
                })
            });

        },
        showAnnotations: function () {
            cat.getAnnotationCount(function() {
                if(cat.count==0) cat.count=1;
                chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                    console.log("current tab url:" + tabs[0].url);
                    var url = tabs[0].url;
                    if (url.split("#.").length > 0)
                        url = url.split("#.")[0];
                    console.log("showing for target " + url);
                    console.log("count: " + cat.count);
                    cat.post("/source", {"targetSource": url,pageNumber:1,pageSize:cat.count}, function (json) {
                        chrome.tabs.sendMessage(tabs[0].id, {
                            "sender": "cat",
                            "action": "showAnnotations",
                            "data": json
                        }, function (response) {
                        });
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
                "email":email,
                "userType": "ROLE_MEMBER",
                "password": password
            };
            cat.post("/register", registerInputData, function (json) {
                window.location = "index.html";
                console.log("registration succeeded");
            }, false);
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
                    cat.username = json.principal.name;
                    cat.password=user.password;
                    chrome.tabs.sendMessage(tabs[0].id, {
                        "sender": "cat",
                        "action": "userLoggedin",
                        "data":{loggedIn:true,username:cat.username}
                    }, function (response) {
                    });
                    json.principal.password=user.password;

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
        logout:function(){
            chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                chrome.tabs.sendMessage(tabs[0].id, {
                    "sender": "cat",
                    "action": "userLoggedin",
                    "data": {loggedIn:false}
                }, function (response) {
                });
            });

        },
        saveAnnotation: function (annotation) {
            annotation.creator.name=cat.username;
            annotation.creator.nickname=cat.username;
            if (annotation.id.split("#.").length > 0)
                annotation.id = annotation.id.split("#.")[0];
            if (annotation.target.source.split("#.").length > 0)
                annotation.target.source = annotation.target.source.split("#.")[0];
//"TextPositionSelector"
            if (annotation.target.selector.type == "TextPositionSelector") {
                var trimmed = $.trim(annotation.target.selector.value);
                if (trimmed.length !== annotation.target.selector.value.length) {
                    var diff = annotation.target.selector.value.length - trimmed.length;
                    annotation.target.selector.value = trimmed;
                    if (annotation.target.selector.value.charAt(0) == trimmed.charAt(0)) { //means trim was done to the right
                        annotation.target.selector.end = annotation.target.selector.end - diff;
                    } else if (annotation.target.selector.value.charAt(annotation.target.selector.value.length-1) == trimmed.charAt(trimmed.length-1)) {
                        annotation.target.selector.start = annotation.target.selector.start + diff;
                    }
                }
            }

            cat.post("/add", annotation, function (json) {
                //get annotations
                chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                    chrome.tabs.sendMessage(tabs[0].id, {
                        "sender": "cat",
                        "action": "clearHighlights",
                        "data":{}
                    }, function (response) {
                        console.log("got response: " + response);
                        cat.showAnnotations();
                    });
                });
            },true)

        },
        post: function (url, data, success,isAuthRequest) {
            var server = serverUrl;



            var nickpass = cat.username + ":" + cat.password;
            console.log("nickpass",nickpass);
            var headers={};
            if(isAuthRequest&&isAuthRequest==true) {
                headers = {
                    "Authorization": "Basic " + btoa(nickpass)
                };
            }
            $.ajax({
                type: "POST",
                url: server + url,
                data: JSON.stringify(data),
                crossDomain: true,
                headers:headers,
                cache: false,
                success: function (json) {
                    if (success)
                        success(json);
                },
                error: function (json) {
                    //$("#cat-info").removeClass("hide").html(JSON.parse(json.responseText).message);

                },
                complete: function (json) {
                    console.log("post completed", JSON.stringify(json));
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
        },
        search:function(data){
            cat.post("/basicSearch",{startDate:data.start,endDate:data.end,bodyValue:data.text,pageNumber:data.page,pageSize:data.pageSize },
                function(json){
                    chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                        chrome.tabs.sendMessage(tabs[0].id, {
                            "sender": "cat",
                            "action": "showSearchResults",
                            "data": {items:json,search:data.text}
                        }, function (response) {
                        });
                    });
            },true);
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
        console.log(message.data);
        cat.saveAnnotation(message.data);
    }
    else if(message.action=="getUser"){
            getUser();
        }
    }
function getUser() {
    console.log("getting user")

    var getPrincipal = browser.storage.local.get("principal");
    getPrincipal.then(function(principal){
        console.log("principal", principal);
        if (principal.hasOwnProperty("principal")==false) {
            chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                chrome.tabs.sendMessage(tabs[0].id, {
                    "sender": "cat",
                    "action": "userLoggedin",
                    "data": {loggedIn:false}
                }, function (response) {
                });
            });



        }
        else {
            console.log("principal",principal)
            cat.username=principal.principal.name;
            cat.password=principal.principal.password;
            chrome.tabs.query({active: true, currentWindow: true}, function (tabs) {
                chrome.tabs.sendMessage(tabs[0].id, {
                    "sender": "cat",
                    "action": "userLoggedin",
                    "data": {loggedIn:true,username:principal.principal.name}
                }, function (response) {
                });
            });
        }
    },function (a) {
        console.log("error",a)
    })

}
