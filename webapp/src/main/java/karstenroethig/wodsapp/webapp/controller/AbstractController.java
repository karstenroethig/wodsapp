package karstenroethig.wodsapp.webapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import karstenroethig.wodsapp.webapp.controller.exceptions.ForbiddenException;
import karstenroethig.wodsapp.webapp.controller.exceptions.NotFoundException;

public abstract class AbstractController
{
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
