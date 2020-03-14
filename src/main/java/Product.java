public class Product {
    private String description;
    private Float price;
    private Integer article;

    public Product(String description, Float price, Integer article) {
        this.description = description;
        this.price = price;
        this.article = article;
    }

    public Product(String json) {

    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getArticle() {
        return article;
    }
}
