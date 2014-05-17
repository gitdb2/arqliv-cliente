/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.arqliv.obligatorio.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import uy.edu.ort.arqliv.obligatorio.client.menus.arrival.MenuArrivalList;
import uy.edu.ort.arqliv.obligatorio.client.menus.pdf.PDFRenderer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

/**
 * 
 * @author rodrigo
 */
public class DemoConsole1 {

	public static void main(String[] args) throws IOException,
			DocumentException {

		testListadoArrival();

	}
	private static void testListadoArrival(){
		new MenuArrivalList(true).render();
	}
	
	
	private static void testListadoship() {
		List<String> lines = Arrays.asList(String.format("%10s " // ID
				+ "%-30s " + "%-15s " + "%15s " + "%20s " + "%20s " + "%15s",
				"Id", "Nombre", "Bandera", "Codigo", "Año Manufactura",
				"Cant. Tripulacion", "Capacidad"), String.format

		("%10d " + "%-30s " + "%-15s " + "%15d " + "%20d " + "%20d " + "%15.2f"

		, 2, "rojito", "UY", 523, 1978, 55, 1687.00), String.format

		("%10d " + "%-30s " + "%-15s " + "%15d " + "%20d " + "%20d " + "%15.2f", 3,
				"delicious", "BR", 523, 1978, 55, 11.00), String.format

				("%10d " + "%-30s " + "%-15s " + "%15d " + "%20d " + "%20d " + "%15.2f", 5,
				"delicious", "UY", 523, 1978, 55, 11.00), String.format

				("%10d " + "%-30s " + "%-15s " + "%15d " + "%20d " + "%20d " + "%15.2f", 6,
				"aaaa", "bbbb", 1111, 1989, 5, 20003.00)

		);

		PDFRenderer render = new PDFRenderer("E:/ORT/arch_"
				+ System.currentTimeMillis() + ".pdf", "",
				"aaaaaaa aaaaaa aaaaaaaa", lines, "FOOTER");
		render.render();
		System.out.println("Fin");
	}

	public static String TEXT = "1234567890 abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String[][] FONTS = {
			{ BaseFont.HELVETICA, BaseFont.WINANSI },
			{ "resources/fonts/CONSOLA.TTF", BaseFont.WINANSI },
			{ "c:/windows/fonts/arial.ttf", BaseFont.WINANSI },
			{ "c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H },
			{ "c:/windows/fonts/msgothic.ttc,0", BaseFont.IDENTITY_H } };

	public static void createPdf(String filename) throws IOException,
			DocumentException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		document.open();
		BaseFont bf;
		Font font;
		for (int i = 0; i < FONTS.length; i++) {
			bf = BaseFont.createFont(FONTS[i][0], FONTS[i][1],
					BaseFont.EMBEDDED);
			document.add(new Paragraph(String.format(
					"Font file: %s with encoding %s", FONTS[i][0], FONTS[i][1])));
			document.add(new Paragraph(String.format("iText class: %s", bf
					.getClass().getName())));
			font = new Font(bf, 12);
			document.add(new Paragraph(TEXT, font));
			document.add(new LineSeparator(0.5f, 100, null, 0, -5));
		}
		document.close();
	}
}
