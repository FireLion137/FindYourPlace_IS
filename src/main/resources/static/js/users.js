function openTabContentB(x){
    let navlink=document.getElementById("tab-content-"+x);
    let button=document.getElementById("Button-"+x)
    if (navlink.style.height==="0px"){
        navlink.style.height="auto";
        navlink.style.border="1px solid #737373";
        navlink.style.removeProperty("padding");
        navlink.style.removeProperty("margin");
        navlink.style.display="block"; //prov
        button.innerText="Annulla";
    }
    else{
        navlink.style.height="0px";
        navlink.style.border="0px solid #737373";
        navlink.style.margin="0px";
        navlink.style.padding="0px";
        navlink.style.display="none"; //prov
        if (x==='form-notification')
        button.innerText="Invia notifica";
        else
            button.innerText="Invia notifica Broadcast";
    }
}    function openTabContent(x){
    let navlink=document.getElementById("tab-content-"+x);
    if (navlink.style.height==="0px")
        navlink.style.height="auto";
    else
        navlink.style.height="0px";
}

function confermaParametri(x) {

if (x==='single')
{
    let author= document.getElementById('autoreSendnot').value;
    let text= document.getElementById('textSendnot').value;


    const authorRGX= /^[A-Za-z][A-Za-z0-9_]{4,29}$/;
    const textRGX=/^[\s\S]{2,500}$/;


    if(!authorRGX.test(author)) {
        alert("L'autore non rispecchia il formato corretto. Riprovare!");
        return false;
    }
    if(!textRGX.test(text)) {
        alert("Il testo non rispecchia il formato corretto. Riprovare!");
        return false;
    }

    return true;
}else if(x==='all'){
    let author= document.getElementById('autoreSendnotB').value;
    let text= document.getElementById('textSendnotB').value;


    const authorRGX= /^[A-Za-z][A-Za-z0-9_]{4,29}$/;
    const textRGX=/^[\s\S]{2,500}$/;


    if(!authorRGX.test(author)) {
        alert("L'autore non rispecchia il formato corretto. Riprovare!");
        return false;
    }
    if(!textRGX.test(text)) {
        alert("Il testo non rispecchia il formato corretto. Riprovare!");
        return false;
    }

    return true;
}else{return false}

    }
    function ConfermaAzioneResetUsername(){
        if(!confirm('Vuoi davvero resettare l\x27username?\n' +
            'Quest\x27azione non è reversibile!'))
            return false;

    }
function ConfermaAzioneResetPassword(){
    if(!confirm('Vuoi davvero reimpostare la Password?\n' +
        'Quest\x27azione non è reversibile!'))
        return false;

}
