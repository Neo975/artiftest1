import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainCitilink {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\JavaProjects\\Crawler_Ulmart\\drivers\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        findRootURLs(driver);

        int k = 44;

    }

    public static void findRootURLs(WebDriver driver) {
        driver.get("https://www.citilink.ru/catalog");
        List<WebElement> list = driver.findElements(By.xpath("//ul[@class='category-catalog__children-list']/li"));
        System.out.println("root URLS: " + list.size());
    }
}
