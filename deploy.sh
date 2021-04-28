#!/bin/bash
echo "==============="
echo "DEPLOY fabric/demo"
echo "==============="

VERSION=2.0.0

echo "Build Version:" ${VERSION}

echo "1. Build ecsoya/fabric docker image."
# -t 表示指定镜像仓库名称/镜像名称:镜像标签 .表示使用当前目录下的Dockerfile
docker build -t fabric/demo:${VERSION} /home/fabric/demo

echo "2. Run fabric containers"
echo "   remove:"
docker stop fabric-demo || true && docker rm fabric-demo || true
echo "   create:"
docker run --restart=always -it -d -p 8081:8081 -v /home/fabric/demo/logs:/logs -v /etc/localtime:/etc/localtime -e TZ='GMT+08' --name fabric-demo fabric/demo:${VERSION}

docker ps -a

echo "==============="
echo "DEPLOY FINISHED"
echo "==============="

