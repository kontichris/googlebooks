public class SaleInfo {
    private String country;
    private boolean isEbook;
    private String saleability;

    public SaleInfo() {
    }

    
    
    public SaleInfo(String country, boolean isEbook, String saleability) {
        this.country = country;
        this.isEbook = isEbook;
        this.saleability = saleability;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isIsEbook() {
        return isEbook;
    }

    public void setIsEbook(boolean isEbook) {
        this.isEbook = isEbook;
    }

    public String getSaleability() {
        return saleability;
    }

    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    @Override
    public String toString() {
        return "SaleInfo{" + "country=" + country + ", isEbook=" + isEbook + ", saleability=" + saleability + '}';
    }
    
    
    
}
