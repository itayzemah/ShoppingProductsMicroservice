package demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.logic.CategoryManagementService;
import demo.logic.ProductManagementService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/shopping")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingController {
	private ProductManagementService productManagementService;
	private CategoryManagementService categoryManagementService;
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteAll() {
		productManagementService.deleteAllProducts();
		categoryManagementService.deleteAllCategories();
	}

}
