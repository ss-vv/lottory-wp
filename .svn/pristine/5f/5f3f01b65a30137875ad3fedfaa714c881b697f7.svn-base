                    Message Center

如何部署"历史票处理模块"?
=============================
1、mvn clean package -P production
2、上传压缩文件
3、解压并移动到目录 mc_for_history
4、删除 bin/run.sh，避免误启动 mc
5、修改 init.properties 中的 btm 配置文件名，避免和mc的冲突。
6、执行命令，处理历史方案：java JCHistoryTicketChecker <方案id>