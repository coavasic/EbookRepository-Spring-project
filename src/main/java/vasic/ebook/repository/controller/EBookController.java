package vasic.ebook.repository.controller;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.SecretKeyCallback.Request;

import org.apache.pdfbox.pdmodel.PDDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vasic.ebook.repository.dto.EBookDTO;
import vasic.ebook.repository.entity.Category;
import vasic.ebook.repository.entity.EBook;
import vasic.ebook.repository.entity.Language;
import vasic.ebook.repository.lucene.indexing.handlers.PDFHandler;
import vasic.ebook.repository.lucene.model.IndexUnit;
import vasic.ebook.repository.service.CategoryService;
import vasic.ebook.repository.service.EBookService;
import vasic.ebook.repository.service.LanguageService;

@RestController
@RequestMapping(value="/api/ebooks")
public class EBookController {

	@Autowired
	private EBookService eBookService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private LanguageService languageService;
	
	
	
	@RequestMapping(value="/check",method=RequestMethod.POST,consumes = "multipart/form-data")
	public ResponseEntity<EBookDTO> checkbeforeUpload(@RequestParam("file") MultipartFile file) throws IOException{
		
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getOriginalFilename().length());
		//System.out.println(file.getOriginalFilename().substring(-3));

		
		String ludiString= file.getOriginalFilename().substring(file.getOriginalFilename().length()-3,file.getOriginalFilename().length());
		System.out.println(ludiString);
		
		if(file.getOriginalFilename().substring(file.getOriginalFilename().length() -3, file.getOriginalFilename().length()).equals("pdf")) {
			
			EBookDTO ebook = new EBookDTO();
			
			

	        String fileLocation= new File("data").getAbsolutePath()+"\\"+file.getOriginalFilename();

			if (! file.isEmpty()) {
	            byte[] bytes = file.getBytes();
	          	FileOutputStream fos = new FileOutputStream(fileLocation);
	          	fos.write(bytes);
	          	fos.close();
			}
			File pdfFile = new File(fileLocation);

			

			
			
			PDFHandler handler = new PDFHandler();
			IndexUnit indexUnit = handler.getIndexUnit(pdfFile);
			System.out.println(indexUnit.getFiledate());
			ebook.setTitle(indexUnit.getTitle());
			Integer pubYear = Integer.parseInt(indexUnit.getFiledate().substring(0, 4));
			ebook.setPublicationYear(pubYear);
			ebook.setKeywords(indexUnit.getKeywords());
			ebook.setAuthor(indexUnit.getAuthor());
			ebook.setFileName(file.getOriginalFilename());
			ebook.setMime(indexUnit.getMime());
			
			

			
			return new ResponseEntity<EBookDTO>(ebook,HttpStatus.OK);
			
			
			
		}
		
		else {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		

		
		
	}
	
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public ResponseEntity<List<EBookDTO>> getEbooks(){
		
		List<EBookDTO> dtos = new ArrayList<>();

		List<EBook> ebooks = eBookService.findAll();
		for (EBook eBook : ebooks) {
			
			
			System.out.println(eBook.getAuthor());
			dtos.add(new EBookDTO(eBook));
			
		}	
			
		if(ebooks.isEmpty()) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<EBookDTO>>(dtos,HttpStatus.OK);
		
			
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<EBookDTO> getById(@PathVariable Integer id){
		
		EBook book = eBookService.findOne(id);
		if(book!=null) {
			
			return new ResponseEntity<EBookDTO>(new EBookDTO(book),HttpStatus.OK);
			
			
		}else {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
		
		
	}
	
	@RequestMapping(value="/dodaj",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<EBookDTO> saveEbook(@RequestBody EBookDTO bookDTO) throws ParseException, IOException{
		
		PDFHandler handler = new PDFHandler();

		
		Category category = categoryService.findOne(bookDTO.getCategoryId());
		Language language = languageService.findOne(bookDTO.getLanguageId());
		
        String fileLocation= new File("data").getAbsolutePath()+"\\"+bookDTO.getFileName();
        String fileLocation1= new File("books").getAbsolutePath()+"\\"+bookDTO.getFileName();



		
		File pdfFile = new File(fileLocation);
		
		PDDocument doc = handler.setAttributes(pdfFile,bookDTO);
		
		System.out.println(doc.getDocumentInformation().getCustomMetadataValue("mime"));
		
		File f = new File(fileLocation1);
		FileOutputStream fOut = new FileOutputStream(f);
		doc.save(fOut);
		doc.close();
		
		//deleting temporary file
		pdfFile.delete();
		
		
		
		EBook book = new EBook();
		book.setTitle(bookDTO.getTitle());
		book.setAuthor(bookDTO.getAuthor());
		book.setPublicationYear(bookDTO.getPublicationYear());
		book.setKeywords(bookDTO.getKeywords());
		book.setFileName(bookDTO.getFileName());
		book.setCategory(category);
		book.setMime(bookDTO.getMime());
		book.setLanguage(language);
		
		return new ResponseEntity<EBookDTO>(new EBookDTO(eBookService.save(book)),HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/download/{filePath}",method=RequestMethod.GET)
	public ResponseEntity<?> download(@PathVariable String filePath) throws IOException{
		
        String fileLocation1= new File("books").getAbsolutePath()+"\\"+filePath+".pdf";
        
        System.out.println(filePath);

		
		Path path = Paths.get(fileLocation1);
		if(new File(fileLocation1).exists()) {
			
			byte[] content = Files.readAllBytes(path);
			
			 HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.parseMediaType("application/pdf"));
			 headers.setContentDispositionFormData(filePath, filePath);
			 headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			
			return new ResponseEntity<byte[]>(content,headers,HttpStatus.OK);
				

			
			
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		
	}
	
	
	@RequestMapping(value="/bycategory/{id}")
	public ResponseEntity<List<EBookDTO>> getEbookByCat(@PathVariable Integer id){
		
		
		List<EBookDTO> dtos = new ArrayList<EBookDTO>();
		List<EBook> ebooks = eBookService.findAllByCategoryId(id);
		
		for (EBook eBook : ebooks) {
			
			dtos.add(new EBookDTO(eBook));
			
		}
		
		return new ResponseEntity<List<EBookDTO>>(dtos,HttpStatus.OK);
		
		
		
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteEbook(@PathVariable Integer id){
		
		EBook eBook = eBookService.findOne(id);
		if(eBook != null) {
        
			String fileLocation1= new File("books").getAbsolutePath()+"\\"+eBook.getFileName();
			File pdfFile = new File(fileLocation1);
			pdfFile.delete();

			eBookService.remove(id);
			
			return new ResponseEntity<>(HttpStatus.OK);
			
		}else {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
	}
	
	@RequestMapping(value="update/{id}",method=RequestMethod.PUT)
	public ResponseEntity<EBookDTO> updateEbook(@RequestBody EBookDTO bookDTO,@PathVariable Integer id){
		
		
		EBook ebook = eBookService.findOne(id);
		
		if(ebook != null) {
			
			ebook.setTitle(bookDTO.getTitle());
			ebook.setAuthor(bookDTO.getAuthor());
			ebook.setKeywords(bookDTO.getKeywords());
			ebook.setPublicationYear(bookDTO.getPublicationYear());
			ebook.setMime(bookDTO.getMime());
			
			return new ResponseEntity<EBookDTO>(new EBookDTO(eBookService.save(ebook)),HttpStatus.OK);
			
		}else {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			
		}
		
	}
	
         	  
}
	

	
	


	
	
	
	

