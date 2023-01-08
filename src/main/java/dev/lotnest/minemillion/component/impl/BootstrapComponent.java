package dev.lotnest.minemillion.component.impl;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.component.Component;
import dev.lotnest.minemillion.component.ComponentResult;
import dev.lotnest.minemillion.util.LoggerUtil;
import dev.lotnest.minemillion.util.StringUtil;
import org.jetbrains.annotations.NotNull;

public class BootstrapComponent extends Component {

    private static final String BOOT_FIGLET_MESSAGE = """
                        
            '##::::'##:'####:'##::: ##:'########:'##::::'##:'####:'##:::::::'##:::::::'####::'#######::'##::: ##:
             ###::'###:. ##:: ###:: ##: ##.....:: ###::'###:. ##:: ##::::::: ##:::::::. ##::'##.... ##: ###:: ##:
             ####'####:: ##:: ####: ##: ##::::::: ####'####:: ##:: ##::::::: ##:::::::: ##:: ##:::: ##: ####: ##:
             ## ### ##:: ##:: ## ## ##: ######::: ## ### ##:: ##:: ##::::::: ##:::::::: ##:: ##:::: ##: ## ## ##:
             ##. #: ##:: ##:: ##. ####: ##...:::: ##. #: ##:: ##:: ##::::::: ##:::::::: ##:: ##:::: ##: ##. ####:
             ##:.:: ##:: ##:: ##:. ###: ##::::::: ##:.:: ##:: ##:: ##::::::: ##:::::::: ##:: ##:::: ##: ##:. ###:
             ##:::: ##:'####: ##::. ##: ########: ##:::: ##:'####: ########: ########:'####:. #######:: ##::. ##:
            ..:::::..::....::..::::..::........::..:::::..::....::........::........::....:::.......:::..::::..::
            """;

    private boolean isInitialized;

    public BootstrapComponent(@NotNull MineMillionPlugin plugin) {
        super(plugin);
    }

    @Override
    public @NotNull ComponentResult initialize() {
        if (isInitialized) {
            return ComponentResult.ALREADY_INITIALIZED;
        }

        LoggerUtil.infoMessage(BOOT_FIGLET_MESSAGE);
        LoggerUtil.info("general.pluginInfo", plugin.getDescription().getVersion(),
                StringUtil.join(plugin.getDescription().getAuthors()));

        isInitialized = true;
        return ComponentResult.INITIALIZED;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public @NotNull ComponentResult shutdown() {
        if (!isInitialized) {
            return ComponentResult.NOT_INITIALIZED;
        }

        isInitialized = false;
        return ComponentResult.SHUTDOWN;
    }
}
