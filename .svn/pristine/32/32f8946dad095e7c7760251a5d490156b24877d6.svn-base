/**
 * author : Yang Bo (bob.yang.dev@gmail.com)
 */
Jet().$package(function(J){

	// 日志方法，部署时需要去掉，否则IE8以下报错。
	function log(msg){
		console.log('BitSet: ' + msg);
	}
	
	/**
	 * BitSet 二进制位集合.以二进制方式存放信息.支持大于32位的长度。
	 */
	var BitSet = function(){};
	BitSet.prototype = {
		/**
		 * 构造最大长度为 length 的 BitSet.
		 * @param length 最大长度
		 */
		init: function(length){
			this.INT_BITS = 32;
			this.className = 'BitSet';
			this.maxBitsNum = length;
			this.maxIntNum = Math.ceil(length / 32);
			this.buf = new Array();
			var nb = this.buf;
			// 初始化为0
			for (var i=0; i<this.maxIntNum; i++){
				nb[i] = 0;
			}
		},
		/**
		 * 设置 index 下标的位为1.
		 * @return this object
		 */
    	setAt: function(index){
    		var n = Math.floor(index / this.INT_BITS);
    		var idx = index % this.INT_BITS;
    		this.buf[n] |= (1 << idx);
    		return this;
    	},
    	/**
    	 * 设置所有index为1
    	 */
    	setAll: function(){
    		for(var index=0;index<this.buf.length;index++){
        		this.buf[index] = 0xFFFFFFFF;
    		}
    		return this;
    	},
    	/**
    	 * 设置所有index为0
    	 */
    	removeAll: function(){
    		for(var index=0;index<this.buf.length;index++){
    			this.buf[index] = 0;
    		}
    		return this;
    	},
    	/**
    	 * 设置 index 处的值为0
    	 */
    	clearAt: function(index) {
    		var n = Math.floor(index / this.INT_BITS);
    		var idx = index % this.INT_BITS;
    		this.buf[n] &= ~(1 << idx);
    		return this;
    	},
    	toggleAt: function(index) {
    		var n = Math.floor(index / this.INT_BITS);
    		var idx = index % this.INT_BITS;
    		var mask = 0xFFFFFFFF;
    		var mask2 = 1 << idx;
    		this.buf[n] ^= mask;
    		this.buf[n] ^= mask2;
    		return this;
    	},
    	/**
    	 * @returns 0 or 1
    	 */
    	getAt: function(index){
    		var n = Math.floor(index / this.INT_BITS);
    		var idx = index % this.INT_BITS;
    		var r = this.buf[n] & (1 << idx);
    		if (r==0) return 0;
    		else return 1;
    	},
    	/**
    	 * 比较是否和另外一个 BitSet 相同
    	 * @param other BitSet 对象
    	 * @returns {Boolean}
    	 */
    	equals: function(other){
    		if (other.className != "BitSet") {
    			return false;
    		}
    		if (other.buf.length != this.buf.length){
    			return false;
    		}
    		for (var i=0; i<this.buf.length; i++){
    			if (other.buf[i] != this.buf[i]){
    				return false;
    			}
    		}
    		return true;
    	},
    	/**
    	 * 是否被1填满
    	 */
    	isFull:function(){
    		return this.size()==this.maxBitsNum;
    	},
    	/**
    	 * 1的个数。
    	 * @returns
    	 */
    	size: function(){
    		var oneCount = 0;
    		for (var i=0; i<this.buf.length; i++){
    			var n = this.buf[i]; 
    			for (var j=0; j<this.INT_BITS; j++){
    				var t = n>>>j;
    				if ((t & 1)==1){
    					oneCount++;
    				} 
    			}
    		}
    		return oneCount;
    	},
    	/**
    	 * 与上 other BitSet，返回一个新的 BitSet 对象。
    	 */
    	and: function(other){
    		if (other.className != "BitSet") {
    			throw '不能 and 非BitSet对象。';
    		}
    		var bs = new BitSet(other.maxBitsNum);
    		for (var i=0; i<this.buf.length; i++){
    			bs.buf[i] = this.buf[i] & other.buf[i];
    		}
    		return bs;
    	},
    	or: function(other){
    		if (other.className != "BitSet") {
    			throw '不能 or 非BitSet对象。';
    		}
    		var bs = new BitSet(other.maxBitsNum);
    		for (var i=0; i<this.buf.length; i++){
    			bs.buf[i] = this.buf[i] | other.buf[i];
    		}
    		return bs;
    	}
    };
	BitSet = Jooe.extend(BitSet.prototype);
	this.BitSet = BitSet;
});