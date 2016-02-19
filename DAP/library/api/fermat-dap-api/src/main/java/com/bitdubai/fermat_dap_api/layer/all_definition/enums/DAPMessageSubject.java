package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 16/02/16.
 */
public enum DAPMessageSubject implements FermatEnum {

    //ENUM DECLARATION
    /**
     * This one will be consider as the default option
     * from which that message was created, and all the
     * plugins that are listening to that option will respond
     * to that message.
     */
    DEFAULT("DEFAULT"),
    ASSET_DISTRIBUTION("ASRE"),
    ASSET_TRANSFER("ASTR"),
    NEW_NEGOTIATION_STARTED("NNS"),
    NEW_SELL_STARTED("NSS"),
    TRANSACTION_SIGNED("TXSI"),
    DEAL_CHANGED("DECH"),
    USER_REDEMPTION("USRE");

    //VARIABLE DECLARATION

    private String code;

    //CONSTRUCTORS

    DAPMessageSubject(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static DAPMessageSubject getByCode(String code) throws InvalidParameterException {
        for (DAPMessageSubject fenum : values()) {
            if (fenum.getCode().equals(code)) return fenum;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the DAPMessageSubject enum.");
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    @Override
    public String getCode() {
        return code;
    }
}