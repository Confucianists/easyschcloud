package com.ymy.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name = "sysresource")
public class Resource implements Serializable, Comparable<Resource> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "pid")
	private Resource resource;

	@Transient
	private List<Resource> resources;

	private String name;

	private String url;

	private Long sorts;

	private String resdesc;

	private java.util.Date createtime;

	private String logo;
	@Transient
	private Map<String, Object> state;
	@Transient
	private String icon;

	public String getIcon() {
		return "fa fa-folder icon-state-default";
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Map<String, Object> getState() {
		return state;
	}

	public void setState(Map<String, Object> state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getSorts() {
		return sorts;
	}

	public void setSorts(Long sorts) {
		this.sorts = sorts;
	}

	public String getResdesc() {
		return resdesc;
	}

	public void setResdesc(String resdesc) {
		this.resdesc = resdesc;
	}

	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	@Override
	public int compareTo(Resource rd) {
		return this.getSorts().compareTo(rd.getSorts());
		// if (rd == null) {
		// return 1;
		// }
		// if (this.getSorts() > rd.getSorts())
		// return 1;
		// else if (this.getSorts() == rd.getSorts()) {
		// if (this.getId() > rd.getId()) {
		// return 1;
		// } else {
		// return 0;
		// }
		// } else
		// return 0;
	}

}