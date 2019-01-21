package assign4;
import javax.swing.*;

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
import org.apache.lucene.search.similarities.BooleanSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.awt.*;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;




public class gui extends JFrame implements ActionListener {  
  
    JButton jb1,jb2,jb3=null;  
    JRadioButton jrb1,jrb2=null;  
    JPanel jp1,jp2,jp3,jp4=null;  
    JTextField jtf=null;  
    JLabel jlb1,jlb2,jlb3=null;  
    JPasswordField jpf=null;  
    ButtonGroup bg=null;  


    public static void main(String[] args) {  

        gui mUI=new gui();  
    }  
    public gui()  
    {  
       
        jb1=new JButton("Enter"); 
        jb1.setFont(new Font("Serif", Font.PLAIN, 30));
        jb1.setForeground(Color.RED);
        jb2=new JButton("Clear"); 
        jb2.setFont(new Font("Serif", Font.PLAIN, 30));
        jb3=new JButton("Quit");
        jb3.setFont(new Font("Serif", Font.PLAIN, 30));

     
        jb1.addActionListener(this);  
        jb2.addActionListener(this);  
        jb3.addActionListener(this);  

        jrb1=new JRadioButton("Boolean"); 
        jrb1.setFont(new Font("Serif", Font.PLAIN, 30));
        jrb2=new JRadioButton("BM25");  
        jrb2.setFont(new Font("Serif", Font.PLAIN, 30));
        bg=new ButtonGroup();  
        bg.add(jrb1);  
        bg.add(jrb2);  
        jrb2.setSelected(true);  

        jp1=new JPanel();  
        jp2=new JPanel();  
        jp3=new JPanel();  
        jp4=new JPanel();                 

        jlb1=new JLabel("Searching query"); 
        jlb1.setFont(new Font("Serif", Font.BOLD, 30));
        jlb3=new JLabel("Method£º");  
        jlb3.setFont(new Font("Serif", Font.BOLD, 30));

        jtf=new JTextField(10);  
        jtf.setFont(new Font("Serif", Font.PLAIN, 30));
        jp1.add(jlb1);  
        jp1.add(jtf);  



        jp3.add(jlb3);      
        jp3.add(jrb1);  
        jp3.add(jrb2);  

        jp4.add(jb1);      
        jp4.add(jb2);  
        jp4.add(jb3);

        this.add(jp1);  
        this.add(jp2);  
        this.add(jp3);  
        this.add(jp4);  

        this.setLayout(new GridLayout(4,1));            
        this.setTitle("kookle---The super searching enigne for Australia");          
        this.setSize(600,600);         
        this.setLocation(400, 200);           
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        this.setVisible(true);  
        this.setResizable(true);  

    }  

    public void actionPerformed(ActionEvent e) {           
    	 {
    		    try {
    		        if (e.getSource() == jb1) {
    		            if (jrb1.isSelected()) {
    		                String n = jtf.getText();
    		                n = "content:" + n;
    		                tealogin(n);
    		            } else if (jrb2.isSelected()) {
    		                String n = jtf.getText();
    		                n = "content:" + n;
    		                stulogin(n);
    		            }
    		        } else if (e.getSource() == jb2) {
    		            clear();
    		        }

    		    } catch (Exception ex) {
    		        ex.printStackTrace();
    		    }
    		}      

    }  


    public static void stulogin(String n) throws Exception { 
    {  
//        String[] n=jtf.getText();
		Path path=Paths.get("../index");
//		reader = new Scanner(System.in);
//		System.out.println("Enter a query: ");
//		String n = reader.nextLine(); 
//		n = "content:" + n;
	
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
    } } }
 
    public static void tealogin(String n) throws Exception { 
    {  
//        String[] n=jtf.getText();
		Path path=Paths.get("../index");
//		reader = new Scanner(System.in);
//		System.out.println("Enter a query: ");
//		String n = reader.nextLine(); 
//		n = "content:" + n;
	
		Directory directory = FSDirectory.open(path);
		IndexReader IR = DirectoryReader.open(directory);
		IndexSearcher searcher= new IndexSearcher(IR);
		searcher.setSimilarity(new BooleanSimilarity());
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
    } } }

    public  void clear()  
    {  
        jtf.setText("");  
        jpf.setText("");  
    }  

}