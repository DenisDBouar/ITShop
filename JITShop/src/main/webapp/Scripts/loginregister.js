var rootURL = "http://localhost:8080/JITShop/rest/";

$(document).ready(function(){

	
	$("#sublit_pass").click(function(){
			$.ajax({
			  type: 'GET',
			  url: rootURL + "getcookie",
			  success:function(data, textStatus, jqXHR){
				setInitCookie(data);
				checkCookie();
				setuserlogin();
			  },
		  	 error: function(jqXHR, textStatus, errorThrown){
				alert('Contact page error: ' + textStatus);
			}  
			});
	  });
	
	$("#but_register").click(function(){
		$.ajax({
			  type: 'GET',
			  url: rootURL + "getcookie",
			  success:function(data, textStatus, jqXHR){
				setInitCookie(data);
				checkCookie();
				registernewuser();
				logoutajax();
			  },
		  	 error: function(jqXHR, textStatus, errorThrown){
				alert('Contact page error: ' + textStatus);
			}  
			});
		registernewuser()
	});
  

});

function deletecookie(){
	document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
}

function setInitCookie(data) {
	deletecookie();
	
	var exdays = 30;
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = "username"+"="+data+"; "+expires;
}


function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return "";
}

function checkCookie() {
    var user=getCookie("username");
    if (user != "") {
       // alert("Welcome again " + user);
        $('#a_login').text( "Logout");
        $('#a_register').hide();
        if (user.indexOf("Admin") !=-1) {
        	$('#adminLink').show();
        }
        return user;
    } else {
    	//alert(" no user ");
    	$('#a_login').text( "Log in");
    	$('#a_register').show();
    	$('#adminLink').hide();
    	return "";
    }
}
function setuserlogin(){
	var user=getCookie("username");
	if (user != "") {
    	var ca = user.split('|');
        var password = $('#MainContent_UserName').val() + ',' + $('#MainContent_Password').val();
        var rsa = new RSAKey();
        rsa.setPublic(ca[0], ca[1]);
        var res = rsa.encrypt(password);
        $.ajax({
			type: "POST",
			url: rootURL + "login2", 
			data: ca[0]+','+res,
			contentType:"text/html",
			
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Log in Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
				logoutajax();
			},
			success: function(data, textStatus, jqXHR){
				var user=getCookie("username");
				setInitCookie(user +"|"+ data);
				checkCookie();
				 $("#page2").html("<h2>Log in.</h2>");
			},
			complete: function(XMLHttpRequest) {
				//console.log( XMLHttpRequest.getAllResponseHeaders() );
			}, 
		});
        
    } else {
    	//alert(" no user setuserlogin");
    }
}

function registernewuser(){
	var user=getCookie("username");
	if (user != "") {
    	var ca = user.split('|');
        var password = $('#MainContent_UserName').val() +"," + $('#MainContent_Password').val();
        var rsa = new RSAKey();
        rsa.setPublic(ca[0], ca[1]);
        var res = rsa.encrypt(password);
        
        $.ajax({
    		
    		
    		type: "POST",
    		url: rootURL + "register2", 
    		data: ca[0]+','+res,
    		contentType:"text/html",
    		
    		error: function(jqXHR, textStatus, errorThrown) {
    			alert("Register Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
    		},
    		success: function(data, textStatus, jqXHR){
    			if(data == 1)
    			  $("#page2").html("<h2> " + $('#MainContent_UserName').val() + " registered.</h2>");
    			else
    				alert($('#MainContent_UserName').val() + " already exist");
    		},
    		complete: function(XMLHttpRequest) {
    		}, 
    	});
    } else {
    	//alert(" no user registernewuser");
    }
	
}