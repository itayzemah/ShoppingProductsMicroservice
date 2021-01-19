package demo.logic;

import demo.boundaries.CategoryBoundary;

public interface CategoryManagementService {

	CategoryBoundary create(CategoryBoundary categoryInput);

	CategoryBoundary[] getCategories(String sortBy, String order, int size, int page);

	void deleteAllCategories();
}

