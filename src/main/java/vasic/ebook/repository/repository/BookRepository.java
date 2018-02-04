package vasic.ebook.repository.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import vasic.ebook.repository.lucene.model.IndexUnit;


public interface BookRepository extends ElasticsearchRepository<IndexUnit, String> {

	List<IndexUnit> findByTitle(String title);

	IndexUnit findByFilename(String filename);
}
