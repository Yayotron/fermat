package org.fermat.fermat_dap_api.layer.all_definition.util;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import org.fermat.fermat_dap_api.layer.actor_connection.asset_issuer.interfaces.AssetIssuerActorConnectionManager;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public final class ActorUtils {

    //VARIABLE DECLARATION

    //CONSTRUCTORS
    private ActorUtils() {
        throw new AssertionError("NO INSTANCES!!");
    }

    //PUBLIC METHODS
    public static Actors getActorType(DAPActor dapActor) {
        if (dapActor instanceof ActorAssetIssuer) {
            return Actors.DAP_ASSET_ISSUER;
        }
        if (dapActor instanceof org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint) {
            return Actors.DAP_ASSET_REDEEM_POINT;
        }
        if (dapActor instanceof org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser) {
            return Actors.DAP_ASSET_USER;
        }
        return null;
    }

    public static PlatformComponentType getPlatformComponentType(DAPActor dapActor) {
        if (dapActor instanceof ActorAssetIssuer) {
            return PlatformComponentType.ACTOR_ASSET_ISSUER;
        }
        if (dapActor instanceof org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint) {
            return PlatformComponentType.ACTOR_ASSET_REDEEM_POINT;
        }
        if (dapActor instanceof org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser) {
            return PlatformComponentType.ACTOR_ASSET_USER;
        }
        return null;
    }

    public static DAPActor getActorFromPublicKey(String actorPublicKey, Actors actorType, org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager userManager, org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager redeemPointManager, ActorAssetIssuerManager issuerManager) {
        try {
            switch (actorType) {
                case DAP_ASSET_ISSUER:
                    return issuerManager.getActorByPublicKey(actorPublicKey);
                case DAP_ASSET_USER:
                    return userManager.getActorByPublicKey(actorPublicKey);
                case DAP_ASSET_REDEEM_POINT:
                    return redeemPointManager.getActorByPublicKey(actorPublicKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void storeDAPActor(DAPActor actorToStore, ActorAssetUserManager userManager, ActorAssetRedeemPointManager redeemPointManager, AssetIssuerActorConnectionManager issuerActorConnectionManager) throws DAPException {
        try {
            if (actorToStore instanceof ActorAssetIssuer) {
                issuerActorConnectionManager.saveNewActorAssetIssuer((ActorAssetIssuer) actorToStore);
            }
            if (actorToStore instanceof org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint) {
                redeemPointManager.saveRegisteredActorRedeemPoint((org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint) actorToStore);
            }
            if (actorToStore instanceof org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser) {
                userManager.createActorAssetUserRegisterInNetworkService((org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser) actorToStore);
            }
        } catch (Exception e) {
            throw new org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException(e);
        }
    }

    public static boolean isValidIssuer(DigitalAsset asset, AssetIssuerActorConnectionManager issuerActorConnectionManager) {
        for (ActorAssetIssuer issuer : issuerActorConnectionManager.getAllIssuerConnectedWithExtendedPk()) {
            if (issuer.getActorPublicKey().equals(asset.getIdentityAssetIssuer().getPublicKey())) {
                return true;
            }
        }
        return false;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
