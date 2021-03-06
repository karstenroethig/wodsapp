package karstenroethig.wodsapp.webapp.controller.util;

import org.apache.commons.lang3.StringUtils;

public class UrlMappings
{
	private static final String REDIRECT_PREFIX = "redirect:";

	public static final String HOME = "/";
	public static final String DASHBOARD = "/dashboard";

	public static final String CONTROLLER_CATEGORY = "/category";
	public static final String CONTROLLER_WOD = "/wod";
	public static final String CONTROLLER_ADMIN = "/admin";

	public static final String ACTION_LIST = "/list";
	public static final String ACTION_SHOW = "/show/{id}";
	public static final String ACTION_CREATE = "/create";
	public static final String ACTION_EDIT = "/edit/{id}";
	public static final String ACTION_DELETE = "/delete/{id}";
	public static final String ACTION_SAVE = "/save";
	public static final String ACTION_UPDATE = "/update";
	public static final String ACTION_SEARCH = "/search";

	private UrlMappings() {}

	public static String redirect(String controllerPath, String actionPath)
	{
		return redirect(controllerPath + actionPath);
	}

	public static String redirectWithId(String controllerPath, String actionPath, Long id)
	{
		String idString = id == null ? StringUtils.EMPTY : id.toString();
		String path = StringUtils.replace(controllerPath + actionPath, "{id}", idString);
		return redirect(path);
	}

	public static String redirect(String path)
	{
		return REDIRECT_PREFIX + path;
	}
}
