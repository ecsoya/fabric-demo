package io.github.ecsoya.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.github.ecsoya.fabric.FabricPagination;
import io.github.ecsoya.fabric.FabricPaginationQuery;
import io.github.ecsoya.fabric.FabricResponse;
import io.github.ecsoya.fabric.bean.FabricObject;
import io.github.ecsoya.fabric.service.IFabricService;

@RestController
public class FabricDemoController {

	@Autowired
	private IFabricService fabricService;

	@RequestMapping("/")
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

	@RequestMapping("/list")
	public FabricPagination<FabricObject> list(FabricPaginationQuery<FabricObject> query) {
		return fabricService.pagination(query);
	}

	@RequestMapping("/add")
	public FabricResponse add(FabricObject object) {
		return fabricService.create(object);
	}

	@RequestMapping("/update")
	public FabricResponse update(FabricObject object) {
		return fabricService.update(object);
	}

	@RequestMapping("/remove")
	public FabricResponse remove(FabricObject object) {
		return fabricService.delete(object.getId(), object.getType());
	}
}
