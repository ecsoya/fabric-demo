package io.github.ecsoya.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;

import io.github.ecsoya.fabric.FabricPagination;
import io.github.ecsoya.fabric.FabricPaginationQuery;
import io.github.ecsoya.fabric.FabricResponse;
import io.github.ecsoya.fabric.bean.FabricObject;
import io.github.ecsoya.fabric.service.IFabricObjectService;

@RestController
public class FabricDemoController {

	@Autowired
	private IFabricObjectService fabricService;

	@GetMapping("/")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("baseURL", baseUrl(request));
		return modelAndView;
	}

	private String baseUrl(HttpServletRequest request) {
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int port = request.getServerPort();
		String path = request.getContextPath();
		return scheme + "://" + serverName + ":" + port + path;
	}

	@GetMapping("/list")
	public FabricPagination<FabricObject> list(FabricPaginationQuery<FabricObject> query, String value) {
		if (value != null && !value.equals("")) {
			JsonObject json = new JsonObject();
			json.addProperty("value", value);
			query.equals("values", json);
		}
		return fabricService.pagination(query);
	}

	@GetMapping("/add")
	public FabricResponse add(FabricObject object) {
		return fabricService.create(object);
	}

	@GetMapping("/update")
	public FabricResponse update(FabricObject object) {
		return fabricService.update(object);
	}

	@GetMapping("/remove")
	public FabricResponse remove(FabricObject object) {
		return fabricService.delete(object.getId(), object.getType());
	}
}
