package karstenroethig.wodsapp.webapp.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class WodDto extends AbstractDtoId
{
	@NotNull
	@Size(min = 1, max = 255)
	private String name;

	@NotNull
	@Size(min = 1)
	private String description;

	private CategoryDto type;

	private CategoryDto scheme;

	private CategoryDto event;

	private LocalDateTime updatedDate;
}
