package com.saothienhat.exportdataservice.model;

import org.apache.poi.ss.usermodel.CellType;

public class ExcelCell<T> {
	private int index;
	private T cellValue;
	private CellType cellType;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public T getCellValue() {
		return cellValue;
	}
	public void setCellValue(T cellValue) {
		this.cellValue = cellValue;
	}
	public CellType getCellType() {
		return cellType;
	}
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}
	public ExcelCell(int index, T cellValue, CellType cellType) {
		this.index = index;
		this.cellValue = cellValue;
		this.cellType = cellType;
	}

	
	
}
