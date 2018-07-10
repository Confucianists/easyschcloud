package com.ymy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ymy.entity.Teacher;
import com.ymy.entity.Will;

public interface WillDao extends PagingAndSortingRepository<Will, Long>,JpaSpecificationExecutor<Will>{
	//获取近一周数据
	@Query(value="SELECT DATE_FORMAT(createtime,'%Y-%m-%d'),COUNT(*) FROM will WHERE createtime>DATE_SUB(NOW(),INTERVAL 7 DAY) AND STATUS=1 GROUP BY DATE_FORMAT(createtime,'%Y-%m-%d') ORDER BY DATE_FORMAT(createtime,'%Y-%m-%d') DESC",nativeQuery=true)
	public List<Object[]> getRecentWeek();
	
	//获取近一个月数据
	@Query(value="SELECT DATE_FORMAT(createtime,'%Y-%m-%d'),COUNT(*) FROM will WHERE createtime>DATE_SUB(NOW(),INTERVAL 1 MONTH) AND STATUS=1 GROUP BY DATE_FORMAT(createtime,'%Y-%m-%d') ORDER BY DATE_FORMAT(createtime,'%Y-%m-%d') DESC",nativeQuery=true)
	public List<Object[]> getRecentMonth();
	
	//获取近三个月数据
	@Query(value="SELECT DATE_FORMAT(createtime,'%Y-%m-%d'),COUNT(*) FROM will WHERE createtime>DATE_SUB(NOW(),INTERVAL 3 MONTH) AND STATUS=1 GROUP BY DATE_FORMAT(createtime,'%Y-%m-%d') ORDER BY DATE_FORMAT(createtime,'%Y-%m-%d') DESC",nativeQuery=true)
	public List<Object[]> getRecentThreeMonth();
		
	//获取近六个月数据
	@Query(value="SELECT DATE_FORMAT(createtime,'%Y-%m-%d'),COUNT(*) FROM will WHERE createtime>DATE_SUB(NOW(),INTERVAL 6 MONTH) AND STATUS=1 GROUP BY DATE_FORMAT(createtime,'%Y-%m-%d') ORDER BY DATE_FORMAT(createtime,'%Y-%m-%d') DESC",nativeQuery=true)
	public List<Object[]> getRecentSixMonth();
	
	//获取本月报名人数
	@Query(value="select count(*) from will where date_format(createtime,'%Y-%m')=date_format(now(),'%Y-%m') AND STATUS=1",nativeQuery=true)
	public int getCurrentMonth();
}
