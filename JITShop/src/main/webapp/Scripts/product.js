var rootURL = "http://localhost:8080/JITShop/rest/";

$(document).ready(function(){
	 $("#a_about").click(function(){
		 
	 });
});

function productdetails(id){
	$.ajax({
		  type: 'GET',
		  url: rootURL + "productdetails?id=" + id,
		  success:function(data, textStatus, jqXHR){
			var res = "";
			    var servers = JSON.parse(data);
			    image = 'ImagesCatalogIMG/' + GetThumbsPath(servers[0].CategoryID) +'/Thumbs/'+ servers[0].ImagePath;
			    res = '<div><h1>ACER Intel Pentium 2117U</h1></div><br><table><tbody><tr>'+
			    '<td><img src="'+ image +'" alt="'+ servers[0].ProductName +'" style="border: solid currentColor; border-image: none; height: 300px;">'+ 
			    '</td><td>&nbsp;</td><td style="text-align: left; vertical-align: top;"><b>Description:</b><br>'+ servers[0].Description +'<br><span><b>Price:</b>&nbsp;'+ servers[0].UnitPrice +'$.</span><br><span><b>Product'+ 
			    '  Number:</b>&nbsp;1</span><br>'+//<input type="submit" onclick="goBack()" value="Back"> '+
			     '</td></tr></tbody></table>';
			    $('#page2').html(res);
			  },
		  	 error: function(jqXHR, textStatus, errorThrown){
			alert('4to-to error: ' + textStatus);
		}  
	});
}

function goBack() {
    window.history.back();
}