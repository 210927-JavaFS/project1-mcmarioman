//const URL = "http://localhost:8081/";
const URL = "http://35.86.125.91:8080/";


let btnSubmit = document.getElementById('btnLogin');

btnSubmit.onclick = loginToApp;

async function loginToApp(){

    let form = document.querySelector('form');

    if(!form.reportValidity()){
      
      return;
    }

    $('#btnLogin').prop('disabled', true);

    let txtUserName = document.getElementById('username');
    let txtPassword = document.getElementById('password');
    
    let user = {
        username:txtUserName.value,
        password:txtPassword.value
      }

    let response = await fetch(URL+"login", {
        method:"POST",
        body:JSON.stringify(user),
        credentials:"include" //This will save the cookie when we receive it. 
      });

    console.log(response.status);
    if(response.status===200){
        window.location = "helpdesk.html";
    }else{
        $('#invalidBox').fadeIn("fast");
        $('#btnLogin').prop('disabled', false);
    }

}

