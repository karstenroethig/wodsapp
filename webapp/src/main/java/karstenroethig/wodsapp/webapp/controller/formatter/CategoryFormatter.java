package karstenroethig.wodsapp.webapp.controller.formatter;

import karstenroethig.wodsapp.webapp.dto.CategoryDto;

public class CategoryFormatter extends AbstractIdFormatter<CategoryDto>
{
	@Override
	protected CategoryDto createInstance()
	{
		return new CategoryDto();
	}
}
