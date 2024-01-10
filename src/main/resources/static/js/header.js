function openNav() {
    let el= document.getElementById("myNavBar");
    if (el.className === "navBar")
        el.className = "navBar navBar--open";
}
function closeNav() {
    let el= document.getElementById("myNavBar");
    if (el.className === "navBar navBar--open")
        el.className = "navBar";
}

function subCatOpen(drpCntNumber) {
    let nav= document.getElementById("navCats");
    let drpCnt= document.getElementById("drpCnt"+drpCntNumber);
    if (nav.className === "navCategories")
        nav.className = "navCategories navCategories--sub";
    if (drpCnt.className === "dropdown-content")
        drpCnt.className = "dropdown-content dropdown-content--active";

    let hmBtn= document.getElementById("hmBtn");
    if (hmBtn.className === "homeBtn")
        hmBtn.className = "homeBtn homeBtn--remove";
    let goBck= document.getElementById("goBck");
    if (goBck.className === "goBack")
        goBck.className = "goBack goBack--appear";
}
function subCatClose() {
    let nav= document.getElementById("navCats");
    let drpCntArr= document.getElementsByClassName("dropdown-content");
    if (nav.className === "navCategories navCategories--sub")
        nav.className = "navCategories";
    for (let drpCnt of drpCntArr) {
        if (drpCnt.className === "dropdown-content dropdown-content--active")
            drpCnt.className = "dropdown-content";
    }

    let hmBtn= document.getElementById("hmBtn");
    if (hmBtn.className === "homeBtn homeBtn--remove")
        hmBtn.className = "homeBtn";
    let goBck= document.getElementById("goBck");
    if (goBck.className === "goBack goBack--appear")
        goBck.className = "goBack";
}

function actionOpen(drpName) {
    let drpCnt= document.getElementById("drpCnt"+drpName);
    if (drpCnt.className === "dropdown-content")
        drpCnt.className = "dropdown-content dropdown-content--active";
    else
        drpCnt.className = "dropdown-content";

    let closeAct= document.getElementById("closeAct");
    if (closeAct.className === "closeAction")
        closeAct.className = "closeAction closeAction--appear";
    else
        closeAct.className = "closeAction";
}
function actionClose() {
    let drpCntArr= document.getElementsByClassName("dropdown-content");

    for (let drpCnt of drpCntArr) {
        if (drpCnt.className === "dropdown-content dropdown-content--active")
            drpCnt.className = "dropdown-content";
    }

    let closeAct= document.getElementById("closeAct");
    if (closeAct.className === "closeAction closeAction--appear")
        closeAct.className = "closeAction";
}


window.addEventListener("scroll", function() {
    let body=  document.getElementsByTagName("body")[0];
    let head=  document.getElementsByClassName("mainHeader")[0];
    if (window.scrollY!==0){
        if (window.innerWidth>=1024){
            body.style.paddingTop="80px";
        }
        else{
            body.style.paddingTop="62px";
        }
        head.style.position="fixed";
        head.style.top="0px";
        head.style.left="0px";
        head.style.width="100%";
    } else{
        body.style.removeProperty("padding-top");
        head.style.removeProperty("position");
        head.style.removeProperty("top");
        head.style.removeProperty("left");
        head.style.removeProperty("width");
    }

});

$(function() {
        $('#drpCntAccount, .account-btn, #myNavBar, .menuIcon').click(function(e) {
        e.stopPropagation();
    });
});

$(function(){
    $(document).click(function(){
        actionClose();
        subCatClose();
        closeNav();
    });
});