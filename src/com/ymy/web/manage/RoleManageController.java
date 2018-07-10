package com.ymy.web.manage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.ymy.entity.Resource;
import com.ymy.entity.ResourceRole;
import com.ymy.entity.Role;
import com.ymy.entity.User;
import com.ymy.service.ResourceRoleService;
import com.ymy.service.ResourceService;
import com.ymy.service.RoleService;
import com.ymy.service.UserService;

@Controller
@RequestMapping(value = "/manage/rolemanage")
public class RoleManageController {
	@Autowired
	RoleService roleService;
	@Autowired
	UserService userService;
	@Autowired
	ResourceRoleService resourceRoleService;
	@Autowired
	ResourceService resourceService;

	@RequestMapping(method = RequestMethod.GET)
	public String List(Model model) {
		return "manage/role/list";
	}

	@RequestMapping(value = "list")
	@ResponseBody
	private Page<Role> getRecordList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pagesize", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, HttpServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		return roleService.getRoles(searchParams, pageNumber, pageSize, sortType);
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(@Valid Role role) {
		Map<String, Object> result = new HashMap<String, Object>();
		roleService.save(role);
		result.put("result", "1");
		return result;
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, Object> update(@Valid Role role) {
		Map<String, Object> result = new HashMap<String, Object>();
		roleService.save(role);
		result.put("result", "1");
		return result;
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "id") Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (userService.getCountByRole(id) > 0) {
			result.put("result", "0");
		} else {
			resourceRoleService.deleteByRoleId(id);
			roleService.delete(id);
			result.put("result", "1");
		}
		return result;
	}

	@RequestMapping(value = "saveroleres")
	@ResponseBody
	public String saveRoleRes(@RequestParam("ids") String ids, @RequestParam("roleId") Long roleId) {
		resourceRoleService.deleteByRoleId(roleId);
		Role r = new Role();
		r.setId(roleId);
		if (ids != null && ids.trim().length() > 0) {
			String[] lids = ids.split("#");
			for (String s : lids) {
				if (s.equals("-1")) {
					continue;
				} else {
					ResourceRole rr = new ResourceRole();
					rr.setRole(r);
					Resource rc = new Resource();
					rc.setId(Long.parseLong(s));
					rr.setResource(rc);
					resourceRoleService.save(rr);
				}
			}
		}
		return "1";
	}
}
