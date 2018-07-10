package com.ymy.web.manage;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.ymy.entity.Resource;
import com.ymy.entity.ResourceRole;
import com.ymy.entity.Role;
import com.ymy.mixinSource.ResourceMixin_resource;
import com.ymy.mixinSource.ResourceMixin_resources;
import com.ymy.service.ResourceRoleService;
import com.ymy.service.ResourceService;
import com.ymy.service.RoleService;
import com.ymy.utils.Util;

@Controller
@RequestMapping(value = "/manage/resourcemanage")
public class ResourceManageController {

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ResourceRoleService resourceRoleService;

	@RequestMapping()
	public String list() {
		return "manage/resource/list";
	}

	@RequestMapping(value = "treedata")
	public void getTreeData(HttpServletResponse response, @RequestParam(value = "rid", required = false) Long rid) {
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		List<Resource> lr = resourceService.getAllResource(rid);
		ObjectMapper mapper = new ObjectMapper();
		SerializationConfig serializationConfig = mapper.getSerializationConfig();
		serializationConfig.addMixInAnnotations(Resource.class, ResourceMixin_resource.class);
		try {
			mapper.writeValue(response.getOutputStream(), lr);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(@Valid Resource resource) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (resource.getResource() != null && resource.getResource().getId() == null) {
			resource.setResource(null);
		}
		resourceService.save(resource);
		result.put("result", "1");
		return result;
	}

	@RequestMapping(value = "getdata")
	public void getData(@RequestParam(value = "id") Long id, HttpServletResponse response) {
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		SerializationConfig serializationConfig = mapper.getSerializationConfig();
		serializationConfig.addMixInAnnotations(Resource.class, ResourceMixin_resources.class);
		try {
			mapper.writeValue(response.getOutputStream(), resourceService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "id") Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "1");
		resourceService.deleteSubResouece(id);
		return result;
	}
}
