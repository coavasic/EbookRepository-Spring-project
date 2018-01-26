package vasic.ebook.repository.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vasic.ebook.repository.indexer.Indexer;


@RestController
@RequestMapping(value="api/index")
public class IndexController {
	
	private static String DATA_DIR_PATH;
	
	static {
		ResourceBundle rb=ResourceBundle.getBundle("application");
		DATA_DIR_PATH=rb.getString("dataDir");
	}
	
	@GetMapping("/reindex")
	public ResponseEntity<String> index() throws IOException {
		File dataDir = new File("books");
		long start = new Date().getTime();
		int numIndexed = Indexer.getInstance().index(dataDir);
		long end = new Date().getTime();
		String text = "Indexing " + numIndexed + " files took "
				+ (end - start) + " milliseconds";
		return new ResponseEntity<String>(text, HttpStatus.OK);
	}
	
	
	

}
