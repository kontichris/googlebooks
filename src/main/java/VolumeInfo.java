
import java.util.ArrayList;

class ReadingModes {

    boolean image;
    boolean text;

    public ReadingModes() {
    }

    public ReadingModes(boolean image, boolean text) {
        this.image = image;
        this.text = text;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public boolean isText() {
        return text;
    }

    public void setText(boolean text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ReadingModes{" + "image=" + image + ", text=" + text + '}';
    }
    
    
}

class ImageLinks {

    String thumbnail;
    String smallThumbnail;

    public ImageLinks() {
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    @Override
    public String toString() {
        return "ImageLinks{" + "thumbnail=" + thumbnail + ", smallThumbnail=" + smallThumbnail + '}';
    }
    
    

}

class PanelizationSummary {

    private boolean containsImageBubbles;
    private boolean containsEpubBubbles;

    public PanelizationSummary() {
    }

    public PanelizationSummary(boolean containsImageBubbles, boolean containsEpubBubbles) {
        this.containsImageBubbles = containsImageBubbles;
        this.containsEpubBubbles = containsEpubBubbles;
    }

    public boolean isContainsImageBubbles() {
        return containsImageBubbles;
    }

    public void setContainsImageBubbles(boolean containsImageBubbles) {
        this.containsImageBubbles = containsImageBubbles;
    }

    public boolean isContainsEpubBubbles() {
        return containsEpubBubbles;
    }

    public void setContainsEpubBubbles(boolean containsEpubBubbles) {
        this.containsEpubBubbles = containsEpubBubbles;
    }

    @Override
    public String toString() {
        return "PanelizationSummary{" + "containsImageBubbles=" + containsImageBubbles + ", containsEpubBubbles=" + containsEpubBubbles + '}';
    }

   
}

class IndustryIdentifiers{
    String identifier;
    String type;

    public IndustryIdentifiers() {
    }

    public IndustryIdentifiers(String identifier, String type) {
        this.identifier = identifier;
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IndustryIdentifiers{" + "identifier=" + identifier + ", type=" + type + '}';
    }
    
}

public class VolumeInfo {

    private IndustryIdentifiers[] industryIdentifiers;
    private String pageCount;
    private String printType;
    private ReadingModes readingModes;
    private String previewLink;
    private String canonicalVolumeLink;
    private String language;
    private String title;
    private ImageLinks imageLinks;
    private PanelizationSummary panelizationSummary;
    private String publishedDate;
    private String[] categories;
    private String maturityRating;
    private boolean allowAnonLogging;
    private String contentVersion;
    private String subtitle;

    private String[] authors;
    private String infoLink;
    private String publisher;
    private String description;
    private int averageRating;
    private int ratingsCount;
    

    public VolumeInfo() {
    }

    public VolumeInfo(IndustryIdentifiers[] industryIdentifiers, String pageCount, String printType, ReadingModes readingModes, String previewLink, String canonicalVolumeLink, String language, String title, ImageLinks imageLinks, PanelizationSummary panelizationSummary, String publishedDate, String[] categories, String maturityRating, boolean allowAnonLogging, String contentVersion, String subtitle, String[] authors, String infoLink, String publisher, String description, int averageRating, int ratingsCount) {
        this.industryIdentifiers = industryIdentifiers;
        this.pageCount = pageCount;
        this.printType = printType;
        this.readingModes = readingModes;
        this.previewLink = previewLink;
        this.canonicalVolumeLink = canonicalVolumeLink;
        this.language = language;
        this.title = title;
        this.imageLinks = imageLinks;
        this.panelizationSummary = panelizationSummary;
        this.publishedDate = publishedDate;
        this.categories = categories;
        this.maturityRating = maturityRating;
        this.allowAnonLogging = allowAnonLogging;
        this.contentVersion = contentVersion;
        this.subtitle = subtitle;
        this.authors = authors;
        this.infoLink = infoLink;
        this.publisher = publisher;
        this.description = description;
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
    }

    public IndustryIdentifiers[] getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(IndustryIdentifiers[] industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public ReadingModes getReadingModes() {
        return readingModes;
    }

    public void setReadingModes(ReadingModes readingModes) {
        this.readingModes = readingModes;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getCanonicalVolumeLink() {
        return canonicalVolumeLink;
    }

    public void setCanonicalVolumeLink(String canonicalVolumeLink) {
        this.canonicalVolumeLink = canonicalVolumeLink;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public PanelizationSummary getPanelizationSummary() {
        return panelizationSummary;
    }

    public void setPanelizationSummary(PanelizationSummary panelizationSummary) {
        this.panelizationSummary = panelizationSummary;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String getMaturityRating() {
        return maturityRating;
    }

    public void setMaturityRating(String maturityRating) {
        this.maturityRating = maturityRating;
    }

    public boolean isAllowAnonLogging() {
        return allowAnonLogging;
    }

    public void setAllowAnonLogging(boolean allowAnonLogging) {
        this.allowAnonLogging = allowAnonLogging;
    }

    public String getContentVersion() {
        return contentVersion;
    }

    public void setContentVersion(String contentVersion) {
        this.contentVersion = contentVersion;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    @Override
    public String toString() {
        return "VolumeInfo{" + "industryIdentifiers=" + industryIdentifiers + ", pageCount=" + pageCount + ", printType=" + printType + ", readingModes=" + readingModes + ", previewLink=" + previewLink + ", canonicalVolumeLink=" + canonicalVolumeLink + ", language=" + language + ", title=" + title + ", imageLinks=" + imageLinks + ", panelizationSummary=" + panelizationSummary + ", publishedDate=" + publishedDate + ", categories=" + categories + ", maturityRating=" + maturityRating + ", allowAnonLogging=" + allowAnonLogging + ", contentVersion=" + contentVersion + ", subtitle=" + subtitle + ", authors=" + authors + ", infoLink=" + infoLink + ", publisher=" + publisher + ", description=" + description + ", averageRating=" + averageRating + ", ratingsCount=" + ratingsCount + '}';
    }

    
    
}
