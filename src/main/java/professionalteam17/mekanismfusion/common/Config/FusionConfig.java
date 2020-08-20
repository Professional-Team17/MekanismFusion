package professionalteam17.mekanismfusion.common.Config;

import mekanism.common.config.BaseConfig;
import mekanism.common.config.GeneralConfig;
import mekanism.common.config.MekanismConfig;
import mekanism.common.config.options.IntOption;

public class FusionConfig extends BaseConfig {
    private static FusionConfig LOCAL = new FusionConfig();
    private static FusionConfig SERVER = null;
    public static FusionConfig current() {
        return SERVER != null ? SERVER : LOCAL;
    }
    public static FusionConfig local() {

        return LOCAL;
    }
    public OreConfig ore = new OreConfig();
}
