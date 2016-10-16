document.addEventListener("click", function(e) {
    if (e.target.id == "btnShowAnnotations") {
        cat.showAnnotations();
    }
});

var plugin=(function () {
    return {
        alert:function () {
            alert("here");
        },
        textSelected:function (selection) {
            plugin.alert(selection.toString())
        }
    }
})();