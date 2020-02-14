package karstenroethig.wodsapp.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import karstenroethig.wodsapp.webapp.domain.Wod;

public interface WodRepository extends JpaRepository<Wod,Long>, JpaSpecificationExecutor<Wod>
{
	Wod findOneByNameIgnoreCase(String name);
}
