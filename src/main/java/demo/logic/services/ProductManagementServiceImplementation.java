package demo.logic.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import demo.boundaries.CategoryBoundary;
import demo.boundaries.ProductBoundary;
import demo.data.Product;
import demo.data.converters.CategoryConverter;
import demo.data.converters.ProductConverter;
import demo.data.dal.CategoryDataAccessRepository;
import demo.data.dal.ProductDataAccessRepository;
import demo.logic.ProductManagementService;
import demo.logic.exception.InvalidDataException;
import demo.logic.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class ProductManagementServiceImplementation implements ProductManagementService ,CommandLineRunner{
	private ProductDataAccessRepository productDAL;
	private CategoryDataAccessRepository categoryDAL;
	private ProductConverter converter;

	@Override
	public void deleteAllProducts() {
		this.productDAL.deleteAll();	
	}

	@Override
	public ProductBoundary create(ProductBoundary productInput) {
		if(this.productDAL.existsById(productInput.getId())) {
			throw new InvalidDataException("Product with this id already exist.");
		}
		if(!this.categoryDAL.existsById(productInput.getCategory().getName())){
			throw new InvalidDataException("Category does not exist.");
		}
		Product entity = this.converter.boundaryToEntity(productInput);
		entity = this.productDAL.save(entity);
		return this.converter.entityToBoundary(entity);
	}

	@Override
	public ProductBoundary getProductById(String id) {
		Product entity = this.productDAL.findById(id).orElseThrow(() -> new ProductNotFoundException("Unknown ID: " + id));
		return this.converter.entityToBoundary(entity);
	}

	@Override
	public ProductBoundary[] getProductsBy(String filterType, String filterValue, String sortAttribute, String order,
			int size, int page) {
		if (filterType == null || filterType.isEmpty() || filterValue == null || filterValue.isEmpty()) {
			return this.getProducts(sortAttribute, order, size, page);
		}
		switch (filterType) {
		case "byCategoryName":
			return this.getProductsByCategoryName(filterValue, sortAttribute, order, size, page);
		case "byMaxPrice":
			return this.getProductsByMaxPrice(filterValue, sortAttribute, order, size, page);
		case "byMinPrice":
			return this.getProductsByMinPrice(filterValue, sortAttribute, order, size, page);
		case "byName":
			return this.getProductsByName(filterValue, sortAttribute, order, size, page);
		default:
			throw new ProductNotFoundException("Unknown Filter");
		}
	}

	private ProductBoundary[] getProductsByName(String filterValue, String sortAttribute, String order, int size,
			int page) {
		return productDAL.findAllByName(filterValue, PageRequest.of(page, size, Direction.fromString(order), sortAttribute)).stream()
				.map(this.converter::entityToBoundary).collect(Collectors.toList()).toArray(new ProductBoundary[0]);
	}

	private ProductBoundary[] getProductsByCategoryName(String filterValue, String sortAttribute, String order,
			int size, int page) {
		return productDAL.findAllByCategory_Name(filterValue, PageRequest.of(page, size, Direction.fromString(order), sortAttribute)).stream()
				.map(this.converter::entityToBoundary).collect(Collectors.toList()).toArray(new ProductBoundary[0]);
	}

	private ProductBoundary[] getProductsByMaxPrice(String filterValue, String sortAttribute, String order, int size,
			int page) {
		double price;
		try {
			price = Double.parseDouble(filterValue);
		} catch (NumberFormatException e) {
			throw new InvalidDataException("Invalid price format");
		}
		return productDAL.findAllByPriceLessThanEqual(price, PageRequest.of(page, size, Direction.fromString(order), sortAttribute)).stream()
				.map(this.converter::entityToBoundary).collect(Collectors.toList()).toArray(new ProductBoundary[0]);
	}

	private ProductBoundary[] getProductsByMinPrice(String filterValue, String sortAttribute, String order, int size,
			int page) {
		double price;
		try {
			price = Double.parseDouble(filterValue);
		} catch (NumberFormatException e) {
			throw new InvalidDataException("Invalid price format");
		}
		return productDAL.findAllByPriceGreaterThanEqual(price, PageRequest.of(page, size, Direction.fromString(order), sortAttribute)).stream()
				.map(this.converter::entityToBoundary).collect(Collectors.toList()).toArray(new ProductBoundary[0]);
	}

	private ProductBoundary[] getProducts(String sortAttribute, String order, int size, int page) {
		return productDAL.findAll(PageRequest.of(page, size, Direction.fromString(order), sortAttribute)).stream()
				.map(this.converter::entityToBoundary).collect(Collectors.toList()).toArray(new ProductBoundary[0]);
	}

	@Override
	public void run(String... args) throws Exception {
		CategoryBoundary category1 = new CategoryBoundary();
		CategoryConverter categoryConverter = new CategoryConverter();
		category1.setName("c1");
		category1.setDescription("c1 des");
		this.categoryDAL.save(categoryConverter.boundaryToEntity(category1));
		ProductBoundary p1 = new ProductBoundary();
		p1.setCategory(category1);
		p1.setId("1");
		p1.setName("p1");
		p1.setPrice(100.20);
		p1.setImage("img.jpg");
		this.create(p1);
	}

	
}
