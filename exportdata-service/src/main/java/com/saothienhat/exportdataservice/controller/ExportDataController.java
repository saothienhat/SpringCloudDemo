package com.saothienhat.exportdataservice.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saothienhat.exportdataservice.model.Employee;
import com.saothienhat.exportdataservice.model.ExcelCell;
import com.saothienhat.exportdataservice.model.ExcelRow;
import com.saothienhat.exportdataservice.model.ExportFileResult;
import com.saothienhat.exportdataservice.service.ExportDataService;

@RestController
@RequestMapping("/export")
public class ExportDataController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExportDataController.class);
	
	@Autowired
	private ExportDataService exportService;
	
	@GetMapping("/excel")
	public ExportFileResult exportExcel() throws IOException {
		LOGGER.info("Exporting Excel file....");
		String path = System.getProperty("user.home") + File.separator + "uploads";
		LOGGER.info("Exporting Excel file....: " + path);
		
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
        
        List<ExcelRow> rowDataList = this.getRowDataList(employees);
        
        ExportFileResult result = exportService.exportExcel("TestFile", "Test Sheet", headers, rowDataList);
		
		return result;
	}
	
	private List<ExcelRow> getRowDataList(List<Employee> employees) {
		List<ExcelRow> rows = new ArrayList<ExcelRow>();
        for (int index = 0; index < employees.size(); index++) {
			Employee employee = employees.get(index);
			ExcelRow row = new ExcelRow();
			row.getCells().add(new ExcelCell<String>(0, employee.getName(), CellType.STRING));
			row.getCells().add(new ExcelCell<String>(1, employee.getEmail(), CellType.STRING));
			row.getCells().add(new ExcelCell<Date>(2, employee.getDateOfBirth(), CellType.STRING));
			row.getCells().add(new ExcelCell<Double>(3, employee.getSalary(), CellType.NUMERIC));
			rows.add(row);
		}
        
		return rows;
	}
	
}
