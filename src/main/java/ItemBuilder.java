import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.List;

public class ItemBuilder {

    public AmItem buildItem(WebElement itemEl){
        AmItem item = new AmItem();

        String asin = getAsin(itemEl);
        String title = getTitle(itemEl);
        String link = getLink(itemEl);
        String brand = "WeatherTech";
        BigDecimal wholePrice = getWholePrice(itemEl);
        BigDecimal suggestedRetailPrice = getSuggestedRetailPrice(itemEl);
        String imgLink = getImgLink(itemEl);
        String prime = getPrime(itemEl);
        String freeShipping = getFreeShipping(itemEl);
        Integer customerReviewsQuantity = getCustomerReviewsQuantity(itemEl);
        String customerReviewLink = getCustomerReviewLink(itemEl);
        String priceListingLink = getPriceListingLink(itemEl);

        item.setAsin(asin);
        item.setTitle(title);
        item.setLink(link);
        item.setBrand(brand);
        item.setWholePrice(wholePrice);
        item.setSuggestedRetailPrice(suggestedRetailPrice);
        item.setImgLink(imgLink);
        item.setPrime(prime);
        item.setFreeShipping(freeShipping);
        item.setCustomerReviewsQuantity(customerReviewsQuantity);
        item.setCustomerReviewLink(customerReviewLink);
        item.setPriceListLink(priceListingLink);

        System.out.println(item);

        /*System.out.println(asin);
        System.out.println(title);
        System.out.println(link);
        System.out.println(brand);
        System.out.printf("%.2f", wholePrice);
        System.out.println();
        System.out.printf("%.2f", suggestedRetailPrice);
        System.out.println();
        System.out.println(imgLink);
        System.out.println(prime);
        System.out.println(freeShipping);
        System.out.println(customerReviewsQuantity);
        System.out.println(customerReviewLink);*/

        return item;
    }

    private String getPriceListingLink(WebElement itemEl) {
        List<WebElement> elements = itemEl.findElements(By.tagName("a"));
        for (WebElement element: elements){
            String href = element.getAttribute("href");
            if (href.contains("offer-listing")){
                return href;
            }
        }
        return "";
    }

    private String getCustomerReviewLink(WebElement itemEl) {
        List<WebElement> linkEls = itemEl.findElements(By.tagName("a"));
        String linkText = "";
        for (WebElement el: linkEls){
            String href = el.getAttribute("href");
            if (href.contains("customerReviews")){
                linkText=href;
                break;
            }
        }
        return linkText;
    }

    private Integer getCustomerReviewsQuantity(WebElement itemEl) {
        List<WebElement> linkEls = itemEl.findElements(By.tagName("a"));
        String linkText = "";
        for (WebElement el: linkEls){
            String href = el.getAttribute("href");
            if (href.contains("customerReviews")){
                linkText=el.getText();
                break;
            }
        }
        if (linkText.length()==0){
            return 0;
        }
        return Integer.parseInt(linkText);
    }

    private String getFreeShipping(WebElement itemEl) {
        String txt = itemEl.getText();
        if (txt.contains("FREE Shipping")){
            return "FREE Shipping";
        }
        return "";
    }

    private String getPrime(WebElement itemEl) {
        WebElement primeEl = null;
        try {
            primeEl = itemEl.findElement(By.cssSelector("i[aria-label='Prime']"));
        }
        catch (NoSuchElementException e){
            return "";
        }
        return "Prime";
    }

    private String getImgLink(WebElement itemEl) {
        WebElement imgEl = itemEl.findElement(By.tagName("img"));
        String imgLink = imgEl.getAttribute("src");
        imgLink = imgLink.replace("US160", "US480");
        return imgLink;
    }

    private BigDecimal getSuggestedRetailPrice(WebElement itemEl) {
        WebElement sugPriceEl = null;
        try {
            sugPriceEl = itemEl.findElement(By.cssSelector("span[aria-label^='Suggested Retail Price']"));
        }
        catch (NoSuchElementException e){
            return null;
        }
        String sugPriceStr = sugPriceEl.getText();
        sugPriceStr = sugPriceStr.replace("$", "");
        Double doublePrice = Double.parseDouble(sugPriceStr);
        BigDecimal priceDec = new BigDecimal(doublePrice);
        priceDec = priceDec.setScale(2, BigDecimal.ROUND_HALF_UP);

        return priceDec;
    }

    private BigDecimal getWholePrice(WebElement itemEl) {
        WebElement wholeEl = null;
        Double price = null;
        try{
            wholeEl = itemEl.findElement(By.className("sx-price-whole"));
            WebElement fracEl = itemEl.findElement(By.className("sx-price-fractional"));
            String pBuilder = wholeEl.getText() +
                    "." +
                    fracEl.getText();
            price = Double.parseDouble(pBuilder);
        }
        catch (NoSuchElementException e){
           return null;
        }


        BigDecimal priceDec = new BigDecimal(price);
        priceDec = priceDec.setScale(2, BigDecimal.ROUND_HALF_UP);

        return priceDec;
    }

    private String getLink(WebElement itemEl) {
        List<WebElement> aElems = itemEl.findElements(By.tagName("a"));
        WebElement linkEl = null;
        for (WebElement aEl: aElems){
            try {
                linkEl = aEl;
                aEl.findElement(By.tagName("h2"));
                break;
            }
            catch (NoSuchElementException ignored){
            }
        }

        return linkEl.getAttribute("href");
    }

    private String getTitle(WebElement itemEl) {
        WebElement titleEl = itemEl.findElement(By.tagName("h2"));

        return titleEl.getText();
    }

    private String getAsin(WebElement itemEl) {
        return itemEl.getAttribute("data-asin");
    }
}
