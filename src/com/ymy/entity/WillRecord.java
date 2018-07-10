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
 * 潜在学员跟踪记录表
 * 
 * @author ymy
 *
 */
@Entity
@Table(name="willrecord")
public class WillRecord {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name="will_id")
	private Will will;
	
	private Integer type;//������ʽ 1�ֳ�2�绰3΢��
	
	private String note;//��ͨ����
	
	private Date nexttime;
	
	private Integer nexttype;//������ʽ 1�ֳ�2�绰3΢��

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Will getWill() {
		return will;
	}

	public void setWill(Will will) {
		this.will = will;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getNexttime() {
		return nexttime;
	}

	public void setNexttime(Date nexttime) {
		this.nexttime = nexttime;
	}

	public Integer getNexttype() {
		return nexttype;
	}

	public void setNexttype(Integer nexttype) {
		this.nexttype = nexttype;
	}
	
	
}
