import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "weathertech")
public class AmItem {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int itemID;

    @Column(name = "ASIN")
     private String asin;

    @Column(name = "TITLE")
     private String title;

    @Column(name = "LINK")
     private String link;

    @Column(name = "BRAND")
     private String brand;

    @Column(name = "WHOLE_PRICE")
     private BigDecimal wholePrice;

    @Column(name = "SUG_RETAIL_PRICE")
     private BigDecimal suggestedRetailPrice;

    @Column(name = "IMG_LINK")
     private String imgLink;

    @Column(name = "PRIME")
     private String prime;

    @Column(name = "FREE_SHIPPING")
     private String freeShipping;

    @Column(name = "REVIEW_QUANTITY")
     private Integer customerReviewsQuantity;

    @Column(name = "REVIEWS_LINK")
     private String customerReviewLink;

    @Column(name = "SKU")
     private String sku;

    @Override
    public String toString() {
        return "AmItem{" +
                "itemID=" + itemID +
                ", asin='" + asin + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", brand='" + brand + '\'' +
                ", wholePrice=" + wholePrice +
                ", suggestedRetailPrice='" + suggestedRetailPrice + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", prime='" + prime + '\'' +
                ", freeShipping='" + freeShipping + '\'' +
                ", customerReviewsQuantity='" + customerReviewsQuantity + '\'' +
                ", customerReviewLink='" + customerReviewLink + '\'' +
                ", sku='" + sku + '\'' +
                '}';
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getWholePrice() {
        return wholePrice;
    }

    public void setWholePrice(BigDecimal wholePrice) {
        this.wholePrice = wholePrice;
    }

    public BigDecimal getSuggestedRetailPrice() {
        return suggestedRetailPrice;
    }

    public void setSuggestedRetailPrice(BigDecimal suggestedRetailPrice) {
        this.suggestedRetailPrice = suggestedRetailPrice;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getPrime() {
        return prime;
    }

    public void setPrime(String prime) {
        this.prime = prime;
    }

    public Integer getCustomerReviewsQuantity() {
        return customerReviewsQuantity;
    }

    public void setCustomerReviewsQuantity(Integer customerReviewsQuantity) {
        this.customerReviewsQuantity = customerReviewsQuantity;
    }

    public String getCustomerReviewLink() {
        return customerReviewLink;
    }

    public void setCustomerReviewLink(String customerReviewLink) {
        this.customerReviewLink = customerReviewLink;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(String freeShipping) {
        this.freeShipping = freeShipping;
    }
}
