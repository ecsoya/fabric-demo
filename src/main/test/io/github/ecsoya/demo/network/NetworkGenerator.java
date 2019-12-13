package io.github.ecsoya.demo.network;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class NetworkGenerator {

	public static void main(String[] args) {
		String domain = "example.com";
		String[] clients = { "Org1", "Org2" };

		File root = new File("").getAbsoluteFile();

		for (int i = 0; i < clients.length; i++) {
			try {

				JsonObject json = new NetworkBuilder(domain).name("demo-fabric-network").clientOrg(clients[i])
						.ordererOrg("orderer").orderers("orderer").peerOrgs(clients).peers("peer0", "peer1")
						.channels("mychannel").root(new File(root, "first-network/crypto-config"))
						.url("Org1", "*", "localhost").url("Org2", "*", "localhost").url("orderer", "*", "localhost")
						.port("Org1", "peer0", 7051).port("Org1", "peer1", 8051).port("Org2", "peer0", 9051)
						.port("Org2", "peer1", 10051).build();

				Gson gson = new Gson();
				String value = gson.toJson(json);

				Yaml yaml = new Yaml();
				Object map = yaml.load(value);
				String yvalue = yaml.dump(map);

				File file = new File(root, "src/main/resources/network/connection-" + clients[i] + ".yml");
				if (!file.exists()) {
					Files.write(file.toPath(), yvalue.getBytes(), StandardOpenOption.CREATE_NEW,
							StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
				} else {
					Files.write(file.toPath(), yvalue.getBytes(), StandardOpenOption.TRUNCATE_EXISTING,
							StandardOpenOption.WRITE);
				}

			} catch (NetworkBuilderException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
