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
			 * 
			 * @param {Function} func
			 * @returns Function
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
			 * 
			 * @param 
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
							if(typeof _super[name] !== "undefined"){
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
			 * 对类（Function）或者类实例（Object）进行扩展，使其函数支持this._super方式调用父类函数。<br>
			 * 
			 * 使用方法：extend.apply(); 根据this和self参数不同，_super取不同值:<br>
			 * <ul>
			 * <li>typeof this === 'object' 时， _super = this.constructor._super；表示对类实例进行动态扩展</li>
			 * <li>typeof this !== 'object' 且self !== true时， _super = this.prototype；表示对父类进行继承并扩展</li>
			 * <li>typeof this !== 'object' 且self === true时， _super = this._super；表示对类本身进行扩展</li>
			 * </ul>
			 * 
			 * @param {Object} prototype 被扩充的原型对象
			 * @param {Object} option 子类定义
			 * @param {Boolean} self 保持父类属性，只是对接口进行扩展
			 * @returns Jooe
			 */
			var extend = function(prototype, option, self){		
				//保存父类prototype接口，以供子类使用this._super调用
				var _super = this.prototype;
				
				if(typeof this === 'object'){
					//this is an instance(object) of class
					_super = this.constructor._super;
				}else if(self === true){
					//this is class(function)
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
			 * Jooe = Javascript Object-Oriented Extend<br>
			 * 在实现Javascript基本面向对象过程外，对子类函数增加this._super支持，子类函数可以通过
			 * this._super对父类函数进行引用。<br>
			 * 主要功能为：
			 * <ul>
			 * <li>实现静态面向对象过程，产生新的子类。（见用法一）</li>
			 * <li>实现对类自身进行扩展，不产生子类，在静态和动态（Runtime）过程中子类都能看到父类的扩展。（见用法二）</li>
			 * <li>实现对类实例进行动态（Runtime）扩展。（见用法三）</li>
			 * </ul>
			 * 
			 * <br>
			 * 用法一：静态面向对象实现,产生子类。 Jooe.extend({})<br>
			 * 例如：<br>
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
			 * <br>
			 * 用法二：实现对类自身进行扩展，不产生子类。<br>
			 * 例如：<br>
			 * <pre>
			 * 在用法一基础上对Person进行扩展：
			 * var peter1 = new Peter();
			 * Person = Person.extend(true, {
			 * 		cry: function(){
			 * 			alert("Person cry");
			 * 		}
			 * });
			 * var peter2 = new Peter();
			 * 
			 * peter1.cry(); //ok
			 * peter2.cry(); //ok
			 * </pre>
			 * 
			 * <br>
			 * 用法三：实现对类实例进行动态（Runtime）扩展。<br>
			 * 例如：<br>
			 * <pre>
			 * 在用法一基础上对Peter进行扩展：
			 * var peter1 = new Peter();
			 * var peter2 = new Peter();
			 * Jooe.extend(peter1, {
			 * 		hello: function(){
			 * 			alert("say hello");
			 * 		}
			 * });
			 * 
			 * peter1.hello(); //ok
			 * peter2.hello(); //exception
			 * </pre>
			 * 
			 * @returns Jooe
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
				}else if(typeof self === "object"){
					return extend.apply(self, [self, option, true]);
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
			window.Jooe = Jooe;
		}
	}catch(e){
		
	}
	
})(window);