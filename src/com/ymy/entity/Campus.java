package com.ymy.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 校区表
 * 
 * @author ymy
 *
 */
@Entity
@Table(name="campus")
public class Campus {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;//校区名
	
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinColumn(name="receptor_id")
	private Receptor receptor;
	
	private Date createtime;
	
	private String note;
	
	private Long schoolid;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Receptor getReceptor() {
		return receptor;
	}


	public void setReceptor(Receptor receptor) {
		this.receptor = receptor;
	}


	public Date getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public Long getSchoolid() {
		return schoolid;
	}


	public void setSchoolid(Long schoolid) {
		this.schoolid = schoolid;
	}
	
	
}
