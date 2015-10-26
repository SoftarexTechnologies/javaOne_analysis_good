package com.dcservice.common.helpers;

public class EnumHelper extends BaseHelper {

	public static <E extends Enum<E>> String toStringFormatter(E obj) {
		if (obj == null) {
			return null;
		}

		return obj.getClass().getSimpleName().substring(0, 1).toLowerCase()
				.concat(obj.getClass().getSimpleName().substring(1))
				.concat(obj.name());
	}
}
