package com.report.generation.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import com.lowagie.text.pdf.PdfWriter;
import com.report.generation.binding.CitizenPlan;
import com.report.generation.binding.SearchRequest;
import com.report.generation.repository.CitizenPlanRepository;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	public CitizenPlanRepository cpRepo;

	@Override
	public List<String> getPlanNames() {

		return cpRepo.findByPlanNames();
	}

	@Override
	public List<String> getPlanStatus() {

		return cpRepo.findByPlanStatuses();
	}

	@Override
	public List<CitizenPlan> getCitizenPlans(SearchRequest request) {

		CitizenPlan citizenPlan = new CitizenPlan();

		if ((request.getPlanName() != null) && !(request.getPlanName().equals(""))) {
			citizenPlan.setPlanName(request.getPlanName());
		}

		if ((request.getPlanStatus() != null && !(request.getPlanStatus().equals("")))) {
			citizenPlan.setPlanStatus(request.getPlanStatus());
		}

		Example<CitizenPlan> example = Example.of(citizenPlan);
		List<CitizenPlan> citizenPlans = cpRepo.findAll(example);
		return citizenPlans;
	}

	@Override
	public void exportExcel(HttpServletResponse response) throws Exception {

		List<CitizenPlan> citizenPlan = cpRepo.findAll();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Citizen_Palns");

		HSSFRow row = sheet.createRow(0);

		row.createCell(0).setCellValue("cid");
		row.createCell(1).setCellValue("planName");
		row.createCell(2).setCellValue("planStatus");
		row.createCell(3).setCellValue("cname");
		row.createCell(4).setCellValue("cemail");
		row.createCell(5).setCellValue("gender");
		row.createCell(6).setCellValue("phno");
		row.createCell(7).setCellValue("ssn");

		int rowIndex = 1;
		for (CitizenPlan plan : citizenPlan) {
			HSSFRow hssfRow = sheet.createRow(rowIndex);

			hssfRow.createCell(0).setCellValue(plan.getCid());
			hssfRow.createCell(1).setCellValue(plan.getPlanName());
			hssfRow.createCell(2).setCellValue(plan.getPlanStatus());
			hssfRow.createCell(3).setCellValue(plan.getCname());
			hssfRow.createCell(4).setCellValue(plan.getCemail());
			hssfRow.createCell(5).setCellValue(plan.getGender());
			hssfRow.createCell(6).setCellValue(plan.getPhno());
			hssfRow.createCell(7).setCellValue(plan.getSsn());
			rowIndex++;
		}

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}

	@Override
	public void exportPDF(HttpServletResponse response) throws Exception{

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();

		Paragraph paragraph = new Paragraph("Citizen Plan....");
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(paragraph);

		PdfPTable pdfTable = new PdfPTable(8);

		pdfTable.setWidthPercentage(100f);
		pdfTable.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2});
		pdfTable.setSpacingBefore(10);

		// Set table headers
		PdfPCell cell = new PdfPCell();

		cell.setBackgroundColor(CMYKColor.BLUE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(CMYKColor.WHITE);

		cell.setPhrase(new Phrase("cid", font));
		pdfTable.addCell(cell);

		cell.setPhrase(new Phrase("cemail", font));
		pdfTable.addCell(cell);
		
		cell.setPhrase(new Phrase("cname", font));
		pdfTable.addCell(cell);
	

		cell.setPhrase(new Phrase("gender", font));
		pdfTable.addCell(cell);

		cell.setPhrase(new Phrase("phno", font));
		pdfTable.addCell(cell);

		cell.setPhrase(new Phrase("planName", font));
		pdfTable.addCell(cell);

		cell.setPhrase(new Phrase("planStatus", font));
		pdfTable.addCell(cell);

		cell.setPhrase(new Phrase("ssn", font));
		pdfTable.addCell(cell);
		// Set table data

		List<CitizenPlan> citizenPlan = cpRepo.findAll();

		for (CitizenPlan ciPlan : citizenPlan) {
			pdfTable.addCell(String.valueOf(ciPlan.getCid()));
			pdfTable.addCell(ciPlan.getCemail());
			pdfTable.addCell(ciPlan.getCname());
			
			pdfTable.addCell(ciPlan.getGender());
			pdfTable.addCell(String.valueOf(ciPlan.getPhno()));
			pdfTable.addCell(ciPlan.getPlanName());
			pdfTable.addCell(ciPlan.getPlanStatus());
			pdfTable.addCell(String.valueOf(ciPlan.getSsn()));
		}

		document.add(pdfTable);

		document.close();

	}

}
