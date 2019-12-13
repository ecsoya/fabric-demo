##Spring Fabric Demo

### 1. Build Fabric Network

open Terminal and go to `first-network` folder.

then run command:

`./byfn.sh up -a -s couchdb`

### 2. Generate Fabric Network Config files.

Run `io.github.ecsoya.demo.network.NetworkGenerator` in `src/main/test` folder.

you will get 2 network config files in `src/main/resources/network` folder 

`connection-Org1.yml`

and

`connection-Org2.yml`

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
         file: network/connection-Org1.yml
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

