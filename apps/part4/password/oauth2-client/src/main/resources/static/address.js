$(function() {  
	var fragment = window.location.hash;  
	var parameters = fragment.slice(1).split('&');  
	var oauth2Token = {};  
	$(parameters).each(function(idx, param) {    
		var keyValue = param.split('=');    
		oauth2Token[keyValue[0]] = keyValue[1];  
		});  
	$.ajax({      
		url: 'http://localhost:8181/address',      
		beforeSend: function(xhr) {          
			xhr.setRequestHeader("Authorization", "Bearer " + oauth2Token['access_token']);      },      
			success: function(data) {        
				console.log(data.street);
				window.location.replace("#");      
			},      
			error: function(jqXHR, textStatus, errorThrown)   {          
				console.log(textStatus);      
				
			}  });
	});