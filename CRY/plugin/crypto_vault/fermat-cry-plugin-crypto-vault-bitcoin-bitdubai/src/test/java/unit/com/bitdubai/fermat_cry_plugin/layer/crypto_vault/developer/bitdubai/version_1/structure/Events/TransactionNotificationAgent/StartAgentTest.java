package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.Events.TransactionNotificationAgent;

import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.events.TransactionNotificationAgent;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common.MockedPluginFileSystem;

/**
 * Created by rodrigo on 2015.07.15..
 */
@RunWith(MockitoJUnitRunner.class)
public class StartAgentTest {
    @Mock
    ErrorManager errorManager;

    @Mock
    LogManager logManager;

    @Mock
    EventManager eventManager;

    @Mock
    DeviceUserManager deviceUserManager;

    @Mock
    BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    MockedPluginFileSystem pluginFileSystem;

    @Test
    public void startTest() throws CantStartAgentException {
        TransactionNotificationAgent transactionNotificationAgent = new TransactionNotificationAgent(eventManager,pluginDatabaseSystem,errorManager, UUID.randomUUID(), UUID.randomUUID());
        transactionNotificationAgent.start();
    }

    @Test
    public void stopTest() throws CantStartAgentException {
        TransactionNotificationAgent transactionNotificationAgent = new TransactionNotificationAgent(eventManager,pluginDatabaseSystem,errorManager, UUID.randomUUID(), UUID.randomUUID());
        transactionNotificationAgent.start();
        transactionNotificationAgent.stop();
    }

}