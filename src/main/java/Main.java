import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
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
//        someTests(driver);
        int k = 44;
    }

    public static void someTests(WebDriver driver) {
        driver.get("https://www.ulmart.ru");
        driver.navigate().to("/catalog/101877");
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
        List<String> urlList = new ArrayList<>();
        List<String> finalUrlList = new ArrayList<>();

        driver.get("https://www.ulmart.ru");
        WebElement elementMenu = driver.findElement(By.xpath("//div[@id='b-dropdown-catalog-menu']"));
        actions.moveToElement(elementMenu).perform();
        WebElement elementList = driver.findElement(By.xpath("//ul[@class='b-list b-list_theme_normal b-list_catalog-menu']"));

        List<WebElement> elements = elementList.findElements(By.xpath("child::*"));

        for (WebElement parentElement : elements) {
            actions.moveToElement(parentElement).perform();
            List<WebElement> childesList = parentElement.findElements(By.xpath(".//li[@class='b-list__item b-list__item_bigger ']"));
            for (WebElement childElement : childesList) {
                urlList.add(childElement.findElement(By.xpath(".//a")).getAttribute("href"));
            }
        }
        //После выполнения предыдущего кода имеется список корневых элементов, которые передаются в рекурсивную функцию в дальнейшем
        for (int i = 0; i < urlList.size(); i++) {
            finalUrlList.addAll(searchProductList(driver, urlList.get(i)));
        }

        //Этап финальных страниц-агрегаторов
        //Форирование списка финальных URL
/*
        for (String url : urlList) {
            driver.get(url);
            List<WebElement> finalList = driver.findElements(By.xpath("//section[@class='h-sect-margin1-bottom']//li[@class='b-list__item b-list__item_bigger ']"));
            System.out.println("finalList, size = " + finalList.size());
        }
*/
        // Этап страниц со списками товаров


        return elements.size();
    }

    //Рекурсивный метод для поиска финальных URL, содержащих списки товаров
    public static List<String> searchProductList(WebDriver driver, String parentURL) {
        List<String> resultList = new ArrayList<>();

        driver.get(parentURL);
        List<WebElement> list = driver.findElements(By.xpath("//section[@class='b-product b-product_theme_normal b-box box_theme_normal b-box_hoverable b-product_list-item-w-foto  double-hover-wrap  js-fly']"));

        if (list.size() > 0) {
            resultList.add(parentURL);
            return resultList;
        }

        list = driver.findElements(By.xpath("//section[@class='h-sect-margin1-bottom']//li[@class='b-list__item b-list__item_bigger ']"));

        for (WebElement element : list) {
            resultList.addAll(searchProductList(driver, element.findElement(By.xpath(".//a")).getAttribute("href")));
        }

        return resultList;
    }
}
