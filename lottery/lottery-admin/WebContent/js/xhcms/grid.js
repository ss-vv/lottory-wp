Jet().$package(function(J){
	var $ = jQuery;
	var dataGrid = function(){};
	
	dataGrid.prototype = {
		init: function(option){
			this.$grid = option.grid;
			this.url = option.url;
			this.notFound = option.notFound || '没有查到相关数据';
			this.renderers = option.renderers;
			this.wrap = option.wrap || null;
			this.bindEvent = option.bindEvent || function(){};
			this.readParams = option.readParams || function(){ return null; };
		},
		
		_wrap: function(data){
			var grid = this.$grid, len = data.length - 1, rs = this.renderers;
            
			grid.find('tr:gt(0)').remove();
            
			if(len == -1){
				grid.append('<tr class="last"><td class="tdr" colspan="' + rs.length + '">' + this.notFound + '</td></tr>');
			}else if(this.wrap != null){
				this.wrap(data);
			}else{
	            $(data).each(function(i){
	            	var html = [], data = this;
	            	if(i == len){
	            		html.push('<tr class="last">');
	            	}else{
	            		html.push('<tr>');
	            	}

	            	for(var j = 0, rlen = rs.length - 1; j <= rlen; j++){
	            		if(rlen == j){
	            			html.push($.format('<td class="tdr">{0}</td>', rs[j](i, data)));
	            		}else{
	            			html.push($.format('<td>{0}</td>', rs[j](i, data)));
	            		}
	            	}

	            	html.push('</tr>');
	            	
	            	$(html.join('')).data(this).appendTo(grid);
	            });
	            
	            if(this.bindEvent){
	            	this.bindEvent();
	            }
			}
		},
		
		load: function(){
			var ctx = this, params = this.readParams();
			if(params == null || $.isPlainObject(params)){
				$.getJSON(this.url, params, function(json){
			        if(json.success){
			            ctx._wrap(json.data);
			        }
			    });
			}
		}
	};
	
	dataGrid = Jooe.extend(dataGrid.prototype);
	this.DataGrid = dataGrid;
});