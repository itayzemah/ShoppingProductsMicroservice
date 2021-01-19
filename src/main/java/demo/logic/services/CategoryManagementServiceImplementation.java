package demo.logic.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import demo.boundaries.CategoryBoundary;
import demo.data.Category;
import demo.data.converters.CategoryConverter;
import demo.data.dal.CategoryDataAccessRepository;
import demo.logic.CategoryManagementService;
import demo.logic.exception.InvalidDataException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class CategoryManagementServiceImplementation implements CategoryManagementService {
	private CategoryDataAccessRepository categoryDAL;
	private CategoryConverter converter;

	@Override
	public CategoryBoundary create(CategoryBoundary categoryInput) {
		if(this.categoryDAL.existsById(categoryInput.getName())) {
			throw new InvalidDataException("Category with this name already exists");
		}
		Category entity = this.converter.boundaryToEntity(categoryInput);
		entity = this.categoryDAL.save(entity);
		return this.converter.entityToBoundary(entity);
	}

	@Override
	public CategoryBoundary[] getCategories(String sortBy, String order, int size, int page) {
		return categoryDAL.findAll(PageRequest.of(page, size, Direction.fromString(order), sortBy)).stream()
				.map(this.converter::entityToBoundary).collect(Collectors.toList()).toArray(new CategoryBoundary[0]);
	}

	@Override
	public void deleteAllCategories() {
		this.categoryDAL.deleteAll();		
	}

	
}
