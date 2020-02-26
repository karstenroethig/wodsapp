package karstenroethig.wodsapp.webapp.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import karstenroethig.wodsapp.webapp.domain.Category_;
import karstenroethig.wodsapp.webapp.domain.Wod;
import karstenroethig.wodsapp.webapp.domain.Wod_;
import karstenroethig.wodsapp.webapp.dto.WodSearchDto;

public class WodSpecifications
{
	private WodSpecifications() {}

	public static Specification<Wod> matchesSearchParam(WodSearchDto wodSearchDto)
	{
		return (Root<Wod> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
			{
				List<Predicate> restrictions = new ArrayList<>();

				if (StringUtils.isNotBlank(wodSearchDto.getText()))
					restrictions.add(cb.or(
							cb.like(root.get(Wod_.name), "%" + wodSearchDto.getText() + "%"),
							cb.like(root.get(Wod_.description), "%" + wodSearchDto.getText() + "%")));

				if (wodSearchDto.getType() != null)
					restrictions.add(cb.equal(root.get(Wod_.type).get(Category_.id), wodSearchDto.getType().getId()));

				if (wodSearchDto.getScheme() != null)
					restrictions.add(cb.equal(root.get(Wod_.scheme).get(Category_.id), wodSearchDto.getScheme().getId()));

				if (wodSearchDto.getEvent() != null)
					restrictions.add(cb.equal(root.get(Wod_.event).get(Category_.id), wodSearchDto.getEvent().getId()));

				return cb.and(restrictions.toArray(new Predicate[] {}));
			};
	}
}
