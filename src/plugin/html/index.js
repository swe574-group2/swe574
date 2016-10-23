
document.addEventListener("click", function(e) {
    if (e.target.id == "btnShowAnnotations") {
        cat.showAnnotations();
    }
    if (e.target.id == "btnSaveTextAnnotation") {

    }
});



renderAnnotationCount();
function  renderAnnotationCount(){
    cat.getAnnotationCount();
}

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