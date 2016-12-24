checkUser();
function checkUser() {
    var getPrincipal = browser.storage.local.get("principal");
    getPrincipal.then(function(principal){
        if (principal.hasOwnProperty("principal")==false) {
            console.log("principal", principal);
            setTimeout(function(){document.location.href="index.html"},300);
        }
        else {
            cat.username=principal.principal.name;
            cat.password=principal.principal.password;
            cat.getAnnotationCount();
        }
    },function (a) {
        console.log("error",a)
    })

}

document.addEventListener("click", function (e) {
    if (e.target.id == "btnShowAnnotations") {
        cat.showAnnotations();
    }
    else if (e.target.id == "btnLogout") {
        var op=browser.storage.local.remove("principal");
        op.then(function () {
            cat.logout();
            setTimeout(function(){document.location.href="index.html"},300);
        })

    }
    else if(e.target.id=="btnSearch"){
        cat.search({text:$("#body").val(),pageSize:$("#pageSize").val(),page:$("#currenPage").val(),start:$("#startDate").val(),end:$("#endDate").val()});
    }

});