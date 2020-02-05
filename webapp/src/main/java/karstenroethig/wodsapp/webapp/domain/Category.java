package karstenroethig.wodsapp.webapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import karstenroethig.wodsapp.webapp.domain.enums.CategoryTypeEnum;
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

@Entity
@Table(
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"id"}),
		@UniqueConstraint(columnNames = {"name", "type"})
	}
)
public class Category extends AbstractEntityId
{
	@Column(length = 255, nullable = false)
	private String name;

	@Column(length = 255, nullable = false)
	@Enumerated(EnumType.STRING)
	private CategoryTypeEnum type;
}
