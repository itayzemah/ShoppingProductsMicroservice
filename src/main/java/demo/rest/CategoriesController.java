package demo.rest;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.boundaries.CategoryBoundary;
import demo.logic.CategoryManagementService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/shopping/categories")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CategoriesController {
	private CategoryManagementService managementService;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CategoryBoundary storeCategory(@RequestBody CategoryBoundary categoryInput) {
		return managementService.create(categoryInput);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CategoryBoundary[] getCategories(
			@RequestParam(name = "sortBy", required = false, defaultValue = "name") String sortBy,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") String order,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {
		return managementService.getCategories(sortBy, order, size, page);
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, String> handleException(RuntimeException e) {
		String error = e.getMessage();
		if (error == null) {
			error = "Not found";
		}
		return Collections.singletonMap("error", error);
	}
}
