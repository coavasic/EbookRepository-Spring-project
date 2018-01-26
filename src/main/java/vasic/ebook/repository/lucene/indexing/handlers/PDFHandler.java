package vasic.ebook.repository.lucene.indexing.handlers;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.queries.function.valuesource.IfFunction;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

import vasic.ebook.repository.dto.EBookDTO;
import vasic.ebook.repository.entity.EBook;
import vasic.ebook.repository.lucene.model.IndexUnit;


public class PDFHandler extends DocumentHandler {

	@Override
	public IndexUnit getIndexUnit(File file) {
		IndexUnit retVal = new IndexUnit();
		String modificationDate="";
		try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			String text = getText(parser);
			retVal.setText(text);

			// metadata extraction
			PDDocument pdf = parser.getPDDocument();
			PDDocumentInformation info = pdf.getDocumentInformation();
			
			pdf.setDocumentInformation(info);

			String title = info.getTitle();
			retVal.setTitle(title);
			
			String author = info.getAuthor();
			retVal.setAuthor(author);

			String keywords = info.getKeywords();
			
		
			retVal.setMime(info.getCustomMetadataValue("mime"));
		
			
			
			retVal.setKeywords(keywords);
			retVal.setFilename(file.getCanonicalPath());
			if(info.getCreationDate() != null) {
			modificationDate=""+info.getCreationDate().get(Calendar.YEAR);
			}else {
				modificationDate="0000";
			}
			
			retVal.setFiledate(modificationDate);
			
			pdf.close();
		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}

		return retVal;
	}
	
	public PDDocument setAttributes(File file, EBookDTO bookDTO) throws ParseException {
		PDDocument pdf = null;
		
		try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			cal.setTime(sdf.parse(bookDTO.getPublicationYear().toString()));

			// metadata extraction
			pdf = parser.getPDDocument();
			pdf.getDocumentInformation().setTitle(bookDTO.getTitle());
			pdf.getDocumentInformation().setAuthor(bookDTO.getAuthor());
			pdf.getDocumentInformation().setCreationDate(cal);
			pdf.getDocumentInformation().setKeywords(bookDTO.getKeywords());
			System.out.println(bookDTO.getMime() + "PROVERA MIME");
			pdf.getDocumentInformation().setCustomMetadataValue("mime", bookDTO.getMime());
			
			
		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}
		
		
		return pdf;

		
	}
		
		
		
		
		
	

	@Override
	public String getText(File file) {
		try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(parser.getPDDocument());
			return text;
		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}
		return null;
	}
	
	public String getText(PDFParser parser) {
		try {
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(parser.getPDDocument());
			return text;
		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}
		return null;
	}

}