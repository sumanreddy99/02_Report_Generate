package com.report.generation.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.report.generation.binding.CitizenPlan;
import com.report.generation.binding.SearchRequest;
import com.report.generation.service.ReportService;

@RestController
public class ReportController {

	@Autowired
	public ReportService reportService;

	@GetMapping("/planNames")
	public List<String> getPlanNames() {
		return reportService.getPlanNames();
	}

	@GetMapping("/palnStatus")
	public List<String> getPlanStatus() {
		return reportService.getPlanStatus();
	}

	@PostMapping("/search")
	public List<CitizenPlan> getCitizenPlans(@RequestBody SearchRequest request) {
		return reportService.getCitizenPlans(request);
	}

	@GetMapping("/excel")
	public void exportExcel(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");

		String key = "Content-Disposition";
		String value = "attachment;filename=citizen-plans.xls";

		response.setHeader(key, value);
		reportService.exportExcel(response);
	}

	@GetMapping("/exportpdf")
	public void exportPDF(HttpServletResponse response) throws Exception
	{
		response.setContentType("application/pdf");
		
		String key="Content-Disposition";
		String value="attachemnt;filename=citizen-plan.pdf";
				
		response.setHeader(key, value);
		reportService.exportPDF(response);
	}
}
