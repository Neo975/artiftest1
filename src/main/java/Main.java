import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\temp2\\mike\\artiftest1\\drivers\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");

        WebDriver driver = new ChromeDriver(options);
        Actions actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

//        priceTest(driver);
        popupBypass(driver, actions);
        int k = 44;
    }

    public static void priceTest(WebDriver driver) {
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
    }

    //Hover test
    public static int popupBypass(WebDriver driver, Actions actions) {
        driver.get("https://www.ulmart.ru");
        WebElement elementMenu = driver.findElement(By.xpath("//div[@id='b-dropdown-catalog-menu']"));
        actions.moveToElement(elementMenu).perform();
        WebElement elementList = driver.findElement(By.xpath("//ul[@class='b-list b-list_theme_normal b-list_catalog-menu']"));

//        List<WebElement> elements = elementList.findElements(By.xpath(".//li[@class='b-list__item b-dropdown b-dropdown_toright b-dropdown_catalog-menu dropdown dropdown_theme_normal dropdown_catalog-menu']"));
        List<WebElement> elements = elementList.findElements(By.xpath("child::*"));

        for (WebElement parentElement : elements) {
            actions.moveToElement(parentElement).perform();
            List<WebElement> childesList = parentElement.findElements(By.xpath(".//li[@class='b-list__item b-list__item_bigger ']"));
            for (WebElement childElement : childesList) {
                System.out.println(childElement.findElement(By.xpath(".//a")).getAttribute("href"));
            }
        }

        System.out.println(elements.size());

        return elements.size();
    }
}
