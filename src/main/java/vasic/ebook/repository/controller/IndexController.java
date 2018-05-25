package vasic.ebook.repository.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vasic.ebook.repository.indexer.Indexer;


@RestController
@RequestMapping(value="api")
public class IndexController {
	
	private static String DATA_DIR_PATH;
	
	@Autowired
	private Indexer indexer;
	

	
	static {
		ResourceBundle rb=ResourceBundle.getBundle("application");
		DATA_DIR_PATH=rb.getString("dataDir");
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/reindex")
	public ResponseEntity<?> index() throws IOException {
		File dataDir = new File("books");
		long start = new Date().getTime();
		int numIndexed = indexer.index(dataDir);
		long end = new Date().getTime();
		String text = "Indexing " + numIndexed + " files took "
				+ (end - start) + " milliseconds";
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	

}
