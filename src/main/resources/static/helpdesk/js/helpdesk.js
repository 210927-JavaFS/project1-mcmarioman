const URL = "http://localhost:8081/";

let requestsLoaded = false;
let glovalUserId;

async function addReimbursement(){

    let form = document.querySelector('form');

    if(!form.reportValidity()){
      
      return;
    }

    let _receipt = document.getElementById("receiptFile").files[0];

    if(_receipt){
        let formData = new FormData();
        formData.append("receipt", _receipt);

        const ctrl = new AbortController()    // timeout
        setTimeout(() => ctrl.abort(), 5000);

        try {
            let r = await fetch(URL+'upload', 
            {method: "POST", body: formData, signal: ctrl.signal}); 
            console.log('HTTP response code:',r.status); 
        }catch(e){
            console.log('Huston we have problem...:', e);
        }
    }
  
    let reimbursement = getNewReimbursement();
    
    let response = await fetch(URL+"reimbursements", {
      method:'POST',
      body:JSON.stringify(reimbursement),
      credentials:"include"
    });

  
    if(response.status===201){
      $('#newSuccess').fadeIn("slow").delay(2000).fadeOut("slow");
      $('#txtAmount').val('');
      $('#txtDescription').val('');
      $('#receiptFile').val('');
      $('#listType').val('0');
      $('#tbReimbursement').empty();
      loadTickets(glovalUserId);
    }else{
      console.log("Something went wrong creating your ticket.")
    }
    
}

async function logout(){

    let response = await fetch(URL+"logout", {
      method:'POST',
      credentials:"include"
    });
    if(response.status===410){
        window.location = "index.html";
    }else{
        console.log('Logout fail');
    }
}

function getNewReimbursement(){

    let newDescription = document.getElementById("txtDescription").value;
    let newAmount = document.getElementById("txtAmount").value; 
    let newTypeId = document.getElementById("listType").value;
    let _receipt = document.getElementById("receiptFile").files[0];

    let newReceipt = "";

    if(_receipt){
        newReceipt = _receipt.name
    }
  
    let reimbursement =  {
        reimbursementId:0,
        amount:newAmount,
        description:newDescription,
        typeId:newTypeId,
        statusId:1,
        receipt:newReceipt
    }
  
    return reimbursement;
}



window.onload = loadInfo;

async function loadInfo(){
    let response = await fetch(URL+"loggeduser", {
    credentials:"include" 
  }); 
  if(response.status===200){
    let user = await response.json();
    let lblUserName = document.getElementById('lblUserName');
    lblUserName.innerText = user.userName;
    glovalUserId = user.userId;
    setRole(user.rol.roleId);
    loadTickets(user.userId);
  }else{
      window.location = "index.html";
  }
}

function setRole(roleid){
    if(roleid == 1){
        $('#tabRequets').hide();
    }
}

// Create our number formatter.
let formatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
  
    // These options are needed to round to whole numbers if that's what you want.
    //minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
    //maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
  });

async function loadTicketsByStatus(statusId){

    if(requestsLoaded){
        return;
    }

    let response =  await fetch(URL + "reimbursements/status/" + statusId, {
        credentials:"include" 
      });
      if(response.status===200){
        let reimbursements = await response.json();
        if(reimbursements.length > 0){
            populateTable(reimbursements, "tbRequests");
        }else{
            $('#tblRequests').fadeOut("fast");
            $('#noPending').fadeIn("slow");
        }
        
      }

      requestsLoaded = true;
}

async function loadTickets(userId){
    let response =  await fetch(URL + "reimbursements/user/" + userId, {
        credentials:"include" 
      }); 

    if(response.status===200){

        let reimbursements = await response.json();
        populateTable(reimbursements, "tbReimbursement");

    }

}

function populateTable(reimbursements, table){
    let tbReimbursement = document.getElementById(table);

    for (let _reimbursement of reimbursements){
        
        let _tr = document.createElement('tr');
        
        let _td1 = document.createElement('td');
        _td1.innerText = _reimbursement.description;              
        _tr.appendChild(_td1);

        let _td2 = document.createElement('td');
        _td2.innerText = formatter.format(_reimbursement.amount);
        _tr.appendChild(_td2);

        let _td3 = document.createElement('td');
        _td3.innerText = _reimbursement.reimbursementType.typeName;
        _tr.appendChild(_td3);

        let _td4 = document.createElement('td');
        _td4.innerText = _reimbursement.reimbursementStatus.statusName;
        _tr.appendChild(_td4);

        let _td5 = document.createElement('td');
        _td5.innerText =  new Date(_reimbursement.submitted).toLocaleDateString("en-US");
        _tr.appendChild(_td5);

        let _td6 = document.createElement('td');
        if(_reimbursement.receipt){
            let _af = document.createElement('a');
            _af.innerHTML = "<i class='bx bx-receipt'></i>";
            _af.setAttribute("href", URL + "download/" + _reimbursement.receipt);
            _af.setAttribute("target", "_blank");
            
            _td6.appendChild(_af);
        }else{
            _td6.innerText = "-";
        }
        _td6.setAttribute("class", "action");
        _tr.appendChild(_td6);

        let _td7 = document.createElement('td');
        if(table == "tbRequests"){
            let _a1 = document.createElement('a');
            _a1.innerHTML = "<i class='bx bx-check-circle'></i>"
            _a1.setAttribute("href", "#");
            _a1.setAttribute("data-bs-toggle", "modal");
            _a1.setAttribute("data-bs-target", "#staticBackdrop");
            _a1.setAttribute("data-bs-whatever", _reimbursement.reimbursementId);
            _a1.setAttribute("data-bs-action", "2");

            _td7.setAttribute("class", "action");
            _td7.appendChild(_a1);
        }else{
            if(_reimbursement.resolved){  
                _td7.innerText =  new Date(_reimbursement.resolved).toLocaleDateString("en-US");;
                
            }else{
                _td7.innerText = "-";
            }
        }
        _td7.setAttribute("style", "text-align: center;");
        _tr.appendChild(_td7);

        let _td8 = document.createElement('td');
        if(table == "tbRequests"){
            let _a2 = document.createElement('a');
            _a2.innerHTML = "<i class='bx bx-x-circle'></i>"
            _a2.setAttribute("href", "#");
            _a2.setAttribute("data-bs-toggle", "modal");
            _a2.setAttribute("data-bs-target", "#staticBackdrop");
            _a2.setAttribute("data-bs-whatever", _reimbursement.reimbursementId);
            _a2.setAttribute("data-bs-action", "3");
            _td8.setAttribute("class", "action");
            _td8.appendChild(_a2);
        }else{
            if(_reimbursement.resolver){       
                _td8.innerText = _reimbursement.resolver.userName;
                
            }else{
                _td8.innerText = "-";
            }

        }
        _td8.setAttribute("style", "text-align: center;");
        _tr.appendChild(_td8);

        tbReimbursement.appendChild(_tr);
    }
}

var myModalEl = document.getElementById('staticBackdrop');

myModalEl.addEventListener('show.bs.modal', function (event) {
    let button = event.relatedTarget;
    // Extract info from data-bs-* attributes
    let reimbursementId = button.getAttribute('data-bs-whatever');
    let action = button.getAttribute('data-bs-action');
    getReimbursementById(reimbursementId, action);

});

myModalEl.addEventListener('hidden.bs.modal', function (event) {
    $('#tbRequests').empty();
    requestsLoaded = false;
    loadTicketsByStatus(1);
});

async function getReimbursementById(reimbursementId, action){
    let response =  await fetch(URL + "reimbursements/" + reimbursementId, {
        credentials:"include" 
      });
      if(response.status===200){
        let reimbursement = await response.json();
        populateModal(reimbursement, action);
      }
}

function populateModal(data, action){
    let modalDescription = document.getElementById("modalDescription");
    let modalAmount = document.getElementById("modalAmount");
    let modalAuthor = document.getElementById("modalAuthor");

    modalDescription.innerText = data.description;
    modalAmount.innerText = formatter.format(data.amount);
    modalAuthor.innerText = data.author.firstName + " " + data.author.last_name;

    let btnApprove = document.getElementById("modalBtnApprove");
    btnApprove.setAttribute("data-r-id", data.reimbursementId);
    btnApprove.setAttribute("data-action", action);
    $('#modalBtnApprove').prop('disabled', false);
    $('#modalSuccess').hide();

    let modalAlert = document.getElementById("modalAlert");

    if(action == 3){
        btnApprove.innerText = "Deny";
        if(btnApprove.classList.contains('btn-outline-success')){
            btnApprove.classList.remove('btn-outline-success');
        }
        if(!btnApprove.classList.contains('btn-outline-danger')){
            btnApprove.classList.add('btn-outline-danger');
        }
 
    }else{
        btnApprove.innerText = "Approve";
        if(btnApprove.classList.contains('btn-outline-danger')){
            btnApprove.classList.remove('btn-outline-danger');
        }
        if(!btnApprove.classList.contains('btn-outline-success')){
            btnApprove.classList.add('btn-outline-success');
        }
    }

}

async function approveReimbursement(){
    let reimbursement = getReimbursementToUpdate();

    let response = await fetch(URL+"reimbursements", {
        method:'PATCH',
        body:JSON.stringify(reimbursement),
        credentials:"include"
      });
    
      if(response.status===201){

      $('#modalSuccess').fadeIn("slow");
      $('#modalBtnApprove').prop('disabled', true);

      }else{
        console.log("Something went wrong updating your ticket.")
      }
}

function getReimbursementToUpdate(){

    let _reimbursementId = document.getElementById("modalBtnApprove").getAttribute('data-r-id');
    let newStatusId = document.getElementById("modalBtnApprove").getAttribute('data-action');
    let reimbursement =  {
        reimbursementId:_reimbursementId,
        amount:0,
        description:'',
        typeId:0,
        statusId:newStatusId
    }
  
    return reimbursement;
}