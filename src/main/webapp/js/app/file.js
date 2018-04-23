define(['jquery'], function($) {
	return {
		readFiles : function (files) {
	          if (files.length) {
	              var file = files[0];
	              var reader = new FileReader();//new一个FileReader实例
	              if (/text+/.test(file.type)) {//判断文件类型，是不是text类型
	                  reader.onload = function() {
	                      $('#source-code').append('<pre>' + this.result + '</pre>');
	                 }
	                 reader.readAsText(file, 'utf-8');
	             } else if(/image+/.test(file.type)) {//判断文件是不是imgage类型
                reader.onload = function() {
	                    $('#source-code').append('<img src="' + this.result + '"/>');
	                 }
	                 reader.readAsDataURL(file);
	             }
	         }
	     }
	};
});