define('api',['jquery'], function() {
	return {
	    lexicalAnalysis : function() {	
			$.post( "./lexer/readFile", { code: $("#source-code").text()}, function( data ) {
				  // $( "#symbol" ).html( data );
			$.each(data, function(i, item) {
					$("#symbol").append("<tr>" +
						"<th scope='row'>"+item.name.word+"</th>"+
						"<td>"+item.name.word+"</td>"+
						"<td>"+item.token+"</td>"+
						"<td>"+item.name.length+"</td>"+
						"<td>"+item.val+"</td>"+"</tr>");
			});
			});
	    }
	}
});