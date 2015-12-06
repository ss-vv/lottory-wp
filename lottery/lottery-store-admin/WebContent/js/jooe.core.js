/*
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 */

/**
 * Javascript 面向对象支持，样例如下：<br>
 * <pre>
 * var Person = Jooe.extend({
 * 	init: function(){
 * 	},
 * 	smile: function(){
 * 		alert("Person smile");
 * 	}
 * });
 * 
 * var Peter = Person.extend({
 * 	init: function(){
 * 	},
 * 	smile: function(){
 * 		this._super.smile();
 * 		alert("Peter smile");
 * 	}
 * });
 * 
 * 推荐使用另外一种写法：
 * 1.声明类对象：Person = function(){}
 * 2.声明类定义：Person.prototype = {}
 * 3.执行类继承：Person = Jooe.extend(Person.prototype);
 * 
 * var Person = function(){};
 * Person.prototype = {
 * 	init: function(){
 * 	},
 * 	smile: function(){
 * 		alert("Person smile");
 * 	}
 * };
 * Person = Jooe.extend(Person.prototype);
 * 
 * var Peter = function(){};
 * Peter.prototype = {
 * 	init: function(){
 * 	},
 * 	smile: function(){
 * 		this._super.smile();
 * 		alert("Peter smile");
 * 	}
 * };
 * Peter = Person.extend(Peter.prototyp);
 * </pre>
 * 
 * @author hdzhong
 * @version 1.0.1
 */
;(function(window, undefined){
	var Jooe = window.Jooe,
	//探测函数内是否含有this._super.*的引用，如果有则重写调用函数，以支持对父类的调用
	fnTest = /xyz/.test(function(){xyz;}) ? /\bthis\._super\b/ : /.*/;
	
	try{
		//检查Jooe是否已经存在
		if(typeof Jooe === "undefined"){
			/**
			 * 内部函数，对this._super.func中的func进行封装，保证被调用时候使用正确的this指针
			 */
			var delegate = function(func){
				return (function(context, fn){
					return function(){
						var ret = fn.apply(context, arguments);
						return ret;
					};
				})(this, func);
			};
			
			var invokeFinder = /xyz/.test(function(){xyz;}) ? /\bthis\._super\.([\$\w_]+)/mg : false;
			/**
			 * 内部函数，在子类扩展时候，如果子类函数包含this._super的调用，则对该函数进行代理，
			 * 使其在执行时候支持通过this._super方式对父类进行引用
			 */
			var proxy = function(_super, name, func){
				/*分析子类函数， 获得所有对父类调用成员 */
				var invokes = (function(func){
					if(invokeFinder !== false){
						//支持对子函数源代码分析
						invokes = [];
						var names;
						while( (names = invokeFinder.exec(func)) != null){
							invokes.push(names[1]);
						}
						return invokes;
					}
					return false;
				})(func);
				
				var _getSuper = function(){
					//FIXME 去掉缓存方式，换取动态类扩展功能，会带来性能问题？
					this._super = {};
					if(invokes !== false){
						//根据分析后的调用成员创建this._super信息
						for(var i=0; i < invokes.length; i++){
							var name = invokes[i];
							if(_super[name] !== undefined){
								if(typeof _super[name] == "function"){
	            					this._super[name] = delegate.apply(this, [ _super[name] ]);//delegate the function to use current "this"
	            				}else{
	            					this._super[name] = _super[name];
	            				}
							}
						}
					}else{
						//如果无法对调用成员进行分析，回退到对所有父类成员进行封装
						for(var name in _super){
            				if(name == "constructor")
            					continue;
            				if(typeof _super[name] == "function"){
            					this._super[name] = delegate.apply(this, [ _super[name] ]);//delegate the function to use current "this"
            				}else{
            					this._super[name] = _super[name];
            				}
            			}
					}
				};
				
				return (function(fn){
	                  return function() {
	                	  //注入this._super
		                  _getSuper.apply(this);
		                  //实际调用函数
		                  var ret = fn.apply(this, arguments);
		                   
		                  return ret;
		              };
		        })(func);
			};
			/**
			 * @param prototype 父类原型实例
			 * @param option 子类定义
			 * @param self 保持父类属性，只是对接口进行扩展
			 */
			var extend = function(prototype, option, self){		
				//保存父类prototype接口，以供子类使用this._super调用
				var _super = this.prototype;
				if(self === true){
					_super = this._super;
				}

				//覆盖父类属性和方法
				for(var name in option){
					var copy = option[ name ];
					
					if ( copy !== undefined ) {//忽略未赋值属性
						/**
						 * 1. 如果子类属性为函数，同时如果子类函数包含了_super单词，则对该函数进行重写，支持this._super.*调用；<br>
						 * 2. 如果第1条不满足，直接覆盖父类属性
						 */
						prototype[ name ] = typeof copy == "function"
				        	&& fnTest.test(copy) ? proxy.apply(this, [_super, name, copy]) : copy;
					}
				}
				return prototype;
			};
			
			var Jooe = function(){
			};
			Jooe.version = "1.0.1";
			/**
			 * var Person = Jooe.extend({
			 * 	init: function(){
			 * 	},
			 * 	smile: function(){
			 * 		alert("Person smile");
			 * 	}
			 * });
			 * 
			 * var Peter = Person.extend({
			 * 	init: function(){
			 * 	},
			 * 	smile: function(){
			 * 		this._super.smile();
			 * 		alert("Peter smile");
			 * 	}
			 * });
			 * 
			 * 推荐使用另外一种写法：
			 * 1.声明类对象：Person = function(){}
			 * 2.声明类定义：Person.prototype = {}
			 * 3.执行类继承：Person = Jooe.extend(Person.prototype);
			 * 
			 * var Person = function(){};
			 * Person.prototype = {
			 * 	init: function(){
			 * 	},
			 * 	smile: function(){
			 * 		alert("Person smile");
			 * 	}
			 * };
			 * Person = Jooe.extend(Person.prototype);对内部代理函数proxy()进行重写，实现以下功能或解决Bug：
1、去掉原来的缓存加速方式（可能会带来性能下降）
2、
			 * 
			 * var Peter = function(){};
			 * Peter.prototype = {
			 * 	init: function(){
			 * 	},
			 * 	smile: function(){
			 * 		this._super.smile();
			 * 		alert("Peter smile");
			 * 	}
			 * };
			 * Peter = Person.extend(Peter.prototyp);
			 */
			Jooe.extend = function(self, option){
				if(option == null){
					option = self || {};
					self = false;
				}
				//option.init = option.init || function(){};
				
				if(self === true){
					/*just extend self interface*/
					this.prototype = extend.apply(this,[this.prototype, option, self]);
					return this;
				}
				
				/**
				 * 临时类，主要用于继承父类
				 * 
				 * @ignore 
				 */
				var tempClass = function(){};
				//扩展父类属性
				tempClass.prototype = this.prototype;
				
				/**
				 * 创建prototype实体，
				 * 
				 * @ignore
				 */
				var prototype = new tempClass();
				
				//子类属性扩展/覆盖
				prototype = extend.apply(this,[prototype, option]);
				
				/*create new class*/
				var subClass = function(){
					if(this.init){
						this.init.apply(this, arguments);
					}
				};
				
				//继承父类原型
				subClass.prototype = prototype;
				//重新指定构造函数，确保this.init方法被调用，类的真正初始化操作可以在this.init()方法实现
				subClass.prototype.constructor = subClass;
				//保证每个子类都含有extend方法
				subClass.extend = arguments.callee;
				subClass._super = this.prototype;
				return subClass;
			};
		}
		window.Jooe = Jooe;
	}catch(e){
		
	}
	
})(window);