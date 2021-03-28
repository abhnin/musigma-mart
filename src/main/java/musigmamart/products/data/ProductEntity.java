package musigmamart.products.data;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class ProductEntity {
	@Id
    String sku;

    @NotBlank
    String title;

    @NotNull
    BigDecimal price;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProductEntity) {
            ProductEntity other = (ProductEntity) obj;
            return Objects.equals(this.sku, other.sku);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.sku);
    }
}
