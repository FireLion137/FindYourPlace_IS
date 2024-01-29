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
        if (x==='form-notificationB')
            button.innerText="Invia notifica Broadcast";
        else
            button.innerText="Invia notifica";
    }
}    function openTabContent(x){
    let navlink=document.getElementById("tab-content-"+x);
    if (navlink.style.height==="0px")
        navlink.style.height="auto";
    else
        navlink.style.height="0px";
}

function confermaParametri(x,y) {

if (x==='single')
{
    let author, text;
    if (y!=null)
    {
         author= document.getElementById('autoreSendnot' +y).value;
         text= document.getElementById('testoSendnot'+y).value;
    }else {
         author= document.getElementById('autoreSendnot').value;
         text= document.getElementById('testoSendnot').value;
    }



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
    let text= document.getElementById('testoSendnotB').value;


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


function InvioNotificaSingola(x){
    let username=x
    let form =$('#notForm'+username);
    form.submit(function (e){
        e.preventDefault();
    });
    if (!confermaParametri('single',username)){
        return false;
    }

    $('[id$=Success]').text('');

    $.ajax({
        type: 'POST',
        url: '/sendNotification',
        data: form.serialize(),
        success: function(response, textStatus, xhr) {
            if(xhr.status === 201) {
                $('#SubmitSendnot' + username + 'Success').text("Notifica inviata correttamente");
                $('#textSendnot' + username).val('');
            } else {
                console.log("HTTP Status imprevisto: " + xhr.status)
            }
        },
        error: function(xhr, textStatus, errorThrown) {
            if(xhr.status === 400) {
                $('[id$=Error]').text('');
                xhr.responseJSON.errors.forEach(function(error) {
                    $('#' + error.field +'Sendnot'+ username + 'Error').text(error.defaultMessage);
                });
            }
            else {
                console.log("Errore HTTP Status imprevisto: " + textStatus + errorThrown)
            }
        }
    });
}
function InvioNotificaAll(){
    if (!confermaParametri('all')){
        return false;
    }
    let form =$('#notFormB');
    form.submit(function (e){
        e.preventDefault();
    });
    $('[id$=Success]').text('');

    $.ajax({
        type: 'POST',
        url: '/sendNotificationAll',
        data: form.serialize(),
        success: function(response, textStatus, xhr) {
            if(xhr.status === 201) {
                $('#SubmitSendnotBSuccess').text("Notifica inviata correttamente");
                $('#textSendnotB' ).val('');
            } else {
                console.log("HTTP Status imprevisto: " + xhr.status)
            }
        },
        error: function(xhr, textStatus, errorThrown) {
            if(xhr.status === 400) {
                $('[id$=Error]').text('');
                xhr.responseJSON.errors.forEach(function(error) {
                    $('#' + error.field  + 'BError').text(error.defaultMessage);
                });
            }
            else {
                console.log("Errore HTTP Status imprevisto: " + textStatus + errorThrown)
            }
        }
    });
}
function InvioNotificaSingolaDetUser(){
    if (!confermaParametri('single',null)){
        return false;
    }
    let form =$('#NotForm');
    form.submit(function (e){
        e.preventDefault();
    });
    $('[id$=Success]').text('');

    $.ajax({
        type: 'POST',
        url: '/sendNotification',
        data: form.serialize(),
        success: function(response, textStatus, xhr) {
            if(xhr.status === 201) {
                $('#SubmitSendnotSuccess').text("Notifica inviata correttamente");
                $('#textSendnot' ).val('');
            } else {
                console.log("HTTP Status imprevisto: " + xhr.status)
            }
        },
        error: function(xhr, textStatus, errorThrown) {
            if(xhr.status === 400) {
                $('[id$=Error]').text('');
                xhr.responseJSON.errors.forEach(function(error) {
                    $('#' + error.field  + 'DetUError').text(error.defaultMessage);
                });
            }
            else {
                console.log("Errore HTTP Status imprevisto: " + textStatus + errorThrown)
            }
        }
    });
}


