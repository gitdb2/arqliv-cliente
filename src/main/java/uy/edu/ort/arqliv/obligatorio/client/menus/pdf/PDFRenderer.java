package uy.edu.ort.arqliv.obligatorio.client.menus.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.DocumentFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

/**
 * Clase encargada de generar los pdf y dejarlos en la ruta especificada.
 * 
 * @author rodrigo
 * 
 */
public class PDFRenderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	String outputfile;
	Phrase pageHeader;
	Phrase dataHeader;
	List<String> lines;
	Phrase footer;
	Font font;
	final float FONT_SIZE = (float) 7.2;
//	private int pagenumber = 0;

	public PDFRenderer(String outputfile, String pageHeader, String dataHeader,
			List<String> lines, String footer) {
		super();
		font = new Font();
		font.setSize(FONT_SIZE);
		try {
			BaseFont bf = BaseFont.createFont("resources/fonts/CONSOLA.TTF", BaseFont.WINANSI, BaseFont.EMBEDDED);
			font = new Font(bf, FONT_SIZE);
		} catch (IOException | DocumentException e) {	
			log.error("Problema al cargar la fuente, usando la por defecto",  e);
		}
		
		
		this.outputfile = outputfile;
		this.pageHeader = new Phrase(pageHeader, font);
		this.dataHeader = new Phrase(dataHeader, font);
		this.lines = lines;
		this.footer = new Phrase(footer, font);
	}

	public void render() {
		try {
			
			File output= new File(this.outputfile);
			output= new File(output.getParent());
			output.mkdirs();
			
			Document document = new Document(PageSize.A4);
			
			PdfWriter.getInstance(document, new FileOutputStream(
					this.outputfile));
			document.open();

			document.add(new LineSeparator(0.3f, 100, null, 0, -3));
			document.add(Chunk.NEWLINE);
				
			
			document.add(dataHeader);
			document.add(Chunk.NEWLINE);
			for (String line : lines) {
				document.add(new Phrase(line, font));
				document.add(Chunk.NEWLINE);
			}
		
			document.add(new LineSeparator(0.5f, 100, null, 0, -3));
			document.add(Chunk.NEWLINE);
			document.add(footer);
			document.add(Chunk.NEWLINE);
			document.close();
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			log.error("no se encontro el archivo salida", e);
			throw new RuntimeException("no se encontro el archivo salida", e);
		} catch (DocumentException e) {
			//e.printStackTrace();
			log.error("no se pudo generar el pdf", e);
			throw new RuntimeException("no se pudo generar el pdf", e);
		}
	}

//	public void onStartPage(PdfWriter writer, Document document) {
//		pagenumber++;
//	}
//
//	public void onEndPage(PdfWriter writer, Document document) {
//		Rectangle rect = writer.getBoxSize("art");
//		switch (writer.getPageNumber() % 2) {
//		case 0:
//			ColumnText.showTextAligned(writer.getDirectContent(),
//					Element.ALIGN_RIGHT, pageHeader, rect.getRight(),
//					rect.getTop(), 0);
//			break;
//		case 1:
//			ColumnText.showTextAligned(writer.getDirectContent(),
//					Element.ALIGN_LEFT, pageHeader, rect.getLeft(),
//					rect.getTop(), 0);
//			break;
//		}
//		
//		ColumnText.showTextAligned(writer.getDirectContent(),
//				Element.ALIGN_CENTER,
//				new Phrase(String.format("página %d", pagenumber)),
//				(rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18,
//				0);
//	}

}
