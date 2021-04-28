[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.ecsoya/spring-fabric-gateway/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/io.github.ecsoya/spring-fabric-gateway)


##Spring Fabric Demo


This demo is updated by using Hyperledger Fabric [v2.3.2](https://hyperledger-fabric.readthedocs.io/en/release-2.3/whatis.html) Network now.

[[bootstrap.sh]](https://raw.githubusercontent.com/hyperledger/fabric/master/scripts/bootstrap.sh)

`./bootstrap.sh 2.3.2 1.5.0 -s`

If you still use Fabric 1.4.x network, please check out branch 1.0.6.

[在线演示](http://139.155.177.74:8081/)

### 1. Build Fabric Network

open Terminal and go to `test-network` folder.

then run command:

i. Build Network with channel

`./network.sh up createChannel -c mychannel -s couchdb`

ii. Deploy chaincode 

`./network.sh deployCC -ccn mycc -ccv 1.0 -ccp ../src/chaincode/common -ccl go`

### 2. Generate Fabric Network Config files.

Run `io.github.ecsoya.demo.network.NetworkGenerator` in `src/main/test` folder.

you will get 2 network config files in `src/main/resources/network` folder 

`connection-org1.yml`

and

`connection-org2.yml`

*QA: Why to using custom NetworkGenerator?*

Yes, after build your network by using test-network script files, you will find these 2 files `connection-org1.yml` and `connection-org2.yml` under `organizations/peerOrganizations/org1.example.com` and `organizations/peerOrganizations/org2.example.com` folder.

Firstly, these files does not contain the user identity (admin certificate...) to access wallet.
Secondly, these files does not mapping the DNS to IP correctly.

### 3. Configure `application.yml`

```
spring:
   mvc:
      locale: zh_CN
      locale-resolver: fixed
   fabric:
      chaincode: 
         identify: mycc
         name: Common Chaincode
         version: 1.0
      channel: mychannel
      peers: 2
      organizations:
      - Org1
      - Org2
      name: Common Fabric Network
      gateway:
         wallet:
            identify: admin
      network:
         file: network/connection-org1.yml
         name: example-fabric
# Fabric explorer
      explorer: 
         title: Fabric Explorer
#         logo: img/logo.png
         copyright: Ecsoya (jin.liu@soyatec.com)
         hyperledger-explorer-url: http://www.hyperleder.org
         path: /explorer
```

### Run & Test

Run `DemoApplication`

then open [http://localhost:8080/explorer](http://localhost:8080/explorer) to view the fabric explorer.

