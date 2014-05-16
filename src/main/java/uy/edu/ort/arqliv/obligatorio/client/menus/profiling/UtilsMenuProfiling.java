package uy.edu.ort.arqliv.obligatorio.client.menus.profiling;

import java.util.List;

import uy.edu.ort.arqliv.obligatorio.client.menus.pdf.PDFRenderer;

public class UtilsMenuProfiling {

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
