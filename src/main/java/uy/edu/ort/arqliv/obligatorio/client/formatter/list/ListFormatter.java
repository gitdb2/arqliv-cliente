package uy.edu.ort.arqliv.obligatorio.client.formatter.list;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.arqliv.obligatorio.common.utils.Duple;

public class ListFormatter {
	
	List<Duple<Integer, String>> columnTitles = new ArrayList<>();
	List<List<Object>> columnValues = new ArrayList<>();
	
	public ListFormatter() {
	}

	public ListFormatter(List<Duple<Integer, String>> columnTitles,
			List<List<Object>> columnValues) {
		super();
		this.columnTitles = columnTitles;
		this.columnValues = columnValues;
	}
	
}
