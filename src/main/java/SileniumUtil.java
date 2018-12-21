import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class SileniumUtil {
    private static final Logger logger = LogManager.getLogger(SileniumUtil.class.getName());
    private static final String AMA_URL = "https://www.amazon.com";

    public static void launchParse(String url){
        WebDriver driver = getDriverWithLocationSet(url);

        while (true){
            List<AmItem> items = parseCurrentPage(driver);
            AmaDao.saveItems(items);
            try {
                chooseNextPage(driver);
            } catch (IOException e) {
                logger.info("finishing task....");
                driver.close();
                HibernateUtil.shutdown();
                break;
            }
        }
    }

    private static void chooseNextPage(WebDriver driver) throws IOException {

        By pagnBy = By.id("pagn");
        WebElement pagnEl = waitForElement(driver, pagnBy);
        WebElement curPageEl = pagnEl.findElement(By.className("pagnCur"));
        int pageBefore = Integer.parseInt(curPageEl.getText());
        WebElement nextEl = null;
        try {
            nextEl = pagnEl.findElement(By.id("pagnNextLink"));
        }
        catch (NoSuchElementException e){
            logger.info("No more next pages");
            throw new IOException();
        }
        String nextPageUrl = nextEl.getAttribute("href");
        logger.info("Next page is: " + nextPageUrl);
        sleep(2000);
        driver.get(nextPageUrl);

        while (true){
             pagnEl = waitForElement(driver, pagnBy);
             curPageEl = pagnEl.findElement(By.className("pagnCur"));
             int pageNow = Integer.parseInt(curPageEl.getText());
             if (pageNow!=pageBefore){
                logger.info("Page now is: " + pageNow);
                 break;
             }
        }
    }

    private static List<AmItem> parseCurrentPage(WebDriver driver) {
        List<AmItem> result = new ArrayList<>();
        List<WebElement> itemElements = driver.findElements(By.cssSelector("li[id^='result_']"));
        for (WebElement element: itemElements){
            result.add(new ItemBuilder().buildItem(element));
        }

        return result;
    }

    private static WebDriver getDriver(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");

        return new ChromeDriver();
    }

    private static WebDriver getDriverWithLocationSet(String url){
        WebDriver driver = getDriver();
        driver.get(url);
        By by = By.cssSelector("input[data-action-type='SELECT_LOCATION']");
        WebElement locDrop = waitForElement(driver, by);
        locDrop.click();
        By inputBy = By.id("GLUXZipUpdateInput");
        WebElement input = waitForElement(driver, inputBy);
        input.sendKeys("94529");

        By appButBy = By.cssSelector("input[aria-labelledby='GLUXZipUpdate-announce']");
        WebElement appButton = waitForElement(driver,appButBy);
        sleep(1000);
        appButton.click();
        By waitBy = By.id("GLUXZipConfirmationValue");
        while(true){
            String s = waitForElement(driver, waitBy).getText();
            if (s.equals("94529")){
                break;
            }
            else {
                sleep(100);
            }
        }
        By doneButBy = By.cssSelector("button[name='glowDoneButton']");
        WebElement doneButton = waitForElement(driver, doneButBy);
        doneButton.click();

        By shipToBy = By.id("glow-ingress-block");

        WebElement shipToEl = null;
        while (true){
            shipToEl = waitForElement(driver, shipToBy);
            if (shipToEl.getText().contains("Concord 94529")){
                break;
            }
            else {
                sleep(100);
            }
        }

        return driver;
    }

    private static WebElement waitForElement(WebDriver driver, By by) {
        WebElement result = null;
        int retries = 0;
        while (true){
           try {
               result = driver.findElement(by);
               break;
           }
           catch (NoSuchElementException e){
                sleep(100);
                retries++;
                if (retries==100){
                    if (!hasConnection()){
                        retries = 0;
                    }
                    else {
                        logger.info("No such element");
                        break;
                    }
                }
           }
       }

        return result;
    }

    private static void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
        }
    }

    private static boolean hasConnection(){
        URL url= null;
        try {
            url = new URL(AMA_URL);
            URLConnection con=url.openConnection();
            con.getInputStream();
        } catch (Exception e) {
            logger.error("No connection available");
            return false;
        }

        return true;
    }
}
