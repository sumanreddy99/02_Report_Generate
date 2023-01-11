package com.report.generation.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.report.generation.binding.CitizenPlan;
import com.report.generation.binding.SearchRequest;

public interface ReportService {
	
	public List<String> getPlanNames();
	
	public List<String> getPlanStatus();
	
	public List<CitizenPlan> getCitizenPlans(SearchRequest request);
	
	public void exportExcel(HttpServletResponse response) throws Exception;
	
	public void exportPDF(HttpServletResponse response) throws Exception;;

}
