package musigmamart.products;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Product {
	final private String sku;
	final private String title;
    final private BigDecimal price;
}
