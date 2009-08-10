package com.j6.framework.exception;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ValidatorException extends RuntimeException {
	private List<String> errorMessages = new ArrayList<String>();

	public ValidatorException() {
		super();
	}

	public ValidatorException(String errorMsg, Object... parameters) {
		errorMessages.add(MessageFormat.format(errorMsg, parameters));
	}

	public void addErrorMsg(String errorMsg, Object... parameters) {
		errorMessages.add(MessageFormat.format(errorMsg, parameters));
	}

	public List<java.lang.String> getErrorMessages() {
		return errorMessages;
	}

	public String getMessage() {
		if (errorMessages.size() > 1) {
			String s = "";
			for (int i = 0; i < errorMessages.size(); i++) {
				s += errorMessages.get(i);
				if (i != errorMessages.size() - 1)
					s += "\n";
			}
			return s;
		} else
			return errorMessages.get(0);
	}

	public void throwIfError() {
		if (getErrorMessages().size() != 0)
			throw this;
	}
}
