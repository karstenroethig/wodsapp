package karstenroethig.wodsapp.webapp.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import karstenroethig.wodsapp.webapp.domain.enums.CategoryTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
//@EqualsAndHashCode(exclude="name")
public class CategoryDto extends AbstractDtoId
{
	@NotNull
	@Size(min = 1, max = 255)
	private String name;

	@NotNull
	private CategoryTypeEnum type;

	@Override
	public int hashCode()
	{
		int hash = 5;
		hash = 83 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		final CategoryDto other = (CategoryDto) obj;
		return Objects.equals(this.id, other.id);
	}
}
