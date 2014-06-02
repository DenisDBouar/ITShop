var rootURL = "http://localhost:8080/JITShop/rest/";

$(document).ready(function(){
	
  loadmenu();
	
  /*Return About page*/
  $("#a_about").click(function(){
	  $.ajax({
		  type: 'GET',
		  url: rootURL + "about",
		  success:function(data, textStatus, jqXHR){
		    $("#page2").html(data);
		  },
	  	 error: function(jqXHR, textStatus, errorThrown){
			alert('4to-to error: ' + textStatus);
		}  
		});
  });
  
  /*Return Contact page*/
  $("#a_Contact").click(function(){
	  $.ajax({
		  type: 'GET',
		  url: rootURL + "contact",
		  success:function(data, textStatus, jqXHR){
		    $("#page2").html(data);
		  },
	  	 error: function(jqXHR, textStatus, errorThrown){
			alert('4to-to error: ' + textStatus);
		}  
		});
  });
  
  /*Return Product List page by search*/
  $("#Search").click(function(){
	  loadproductslist("productlistserch?val=" + $("#ss").val());
  });

});

function GetThumbsPath(categoryIdTemp)
{
    var str = "";
    switch (categoryIdTemp)
    {
        case 1:
            str = "Laptops";
            break;
        case 2:
            str = "DesktopPCs";
            break;
        case 3:
            str = "Tablets";
            break;
        case 8:
            str = "GigitCamera";
            break;
        case 4:
            str = "Televisions";
            break;
        case 11:
            str = "Xbox";
            break;
    }
    return str;
}

function loadmenu(){
	$.ajax({
		  type: 'GET',
		  url: rootURL + "menu",
		  success:function(data, textStatus, jqXHR){
			var res = "";
			    var servers = JSON.parse(data);
			    for(var i = 0; i<servers.length; i++){
			    	res = res + '<li class="has-sub">';
			    	res = res + '<a href="http://localhost:24019/ProductList?id=%202#"><span>'+ servers[i].GroupName +'</span></a><ul>';
				    	var categoryrez = JSON.parse(servers[i].Category);
				    	for(var j = 0; j<categoryrez.length; j++){
				    		res = res + '<li><a href="#" onclick="loadproductslist(\'productlist?id='+ categoryrez[j].CategoryID +'\')"><span>'+ categoryrez[j].CategoryName +'</span></a></li>';
				    	}
			    	res = res + '</ul></li">';
			    }
		    $('#cssmenu_content').html(res);
		  },
	  	 error: function(jqXHR, textStatus, errorThrown){
			alert('4to-to error: ' + textStatus);
		}  
		});
}

function loadproductslist(prodid){
	  $.ajax({
		  type: 'GET',
		  url: rootURL + prodid,
		  success:function(data, textStatus, jqXHR){
			 
			var res = "";
			    var servers = JSON.parse(data);
			    var tagtr=0;
			    for(var i = 0; i<servers.length; i++){
			    	if(tagtr == 0){
			    		res = "<tr>" + res;}
			    	else if(tagtr == 4){
				    		res = res + "</tr>";
				    		tagtr =0;
				    	}
			    	tagtr = tagtr +1;
			    	addchart = rootURL + 'href="#" onclick="productdetails(\''+ servers[i].ProductID +'\')"';
			    	image = 'ImagesCatalogIMG/' + GetThumbsPath(servers[i].CategoryID) +'/Thumbs/'+ servers[i].ImagePath;
			    	res = res + '<td class="wrap_cell">'+
			    	'<div style="margin-right: 8px; margin-bottom: 15px; margin-left: 8px; background-color: white;" onmouseover="mOver(this)" onmouseout="mOut(this)">' +
				    '<table><tbody><tr><td><a '+addchart+'><img src="'+image+'" height="94" width="125"></a> </td></tr>'+
				        '<tr><td id="price_center"><a '+addchart+'>'+ servers[i].ProductName +'</a>'+
				                  '<p class="price-ship">Free Shipping</p><br><b>Price: '+
				                  '</b>'+ servers[i].UnitPrice +' $<br><a href="http://localhost:24019/AddToCart.aspx?productID=32">'+
				                  '<b>Add To Cart<b></b></b><b><b></b></b></a><b><b></b></b></td></tr>'+
				       '<tr><td>&nbsp;</td></tr></tbody></table></div>'+
				      '<p class="wrap_graphics"></p>'
				    '</td>';
			    }
			res = '<table> <tbody id="MainContent_for_productList">' + res  + "</tbody></table>";
		    $('#page2').html(res);
		  },
	  	 error: function(jqXHR, textStatus, errorThrown){
			alert('4to-to error: ' + textStatus);
		}  
		});
}