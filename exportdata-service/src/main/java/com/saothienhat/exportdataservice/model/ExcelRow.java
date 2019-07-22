package com.saothienhat.exportdataservice.model;

import java.util.ArrayList;
import java.util.List;

public class ExcelRow {
	private List<ExcelCell> cells;
	
	

	public ExcelRow() {
		cells = new ArrayList<ExcelCell>();
	}

	public List<ExcelCell> getCells() {
		return cells;
	}

	public void setCells(List<ExcelCell> cells) {
		this.cells = cells;
	}

	
}
