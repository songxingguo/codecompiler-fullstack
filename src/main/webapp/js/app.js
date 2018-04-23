requirejs.config({
    baseUrl: './js/',
	paths: {
		'jquery' : [
			'lib/jquery-3.3.1.min',
			'https://cdn.bootcss.com/jquery/3.3.1/jquery.min'
		],
		'bootsrtap' : [
			'lib/bootstrap.bundle.min'
		],
		'tooltip' : [
			'lib/tooltip'
		],
		'file': [
			'app/file'
		],
		'api': [
			'app/api'
		]
	},
	shim: {
		'bootstrap': ['jquery','bundle'],
		'tooltip': ['jquery']
	}
});

require(['jquery','bootsrtap', 'file', 'api'], 
		function($, bootsrtap, file, api){
	$("#save").click(function() {
		api.getUser();
	});
	
	$("#autoShow").click(function() {
		api.lexicalAnalysis();
	});
	window.file = file;
});
