package vasic.ebook.repository.file;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileManager {
	
	public static final String TEMP_FILE_LOCATION = new File("data").getAbsolutePath()+"\\";
	
	public  File saveToTemp(MultipartFile file) {
		
		try {
			
		if (! file.isEmpty()) {
            byte[] bytes = file.getBytes();
          	FileOutputStream fos = new FileOutputStream(TEMP_FILE_LOCATION + file.getOriginalFilename());
          	fos.write(bytes);
          	fos.close();
          	File pdfFile = new File(TEMP_FILE_LOCATION + file.getOriginalFilename());
          	return pdfFile;
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;

	}

}
