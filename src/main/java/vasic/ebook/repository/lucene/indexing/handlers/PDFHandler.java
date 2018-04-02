package vasic.ebook.repository.lucene.indexing.handlers;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import vasic.ebook.repository.dto.EBookDTO;
import vasic.ebook.repository.filters.CyrillicLatinConverter;
import vasic.ebook.repository.lucene.model.IndexUnit;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class PDFHandler extends DocumentHandler {


	public IndexUnit getIndexUnitWithNewArgs(File file, EBookDTO eBookDTO) {
		IndexUnit retVal = new IndexUnit();
		try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			retVal.setText(Normalizer.normalize(CyrillicLatinConverter.cir2lat(getText(parser)), Form.NFD).replaceAll("[^\\p{ASCII}]", ""));

			retVal.setTitle(eBookDTO.getTitle());
			retVal.setAuthor(eBookDTO.getAuthor());
			retVal.setFiledate(eBookDTO.getPublicationYear().toString());
			retVal.setKeywords(eBookDTO.getKeywords());
			retVal.setFilename(eBookDTO.getFileName());

		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}

		return retVal;
	}


	public EBookDTO getPdfMetaData(File file, String fileName) {

		EBookDTO eBookDTO = new EBookDTO();
		try {

			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			PDDocument pdf = parser.getPDDocument();
			PDDocumentInformation info = pdf.getDocumentInformation();
			eBookDTO.setTitle(info.getTitle());
			eBookDTO.setAuthor(info.getAuthor());
			eBookDTO.setKeywords(info.getKeywords());
			eBookDTO.setFileName(fileName);
			eBookDTO.setPublicationYear(info.getCreationDate().get(Calendar.YEAR));

			pdf.close();


		} catch (IOException e) {

			System.out.println("Greska pri parsiranju dokumenta");

		}

		return eBookDTO;


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
			
			
		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}
		
		
		return pdf;

		
	}


	@Override
	public IndexUnit getIndexUnit(File file) {

		IndexUnit retVal = new IndexUnit();
		String creationDate = "";

		try {


			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			String text = Normalizer.normalize(CyrillicLatinConverter.cir2lat(getText(parser)), Form.NFD).replaceAll("[^\\p{ASCII}]", "");
			// metadata extraction
			PDDocument pdf = parser.getPDDocument();
			PDDocumentInformation info = pdf.getDocumentInformation();

			retVal.setTitle(info.getTitle());
			retVal.setAuthor(info.getAuthor());
			retVal.setKeywords(info.getKeywords());
			retVal.setFilename(file.getCanonicalPath());
			if (info.getCreationDate() != null) {
				creationDate = "" + info.getCreationDate().get(Calendar.YEAR);
			} else {
				creationDate = "1389";
			}

			retVal.setFiledate(creationDate);

			pdf.close();


		} catch (IOException e) {

			System.out.println("Greska pri konvertovanju fajla u pdf");
		}

		return retVal;
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
