package demo.rest;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.boundaries.ProductBoundary;
import demo.logic.ProductManagementService;
import demo.logic.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/shopping/products")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductsController {
	private ProductManagementService managementService;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductBoundary storeProduct(@RequestBody ProductBoundary productInput){
		return managementService.create(productInput);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductBoundary getProductById(@PathVariable("id") String id) {
		return managementService.getProductById(id);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductBoundary[] getProductsBy(
			@RequestParam(name = "filterType", required = false, defaultValue = "") String filterType,
			@RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue,
			@RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortAttribute,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") String order,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size){
		return managementService.getProductsBy(filterType, filterValue, sortAttribute, order, size, page);
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
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, String> handleException(ProductNotFoundException e) {
		String error = e.getMessage();
		if (error == null) {
			error = "Not found";
		}
		return Collections.singletonMap("error", error);
	}
	
	


}
