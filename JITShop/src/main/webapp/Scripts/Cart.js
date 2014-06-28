var rootURL = "http://localhost:8080/JITShop/rest/";

$(document).ready(function(){
	var user = checkCookie();
	$("#MainContent_UpdateBtn").click(function(){
		var n = $( "input:checked.C" ).length;
		   var arr="";
		   for(i=0;i<n;++i){
		   if(i > 0)
		       arr = arr +",";
		       arr = arr + $($( "input:checked.C" )[i]).val();
		   }
		   $.ajax({
				  type: 'DELETE',
				  url: rootURL + "cart4/"+user+","+arr,
				  success:function(data, textStatus, jqXHR){
					  loadDataToPagw(user);
				  },
			  	 error: function(jqXHR, textStatus, errorThrown){
					alert('Delete data from cart page error: ' + textStatus);
				}  
				});
	 });
});

function addToChart(ProductID){
	var user = checkCookie();
	if(user != ""){
		returnCartPage();
		addDataToChart(user, ProductID);
		
	}
	else
		alert("Log In in first");
}

function goToChart(){
	var user = checkCookie();
	if(user != ""){
		returnCartPage();
		loadDataToPagw(user);
	}
	else
		alert("Log In in first");
}

/*Return Cart page*/
function returnCartPage(){
	  $.ajax({
		  type: 'GET',
		  url: rootURL + "cart",
		  success:function(data, textStatus, jqXHR){
		    $("#page2").html(data);
		  },
	  	 error: function(jqXHR, textStatus, errorThrown){
			alert('Cart page error: ' + textStatus);
		}  
		});
}


function addDataToChart(user, ProductID){
	
	$.ajax({
		type: "POST",
		url: rootURL + "cart2", 
		data: user +"_"+ProductID,
		contentType:"text/html",
		
		error: function(jqXHR, textStatus, errorThrown) {
			alert("Add data to chart Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
		},
		success: function(data, textStatus, jqXHR){
			loadDataToPagw(user);
		},
		complete: function(XMLHttpRequest) {
		}, 
	});
}

function loadDataToPagw(user){
	
	$.ajax({
		  type: 'GET',
		  url: rootURL + "cart3/"+user,
		  //data: user,
		  success:function(data, textStatus, jqXHR){
			  var res = "";
			    var servers = JSON.parse(data);
			    for(var i = 0; i<servers.length; i++){
				res = res +'<tr><td>'+servers[i].ProductID+'</td>'+
					'<td>'+servers[i].ProductName+'</td>'+
					'<td>'+servers[i].UnitPrice+'$.</td>'+
					'<td>'+(servers[i].Quantity)+'</td>'+
					'<td>'+(servers[i].UnitPrice * servers[i].Quantity)+'$.</td>'+
					'<td><input class="C" value="'+servers[i].ProductID+'" type="checkbox" id="MainContent_CartList_Remove_'+i+'"></td></tr>';
			    }
		    $("#cart_table").html(res);
		  },
	  	 error: function(jqXHR, textStatus, errorThrown){
			alert('Load data to cart page error: ' + textStatus);
		}  
		});
}
