(function($){
    $.extend({
    	countBetMinFunction:function(pt, val, cur){ 
    		pt = parseInt(pt);
    		var v = function(i){
		        return (pt > (i+1) * 1000 ? Number(val[cur[i]]) : 0);
		    };
		    var min = function (passType,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o){
		    	switch(passType){
		    	case 1001:
		            return a;
		        case 2001:
		            return a*b;
		        case 2003:
		            return Math.min(a,b);
		        case 3001:
		            return a*b*c;
		        case 3003:
		            return Math.min(a*b,a*c,b*c);
		        case 3004:
		            return Math.min(b*c,a*b,a*c);
		        case 3007:
		            return Math.min(b*c,a*b,a*c);
		        case 4001:
		            return a*b*c*d;
		        case 4004:
		            return Math.min(a*b*c,a*b*d,a*c*d,b*c*d);
		        case 4005:
		            return Math.min(a*b*c,a*b*d,a*c*d,b*c*d);
		        case 4006:
		            return Math.min(a*b,a*c,a*d,b*c,b*d,c*d);
		        case 4011:
		            return Math.min(a*b,a*c,a*d,b*c,b*d,c*d);
		        case 4015:
		            return Math.min(a*b,a*c,a*d,b*c,b*d,c*d);
		        case 5001:
		            return a*b*c*d*e;
		        case 5005:
		            return Math.min(a*b*c*d,a*b*c*e,a*b*d*e,a*c*d*e,b*c*d*e);
		        case 5006:
		            return Math.min(a*b*c*d,a*b*c*e,a*b*d*e,a*c*d*e,b*c*d*e);
		        case 5010:
		            return Math.min(a*b,a*c,a*d,a*e,b*c,b*d,b*e,c*d,c*e,d*e);
		        case 5016: 
		            return Math.min(c*d*e,a*d*e,b*d*e,a*c*e,b*c*e,a*b*e,a*c*d,b*c*d,a*b*d,a*b*c);
		        case 5020:
		            return Math.min(a*b,a*c,a*d,a*e,b*c,b*d,b*e,c*d,c*e,d*e);
		        case 5026:
		            return Math.min(a*b,a*c,a*d,a*e,b*c,b*d,b*e,c*d,c*e,d*e);
		        case 5031:
		            return Math.min(a*b,a*c,a*d,a*e,b*c,b*d,b*e,c*d,c*e,d*e);
		        case 6001:
		            return a*b*c*d*e*f;
		        case 6006:
		            return Math.min(a*b*c*d*e,a*b*c*d*f,a*b*c*e*f,a*b*d*e*f,a*c*d*e*f,b*c*d*e*f);
		        case 6007:
		        	return Math.min(a*b*c*d*e,a*b*c*d*f,a*b*c*e*f,a*b*d*e*f,a*c*d*e*f,b*c*d*e*f);
		        case 6015:
		            return Math.min(a*b,a*c,b*c,a*d,b*d,a*e,b*e,a*f,b*f,c*d,c*e,d*e,c*f,d*f,e*f);
		        case 6020:
		            return Math.min(a*b*c,a*b*d,a*b*e,a*b*f,a*c*d,a*c*e,a*c*f,b*c*d,b*c*e,b*c*f,a*d*e,a*d*f,b*d*e,b*d*f,c*d*e,c*d*f,a*e*f,b*e*f,c*e*f,d*e*f);
		        case 6022:
		            return Math.min(a*b*c*d*e,a*b*c*d,a*b*c*e,a*b*c*f,a*b*d*e,a*b*d*f,a*c*d*e,a*c*d*f,b*c*d*e,b*c*d*f,a*b*e*f,a*c*e*f,a*d*e*f,b*c*e*f,b*d*e*f,c*d*e*f);
		        case 6035:
		            return Math.min(a*b,a*c,b*c,a*d,b*d,a*e,b*e,a*f,b*f,c*d,c*e,d*e,c*f,d*f,e*f);
		        case 6042:
		            return Math.min(a*b*c,a*b*d,a*b*e,a*b*f,a*c*d,a*c*e,a*c*f,b*c*d,b*c*e,b*c*f,a*d*e,a*d*f,b*d*e,b*d*f,c*d*e,c*d*f,a*e*f,b*e*f,c*e*f,d*e*f);
		        case 6050:
		            return Math.min(a*b,a*c,b*c,a*d,b*d,a*e,b*e,a*f,b*f,c*d,c*e,d*e,c*f,d*f,e*f);
		        case 6057:
		            return Math.min(a*b,a*c,b*c,a*d,b*d,a*e,b*e,a*f,b*f,c*d,c*e,d*e,c*f,d*f,e*f);
		        case 6063:
		            return Math.min(a*b,a*c,b*c,a*d,b*d,a*e,b*e,a*f,b*f,c*d,c*e,d*e,c*f,d*f,e*f);
		        case 7001:
		            return a*b*c*d*e*f*g;
		        case 7007:
		            return Math.min(a*b*c*d*e*f,a*b*c*d*e*g,a*b*c*d*f*g,a*b*c*e*f*g,a*b*d*e*f*g,a*c*d*e*f*g,b*c*d*e*f*g);
		        case 7008:
		            return Math.min(a*b*c*d*e*f,a*b*c*d*e*g,a*b*c*d*f*g,a*b*c*e*f*g,a*b*d*e*f*g,a*c*d*e*f*g,b*c*d*e*f*g);
		        case 7021:
		            return Math.min(a*b*c*d*e,a*b*c*d*f,a*b*c*d*g,a*b*c*e*f,a*b*c*e*g,a*b*c*f*g,a*b*d*e*f,a*c*d*e*f,b*c*d*e*f,a*b*d*e*g,a*c*d*e*g,b*c*d*e*g,
		            			a*b*d*f*g,a*c*d*f*g,b*c*d*f*g,a*b*e*f*g,a*c*e*f*g,a*d*e*f*g,b*c*e*f*g,b*d*e*f*g,c*d*e*f*g);
		        case 7035:
		            return Math.min(a*b*c*d,a*b*c*e,a*b*c*f,a*b*c*g,a*b*d*e,a*c*d*e,b*c*d*e,a*b*d*f,a*c*d*f,b*c*d*f,a*b*d*g,a*c*d*g,
		            			b*c*d*g,a*b*e*f,a*c*e*f,b*c*e*f,a*b*e*g,a*c*e*g,b*c*e*g,
		            				a*b*f*g,a*c*f*g,b*c*f*g,a*d*e*f,a*d*e*g,a*d*f*g,b*d*e*f,b*d*e*g,b*d*f*g,c*d*e*f,c*d*e*g,c*d*f*g,a*e*f*g,b*e*f*g,c*e*f*g,d*e*f*g);
		        case 7120:
		            return Math.min(a*b,a*c,a*d,a*e,a*f,a*g,b*c,b*d,b*e,b*f,b*g,c*d,c*e,c*f,c*g,d*e,d*f,d*g,e*f,e*g,f*g);
		        case 8001:
		            return a*b*c*d*e*f*g*h;
		        case 8008:
		            return Math.min(a*b*c*d*e*f*g,a*b*c*d*e*f*h,a*b*c*d*e*g*h,a*b*c*d*f*g*h,a*b*c*e*f*g*h,a*b*d*e*f*g*h,a*c*d*e*f*g*h,b*c*d*e*f*g*h);
		        case 8009:
		            return Math.min(a*b*c*d*e*f*g,a*b*c*d*e*f*h,a*b*c*d*e*g*h,a*b*c*d*f*g*h,a*b*c*e*f*g*h,a*b*d*e*f*g*h,a*c*d*e*f*g*h,b*c*d*e*f*g*h);
		        case 8028:
		            return Math.min(a*b*d*e*f*g,a*c*d*e*f*g,b*c*d*e*f*g,a*b*d*e*f*h,a*c*d*e*f*h,b*c*d*e*f*h,a*b*d*e*g*h,a*c*d*e*g*h,b*c*d*e*g*h,a*b*d*f*g*h,a*c*d*f*g*h,b*c*d*f*g*h,
		            		a*b*e*f*g*h,a*c*e*f*g*h,a*d*e*f*g*h,b*c*e*f*g*h,b*d*e*f*g*h,c*d*e*f*g*h,a*b*c*d*e*f,a*b*c*d*e*g,a*b*c*d*e*h,a*b*c*d*f*g,a*b*c*d*f*h,a*b*c*d*g*h,
		            			a*b*c*e*f*g,a*b*c*e*f*h,a*b*c*e*g*h,a*b*c*f*g*h);
		        case 8056:
		            return Math.min(a*b*c*d*e,a*b*c*d*f,a*b*c*d*g,a*b*c*d*h,a*b*c*e*f,a*b*c*e*g,a*b*c*e*h,a*b*c*f*g,a*b*c*f*h,a*b*c*g*h,
		            			a*b*d*e*f,a*c*d*e*f,b*c*d*e*f,a*b*d*e*g,a*c*d*e*g,b*c*d*e*g,a*b*d*e*h,a*c*d*e*h,b*c*d*e*h,a*b*d*f*g,a*c*d*f*g,b*c*d*f*g,
		            				a*b*d*f*h,a*c*d*f*h,b*c*d*f*h,a*b*d*g*h,a*c*d*g*h,b*c*d*g*h,a*b*e*f*g,a*b*e*f*h,a*b*e*g*h,a*c*e*f*g,a*c*e*f*h,a*c*e*g*h,a*d*e*f*g,a*d*e*f*h,a*d*e*g*h,
		            					b*c*e*f*g,b*c*e*f*h,b*c*e*g*h,b*d*e*f*g,b*d*e*f*h,b*d*e*g*h,c*d*e*f*g,c*d*e*f*h,c*d*e*g*h,
		            						a*b*f*g*h,a*c*f*g*h,a*d*f*g*h,a*e*f*g*h,b*c*f*g*h,b*d*f*g*h,b*e*f*g*h,c*d*f*g*h,c*e*f*g*h,d*e*f*g*h);
		        case 8070:
		            return Math.min(a*b*c*d,a*b*c*e,a*b*c*f,a*b*c*g,a*b*c*h,a*b*d*e,a*b*d*f,a*b*d*g,a*b*d*h,a*b*e*f,a*b*e*g,a*b*e*h,a*b*f*g,a*b*f*h,a*b*g*h,
		            			a*c*d*e,a*c*d*f,a*c*d*g,a*c*d*h,a*c*e*f,a*c*e*g,a*c*e*h,a*c*f*g,a*c*f*h,a*c*g*h,
		            				b*c*d*e,b*c*d*f,b*c*d*g,b*c*d*h,b*c*e*f,b*c*e*g,b*c*e*h,b*c*f*g,b*c*f*h,b*c*g*h,
		            					a*d*e*f,a*d*e*g,a*d*e*h,a*d*f*g,a*d*f*h,a*d*g*h,b*d*e*f,b*d*e*g,b*d*e*h,b*d*f*g,b*d*f*h,b*d*g*h,c*d*e*f,c*d*e*g,c*d*e*h,c*d*f*g,c*d*f*h,c*d*g*h,
		            						a*e*f*g,b*e*f*g,c*e*f*g,d*e*f*g,a*e*f*h,b*e*f*h,c*e*f*h,d*e*f*h,a*e*g*h,b*e*g*h,c*e*g*h,d*e*g*h,a*f*g*h,b*f*g*h,c*f*g*h,d*f*g*h,e*f*g*h);
		        case 8247://TODO 未拆最小
		            return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)*(g+1)*(h+1)-(a+b+c+d+e+f+g+h+1);
		        case 9001:
		            return a*b*c*d*e*f*g*h*i;
		        case 10001:
		            return a*b*c*d*e*f*g*h*i*j;
		        case 11001:
		            return a*b*c*d*e*f*g*h*i*j*k;
		        case 12001:
		            return a*b*c*d*e*f*g*h*i*j*k*l;
		        case 13001:
		            return a*b*c*d*e*f*g*h*i*j*k*l*m;
		        case 14001:
		            return a*b*c*d*e*f*g*h*i*j*k*l*m*n;
		        case 15001:
		            return a*b*c*d*e*f*g*h*i*j*k*l*m*n*o;
		        default:
		            return 0;
		    	}
		    };
		    return min(pt,v(0),v(1),v(2),v(3),v(4),v(5),v(6),v(7),v(8),v(9),v(10),v(11),v(12),v(13),v(14));
    	},
    	/**
    	 * 计算串关
    	 * pt : 过关类型。
    	 * val: 玩法对应的所有选择，如胜平负玩法就是[3,1,0]。
    	 * cur: 遍历组合用的状态数组。
    	 * 说明：
    	 *  v(0) 代表第一场比赛的总选项数。
    	 *  v(1) 代表第二场比赛的总选项数。
    	 */
    	countBetChuanguanFunction:function(pt, val, cur){ 
			// 过关方式，算m@n的注数，参数是选项个数
			// 要理解其中的公式，只需要将乘法展开即可。
			pt = parseInt(pt);
			var mn = function (passType,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o){
			    switch(passType){
			        case 1001:
			            return a;
			        case 2001:
			            return a*b;
			        case 2003:
			            return (a+1)*(b+1)-1;
			        case 3001:
			            return a*b*c;
			        case 3003:
			            return a*(b+c)+b*c;
			        case 3004:
			            return (a+1)*b*c+a*(b+c);  // a*b*c + b*c + a*b + a*c
			        case 3007:
			            return (a+1)*(b+1)*(c+1)-1;
			        case 4001:
			            return a*b*c*d;
			        case 4004:
			            return a*b*(c+d)+(a+b)*(c*d);
			        case 4005:
			            return ((a+1)*(b+1)-1)*(c+1)*(d+1)-(a*b+(a+b)*(c+d+1));
			        case 4006:
			            return a*b+(a+b)*(c+d)+c*d;
			        case 4011:
			            return ((a+1)*(b+1)*(c+1)-1)*(d+1)-(a+b+c);
			        case 4015:
			            return (a+1)*(b+1)*(c+1)*(d+1)-1;
			        case 5001:
			            return a*b*c*d*e;
			        case 5005:
			            return a*b*c*(d+e)+(a*(b+c)+b*c)*d*e;
			        case 5006:
			            return a*b*c*(d+e+d*e)+(a*b+a*c+b*c)*d*e;
			        case 5010:
			            return a*(b+c+d+e)+b*(c+d+e)+c*(d+e)+d*e;
			        case 5016:
			            return ((a+1)*(b+1)*(c+1)-1)*(d+1)*(e+1)-(a*b+(a+b)*(c+d+e+1)+c*(d+e+1));
			        case 5020:
			            return (a+1)*b*(c+d+e)+(a+b+1)*c*(d+e)+(a+b+c+1)*d*e+a*(b+c+d+e);
			        case 5026:
			            return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)-(a+b+c+d+e+1);
			        case 5031:
			            return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)-1;
			        case 6001:
			            return a*b*c*d*e*f;
			        case 6006:
			            return a*b*c*(d*e+d*f+e*f)+(a*b+a*c+b*c)*d*e*f;
			        case 6007:
			            return a*b*c*(d*e+d*f+e*f+d*e*f)+(a*b+a*c+b*c)*d*e*f;
			        case 6015:
			            return a*b+(a+b)*(c+d+e+f)+c*d+(c+d)*(e+f)+e*f;
			        case 6020:
			            return a*b*(c+d+e+f)+(a+b)*c*(d+e+f)+(a+b+c)*d*(e+f)+(a+b+c+d)*e*f;
			        case 6022:
			            return a*b*c*d*(e+1)*(f+1)+(a*b*(c+d)+(a+b)*c*d)*((e+1)*(f+1)-1)+(a*b+(a+b)*(c+d)+c*d)*e*f;
			        case 6035:
			            return (a+b)*(c+d+e+f)+c*d+a*b*(c+d+e+f+1)+(a+b)*c*(d+e+f)+((a+b+c+1)*d+c)*(e+f)+(a+b+c+d+1)*e*f;
			        case 6042:
			            return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-(a*(b+c+d+e+f+1)+b*(c+d+e+f+1)+c*(d+e+f+1)+d*(e+f+1)+(e+1)*(f+1));
			        case 6050:
			            return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-(a+b+c+d+e+f+1)-(a*b*(c*d*(e+f)+(c+d)*e*f)+(a+b+a*b)*c*d*e*f);
			        case 6057:
			            return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-(a+b+c+d+e+f+1);
			        case 6063:
			            return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-1;
			        case 7001:
			            return a*b*c*d*e*f*g;
			        case 7007:
			            return a*b*c*(d*(e*(f+g)+f*g)+e*f*g)+(a*b+a*c+b*c)*d*e*f*g;
			        case 7008:
			            return a*b*c*(d*(e*(f+g)+f*g)+e*f*g)+(a*b+a*c+b*c+a*b*c)*d*e*f*g;
			        case 7021:
			            return a*b*c*(d*(e+f+g)+e*(f+g)+f*g)+(a*(b+c)+b*c)*d*(e*f+e*g+f*g)+(a*(b+c+d)+b*(c+d)+c*d)*e*f*g;
			        case 7035:
			            return a*b*c*(d+e+f+g)+(a*b+a*c+b*c)*(d*(e+f+g)+e*(f+g)+f*g)+(a+b+c)*d*(e*(f+g)+f*g)+(a+b+c+d)*e*f*g;
			        case 7120:
			            return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)*(g+1)-(a+b+c+d+e+f+g+1);
			        case 8001:
			            return a*b*c*d*e*f*g*h;
			        case 8008:
			            return a*b*c*d*(e*(f*(g+h)+g*h)+f*g*h)+(a*(b*(c+d)+c*d)+b*c*d)*e*f*g*h;
			        case 8009:
			            return a*b*c*d*(e*(f*(g+h)+g*h)+(e+1)*f*g*h)+(a*(b*(c+d)+c*d)+b*c*d)*e*f*g*h;
			        case 8028:
			            return (a*(b+c)+b*c)*d*(e*(f*(g+h)+g*h)+f*g*h)+(a*(b+c+d)+b*(c+d)+c*d)*e*f*g*h+a*b*c*(d*(e*(f+g+h)+f*(g+h)+g*h)+e*(f*(g+h)+g*h)+f*g*h);
			        case 8056:
			            return a*b*c*(d*(e+f+g+h)+e*(f+g+h)+f*(g+h)+g*h)+(a*(b+c)+b*c)*d*(e*(f+g+h)+f*(g+h)+g*h)+(a*(b+c+d)+b*(c+d)+c*d)*e*(f*(g+h)+g*h)+(a*(b+c+d+e)+b*(c+d+e)+c*(d+e)+d*e)*f*g*h;
			        case 8070:
			            return a*b*(c*(d+e+f+g+h)+d*(e+f+g+h)+e*(f+g+h)+f*(g+h)+g*h)+(a+b)*c*(d*(e+f+g+h)+e*(f+g+h)+f*(g+h)+g*h)+(a+b+c)*d*(e*(f+g+h)+f*(g+h)+g*h)+(a+b+c+d)*e*(f*(g+h)+g*h)+(a+b+c+d+e)*f*g*h;
			        case 8247:
			            return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)*(g+1)*(h+1)-(a+b+c+d+e+f+g+h+1);
			        case 9001:
			            return a*b*c*d*e*f*g*h*i;
			        case 10001:
			            return a*b*c*d*e*f*g*h*i*j;
			        case 11001:
			            return a*b*c*d*e*f*g*h*i*j*k;
			        case 12001:
			            return a*b*c*d*e*f*g*h*i*j*k*l;
			        case 13001:
			            return a*b*c*d*e*f*g*h*i*j*k*l*m;
			        case 14001:
			            return a*b*c*d*e*f*g*h*i*j*k*l*m*n;
			        case 15001:
			            return a*b*c*d*e*f*g*h*i*j*k*l*m*n*o;
			        default:
			            return 0;
			    }
		    };
			var v = function(i){
		        return (pt > (i+1) * 1000 ? Number(val[cur[i]]) : 0);
		    };
		    return mn(pt,v(0),v(1),v(2),v(3),v(4),v(5),v(6),v(7),v(8),v(9),v(10),v(11),v(12),v(13),v(14));    
		},
		calculateBetData:function (mn,ms,val,excludeCompArray,danListIndex,opt){
			var hasDanCode = function (danIndex,valIndexArray,m){
				if(danIndex.length < 1){
					return true;
				}
				var valObj = {};
				for (var i = 0; i < m; i++) {
					valObj[""+valIndexArray[i]]=valIndexArray[i];
				}
				var ret = true;
				for (var i = 0; i < danIndex.length; i++) {
					if(danIndex[i].length > 1){//同一场比赛选了多个玩法的设胆情况
						var a = false;
						for (var j = 0; j < danIndex[i].length; j++) {
							if(danIndex[i][j] in valObj){
								a = true;//有一个胆在选项里面就行
							}
						}
						if(!a){
							ret = false;
							break;
						}
					} else {//同一场比赛只选了一个玩法设胆情况
						if(undefined != danIndex[i][0] && !(danIndex[i][0] in valObj)){
							ret = false;
							break;
						}
					}
				}
				return ret;
			};
			var isCantainExcludeVal = function (valIndexArray,excludeCompArray){
		    	for (var j = 0; j < excludeCompArray.length; j++) {
		    		var count=0;
		    		for (var i = 0; i < valIndexArray.length; i++) {
						for (var k = 0; k < excludeCompArray[j].length; k++) {
							if(parseInt(excludeCompArray[j][k])==parseInt(valIndexArray[i])){
								count ++;
							}
							if(count > 1){
								return true;
							}
						}
					}
				}
		    	return false;
		    };
			//cMax是下标索引最大值，passMax是第一个下标对应值的最大值 
            //c是组合下标状态, [0,1,2...]表示3场比赛的选取下标
            var i = 0, cMAX = mn[0] - 1, passMax = ms - mn[0] + 1;
    		var valIndex = [0, 0, 0, 0, 0, 0, 0, 0];
    		var returnVal=0;
            while(valIndex[0] < passMax){
                for(i++; i < mn[0]; i++){
                    valIndex[i] = valIndex[i - 1] + 1;
                }
                while(valIndex[cMAX] < ms){
                	//当当前组合存在胆码或不需要胆码时方可计算
                	var valIndexArray = valIndex.slice(0,cMAX+1);
                	if(isCantainExcludeVal(valIndexArray,excludeCompArray) == false
                			&& hasDanCode(danListIndex,valIndexArray,mn[0])){
                		var passNum = parseInt(mn[0]*1000)+parseInt(mn[1]);
	                	if(opt == "min"){
	                		var v = $.countBetMinFunction(passNum,val,valIndex);
	                		if(returnVal ==0 || v < returnVal){
		                		returnVal = v;
		                	}
	                	} else {
	                		var v = $.countBetChuanguanFunction(passNum,val,valIndex);
	                		returnVal+=v;
	                	}
                	}
                    valIndex[cMAX]++;
                }
                i = cMAX - 1;
                while(i >= 0){
                    if (valIndex[i] + 1 < valIndex[i + 1]) {
                        valIndex[i]++;
                        break;
                    }
                    i--;
                }
            }
            return returnVal;
		}
    });
})(jQuery);