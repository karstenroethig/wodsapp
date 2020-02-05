package karstenroethig.wodsapp.webapp.controller.util;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

public enum ViewEnum
{
	DASHBOARD("/dashboard"),

	ADMIN_SERVER_INFO(ControllerEnum.ADMIN, "/server-info" ),

	CATEGORY_CREATE(ControllerEnum.CATEGORY, ActionEnum.CREATE),
	CATEGORY_EDIT(ControllerEnum.CATEGORY, ActionEnum.EDIT),
	CATEGORY_LIST(ControllerEnum.CATEGORY, ActionEnum.LIST);

	private static final String VIEW_SUBDIRECTORY = "views";

	@Getter
	private String viewName = StringUtils.EMPTY;

	private enum ControllerEnum
	{
		ADMIN,
		CATEGORY;

		private String path = null;

		private ControllerEnum() {}

		private ControllerEnum(String path)
		{
			this.path = path;
		}

		public String getPath()
		{
			return path != null ? path : ("/" + name().toLowerCase());
		}
	}

	private enum ActionEnum
	{
		CREATE,
		EDIT,
		LIST,
		SHOW;

		private ActionEnum() {}

		public String getPath()
		{
			return "/" + name().toLowerCase();
		}
	}

	private ViewEnum(ControllerEnum subController, ControllerEnum controller, ActionEnum action)
	{
		this(subController, controller, action.getPath());
	}

	private ViewEnum(ControllerEnum controller, ActionEnum action)
	{
		this(null, controller, action.getPath());
	}

	private ViewEnum(ControllerEnum controller, String path)
	{
		this(null, controller, path);
	}

	private ViewEnum(String path)
	{
		this(null, null, path);
	}

	private ViewEnum(ControllerEnum subController, ControllerEnum controller, String path)
	{
		StringBuilder newViewName = new StringBuilder(VIEW_SUBDIRECTORY);

		if (subController != null)
			newViewName.append(subController.getPath());

		if (controller != null)
			newViewName.append(controller.getPath());

		if (path != null)
			newViewName.append(path);

		viewName = StringUtils.removeStart(newViewName.toString(), "/"); // just in case if there is no view sub-directory
	}
}
