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
import karstenroethig.wodsapp.webapp.dto.CategoryDto;
import karstenroethig.wodsapp.webapp.service.exceptions.AlreadyExistsException;
import karstenroethig.wodsapp.webapp.service.impl.CategoryServiceImpl;
import karstenroethig.wodsapp.webapp.util.MessageKeyEnum;
import karstenroethig.wodsapp.webapp.util.Messages;

@ComponentScan
@Controller
@RequestMapping(UrlMappings.CONTROLLER_CATEGORY)
public class CategoryController extends AbstractController
{
	@Autowired CategoryServiceImpl categoryService;

	@GetMapping(value = UrlMappings.ACTION_LIST)
	public String list(Model model)
	{
		model.addAttribute("allCategories", categoryService.getAllElements());
		return ViewEnum.CATEGORY_LIST.getViewName();
	}

	@GetMapping(value = UrlMappings.ACTION_CREATE)
	public String create(Model model)
	{
		model.addAttribute("category", categoryService.create());
		return ViewEnum.CATEGORY_CREATE.getViewName();
	}

	@GetMapping(value = UrlMappings.ACTION_EDIT)
	public String edit(@PathVariable("id") Long id, Model model)
	{
		CategoryDto category = categoryService.find(id);
		if (category == null)
			throw new NotFoundException(String.valueOf(id));

		model.addAttribute("category", category);
		return ViewEnum.CATEGORY_EDIT.getViewName();
	}

	@GetMapping(value = UrlMappings.ACTION_DELETE)
	public String delete(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes, Model model)
	{
		CategoryDto category = categoryService.find(id);
		if (category == null)
			throw new NotFoundException(String.valueOf(id));

		if (categoryService.deleteTag(id))
			redirectAttributes.addFlashAttribute(Messages.ATTRIBUTE_NAME,
					Messages.createWithSuccess(MessageKeyEnum.CATEGORY_DELETE_SUCCESS, category.getName()));
		else
			redirectAttributes.addFlashAttribute(Messages.ATTRIBUTE_NAME,
				Messages.createWithError(MessageKeyEnum.CATEGORY_DELETE_ERROR, category.getName()));

		return UrlMappings.redirect(UrlMappings.CONTROLLER_CATEGORY, UrlMappings.ACTION_LIST);
	}

	@PostMapping(value = UrlMappings.ACTION_SAVE)
	public String save(@ModelAttribute("category") @Valid CategoryDto category, BindingResult bindingResult,
		final RedirectAttributes redirectAttributes, Model model)
	{
		if (bindingResult.hasErrors())
		{
			model.addAttribute(Messages.ATTRIBUTE_NAME, Messages.createWithError(MessageKeyEnum.CATEGORY_SAVE_INVALID));
			return ViewEnum.CATEGORY_CREATE.getViewName();
		}

		try
		{
			if (categoryService.save(category) != null)
			{
				redirectAttributes.addFlashAttribute(Messages.ATTRIBUTE_NAME,
					Messages.createWithSuccess(MessageKeyEnum.CATEGORY_SAVE_SUCCESS, category.getName()));
				return UrlMappings.redirect(UrlMappings.CONTROLLER_CATEGORY, UrlMappings.ACTION_LIST);
			}
		}
		catch (AlreadyExistsException ex)
		{
			bindingResult.rejectValue(ex.getFieldId(), MessageKeyEnum.CATEGORY_SAVE_ERROR_EXISTS.getKey());
			model.addAttribute(Messages.ATTRIBUTE_NAME, Messages.createWithError(MessageKeyEnum.CATEGORY_SAVE_INVALID));
			return ViewEnum.CATEGORY_CREATE.getViewName();
		}

		model.addAttribute(Messages.ATTRIBUTE_NAME, Messages.createWithError(MessageKeyEnum.CATEGORY_SAVE_ERROR));
		return ViewEnum.CATEGORY_CREATE.getViewName();
	}

	@PostMapping(value = UrlMappings.ACTION_UPDATE)
	public String update(@ModelAttribute( "category" ) @Valid CategoryDto category, BindingResult bindingResult,
		final RedirectAttributes redirectAttributes, Model model)
	{
		if (bindingResult.hasErrors())
		{
			model.addAttribute(Messages.ATTRIBUTE_NAME, Messages.createWithError(MessageKeyEnum.CATEGORY_UPDATE_INVALID));
			return ViewEnum.CATEGORY_EDIT.getViewName();
		}

		try
		{
			if (categoryService.update(category) != null)
			{
				redirectAttributes.addFlashAttribute(Messages.ATTRIBUTE_NAME,
					Messages.createWithSuccess(MessageKeyEnum.CATEGORY_UPDATE_SUCCESS, category.getName()));
				return UrlMappings.redirect(UrlMappings.CONTROLLER_CATEGORY, UrlMappings.ACTION_LIST);
			}
		}
		catch (AlreadyExistsException ex)
		{
			bindingResult.rejectValue(ex.getFieldId(), MessageKeyEnum.CATEGORY_SAVE_ERROR_EXISTS.getKey());
			model.addAttribute(Messages.ATTRIBUTE_NAME, Messages.createWithError(MessageKeyEnum.CATEGORY_UPDATE_INVALID));
			return ViewEnum.CATEGORY_EDIT.getViewName();
		}

		model.addAttribute(Messages.ATTRIBUTE_NAME, Messages.createWithError(MessageKeyEnum.CATEGORY_UPDATE_ERROR));
		return ViewEnum.CATEGORY_EDIT.getViewName();
	}

	@Override
	@ExceptionHandler(NotFoundException.class)
	void handleNotFoundException(HttpServletResponse response, NotFoundException ex) throws IOException
	{
		response.sendError(HttpStatus.NOT_FOUND.value(), String.format("Category %s does not exist.", ex.getMessage()));
	}
}
