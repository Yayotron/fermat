package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;

public class ConnectionAlreadyRequestedException extends
		CloudCommunicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7916961691657131265L;
	
	public static final String DEFAULT_MESSAGE = "CONNECTION IS ALREADY REQUESTED";

	public ConnectionAlreadyRequestedException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public ConnectionAlreadyRequestedException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public ConnectionAlreadyRequestedException(final String message) {
		this(message, null);
	}

	public ConnectionAlreadyRequestedException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public ConnectionAlreadyRequestedException() {
		this(DEFAULT_MESSAGE);
	}

}
