function assert(t, tname, msg){
	if (t){
		console.log(tname+'- PASS. ' + msg);
	}else{
		console.log(tname+'- FAIL: ' + msg);
	}
}

function assertTrue(t, tname, msg){
	assert(t==true, tname, msg);
}

function assertFalse(t, tname, msg){
	assert(t==false, tname, msg);
}

var b = new BitSet(64);
b.setAt(3);
var b2 = new BitSet(64);
b2.setAt(3);

var r=b.equals([1,2]);
assertFalse(r, 't1', "测试equals，should be false, when other is not BitSet class.");

r = b.equals(b2);
assertTrue(r, 't2', "测试equals，should be true, when other is BitSet class and have the same content.");

b.setAt(31);
r=b.size();
assertTrue(r==2, 't3', "测试size，应该有2个1。");

b.setAt(32);
r=b.size();
assertTrue(r==3, 't4', "测试size，应该有3个1。");

try{
	b.and(1);
}catch(e){
	assertTrue(e!=null, 't5', "测试and操作，要检查参数类型，如果不是BitSet类要抛出异常。");
}

var b = new BitSet(64);
b.setAt(3);
var b2 = new BitSet(64);
b2.setAt(3);
var r = b.and(b2);
assertTrue(r.getAt(3)==1, 't6', "测试and运算，应该返回1.");

var b = new BitSet(128);
b.setAt(31);
assertTrue(b.getAt(31)==1, 't7', "测试and运算，31位应该是1。");

var b = new BitSet(64);
b.setAt(31);
var b2 = new BitSet(64);
b2.setAt(32);
var r = b.or(b2);
assertTrue(r.getAt(31), 't8', "测试or运算, 31位应该是1。");
assertTrue(r.getAt(32), 't9', "测试or运算, 32位应该是1。");

var b = new BitSet(64);
b.setAt(30);
b.setAt(31);
b.setAt(32);
b.clearAt(31);
assertTrue(b.getAt(30)==1, 't10', "测试clearAt运算, 30位应该是1。");
assertTrue(b.getAt(31)==0, 't11', "测试clearAt运算, 31位应该是1。");
assertTrue(b.getAt(32)==1, 't12', "测试clearAt运算, 32位应该是1。");

var b = new BitSet(64);
b.setAt(30);
b.toggleAt(30);
assertTrue(b.getAt(30)==0, 't13', "测试toggleAt运算, 30位应该是1。");

var b = new BitSet(64);
b.setAll();
assertTrue(b.isFull()==true, 't13', "测试setAll运算, 30位应该是1。");
