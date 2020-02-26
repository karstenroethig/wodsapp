package karstenroethig.wodsapp.webapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import karstenroethig.wodsapp.webapp.controller.exceptions.ForbiddenException;
import karstenroethig.wodsapp.webapp.controller.exceptions.NotFoundException;

public abstract class AbstractController
{
	protected String createCurrentItemsText(Page<?> page)
	{
		int itemsFrom = page.getNumber() * page.getSize() + 1;
		int itemsTo = page.getNumber() * page.getSize() + page.getNumberOfElements();

		return String.format("%s-%s", itemsFrom, itemsTo);
	}

	@ExceptionHandler(ForbiddenException.class)
	void handleForbiddenException(HttpServletResponse response, ForbiddenException ex) throws IOException
	{
		response.sendError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
	}

	@ExceptionHandler(NotFoundException.class)
	void handleNotFoundException(HttpServletResponse response, NotFoundException ex) throws IOException
	{
		response.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}
}
