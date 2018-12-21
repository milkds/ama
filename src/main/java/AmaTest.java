import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmaTest {

    public static void main(String[] args) {
        //testMatcher();
        testAma2();
    }



    public static void testAma(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        String uri = "https://www.amazon.com/s/ref=lp_15735731_nr_p_89_5?fst=as%3Aoff&rh=n%3A15684181%2Cn%3A%2115690151%2Cn%3A15857501%2Cn%3A15735731%2Cp_89%3AWeatherTech&bbn=15735731&ie=UTF8&qid=1544998543&rnid=2528832011";
        driver.get(uri);
        bad_sleep(3000);
        List<WebElement> results = driver.findElements(By.cssSelector("li[id^='result_']"));
        for (WebElement el: results){
            System.out.println(el.getText());
        }
        driver.close();
    }
    public static void testAma2(){
      //  String uri = "https://www.amazon.com/s/ref=lp_15735731_nr_p_89_5?fst=as%3Aoff&rh=n%3A15684181%2Cn%3A%2115690151%2Cn%3A15857501%2Cn%3A15735731%2Cp_89%3AWeatherTech&bbn=15735731&ie=UTF8&qid=1544998543&rnid=2528832011";
        String uri = "https://www.amazon.com/s/ref=sr_nr_n_0?fst=as%3Aoff&rh=n%3A15684181%2Cn%3A%2115690151%2Cn%3A15857501%2Cn%3A15735731%2Cp_89%3AWeatherTech%2Cn%3A15735741&bbn=15735731&ie=UTF8&qid=1545311324&rnid=15735731";
      //  String uri = "https://www.amazon.com/s/ref=sr_nr_n_1?fst=as%3Aoff&rh=n%3A15684181%2Cn%3A%2115690151%2Cn%3A15857501%2Cn%3A15735731%2Cp_89%3AWeatherTech%2Cn%3A318250011&bbn=15735731&ie=UTF8&qid=1545311324&rnid=15735731";
        SileniumUtil.launchParse(uri);
    }

    public static void bad_sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
        }
    }

    public static void testMatcher(){
       // String title = "WeatherTech 443242 FloorLiner";
       // String title = "Weathertech 44481-1-2 Front and Rear Floorliners for 2013-2017 Honda Accord Sedan";
        String title = "WeatherTech (444331-443052 FloorLiner, Front/Rear, Black";
        Pattern pattern = Pattern.compile("([0-9]{0,6}-?[0-9]{0,6})");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()){
            System.out.println(matcher.group(1));
        }
    }
}
