function revealPassword(x) {
    let passField= document.getElementById("password" + x);
    let passEye= document.getElementById("passEye" + x);
    if (passField.type === "password") {
        passField.type= "text";
        passEye.className= "fa fa-eye-slash";
    }
    else {
        passField.type= "password";
        passEye.className= "fa fa-eye";
    }
}

function confermaParametriAuth(x) {
    if(x === 'Login') {
        let username= document.getElementById('usernameLogin').value;
        let password= document.getElementById('passwordLogin').value;

        const usernameRGX= /^[A-Za-z][A-Za-z0-9_]{4,29}$/;
        const emailRGX= /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
        const passwordRGX=/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\[{}\]:;',?*~$^\-+=<>]).{8,30}$/;

        if(!usernameRGX.test(username) && !emailRGX.test(username)) {
            alert("L'username o email non rispecchia il formato corretto. Riprovare!");
            return false;
        }
        if(!passwordRGX.test(password)) {
            alert("La password non rispecchia il formato corretto. Riprovare!");
            return false;
        }

        return true;
    } else if (x === 'Signup') {
        if(document.getElementById('passwordSignup1').value !== document.getElementById('passwordSignup2').value) {
            alert("Le password inserite non sono uguali. Riprovare!");
            document.getElementById('passwordSignup1').value= '';
            document.getElementById('passwordSignup2').value= '';
            return false;
        }

        let name= document.getElementById('nameSignup').value;
        let lastname= document.getElementById('lastnameSignup').value;
        let phone= document.getElementById('phoneSignup').value;
        let birthDate= document.getElementById('birthDateSignup').value;
        let username= document.getElementById('usernameSignup').value;
        let email= document.getElementById('emailSignup').value.toLowerCase();
        let password= document.getElementById('passwordSignup1').value;

        const nameRGX= /^[a-zA-Z.\s]{2,30}$/;
        const phoneRGX= /^$|^([+]?[(]?[0-9]{1,3}[)]?[-\s])?([(]?[0-9]{3}[)]?[-\s]?)?([0-9][-\s]?){3,10}[0-9]$/;
        const dateRGX= /^(\d{4})-(\d{2})-(\d{2})$/;
        const usernameRGX= /^[A-Za-z][A-Za-z0-9_]{4,29}$/;
        const emailRGX= /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
        const passwordRGX=/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\[{}\]:;',?*~$^\-+=<>]).{8,30}$/;

        if(!nameRGX.test(name) || !nameRGX.test(lastname)){
            alert("Nome e/o Cognome non validi!");
            return false;
        }
        if(!phoneRGX.test(phone)) {
            alert("Numero di telefono non valido. Riprovare!");
            return false;
        }
        if(!dateRGX.test(birthDate)) {
            alert("Data di nascita non valida. Riprovare!");
            return false;
        }
        if(!usernameRGX.test(username)) {
            alert("L'username non rispecchia il formato corretto. Riprovare!");
            return false;
        }
        if((!emailRGX.test(email))){
            alert("L'email non rispecchia il formato corretto. Riprovare!");
            return false;
        }
        if(!passwordRGX.test(password)) {
            alert("La password non rispecchia il formato corretto. Riprovare!");
            return false;
        }

        return true;
    } else
        return false;
}

function changeTab(x) {
    if(x === 'Login') {
        let loginLink= document.getElementById("linkTab-Login");
        if (loginLink.className === 'login-link') {
            loginLink.className= 'login-link login-link--active';
            document.getElementById("section-Login").className= 'login-section login-section--active';

            document.getElementById("linkTab-Signup").className= 'login-link';
            document.getElementById("section-Signup").className= 'login-section';
        }
    } else if(x === 'Signup') {
        let loginLink= document.getElementById("linkTab-Signup");
        if (loginLink.className === 'login-link') {
            loginLink.className= 'login-link login-link--active';
            document.getElementById("section-Signup").className= 'login-section login-section--active';

            document.getElementById("linkTab-Login").className= 'login-link';
            document.getElementById("section-Login").className= 'login-section';
        }
    } else
        return false;
}


function confermaParametriEditProfile() {
    if(document.getElementById('passwordUpdate1').value !== document.getElementById('passwordUpdate2').value) {
        alert("Le password inserite non sono uguali. Riprovare!");
        document.getElementById('passwordUpdate1').value= '';
        document.getElementById('passwordUpdate2').value= '';
        return false;
    }

    let name= document.getElementById('nameUpdate').value;
    let lastname= document.getElementById('lastnameUpdate').value;
    let phone= document.getElementById('phoneUpdate').value;
    let birthDate= document.getElementById('birthDateUpdate').value;
    let username= document.getElementById('usernameUpdate').value;
    let email= document.getElementById('emailUpdate').value.toLowerCase();
    let password= document.getElementById('passwordUpdate1').value;

    const nameRGX= /^[a-zA-Z.\s]{2,30}$/;
    const phoneRGX= /^$|^([+]?[(]?[0-9]{1,3}[)]?[-\s])?([(]?[0-9]{3}[)]?[-\s]?)?([0-9][-\s]?){3,10}[0-9]$/;
    const dateRGX= /^(\d{4})-(\d{2})-(\d{2})$/;
    const usernameRGX= /^[A-Za-z][A-Za-z0-9_]{4,29}$/;
    const emailRGX= /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
    const passwordRGX=/^$|^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\[{}\]:;',?*~$^\-+=<>]).{8,30}$/;

    if(!nameRGX.test(name) || !nameRGX.test(lastname)){
        alert("Nome e/o Cognome non validi!");
        return false;
    }
    if(!phoneRGX.test(phone)) {
        alert("Numero di telefono non valido. Riprovare!");
        return false;
    }
    if(!dateRGX.test(birthDate)) {
        alert("Data di nascita non valida. Riprovare!");
        return false;
    }
    if(!usernameRGX.test(username)) {
        alert("L'username non rispecchia il formato corretto. Riprovare!");
        return false;
    }
    if((!emailRGX.test(email))){
        alert("L'email non rispecchia il formato corretto. Riprovare!");
        return false;
    }
    if(!passwordRGX.test(password)) {
        alert("La password non rispecchia il formato corretto. Riprovare!");
        return false;
    }

    return true;
}