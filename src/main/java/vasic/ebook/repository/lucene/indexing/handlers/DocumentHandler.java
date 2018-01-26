package vasic.ebook.repository.lucene.indexing.handlers;

import java.io.File;

import vasic.ebook.repository.lucene.model.IndexUnit;


public abstract class DocumentHandler {
	/**
	 * Od prosledjene datoteke se konstruise Lucene Document
	 * 
	 * @param file
	 *            datoteka u kojoj se nalaze informacije
	 * @return Lucene Document
	 */
	public abstract IndexUnit getIndexUnit(File file);
	public abstract String getText(File file);

}
