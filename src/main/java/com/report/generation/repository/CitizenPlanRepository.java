package com.report.generation.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.report.generation.binding.CitizenPlan;

@Repository
public interface CitizenPlanRepository extends JpaRepository<CitizenPlan, Serializable>{
	
	
	 @Query("select distinct(planName) from CitizenPlan") 
	 public List<String> findByPlanNames();
	  
	  @Query("select distinct(planStatus) from CitizenPlan") 
	  public List<String> findByPlanStatuses();
	  
	  
	  
		/*
		 * SELECT * FROM CITIZENS_PLANS_INFO ; insert into CITIZENS_PLANS_INFO values
		 * (10,'suman@gmail.com','suman','male',232323,'NCP','approved',12312332);
		 * 
		 * insert into CITIZENS_PLANS_INFO values
		 * (11,'harsha@gmail.com','harsha','male',34324,'LIC','proceesing',23344324);
		 * 
		 * insert into CITIZENS_PLANS_INFO values
		 * (12,'pavani@gmail.com','pavani','female',65354,'REL','Rejected',67678);
		 */	 

}
