package com.saothienhat.exportdataservice.model;

/**
 * @author SaoThienHat
 *
 */
public class ExportFileResult {
	private String status;
	private String fileName;
	private String exportLocation;
	private String message;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getExportLocation() {
		return exportLocation;
	}
	public void setExportLocation(String exportLocation) {
		this.exportLocation = exportLocation;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

	
}
