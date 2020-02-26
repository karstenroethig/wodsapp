package karstenroethig.wodsapp.webapp.bean;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import karstenroethig.wodsapp.webapp.dto.WodSearchDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WodSearchBean
{
	private WodSearchDto wodSearchDto;

	@PostConstruct
	public void clear()
	{
		wodSearchDto = new WodSearchDto();
	}
}
