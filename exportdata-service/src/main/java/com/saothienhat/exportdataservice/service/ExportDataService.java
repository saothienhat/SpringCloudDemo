package com.saothienhat.exportdataservice.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.saothienhat.exportdataservice.model.ExportFileResult;

@Service
public class ExportDataService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportDataService.class);

	public ByteArrayResource  export(String fileName) throws IOException {
		 byte[] bytes = new byte[1024];
	      try (Workbook workbook = generateExcel()) {
//	          FileOutputStream fos = write(workbook, fileName);
	    	  
	    	  FileOutputStream fos = new FileOutputStream(fileName);
		      workbook.write(fos);
	    	  
	          fos.write(bytes);
	          fos.flush();
	          fos.close();
	      }

	      return new ByteArrayResource(bytes);
	}

	private Workbook generateExcel() {
		Workbook workbook = new XSSFWorkbook();

		try {
			Sheet sheet = workbook.createSheet("Persons");
			sheet.setColumnWidth(0, 6000);
			sheet.setColumnWidth(1, 4000);

			Row header = sheet.createRow(0);

			CellStyle headerStyle = workbook.createCellStyle();

			headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			XSSFFont font = ((XSSFWorkbook) workbook).createFont();
			font.setFontName("Arial");
			font.setFontHeightInPoints((short) 16);
			font.setBold(true);
			headerStyle.setFont(font);

			Cell headerCell = header.createCell(0);
			headerCell.setCellValue("Name");
			headerCell.setCellStyle(headerStyle);

			headerCell = header.createCell(1);
			headerCell.setCellValue("Age");
			headerCell.setCellStyle(headerStyle);

			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);

			Row row = sheet.createRow(2);
			Cell cell = row.createCell(0);
			cell.setCellValue("John Smith");
			cell.setCellStyle(style);

			cell = row.createCell(1);
			cell.setCellValue(20);
			cell.setCellStyle(style);

			File currDir = new File(".");
			String path = currDir.getAbsolutePath();
			String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

			FileOutputStream outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//	            if (workbook != null) {
//	                    try {
//							workbook.close();
//						} catch (IOException e) {
//							LOGGER.error(e.getMessage());
//							e.printStackTrace();
//						}
//	            }
		}

		return workbook;
	}

//	  private FileOutputStream write(final Workbook workbook, final String filename) throws IOException {
//	      FileOutputStream fos = new FileOutputStream(filename);
//	      workbook.write(fos);
//	      fos.close();
//	      return fos;
//	  }
	
	
	public ExportFileResult writeExcel() throws IOException {
		ExportFileResult exportFileResult = new ExportFileResult();
        Workbook workbook = new XSSFWorkbook();

        try {
            Sheet sheet = workbook.createSheet("Persons");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);

            Row header = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();

            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);
            headerStyle.setFont(font);

            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("Name");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(1);
            headerCell.setCellValue("Age");
            headerCell.setCellStyle(headerStyle);

            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);

            Row row = sheet.createRow(2);
            Cell cell = row.createCell(0);
            cell.setCellValue("John Smith");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(20);
            cell.setCellStyle(style);

            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileName = "temp.xlsx";
			String fileLocation = path.substring(0, path.length() - 1) + fileName;

            LOGGER.info("Export done ! Path: " + fileLocation);
            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            
            exportFileResult.setStatus("Done");
            exportFileResult.setMessage("Export Done");
            exportFileResult.setFileName(fileName);
            exportFileResult.setExportLocation(path);
        } catch (IOException e) {
			LOGGER.error(e.getMessage());
			exportFileResult.setStatus("IOException");
		} 
        finally {
            if (workbook != null) {
                    workbook.close();
            }
        }
        return exportFileResult;
    }

}
