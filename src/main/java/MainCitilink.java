import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainCitilink {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\JavaProjects\\Crawler_Ulmart\\drivers\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
//        Actions actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        test(driver);
        driver.quit();
    }

    public static void test(WebDriver driver) {
//        List<String> rootList = findRootURLs(driver);
        List<String> rootList = Arrays.asList("https://www.citilink.ru/catalog/mobile/", "https://www.citilink.ru/catalog/computers_and_notebooks/");
        List<String> finalList = new ArrayList<>();
        for (String rootURL : rootList) {
            finalList.addAll(findFinalURLS(rootURL));
        }

        for (String finalURL : finalList) {
            System.out.println("FINAL:" + finalURL);
        }

        int k = 44;
    }

    public static void test2(WebDriver driver) {
        List<String> finalList = findFinalURLS("https://www.citilink.ru/catalog/mobile/tablet_pc_aks/");

        for (String finalURL : finalList) {
            System.out.println("FINAL:   " + finalURL);
        }

        int k = 44;
    }

    public static List<String> findRootURLs(WebDriver driver) {
        List<String> res = new ArrayList<>();

        driver.get("https://www.citilink.ru/catalog");
        List<WebElement> list = driver.findElements(By.xpath("//ul[@class='category-catalog__children-list']/li"));
        System.out.println("root URLS: " + list.size());
        for (WebElement element : list) {
            res.add(element.findElement(By.xpath(".//a")).getAttribute("href"));
        }

        return res;
    }

//Возвращает список финальных URL для заданного корневого URL.
//В HTML-странице корневого URL могут быть ссылки на дополнительные страницы со списками товаров.
//Цель этого метода - вернуть URL без ссылок на дополнительные страницы со списками товаров.
    public static List<String> findFinalURLS(String rootURL) {
        List<String> res = new ArrayList<>();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(rootURL);

        List<WebElement> list = driver.findElements(By.xpath("//ul[starts-with(@class, 'col subnavigation__left_side_block_ul')]/li"));

        if (list.size() == 0) {
            //Найден финальный URL
            res.add(rootURL);
            driver.quit();

            return res;
        }

        for (WebElement element : list) {
            String currentURL = element.findElement(By.xpath(".//a")).getAttribute("href");
            List<String> recursiveList = findFinalURLS(currentURL);
            res.addAll(recursiveList);
        }
        driver.quit();

        return res;
    }

//Возвращает список товаров, найденных на странице с указанным URL.
//Метод сам осуществляет пэйджинацию(перелистываение) списка товаров, если список товаров разделен на группы.
    public static List<Product> parseItems(String url) {
        List<Product> res = new ArrayList<>();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(url);
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='js--subcategory-product-item subcategory-product-item product_data__gtm-js  product_data__pageevents-js ddl_product']"));
        //Создаем объекты класса Product, забиваем туда description, price и т.д.
        //Все помещаем в список res
        //..

        //Осуществляем пэйджинацию, при необходимости
        WebElement page = driver.findElement(By.xpath("//div[@class='page_listing'][1]//link[@rel='next']"));
        if (page == null) {
            return res;
        }

        res.addAll(parseItems(page.getAttribute("href")));

        return res;
    }
}
