package musigmamart.order.persistence;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import musigmamart.account.persistence.AccountEntity;

@Entity
@Table(name = "orders")
@Getter @Setter
public class OrderEntity {
	
	OrderEntity(String custID, String status, String orderDetails, String deliveryAddress) {
		this.custid = custID;
		this.status = status;
		this.orderdetails = orderDetails;
		this.deliveryaddress = deliveryAddress;
	}
	
	OrderEntity(){}
	
	@Id
    private int id;

    @NotBlank
    private String custid;
    
    @NotBlank
    private String status;

    @NotBlank
    private String orderdetails;
    
    private String deliveryaddress;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "products='" + orderdetails + '\'' +
                '}';
    }
    

}
