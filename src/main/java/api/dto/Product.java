package api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
@Log
public class Product extends BaseModel {
    protected int id;
    protected String title;
    protected String description;
    protected int price;
    protected double discountPercentage;
    protected double rating;
    protected int stock;
    protected String brand;
    protected String category;
    protected String thumbnail;
    protected ArrayList<String> images;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        boolean productId = id == product.id | id == 0;
        return productId && price == product.price && Double.compare(product.discountPercentage, discountPercentage) == 0 && Double.compare(product.rating, rating) == 0 && stock == product.stock && Objects.equals(title, product.title) && Objects.equals(description, product.description) && Objects.equals(brand, product.brand) && Objects.equals(category, product.category) && Objects.equals(thumbnail, product.thumbnail) && Objects.equals(images, product.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price, discountPercentage, rating, stock, brand, category, thumbnail, images);
    }
}
