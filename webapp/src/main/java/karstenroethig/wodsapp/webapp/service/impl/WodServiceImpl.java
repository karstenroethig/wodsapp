package karstenroethig.wodsapp.webapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import karstenroethig.wodsapp.webapp.domain.Category;
import karstenroethig.wodsapp.webapp.domain.Wod;
import karstenroethig.wodsapp.webapp.dto.CategoryDto;
import karstenroethig.wodsapp.webapp.dto.WodDto;
import karstenroethig.wodsapp.webapp.repository.CategoryRepository;
import karstenroethig.wodsapp.webapp.repository.WodRepository;
import karstenroethig.wodsapp.webapp.service.exceptions.AlreadyExistsException;

@Service
@Transactional
public class WodServiceImpl
{
	@Autowired private WodRepository wodRepository;
	@Autowired private CategoryRepository categoryRepository;

	@Autowired private CategoryServiceImpl categoryService;

	public WodDto create()
	{
		return new WodDto();
	}

	public WodDto save(WodDto wodDto) throws AlreadyExistsException
	{
		doesAlreadyExist(null, wodDto.getName());

		Wod wod = new Wod();
		wod = merge(wod, wodDto);

		return transform(wodRepository.save(wod));
	}

	public WodDto update(WodDto wodDto) throws AlreadyExistsException
	{
		doesAlreadyExist(wodDto.getId(), wodDto.getName());

		Wod wod = wodRepository.findById(wodDto.getId()).orElse(null);
		wod = merge(wod, wodDto);

		return transform(wodRepository.save(wod));
	}

	private void doesAlreadyExist(Long id, String name) throws AlreadyExistsException
	{
		Wod existingWod = wodRepository.findOneByNameIgnoreCase(name);
		if (existingWod != null
				&& (id == null
				|| !existingWod.getId().equals(id)))
			throw new AlreadyExistsException("name");
	}

	public boolean delete(Long id)
	{
		Wod wod = wodRepository.findById(id).orElse(null);

		if (wod != null)
		{
			wodRepository.delete(wod);
			return true;
		}

		return false;
	}

	public List<WodDto> getAllElements()
	{
		return transformWods(wodRepository.findAll(Sort.by("name")));
	}

	public WodDto find(Long id)
	{
		return transform(wodRepository.findById(id).orElse(null));
	}

	private Wod merge(Wod wod, WodDto wodDto)
	{
		if (wod == null || wodDto == null )
			return null;

		wod.setName(wodDto.getName());
		wod.setDescription(wodDto.getDescription());
		wod.setType(transformCategory(wodDto.getType()));
		wod.setScheme(transformCategory(wodDto.getScheme()));
		wod.setEvent(transformCategory(wodDto.getEvent()));
		wod.setUpdatedDate(LocalDateTime.now());

		return wod;
	}

	private Category transformCategory(CategoryDto categoryDto)
	{
		if (categoryDto == null || categoryDto.getId() == null)
			return null;

		return categoryRepository.findById(categoryDto.getId()).orElse(null);
	}

	private WodDto transform(Wod wod)
	{
		if (wod == null)
			return null;

		WodDto wodDto = new WodDto();

		wodDto.setId(wod.getId());
		wodDto.setName(wod.getName());
		wodDto.setDescription(wod.getDescription());
		wodDto.setType(categoryService.transform(wod.getType()));
		wodDto.setScheme(categoryService.transform(wod.getScheme()));
		wodDto.setEvent(categoryService.transform(wod.getEvent()));
		wodDto.setUpdatedDate(wod.getUpdatedDate());

		return wodDto;
	}

	private List<WodDto> transformWods(Iterable<Wod> wods)
	{
		return transformWods(StreamSupport.stream(wods.spliterator(), false));
	}

	private List<WodDto> transformWods(Stream<Wod> wodsStream)
	{
		return wodsStream
//			.sorted(Comparator.comparing(Wod::getName, String.CASE_INSENSITIVE_ORDER))
			.map(this::transform)
			.collect(Collectors.toList());
	}
}
