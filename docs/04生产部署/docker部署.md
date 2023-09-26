# docker部署

## 文件说明
```text
.
├── Dockerfile              # docker镜像配置文件
├── docker-compose.yml      # docker-compose脚本
├── exec.sh                 # 快速进入容器
├── logs.sh                 # 快速查看容器日志
├── restart.sh              # 重新构建并启动容器
├── start.sh                # 启动容器
└── stop.sh                 # 停止容器
```

## 准备工作

1. 将docker目录下的文件放到服务器的`/root/product/zeta_kotlin`文件夹下
2. 给shell脚本添加可执行权限
```text
cd /root/product/zeta_kotlin
chmod +x *.sh
```
 
**tips: 如果遇到下面的错误**

> /bin/bash^M: 解释器错误: No such file or directory

使用sed命令`sed -i "s/\r//" filename` 或者 `sed -i "s/^M//" filename`直接替换结尾符为unix格式

例如：
```bash
# 替换start.sh文件的结尾符
sed -i "s/\r//" start.sh

# 替换当前目录下所有sh文件的结尾符
sed -i "s/\r//" *.sh
```

## 打包

见[打包部署.md](./打包部署.md)


## run

将打包成功后的`zeta-kotlin.jar`上传到服务器的`/root/product/zeta_kotlin`文件夹下

运行脚本
```bash
./restart.sh
```
