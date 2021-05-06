package io.github.ecsoya.demo.ipfs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecsoya.ipfs.gateway.IpfsGateway;
import com.google.gson.JsonObject;

import io.github.ecsoya.demo.ipfs.FabricIpfsFile;
import io.github.ecsoya.demo.ipfs.service.IFabricIpfsFileService;
import io.github.ecsoya.fabric.FabricPagination;
import io.github.ecsoya.fabric.FabricPaginationQuery;
import io.github.ecsoya.fabric.FabricResponse;

@Controller
@RequestMapping("/ipfs")
public class FabricIpfsController {

	@Autowired
	private IFabricIpfsFileService fabricService;

	@GetMapping
	public String index(HttpServletRequest request, ModelMap mmap) {
		mmap.put("baseURL", baseUrl(request));
		return "ipfs/index";
	}

	private String baseUrl(HttpServletRequest request) {
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int port = request.getServerPort();
		String path = request.getContextPath();
		return scheme + "://" + serverName + ":" + port + path;
	}

	@GetMapping("/list")
	@ResponseBody
	public FabricPagination<FabricIpfsFile> list(FabricPaginationQuery<FabricIpfsFile> query, String name) {
		if (!ObjectUtils.isEmpty(name)) {
			JsonObject json = new JsonObject();
			json.addProperty("name", name);
			query.equals("values", json);
		}
		query.setType(FabricIpfsFile.TYPE);
		return fabricService.pagination(query);
	}

	@PostMapping("/add")
	@ResponseBody
	public FabricResponse add(IpfsGateway gateway, MultipartFile file, String name, String owner) {
		return fabricService.upload(gateway, file, name, owner);
	}

}
