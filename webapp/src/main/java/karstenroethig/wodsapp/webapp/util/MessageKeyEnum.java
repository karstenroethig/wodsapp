package karstenroethig.wodsapp.webapp.util;

import lombok.Getter;

public enum MessageKeyEnum
{
	APPLICATION_VERSION("application.version"),
	APPLICATION_REVISION("application.revision"),
	APPLICATION_BUILD_DATE("application.buildDate"),

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
