package demo.data.dal;


import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import demo.data.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductDataAccessRepository extends JpaRepository<Product, String> {
	
	public List<Product> findAllByName(@Param("name") String name, Pageable pageable);
	
	@Query("SELECT p FROM Product p LEFT OUTER JOIN Category c ON p.category = c.name WHERE c.parentCategory = :parentCategory OR c.name = :parentCategory")
	public List<Product> findAllByCategory_Name(@Param("parentCategory") String parentCategory, Pageable pageable);
	
	public List<Product> findAllByPriceLessThanEqual(@Param("price") Double price, Pageable pageable);
	
	public List<Product> findAllByPriceGreaterThanEqual(@Param("price") Double price, Pageable pageable);
}
