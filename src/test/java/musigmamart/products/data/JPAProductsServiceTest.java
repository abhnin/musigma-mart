package musigmamart.products.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import musigmamart.products.Product;

@DataJpaTest
public class JPAProductsServiceTest {
	
	@Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ProductRepository productRepository;

    private JPAProductsService jpaProductsService;

    @BeforeEach
    void setup() {
        this.jpaProductsService = new JPAProductsService(this.productRepository);
    }

    @Test
    @DisplayName("Save and check data from in memory H2 DB")
    void returnsDataFromDatabase() {
        String expectedTitle = "Abhishek Nag";
        String expectedSku = saveTestProduct(expectedTitle, BigDecimal.valueOf(5.55));

        Iterable<Product> products = jpaProductsService.getProducts();

        assertThat(products).anyMatch(item -> expectedTitle.equals(item.getTitle()) && expectedSku.equals(item.getSku()));
    }

    @Test
    @DisplayName("Save and check product from in memory H2 DB")
    void returnsProductBySku() {
        String expectedTitle = "Abhishek Nag";
        String expectedSku = saveTestProduct(expectedTitle, BigDecimal.valueOf(5.55));

        Product itemBySku = jpaProductsService.getProductBySku(expectedSku);

        assertThat(itemBySku.getTitle()).isEqualTo(expectedTitle);
    }

    private String saveTestProduct(String title, BigDecimal price) {
        ProductEntity itemEntity = new ProductEntity();
        String sku = UUID.randomUUID().toString().replace("-", "");
        itemEntity.sku = sku;
        itemEntity.title = title;
        itemEntity.price = price;

        testEntityManager.persistAndFlush(itemEntity);

        return sku;
    }

}
