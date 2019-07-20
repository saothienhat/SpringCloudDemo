package com.saothienhat.exportdataservice.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saothienhat.exportdataservice.model.Employee;
import com.saothienhat.exportdataservice.model.ExportFileResult;
import com.saothienhat.exportdataservice.service.ExportDataService;

@RestController
@RequestMapping("/export")
public class ExportDataController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExportDataController.class);
	
	@Autowired
	private ExportDataService exportService;

//	@GetMapping("/excel")
//	@Produces(MediaType.APPLICATION_OCTET_STREAM)
//	public Response exportExcel(@QueryParam("file") String file) {
//		LOGGER.info("Exporting Excel file....");
//
//		String path = System.getProperty("user.home") + File.separator + "uploads";
//        File fileDownload = new File(path + File.separator + file);
//        ResponseBuilder response = Response.ok((Object) file);
//        response.header("Content-Disposition", "attachment;filename=" + file);
//        return response.build();
//	}
	
//	@GetMapping("/excel")
//	public ResponseEntity<ByteArrayResource> exportExcel() throws IOException {
//		LOGGER.info("Exporting Excel file....");
//		String fileName = "Binh-Text";
//		ByteArrayResource  resource = exportService.export(fileName);
//		return ResponseEntity.ok()
////				.headers(headers) // add headers if any
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
//				.contentLength(resource.contentLength())
//				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);
//	}
	
	@GetMapping("/excel")
	public ExportFileResult exportExcel() throws IOException {
		LOGGER.info("Exporting Excel file....");
		String path = System.getProperty("user.home") + File.separator + "uploads";
		LOGGER.info("Exporting Excel file....: " + path);
		
//		ExportFileResult result = exportService.writeExcel();
		
		// Test data
		List<String> headers = new ArrayList<String>();
		headers.add("Name");
		headers.add("Email");
		headers.add("Date Of Birth");
		headers.add("Salary");
		List<Employee> employees =  new ArrayList<Employee>();
		Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.set(1992, 7, 21);
        employees.add(new Employee("Rajeev Singh", "rajeev@example.com", 
                dateOfBirth.getTime(), 1200000.0));

        dateOfBirth.set(1965, 10, 15);
        employees.add(new Employee("Thomas cook", "thomas@example.com", 
                dateOfBirth.getTime(), 1500000.0));
        dateOfBirth.set(1987, 4, 18);
        employees.add(new Employee("Steve Maiden", "steve@example.com", 
                dateOfBirth.getTime(), 1800000.0));
        
        ExportFileResult result = exportService.exportExcel("TestFile", "Test Sheet", headers, employees);
		
		return result;
	}
	
}
