package demo.logic;

import demo.boundaries.ProductBoundary;

public interface ProductManagementService {
	void deleteAllProducts();

	ProductBoundary create(ProductBoundary productInput);

	ProductBoundary getProductById(String id);

	ProductBoundary[] getProductsBy(String filterType, String filterValue, String sortAttribute, String order, int size,
			int page);

}

