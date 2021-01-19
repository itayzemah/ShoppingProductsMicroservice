package demo.data.converters;
import org.springframework.stereotype.Component;

import demo.boundaries.CategoryBoundary;
import demo.data.Category;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class CategoryConverter {

	public CategoryBoundary entityToBoundary(Category category) {
		return new CategoryBoundary(category.getName(), category.getDescription(), category.getParentCategory());
	}
	
	public Category boundaryToEntity(CategoryBoundary categoryBoundary) {
		return Category.builder()
				.name(categoryBoundary.getName())
				.description(categoryBoundary.getDescription())
				.parentCategory(categoryBoundary.getParentCategory())
				.build();
	}
	
	
}
