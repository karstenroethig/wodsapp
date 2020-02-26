package karstenroethig.wodsapp.webapp.dto;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class WodSearchDto
{
	private String text;
	private CategoryDto type;
	private CategoryDto scheme;
	private CategoryDto event;

	public boolean hasParams()
	{
		return StringUtils.isNotBlank(text)
				|| type != null
				|| scheme != null
				|| event != null;
	}
}
