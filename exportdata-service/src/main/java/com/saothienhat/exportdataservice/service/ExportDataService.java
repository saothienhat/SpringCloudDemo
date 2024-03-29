package com.saothienhat.exportdataservice.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
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

import com.saothienhat.exportdataservice.model.ExcelCell;
import com.saothienhat.exportdataservice.model.ExcelRow;
import com.saothienhat.exportdataservice.model.ExportFileResult;

@Service
public class ExportDataService {
	private final Logger LOGGER = LoggerFactory.getLogger(ExportDataService.class);
	
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
	

	/**
	 * Create Cell Style for formatting Date
	 * @param workbook
	 * @return
	 */
	private CellStyle getDateCellStyle(Workbook workbook) {
		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();
		
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		return dateCellStyle;
	}

	/**
	 * Export Excel file.
	 * Refer: https://www.callicoder.com/java-write-excel-file-apache-poi/
	 * @param fileName: exported file Excel name (.xlsx)
	 * @param sheetName: Sheet name
	 * @param headers: list of header
	 * @param rowDataList
	 * @return exportFileResult: result of exporting
	 * @throws IOException
	 */
	public ExportFileResult exportExcel(String fileName, String sheetName, List<String> headers, List<ExcelRow> rowDataList)  throws IOException{
		ExportFileResult exportFileResult = new ExportFileResult();
		Workbook workbook = new XSSFWorkbook();
		int colNo = (headers != null && headers.size() > 0) ? headers.size() : 0;
		
		if(colNo == 0) {
			exportFileResult.setStatus("Exception");
			exportFileResult.setMessage("The number of column in Excel file should more than 0");
			exportFileResult.setFileName(fileName);
			exportFileResult.setExportLocation("");
			return exportFileResult;
		}

		try {
			/*
			 * CreationHelper helps us create instances of various things like DataFormat,
			 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
			 */
			// CreationHelper createHelper = workbook.getCreationHelper();

			// Create a Sheet
			Sheet sheet = workbook.createSheet(sheetName);

			// Create a Font for styling header cells
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 14);
			headerFont.setColor(IndexedColors.RED.getIndex());

			// Create a CellStyle with the font
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			// Create a Row
			Row headerRow = sheet.createRow(0);

			// Create cells
			for (int index = 0; index < headers.size(); index++) {
				Cell cell = headerRow.createCell(index);
				cell.setCellValue(headers.get(index));
				cell.setCellStyle(headerCellStyle);
			}

			// Create Other rows and cells with Row data
			int rowNum = 1;
			for (ExcelRow excelRow : rowDataList) {
				Row row = sheet.createRow(rowNum++);

				List<ExcelCell> cells = excelRow.getCells();
				for (int index = 0; index < cells.size(); index++) {
					ExcelCell cell = cells.get(index);
					CellType cellType = cell.getCellType();
					if(CellType.STRING.name().equalsIgnoreCase(cellType.name())) {
						if(cell.getCellValue() instanceof Date) {
							Cell dateOfBirthCell = row.createCell(cell.getIndex());
							CellStyle dateCellStyle = getDateCellStyle(workbook);
							dateOfBirthCell.setCellValue((Date) cell.getCellValue());
							dateOfBirthCell.setCellStyle(dateCellStyle);
						} else {
							row.createCell(cell.getIndex()).setCellValue((String) cell.getCellValue());
						}
					} else if(CellType.NUMERIC.name().equalsIgnoreCase(cellType.name())) {
						row.createCell(cell.getIndex()).setCellValue((Double) cell.getCellValue());
					}
					
				}
				
			}

			// Resize all columns to fit the content size
			for (int i = 0; i < headers.size(); i++) {
				sheet.autoSizeColumn(i);
			}

			// ===================

			File currDir = new File(".");
			String path = currDir.getAbsolutePath();
			String fileLocation = path.substring(0, path.length() - 1) + fileName + ".xlsx";
			System.out.println("===>  fileLocation: " + fileLocation);

			FileOutputStream outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);

			exportFileResult.setStatus("Done");
			exportFileResult.setMessage("Export Done");
			exportFileResult.setFileName(fileName);
			exportFileResult.setExportLocation(path);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			exportFileResult.setStatus("IOException");
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return exportFileResult;
	}

}
