public class Item {
    private String kind;
    private String id;
    private String etag;
    private String selfLink;
    private SaleInfo saleInfo;
    private SearchInfo searchInfo;
    private VolumeInfo volumeInfo;
    private AccessInfo accessInfo;

    public Item() {
    }

    public Item(String kind, String id, String etag, String selfLink, SaleInfo saleInfo, SearchInfo searchInfo, VolumeInfo volumeInfo, AccessInfo accessInfo) {
        this.kind = kind;
        this.id = id;
        this.etag = etag;
        this.selfLink = selfLink;
        this.saleInfo = saleInfo;
        this.searchInfo = searchInfo;
        this.volumeInfo = volumeInfo;
        this.accessInfo = accessInfo;
    }

    

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

    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    @Override
    public String toString() {
        return "Item{" + "kind=" + kind + "\n id=" + id + "\n etag=" + etag + "\n selfLink=" + selfLink + "\n saleInfo=" + saleInfo + "\n searchInfo=" + searchInfo + "\n volumeInfo=" + volumeInfo + "\n accessInfo=" + accessInfo + '}';
    }
    
    
    
}
