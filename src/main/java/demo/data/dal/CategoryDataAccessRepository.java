package demo.data.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.data.Category;

public interface CategoryDataAccessRepository extends JpaRepository<Category, String> {
}
