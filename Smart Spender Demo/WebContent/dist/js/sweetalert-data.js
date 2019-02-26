/*SweetAlert Init*/

$(function() {
	"use strict";
	
	var SweetAlert = function() {};

    SweetAlert.prototype.init = function() {
        
    //Parameter
	$('#sa-params').on('click',function(e){
        swal({   
            title: "Are you sure?",   
            text: "You want to access the Inventory Management Module of Smart Spender!",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#f2b701",   
            confirmButtonText: "Yes, Sure",   
            cancelButtonText: "No, Don't want to",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        }, function(isConfirm){   
            if (isConfirm) {     
                swal("Sent!", "Your request for accessing Inventory Management Module has been sent to admin. The result of your request will be shortly notified to you.", "success");   
            } else {     
                swal("Cancelled", "You cancelled the request for Inventory Management Module.", "error");   
            } 
        });
		return false;
    });
    },
    //init
    $.SweetAlert = new SweetAlert, $.SweetAlert.Constructor = SweetAlert;
	
	$.SweetAlert.init();
});