package com.ymy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ymy.entity.Checkin;
import com.ymy.entity.Course;

public interface CheckinDao  extends PagingAndSortingRepository<Checkin, Long>, JpaSpecificationExecutor<Checkin>{
	@Query(value="select * from checkin where studentid=?1 and schoolid=?2 order by createtime desc",nativeQuery=true)
	public List<Checkin> getListByStudentid(Long studentid,Long schoolid);
}
