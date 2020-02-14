package karstenroethig.wodsapp.webapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import karstenroethig.wodsapp.webapp.controller.formatter.CategoryFormatter;

@Configuration
public class FormatterConfiguration implements WebMvcConfigurer
{
	@Override
	public void addFormatters(FormatterRegistry formatterRegistry)
	{
		formatterRegistry.addFormatter(new CategoryFormatter());
	}
}
