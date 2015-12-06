## 通过proto命令编译定义好的数据结构
* 注意不同版本的proto生成的结构差异较大，这里使用的是2.5版本 *
进入到当前目录执行如下命令：
<pre>
	protoc2.5.exe BetSchemeVo.proto --java_out=..\..\main\java\
</pre>

没有报错，表示生成成功；刷新项目即可查看新生成的proto存根java文件