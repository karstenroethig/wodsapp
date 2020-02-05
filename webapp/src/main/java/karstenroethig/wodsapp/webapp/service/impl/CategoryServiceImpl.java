package karstenroethig.wodsapp.webapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import karstenroethig.wodsapp.webapp.domain.Category;
import karstenroethig.wodsapp.webapp.domain.enums.CategoryTypeEnum;
import karstenroethig.wodsapp.webapp.dto.CategoryDto;
import karstenroethig.wodsapp.webapp.repository.CategoryRepository;
import karstenroethig.wodsapp.webapp.service.exceptions.AlreadyExistsException;

@Service
@Transactional
public class CategoryServiceImpl
{
	@Autowired protected CategoryRepository categoryRepository;

	public CategoryDto create()
	{
		return new CategoryDto();
	}

	public CategoryDto save(CategoryDto categoryDto) throws AlreadyExistsException
	{
		doesAlreadyExist(null, categoryDto.getName(), categoryDto.getType());

		Category category = new Category();
		category = merge(category, categoryDto);

		return transform(categoryRepository.save(category));
	}

	public CategoryDto update(CategoryDto categoryDto) throws AlreadyExistsException
	{
		doesAlreadyExist(categoryDto.getId(), categoryDto.getName(), categoryDto.getType());

		Category category = categoryRepository.findById(categoryDto.getId()).orElse(null);
		category = merge(category, categoryDto);

		return transform(categoryRepository.save(category));
	}

	private void doesAlreadyExist(Long id, String name, CategoryTypeEnum type) throws AlreadyExistsException
	{
		Category existingCategory = categoryRepository.findOneByNameIgnoreCaseAndType(name, type);
		if (existingCategory != null
				&& (id == null
				|| !existingCategory.getId().equals(id)))
			throw new AlreadyExistsException("name");
	}

	public boolean deleteTag(Long id)
	{
		Category category = categoryRepository.findById(id).orElse(null);

		if (category != null)
		{
			categoryRepository.delete(category);
			return true;
		}

		return false;
	}

	public List<CategoryDto> getAllElements()
	{
		return transformCategories(categoryRepository.findAll(Sort.by("type", "name")));
	}

	public CategoryDto find(Long id)
	{
		return transform(categoryRepository.findById(id).orElse(null));
	}

	private Category merge(Category category, CategoryDto categoryDto)
	{
		if (category == null || categoryDto == null )
			return null;

		category.setName(categoryDto.getName());
		category.setType(categoryDto.getType());

		return category;
	}

	private CategoryDto transform(Category category)
	{
		if (category == null)
			return null;

		CategoryDto categoryDto = new CategoryDto();

		categoryDto.setId(category.getId());
		categoryDto.setName(category.getName());
		categoryDto.setType(category.getType());

		return categoryDto;
	}

	private List<CategoryDto> transformCategories(Iterable<Category> categories)
	{
		return transformCategories(StreamSupport.stream(categories.spliterator(), false));
	}

	private List<CategoryDto> transformCategories(Stream<Category> categoriesStream)
	{
		return categoriesStream
//			.sorted(Comparator.comparing(Category::getName, String.CASE_INSENSITIVE_ORDER))
			.map(this::transform)
			.collect(Collectors.toList());
	}
}
