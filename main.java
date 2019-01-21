package assign4;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class main {
	private static Scanner reader;

	public static void main(String[] args) throws Exception {
		Path path=Paths.get("C:\\Users\\11984\\Desktop\\assign4\\index");
		reader = new Scanner(System.in);
		System.out.println("Enter a query: ");
		String n = reader.nextLine(); 
		n = "content:" + n;
	
	
		Directory directory = FSDirectory.open(path);
        
		IndexReader IR = DirectoryReader.open(directory);
		IndexSearcher searcher= new IndexSearcher(IR);
		searcher.setSimilarity(new BM25Similarity());
		QueryParser parser = new QueryParser(n,new StandardAnalyzer());
		Query query = parser.parse(n);
		TopDocs topDocs = searcher.search(query,10);
		System.out.println("The top score is"+"\t"+topDocs.getMaxScore()+"\n"+"Here are the relevant docs"+"");
		System.out.println("Rank"+"\t"+"Score"+"\t"+"Docname");
		ScoreDoc[] hits=topDocs.scoreDocs;
		for (int i =0; i<hits.length;++i) {
			int id = hits[i].doc;
			
			float score=hits[i].score;
			Document d = searcher.doc(id);
			String name=d.get("title");
//			List<IndexableField> field = d.getFields();
			System.out.print(i+1+"\t");
			System.out.print(score+"\t");
			System.out.print(name+"\n");
//			for (IndexableField f:field) {
//				System.out.print("\t"+d.get(f.name())+ "\n");
//			}
			
		}

	}}

		
//		Hit=10
//		Iterator<> it = ScoreDoc.iterator();
//		while (it.hasNext()) {
//			Hit hit = it.next();
//			Document document = hit.getDocument();
//			String path = document.get(FIELD_PATH);
//			System.out.println("Hit: " + path);



//	public static void createIndex() throws CorruptIndexException, LockObtainFailedException, IOException {
//		Analyzer analyzer = new StandardAnalyzer();
//		boolean recreateIndexIfExists = true;
//		IndexWriter indexWriter = new IndexWriter(INDEX_DIRECTORY, analyzer, recreateIndexIfExists);
//		File dir = new File(/index);
//		File[] files = dir.listFiles();
//		for (File file : files) {
//			Document document = new Document();
//
//			String path = file.getCanonicalPath();
//			document.add(new Field(FIELD_PATH, path, Field.Store.YES, Field.Index.UN_TOKENIZED));
//
//			Reader reader = new FileReader(file);
//			document.add(new Field(FIELD_CONTENTS, reader));
//
//			indexWriter.addDocument(document);
//		}
//		indexWriter.optimize();
//		indexWriter.close();
//	}

//	public static void searchIndex(String searchString) throws IOException, ParseException {
//		System.out.println("Searching for '" + searchString + "'");
//		Directory directory = FSDirectory.getDirectory(INDEX_DIRECTORY);
//		IndexReader indexReader = IndexReader.open(directory);
//		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//
//		Analyzer analyzer = new StandardAnalyzer();
//		QueryParser queryParser = new QueryParser(FIELD_CONTENTS, analyzer);
//		Query query = queryParser.parse(searchString);
//		Hits hits = indexSearcher.search(query);
//		System.out.println("Number of hits: " + hits.length());
//
//
//		}
//
//	}
//
//}
//}
