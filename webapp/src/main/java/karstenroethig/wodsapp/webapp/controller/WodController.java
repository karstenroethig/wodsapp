package karstenroethig.wodsapp.webapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import karstenroethig.wodsapp.webapp.controller.exceptions.NotFoundException;
import karstenroethig.wodsapp.webapp.controller.util.UrlMappings;
import karstenroethig.wodsapp.webapp.controller.util.ViewEnum;
import karstenroethig.wodsapp.webapp.domain.enums.CategoryTypeEnum;
import karstenroethig.wodsapp.webapp.dto.WodDto;
import karstenroethig.wodsapp.webapp.service.exceptions.AlreadyExistsException;
import karstenroethig.wodsapp.webapp.service.impl.CategoryServiceImpl;
import karstenroethig.wodsapp.webapp.service.impl.WodServiceImpl;
import karstenroethig.wodsapp.webapp.util.MessageKeyEnum;
import karstenroethig.wodsapp.webapp.util.Messages;

@ComponentScan
@Controller
@RequestMapping(UrlMappings.CONTROLLER_WOD)
public class WodController extends AbstractController
{
	@Autowired WodServiceImpl wodService;
	@Autowired CategoryServiceImpl categoryService;

	@GetMapping(value = UrlMappings.ACTION_LIST)
	public String list(Model model)
	{
		model.addAttribute("allWods", wodService.getAllElements());
		return ViewEnum.WOD_LIST.getViewName();
	}

	@GetMapping(value = UrlMappings.ACTION_SHOW)
	public String show(@PathVariable("id") Long id, Model model)
	{
		WodDto wod = wodService.find(id);
		if (wod == null)
			throw new NotFoundException(String.valueOf(id));

		model.addAttribute("wod", wod);
		return ViewEnum.WOD_SHOW.getViewName();
	}

	@GetMapping(value = UrlMappings.ACTION_CREATE)
	public String create(Model model)
	{
		model.addAttribute("wod", wodService.create());
		addBasicAttributes(model);
		return ViewEnum.WOD_CREATE.getViewName();
	}

	@GetMapping(value = UrlMappings.ACTION_EDIT)
	public String edit(@PathVariable("id") Long id, Model model)
	{
		WodDto wod = wodService.find(id);
		if (wod == null)
			throw new NotFoundException(String.valueOf(id));

		model.addAttribute("wod", wod);
		addBasicAttributes(model);
		return ViewEnum.WOD_EDIT.getViewName();
	}

	@GetMapping(value = UrlMappings.ACTION_DELETE)
	public String delete(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes, Model model)
	{
		WodDto wod = wodService.find(id);
		if (wod == null)
			throw new NotFoundException(String.valueOf(id));

		if (wodService.delete(id))
			redirectAttributes.addFlashAttribute(Messages.ATTRIBUTE_NAME,
					Messages.createWithSuccess(MessageKeyEnum.WOD_DELETE_SUCCESS, wod.getName()));
		else
			redirectAttributes.addFlashAttribute(Messages.ATTRIBUTE_NAME,
				Messages.createWithError(MessageKeyEnum.WOD_DELETE_ERROR, wod.getName()));

		return UrlMappings.redirect(UrlMappings.CONTROLLER_WOD, UrlMappings.ACTION_LIST);
	}

	@PostMapping(value = UrlMappings.ACTION_SAVE)
	public String save(@ModelAttribute("wod") @Valid WodDto wod, BindingResult bindingResult,
		final RedirectAttributes redirectAttributes, Model model)
	{
		if (bindingResult.hasErrors())
		{
			model.addAttribute(Messages.ATTRIBUTE_NAME, Messages.createWithError(MessageKeyEnum.WOD_SAVE_INVALID));
			addBasicAttributes(model);
			return ViewEnum.WOD_CREATE.getViewName();
		}

		try
		{
			if (wodService.save(wod) != null)
			{
				redirectAttributes.addFlashAttribute(Messages.ATTRIBUTE_NAME,
					Messages.createWithSuccess(MessageKeyEnum.WOD_SAVE_SUCCESS, wod.getName()));
				return UrlMappings.redirect(UrlMappings.CONTROLLER_WOD, UrlMappings.ACTION_LIST);
			}
		}
		catch (AlreadyExistsException ex)
		{
			bindingResult.rejectValue(ex.getFieldId(), MessageKeyEnum.WOD_SAVE_ERROR_EXISTS.getKey());
			model.addAttribute(Messages.ATTRIBUTE_NAME, Messages.createWithError(MessageKeyEnum.WOD_SAVE_INVALID));
			addBasicAttributes(model);
			return ViewEnum.WOD_CREATE.getViewName();
		}

		model.addAttribute(Messages.ATTRIBUTE_NAME, Messages.createWithError(MessageKeyEnum.WOD_SAVE_ERROR));
		addBasicAttributes(model);
		return ViewEnum.WOD_CREATE.getViewName();
	}

	@PostMapping(value = UrlMappings.ACTION_UPDATE)
	public String update(@ModelAttribute("wod") @Valid WodDto wod, BindingResult bindingResult,
		final RedirectAttributes redirectAttributes, Model model)
	{
		if (bindingResult.hasErrors())
		{
			model.addAttribute(Messages.ATTRIBUTE_NAME, Messages.createWithError(MessageKeyEnum.WOD_UPDATE_INVALID));
			addBasicAttributes(model);
			return ViewEnum.WOD_EDIT.getViewName();
		}

		try
		{
			if (wodService.update(wod) != null)
			{
				redirectAttributes.addFlashAttribute(Messages.ATTRIBUTE_NAME,
					Messages.createWithSuccess(MessageKeyEnum.WOD_UPDATE_SUCCESS, wod.getName()));
				return UrlMappings.redirect(UrlMappings.CONTROLLER_WOD, UrlMappings.ACTION_LIST);
			}
		}
		catch (AlreadyExistsException ex)
		{
			bindingResult.rejectValue(ex.getFieldId(), MessageKeyEnum.WOD_SAVE_ERROR_EXISTS.getKey());
			model.addAttribute(Messages.ATTRIBUTE_NAME, Messages.createWithError(MessageKeyEnum.WOD_UPDATE_INVALID));
			addBasicAttributes(model);
			return ViewEnum.WOD_EDIT.getViewName();
		}

		model.addAttribute(Messages.ATTRIBUTE_NAME, Messages.createWithError(MessageKeyEnum.WOD_UPDATE_ERROR));
		addBasicAttributes(model);
		return ViewEnum.WOD_EDIT.getViewName();
	}

	private void addBasicAttributes(Model model)
	{
		model.addAttribute("allTypes", categoryService.findByType(CategoryTypeEnum.TYPE));
		model.addAttribute("allSchemes", categoryService.findByType(CategoryTypeEnum.SCHEME));
		model.addAttribute("allEvents", categoryService.findByType(CategoryTypeEnum.EVENT));
	}

	@Override
	@ExceptionHandler(NotFoundException.class)
	void handleNotFoundException(HttpServletResponse response, NotFoundException ex) throws IOException
	{
		response.sendError(HttpStatus.NOT_FOUND.value(), String.format("Wod %s does not exist.", ex.getMessage()));
	}
}
