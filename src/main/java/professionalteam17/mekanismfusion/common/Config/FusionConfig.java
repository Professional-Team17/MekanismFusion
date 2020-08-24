package professionalteam17.mekanismfusion.common.Config;

import mekanism.common.config.BaseConfig;

public class FusionConfig extends BaseConfig {
    private static FusionConfig LOCAL = new FusionConfig();
    private static FusionConfig SERVER = null;
    public static FusionConfig current() {
        return SERVER != null ? SERVER : LOCAL;
    }
    public static FusionConfig local() {

        return LOCAL;
    }
}
