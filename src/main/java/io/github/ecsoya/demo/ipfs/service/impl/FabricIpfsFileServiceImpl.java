package io.github.ecsoya.demo.ipfs.service.impl;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecsoya.ipfs.gateway.IpfsGateway;
import com.ecsoya.ipfs.gateway.domain.IpfsFile;
import com.ecsoya.ipfs.gateway.exception.IpfsFileException;
import com.ecsoya.ipfs.gateway.service.IIpfsFileGatewayService;

import io.github.ecsoya.demo.ipfs.FabricIpfsFile;
import io.github.ecsoya.demo.ipfs.service.IFabricIpfsFileService;
import io.github.ecsoya.fabric.FabricResponse;
import io.github.ecsoya.fabric.config.FabricContext;
import io.github.ecsoya.fabric.service.impl.AbstractFabricService;

@Service
public class FabricIpfsFileServiceImpl extends AbstractFabricService<FabricIpfsFile> implements IFabricIpfsFileService {

	@Autowired
	private IIpfsFileGatewayService ipfsService;

	@Autowired
	public FabricIpfsFileServiceImpl(FabricContext fabricContext) {
		super(fabricContext);
	}

	@Override
	protected String getFabricType() {
		return FabricIpfsFile.TYPE;
	}

	@Override
	public FabricResponse upload(IpfsGateway gateway, MultipartFile file, String id, String name, String owner) {
		if (gateway == null || file == null) {
			return FabricResponse.fail("Invalid parameters");
		}
		try {
			FabricIpfsFile fabricFile = new FabricIpfsFile();
			if (id == null || id.equals("")) {
				fabricFile.setId(Long.toString(System.currentTimeMillis()));
			} else {
				fabricFile.setId(id);
			}
			byte[] bytes = file.getBytes();
			fabricFile.setContentType(file.getContentType());
			if (ObjectUtils.isEmpty(name)) {
				name = file.getOriginalFilename();
			}
			fabricFile.setName(name);
			IpfsFile ipfs = ipfsService.uploadFile(gateway, bytes, name);
			if (ipfs != null) {
				fabricFile.setHash(ipfs.getHash());
				fabricFile.setUrl(ipfs.getUrl());
			}
			fabricFile.setOwner(owner);
			fabricFile.setCreateTime(new Date());
			fabricFile.setLength(file.getSize());
			if (id == null || id.equals("")) {
				return create(fabricFile);
			} else {
				return update(fabricFile);
			}
		} catch (IpfsFileException e) {
			return FabricResponse.fail("Upload file failed: " + e.getLocalizedMessage());
		} catch (IOException e) {
			return FabricResponse.fail("Read file failed: " + e.getLocalizedMessage());
		}
	}

}
