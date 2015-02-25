package org.monstercraft.area.api.exception;

public class InvalidDirectionException extends Exception {

    /**
	 *
	 */
    private static final long serialVersionUID = -6522346813278496777L;

    /**
     * An exception to be thrown when the direction specified is invalid.
     *
     * @param error
     *            The error message.
     */
    public InvalidDirectionException(final String error) {
        super(error);
    }

}
