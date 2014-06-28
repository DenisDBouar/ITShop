var rootURL = "http://localhost:8080/JITShop/rest/";

$(document).ready(function(){

	
	 /*Return Admin page*/
	  $("#adminLink").click(function(){
		  $.ajax({
			  type: 'GET',
			  url: rootURL + "admin",
			  success:function(data, textStatus, jqXHR){
			    $("#page2").html(data);
			  },
		  	 error: function(jqXHR, textStatus, errorThrown){
				alert('Admin page error: ' + textStatus);
			}  
			});
		  loadAdminCategory();
		  loadAdminAllProduct();
	  });
	
});

function loadAdminCategory(){
	$.ajax({
		  type: 'GET',
		  url: rootURL + "menu",
		  success:function(data, textStatus, jqXHR){
			  var res = "";
			    var servers = JSON.parse(data);
			    for(var i = 0; i<servers.length; i++){
				    	var categoryrez = JSON.parse(servers[i].Category);
				    	for(var j = 0; j<categoryrez.length; j++){
				    		res = res + '<option value="'+ categoryrez[j].CategoryID +'">'+ categoryrez[j].CategoryName +'</option>';
				    	}
			    }
		    $('#MainContent_DropDownAddCategory').html(res);
		  },
	  	 error: function(jqXHR, textStatus, errorThrown){
			alert('Load menu error: ' + textStatus);
		}  
		});
}


function loadAdminAllProduct(){
	$.ajax({
		  type: 'GET',
		  url: rootURL + "getallproducts",
		  success:function(data, textStatus, jqXHR){
			 var res = "";
			    var servers = JSON.parse(data);
			    for(var i = 0; i<servers.length; i++){
			    	res = res + '<option value="'+ i +'">'+ servers[i].ProductName +'</option>';
			    }
		    $('#MainContent_DropDownRemoveProduct').html(res);
		  },
	  	 error: function(jqXHR, textStatus, errorThrown){
			alert('Load menu error: ' + textStatus);
		}  
		});
}