package dev.lotnest.minemillion.component.impl;

import dev.lotnest.minemillion.MineMillionPlugin;
import dev.lotnest.minemillion.component.Component;
import dev.lotnest.minemillion.component.ComponentResult;
import dev.lotnest.minemillion.language.LanguageProvider;
import dev.lotnest.minemillion.util.StringUtil;

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

    private final LanguageProvider languageProvider;
    private boolean isInitialized;

    public BootstrapComponent(MineMillionPlugin plugin) {
        super(plugin);
        this.languageProvider = plugin.getLanguageProvider();
    }

    @Override
    public ComponentResult initialize() {
        if (isInitialized) {
            return ComponentResult.ALREADY_INITIALIZED;
        }

        plugin.getLogger().info(BOOT_FIGLET_MESSAGE);
        plugin.getLogger().info(
                languageProvider.get("general.pluginInfo", plugin.getDescription().getVersion(),
                        StringUtil.join(plugin.getDescription().getAuthors()))
        );

        isInitialized = true;
        return ComponentResult.INITIALIZED;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public ComponentResult shutdown() {
        if (!isInitialized) {
            return ComponentResult.NOT_INITIALIZED;
        }

        isInitialized = false;
        return ComponentResult.SHUTDOWN;
    }
}
