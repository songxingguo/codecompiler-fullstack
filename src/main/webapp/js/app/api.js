define('api',['jquery'], function() {
	return {
		getUser : function() {
			$.get( "./lexer/readFile", function( data ) {
				  $( ".result" ).html( data );
				  alert( "Load was performed." );
			});
		}
	}
});