package org.monstercraft.area.api.exception;

public class InvalidPlaneException extends Exception {

	/**
	 * Exception Message
	 */
	private static final long serialVersionUID = -377655525836615679L;

	/**
	 * An exception to be thrown when planes are invalid.
	 * 
	 * @param error
	 *            The error message.
	 */
	public InvalidPlaneException(final String error) {
		super(error);
	}

}
