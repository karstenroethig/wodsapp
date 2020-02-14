package karstenroethig.wodsapp.webapp.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

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
	name = "wod",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"id"}),
		@UniqueConstraint(columnNames = {"name"})
	}
)
public class Wod extends AbstractEntityId
{
	@Column(length = 255, nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@JoinColumn(name = "type_id")
	@ManyToOne(optional = true)
	private Category type;

	@JoinColumn(name = "scheme_id")
	@ManyToOne(optional = true)
	private Category scheme;

	@JoinColumn(name = "event_id")
	@ManyToOne(optional = true)
	private Category event;

	@Column(name = "updated_date", nullable = false)
	@Type(type = "org.hibernate.type.LocalDateTimeType")
	private LocalDateTime updatedDate;
}
