package demo.data.converters;
import org.springframework.stereotype.Component;

import demo.boundaries.ProductBoundary;
import demo.data.Product;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class ProductConverter {
	private ObjectMapper jackson;
	
	@PostConstruct
	public void setup() {
		this.jackson = new ObjectMapper(); 
	}

	public ProductBoundary entityToBoundary(Product product) {
		Map<String, Object> productDetailes;
		try {
			productDetailes = this.jackson.readValue(product.getProductDetails(), Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return new ProductBoundary(
				product.getId(), 
				product.getName(), 
				product.getPrice(), 
				product.getImage(), 
				productDetailes, 
				new CategoryConverter().entityToBoundary(product.getCategory()));
	}
	
	public Product boundaryToEntity(ProductBoundary productBoundary) {
		String productDetailes;
		try {
			productDetailes = this.jackson.writeValueAsString(productBoundary.getProductDetails());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new  Product(
				productBoundary.getId(),
				productBoundary.getName(),
				productBoundary.getPrice(),
				productBoundary.getImage(),
				productDetailes,
				new CategoryConverter().boundaryToEntity(productBoundary.getCategory()));
	}
	
}
