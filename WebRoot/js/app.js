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
		]
	},
	shim: {
		'bootstrap': ['jquery','bundle'],
		'tooltip': ['jquery']
	}
});

require(['jquery','bootsrtap', 'file'], 
		function($, bootsrtap, file){
	console.log("dfdsfd");
	window.file = file;
});
