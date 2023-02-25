import java.util.ArrayList;

public class VolumeInfo {
	
	String kind;
	String id;
	String etag;
	String selfLink;
    VolumeAccessInfo vaccessInfo;
    VolumeSaleInfo vSaleInfo;
    ArrayList<String> categoriesList;
	String title;
	ArrayList<String> authorsList;
	private String publisher;
	private String publishedDate;
	private String description;
	private int pageCount;
	VolumeDimension dimension;
	String printType;
	String canonicalVolumeLink;
	
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEtag() {
		return etag;
	}
	public void setEtag(String etag) {
		this.etag = etag;
	}
	public String getSelfLink() {
		return selfLink;
	}
	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}
	public VolumeAccessInfo getVaccessInfo() {
		return vaccessInfo;
	}
	public void setVaccessInfo(VolumeAccessInfo vaccessInfo) {
		this.vaccessInfo = vaccessInfo;
	}
	public void setVSaleInfo(VolumeSaleInfo vSaleInfo) {
		this.vSaleInfo = vSaleInfo;
	}
	public void setCategoriesList(ArrayList<String> categoriesList) {
		this.categoriesList = categoriesList;
	}
	public void setTitle(String title) {
		this.title = title;
		
	}
	public void setAuthorsList(ArrayList<String> authorsList) {
		this.authorsList = authorsList;
		
	}
	public void setPublisher(String publisher) {
		this.publisher= publisher;
		
	}
	public void setPublishedDate(String publishedDate) {
		 this.publishedDate = publishedDate;
		
	}
	public void setDescription(String description) {
		this.description =description;
		
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
		
	}
	public void setVolumeDimension(VolumeDimension dimension) {
		this.dimension = dimension;
	}
	public void setPrintType(String printType) {
		this.printType= printType;
		
	}
	public void setCanonicalVolumeLink(String canonicalVolumeLink) {
		this.canonicalVolumeLink = canonicalVolumeLink;
	}
	public void setInfoLink(String infoLink) {
		// TODO Auto-generated method stub
		
	}
	public void setLanguage(String language) {
		// TODO Auto-generated method stub
		
	}
	public void setContentVersion(String contentVersion) {
		// TODO Auto-generated method stub
		
	}
	public void setRatingsCount(int ratingsCount) {
		// TODO Auto-generated method stub
		
	}
	public void setAverageRating(int averageRating) {
		// TODO Auto-generated method stub
		
	}

}
