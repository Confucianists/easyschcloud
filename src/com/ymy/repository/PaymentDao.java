package com.ymy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ymy.entity.Payment;
import com.ymy.entity.Resource;

public interface PaymentDao extends PagingAndSortingRepository<Payment, Long>, JpaSpecificationExecutor<Payment>{
	//获取近一周数据
	@Query(value="SELECT DATE_FORMAT(createtime,'%Y-%m-%d'),sum(discountprice) FROM payment WHERE createtime>DATE_SUB(NOW(),INTERVAL 7 DAY) GROUP BY DATE_FORMAT(createtime,'%Y-%m-%d') ORDER BY DATE_FORMAT(createtime,'%Y-%m-%d') DESC",nativeQuery=true)
	public List<Object[]> getRecentWeek();
	
	//获取近一个月收费总额
	@Query(value="SELECT DATE_FORMAT(createtime,'%Y-%m-%d'),sum(discountprice) FROM payment WHERE createtime>DATE_SUB(NOW(),INTERVAL 1 MONTH)  GROUP BY DATE_FORMAT(createtime,'%Y-%m-%d') ORDER BY DATE_FORMAT(createtime,'%Y-%m-%d') DESC",nativeQuery=true)
	public List<Object[]> getRecentMonth();
		
	//获取近三个月收费总额
	@Query(value="SELECT DATE_FORMAT(createtime,'%Y-%m-%d'),sum(discountprice) FROM payment WHERE createtime>DATE_SUB(NOW(),INTERVAL 3 MONTH)  GROUP BY DATE_FORMAT(createtime,'%Y-%m-%d') ORDER BY DATE_FORMAT(createtime,'%Y-%m-%d') DESC",nativeQuery=true)
	public List<Object[]> getRecentThreeMonth();
			
	//获取近六个月收费总额
	@Query(value="SELECT DATE_FORMAT(createtime,'%Y-%m-%d'),sum(discountprice) FROM payment WHERE createtime>DATE_SUB(NOW(),INTERVAL 6 MONTH)  GROUP BY DATE_FORMAT(createtime,'%Y-%m-%d') ORDER BY DATE_FORMAT(createtime,'%Y-%m-%d') DESC",nativeQuery=true)
	public List<Object[]> getRecentSixMonth();
		
	//获取本月收费总额
	@Query(value="select sum(discountprice) from payment where date_format(createtime,'%Y-%m')=date_format(now(),'%Y-%m')",nativeQuery=true)
	public Double getCurrentMonth();
}
