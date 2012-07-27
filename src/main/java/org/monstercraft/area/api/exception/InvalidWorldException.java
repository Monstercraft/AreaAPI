package org.monstercraft.area.api.exception;

public class InvalidWorldException extends Exception {
	/**
	 * Exception Message
	 */
	private static final long serialVersionUID = -377655525836615679L;

	/**
	 * An exception to be thrown when worlds are invalid.
	 * 
	 * @param error
	 *            The error message.
	 */
	public InvalidWorldException(final String error) {
		super(error);
	}
}
