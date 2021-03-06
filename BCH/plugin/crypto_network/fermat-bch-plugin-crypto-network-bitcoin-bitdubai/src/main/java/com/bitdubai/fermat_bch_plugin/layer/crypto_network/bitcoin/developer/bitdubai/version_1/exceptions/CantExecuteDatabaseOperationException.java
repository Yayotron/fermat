package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/9/15.
 */
public class CantExecuteDatabaseOperationException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error executing a database operation.";

    public CantExecuteDatabaseOperationException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
