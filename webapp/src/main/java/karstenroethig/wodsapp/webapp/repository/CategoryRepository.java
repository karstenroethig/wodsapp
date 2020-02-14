package karstenroethig.wodsapp.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import karstenroethig.wodsapp.webapp.domain.Category;
import karstenroethig.wodsapp.webapp.domain.enums.CategoryTypeEnum;

public interface CategoryRepository extends JpaRepository<Category,Long>
{
	Category findOneByNameIgnoreCaseAndType(String name, CategoryTypeEnum type);

	List<Category> findAllByTypeOrderByName(CategoryTypeEnum type);
}
