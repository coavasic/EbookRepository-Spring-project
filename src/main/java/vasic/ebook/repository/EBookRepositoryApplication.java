package vasic.ebook.repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class EBookRepositoryApplication {
	


	public static void main(String[] args) {
		SpringApplication.run(EBookRepositoryApplication.class, args);
	}
}
