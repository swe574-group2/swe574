var cat=(function () {
    return {
        showAnnotations:function(){
            chrome.tabs.query({active: true, currentWindow: true}, function(tabs){
                chrome.tabs.sendMessage(tabs[0].id, {"sender": "cat","action":"showAnnotations"}, function(response) {});
            });
        }
    };
})();
chrome.runtime.onMessage.addListener(handleMessage);

function handleMessage(message){
    console.log("cat recieved message",message)
    /*chrome.tabs.getSelected(null, function(tab) {
        chrome.tabs.sendMessage(tab[0].id, {message: message});
    });*/
    if(message.action=="textSelected"){
        plugin.textSelected(message.selection);
    }


}