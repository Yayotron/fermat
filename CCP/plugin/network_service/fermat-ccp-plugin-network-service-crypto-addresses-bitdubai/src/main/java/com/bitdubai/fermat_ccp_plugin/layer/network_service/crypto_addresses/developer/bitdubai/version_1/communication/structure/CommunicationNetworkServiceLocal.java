package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.CryptoAddressesNetworkServicePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.database.OutgoingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.Observable;
import java.util.Observer;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.structure.CommunicationNetworkServiceLocal</code> represent
 * the remote network services locally
 * <p/>
 * This class extend of the <code>java.util.Observer</code> class,  its used on the software design pattern called: The observer pattern,
 * for more info see @link https://en.wikipedia.org/wiki/Observer_pattern
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationNetworkServiceLocal implements Observer, NetworkServiceLocal {

    private final PlatformComponentProfile remoteNetworkServiceProfile;
    private final ErrorManager errorManager;
    private final EventManager eventManager;
    private final OutgoingMessageDao outgoingMessageDao;

    /**
     * Represent the lastMessageReceived
     */
    private FermatMessage lastMessageReceived;

    /**
     * Constructor with parameters.
     */
    public CommunicationNetworkServiceLocal(final PlatformComponentProfile remoteNetworkServiceProfile,
                                            final ErrorManager             errorManager               ,
                                            final EventManager             eventManager               ,
                                            final OutgoingMessageDao       outgoingMessageDao         ) {

        this.remoteNetworkServiceProfile = remoteNetworkServiceProfile;
        this.errorManager                = errorManager               ;
        this.eventManager                = eventManager               ;
        this.outgoingMessageDao          = outgoingMessageDao         ;
    }


    /**
     * (non-javadoc)
     */
    public void sendMessage(final String senderIdentityPublicKey,final String receiverPK ,final String messageContent) {

        try {

            FermatMessage fermatMessage  = FermatMessageCommunicationFactory.constructFermatMessage(senderIdentityPublicKey,  //Sender NetworkService
                                                                                                    remoteNetworkServiceProfile.getIdentityPublicKey(),   //Receiver
                                                                                                    messageContent,                //Message Content
                                                                                                    FermatMessageContentType.TEXT);//Type

            /*
             * Configure the correct status
             */
            ((FermatMessageCommunication)fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.PENDING_TO_SEND);

            /*
             * Save to the data base table
             */
            outgoingMessageDao.create(fermatMessage);

        } catch (Exception e) {
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send message. Error reason: " + e.getMessage()));
        }

    }


    /**
     * Notify the client when a incoming message is receive by the incomingTemplateNetworkServiceMessage
     * ant fire a new event
     *
     * @param incomingMessage received
     */
    private void onMessageReceived(FermatMessage incomingMessage) {

        System.out.println("CommunicationNetworkServiceLocal - onMessageReceived ");
        System.out.println(incomingMessage.getContent());

        /*
         * set the last message received
         */
        this.lastMessageReceived = incomingMessage;

        /**
         * Put the message on a event and fire new event
         */
        FermatEvent fermatEvent = eventManager.getNewEvent(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        fermatEvent.setSource(CryptoAddressesNetworkServicePluginRoot.EVENT_SOURCE);
        ((NewNetworkServiceMessageReceivedNotificationEvent) fermatEvent).setData(incomingMessage);
        eventManager.raiseEvent(fermatEvent);

    }

    /**
     * This method is called automatically when CommunicationNetworkServiceRemoteAgent (Observable object) update the database
     * when new message is received
     *
     * @param observable the observable object
     * @param data       the data update
     */
    @Override
    public void update(Observable observable, Object data) {

        //Validate and process
        if (data instanceof FermatMessage) {
            onMessageReceived((FermatMessage) data);
        }
    }

    /**
     * (non-javadoc)
     * @see NetworkServiceLocal#
     */
    public FermatMessage getLastMessageReceived() {
        return lastMessageReceived;
    }
}
