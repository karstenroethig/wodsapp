package karstenroethig.wodsapp.webapp.util;

import lombok.Getter;

public enum MessageKeyEnum
{
	APPLICATION_VERSION("application.version"),
	APPLICATION_REVISION("application.revision"),
	APPLICATION_BUILD_DATE("application.buildDate"),

	WOD_SAVE_INVALID("wod.save.invalid"),
	WOD_SAVE_SUCCESS("wod.save.success"),
	WOD_SAVE_ERROR("wod.save.error"),
	WOD_SAVE_ERROR_EXISTS("wod.save.error.exists"),
	WOD_UPDATE_INVALID("wod.update.invalid"),
	WOD_UPDATE_SUCCESS("wod.update.success"),
	WOD_UPDATE_ERROR("wod.update.error"),
	WOD_DELETE_SUCCESS("wod.delete.success"),
	WOD_DELETE_ERROR("wod.delete.error"),

	CATEGORY_SAVE_INVALID("category.save.invalid"),
	CATEGORY_SAVE_SUCCESS("category.save.success"),
	CATEGORY_SAVE_ERROR("category.save.error"),
	CATEGORY_SAVE_ERROR_EXISTS("category.save.error.exists"),
	CATEGORY_UPDATE_INVALID("category.update.invalid"),
	CATEGORY_UPDATE_SUCCESS("category.update.success"),
	CATEGORY_UPDATE_ERROR("category.update.error"),
	CATEGORY_DELETE_SUCCESS("category.delete.success"),
	CATEGORY_DELETE_ERROR("category.delete.error");

	@Getter
	private String key;

	private MessageKeyEnum(String key)
	{
		this.key = key;
	}
}
