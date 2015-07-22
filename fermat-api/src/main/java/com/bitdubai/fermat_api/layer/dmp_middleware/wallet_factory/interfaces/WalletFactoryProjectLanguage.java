package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguageException;

import java.util.UUID;

/**
 * Created by eze on 2015.07.15..
 */
public interface WalletFactoryProjectLanguage {

    // TRANSLATOR?

    UUID getId();

    byte[] getFile() throws CantGetWalletFactoryProjectLanguageException;

    String getName();

    Languages getType();

    WalletFactoryProjectProposal getWalletFactoryProjectProposal();

}
