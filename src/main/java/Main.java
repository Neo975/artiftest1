import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\JavaProjects\\artiftest1\\drivers\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.ulmart.ru/catalog/cpu");
        List<WebElement> listOfElements = driver.findElements(By.xpath("//div[@id='catalogGoodsBlock']//section"));

        for (WebElement rootElement : listOfElements) {
 //           System.out.println(elem.getAttribute("innerHTML"));
            WebElement elementPrice = rootElement.findElement(By.xpath(".//span[@class='b-price__num js-price']"));
            WebElement elementArticular = rootElement.findElement(By.xpath(".//div[@class='b-product__art']/span[@class='num']"));;
            WebElement elementTitle = rootElement.findElement(By.xpath(".//a[@class='must_be_href js-gtm-product-click']"));
            String strPrice = elementPrice.getText();
            String strArticular = elementArticular.getText();
            strPrice = strPrice.replaceAll("\\s+", "");
            int price = Integer.valueOf(strPrice);
            System.out.println(strArticular + ", " + price + ", " + elementTitle.getText());
        }

//Hover test
        WebElement elementMenu = driver.findElement(By.xpath("//div[@id='b-dropdown-catalog-menu']"));
        actions.moveToElement(elementMenu).perform();
        WebElement elementList = driver.findElement(By.xpath("//ul[@class='b-list b-list_theme_normal b-list_catalog-menu']"));

        WebElement elementAuto = elementList.findElement(By.xpath("//a[@href='/catalog/auto']"));

//        driver.quit();
        int k = 44;
    }
}
