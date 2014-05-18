package uy.edu.ort.arqliv.obligatorio.client.menus.profiling;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uy.edu.ort.arqliv.obligatorio.client.menus.pdf.PDFRenderer;

public class UtilsMenuProfiling {

	public static final String DATE_PARAMETER_MSG = "Fecha a consultar (formato yyyyMMdd): ";
	
	public static final String NO_RECORDS_FOUND_MSG = "No se encontraron datos para la fecha ";
	
	public static Date parseDate(String toParse) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.parse(toParse);
	}
	
	public static void printToSysOut(String titles, List<String> lines) {
		System.out.println(titles);
		for (String line : lines) {
			System.out.println(line);
		}
		System.out.println("-------------o-------------");
	}
	
	public static void printToPdf(String lineTitles, List<String> lines, String pdfFileName, String pdfTitle) {
		PDFRenderer renderer = new PDFRenderer(pdfFileName, pdfTitle, lineTitles, lines, "");
		renderer.render();
		System.out.println("Archivo generado en " + pdfFileName);
	}
	
}
