package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository implements BaseRepository<Product, String> {
    private final List<Product> productData = new ArrayList<>();

    @Override
    public Product create(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        productData.add(product);
        return product;
    }

    @Override
    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    @Override
    public Product findById(String productId) {
        for (Product product : productData) {
            if (product.getProductId() != null && product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public Product update(String productId, Product updatedProduct) {
        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductId().equals(productId)) {
                productData.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null;
    }

    @Override
    public void delete(String productId) {
        productData.removeIf(product -> product.getProductId().equals(productId));
    }
}
