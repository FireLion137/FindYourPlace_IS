function openTail(x){
    let navlink=document.getElementById("navLink"+x);
    if (navlink.style.height==="0px")
        navlink.style.height="auto";
    else
        navlink.style.height="0px";
}