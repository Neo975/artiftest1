import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainUlmart {
    public static void main(String[] args) {
//        System.setProperty("webdriver.chrome.driver", "C:\\temp2\\mike\\artiftest1\\drivers\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "D:\\JavaProjects\\Crawler_Citilink\\drivers\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

//        priceTest(driver);
//        someTests(driver);

//        popupBypass(driver, actions);
        List<String> list = new ArrayList<>();
        list.add("https://www.ulmart.ru/catalog/amortizatory_i_komplektuusie_1");
        fillProducts(list);

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
    public static List<String> popupBypass(WebDriver driver, Actions actions) {
        List<String> urlList = new ArrayList<>();
        List<String> finalUrlList = new ArrayList<>();

        driver.get("https://www.citilink.ru/catalog");
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
            finalUrlList.addAll(searchProductList(urlList.get(i)));
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


        return finalUrlList;
    }

    //Рекурсивный метод для поиска финальных URL, содержащих списки товаров
    public static List<String> searchProductList(String parentURL) {
        List<String> resultList = new ArrayList<>();

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get(parentURL);
        List<WebElement> list = driver.findElements(By.xpath("//section[@class='b-product b-product_theme_normal b-box box_theme_normal b-box_hoverable b-product_list-item-w-foto  double-hover-wrap  js-fly']"));

        if (list.size() > 0) {
            System.out.println("Ulmart: " + parentURL);
            resultList.add(parentURL);
            driver.quit();
            return resultList;
        }

        list = driver.findElements(By.xpath("//section[@class='h-sect-margin1-bottom']//li[@class='b-list__item b-list__item_bigger ']"));

        for (WebElement element : list) {
            resultList.addAll(searchProductList(element.findElement(By.xpath(".//a")).getAttribute("href")));
        }

        driver.quit();
        return resultList;
    }

    //Создание объектов класса Product и заполнениеих полей названиями, ценами и артикулами товаров
    public static List<Product> fillProducts(List<String> urls) {
        return null;
    }
}
