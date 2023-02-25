
class Pdf{
    private boolean isAvailable;

    public Pdf() {
    }
    
    public Pdf(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Pdf{" + "isAvailable=" + isAvailable + '}';
    }
    
    
    
}
class Epub{
    boolean isAvailable;

    public Epub() {
    }
    
    

    public Epub(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Epub{" + "isAvailable=" + isAvailable + '}';
    }
    
    
}
public class AccessInfo {
    private String accessViewStatus;
    private String country;
    private String viewability;
    private String webReaderLink;
    private Pdf pdf;
    private Epub epub;
    private boolean publicDomain;
    private boolean quoteSharingAllowed;
    private boolean embeddable;
    private String textToSpeechPermission;
    private String selfLink;

    public AccessInfo() {
    }

    public AccessInfo(String accessViewStatus, String country, String viewability, String webReaderLink, Pdf pdf, Epub epub, boolean publicDomain, boolean quoteSharingAllowed, boolean embeddable, String textToSpeechPermission, String selfLink) {
        this.accessViewStatus = accessViewStatus;
        this.country = country;
        this.viewability = viewability;
        this.webReaderLink = webReaderLink;
        this.pdf = pdf;
        this.epub = epub;
        this.publicDomain = publicDomain;
        this.quoteSharingAllowed = quoteSharingAllowed;
        this.embeddable = embeddable;
        this.textToSpeechPermission = textToSpeechPermission;
        this.selfLink = selfLink;
    }

   

    public String getAccessViewStatus() {
        return accessViewStatus;
    }

    public void setAccessViewStatus(String accessViewStatus) {
        this.accessViewStatus = accessViewStatus;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getViewability() {
        return viewability;
    }

    public void setViewability(String viewability) {
        this.viewability = viewability;
    }

    public String getWebReaderLink() {
        return webReaderLink;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    public Pdf getPdf() {
        return pdf;
    }

    public void setPdf(Pdf pdf) {
        this.pdf = pdf;
    }

    public Epub getEpub() {
        return epub;
    }

    public void setEpub(Epub epub) {
        this.epub = epub;
    }

    public boolean isPublicDomain() {
        return publicDomain;
    }

    public void setPublicDomain(boolean publicDomain) {
        this.publicDomain = publicDomain;
    }

    public boolean isQuoteSharingAllowed() {
        return quoteSharingAllowed;
    }

    public void setQuoteSharingAllowed(boolean quoteSharingAllowed) {
        this.quoteSharingAllowed = quoteSharingAllowed;
    }

    public boolean isEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(boolean embeddable) {
        this.embeddable = embeddable;
    }

    public String getTextToSpeechPermission() {
        return textToSpeechPermission;
    }

    public void setTextToSpeechPermission(String textToSpeechPermission) {
        this.textToSpeechPermission = textToSpeechPermission;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    @Override
    public String toString() {
        return "AccessInfo{" + "accessViewStatus=" + accessViewStatus + "\n country=" + country + "\n viewability=" + viewability + "\n webReaderLink=" + webReaderLink + "\n pdf=" + pdf + "\n epub=" + epub + "\n publicDomain=" + publicDomain + "\n quoteSharingAllowed=" + quoteSharingAllowed + "\n embeddable=" + embeddable + "\n textToSpeechPermission=" + textToSpeechPermission + "\n selfLink=" + selfLink + '}';
    }
    
    
}
