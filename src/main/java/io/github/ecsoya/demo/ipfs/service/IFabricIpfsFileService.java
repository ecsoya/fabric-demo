package io.github.ecsoya.demo.ipfs.service;

import org.springframework.web.multipart.MultipartFile;

import com.ecsoya.ipfs.gateway.IpfsGateway;

import io.github.ecsoya.demo.ipfs.FabricIpfsFile;
import io.github.ecsoya.fabric.FabricResponse;
import io.github.ecsoya.fabric.service.IFabricCommonService;
import io.github.ecsoya.fabric.service.IFabricService;

public interface IFabricIpfsFileService extends IFabricService<FabricIpfsFile>, IFabricCommonService<FabricIpfsFile> {

	/**
	 * Upload file to IPFS and store to fabric
	 * 
	 * @param gateway
	 * @param file
	 * @param name
	 * @param owner
	 * @return
	 */
	FabricResponse upload(IpfsGateway gateway, MultipartFile file, String id, String name, String owner);
}
