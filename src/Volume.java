
public class Volume {
	
	String title; 
	String author; 
	String publisher;
	String isbn; 
	String fileFormat;
	String preview;
	String subject;
	String lccn;
	String oclc;

	
	
	public Volume(String title, String author, String publisher, String isbn, String fileFormat, String preview,
			String subject, String lccn, String oclc) {
		super();
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.isbn = isbn;
		this.fileFormat = fileFormat;
		this.preview = preview;
		this.subject = subject;
		this.lccn = lccn;
		this.oclc = oclc;
	}


	public String toString() {
		return  "Title: " + title
        +"Author: " + author
        +"Publisher: " + publisher
        +"ISBN: " + isbn
        +"File Format: " + fileFormat
        +"Preview Link: " + preview
        +"Subject: " + subject
        +"Library of Congress Control Number: " + lccn
        +"Online Computer Library Center number: " + oclc;
		
	}

}
