package professionalteam17.mekanismfusion.common.Config;

import mekanism.common.config.BaseConfig;
import mekanism.common.config.GeneralConfig;
import mekanism.common.config.options.IntOption;

public class OreConfig extends BaseConfig {
    public final IntOption LanthanumPerChunk = new IntOption(this, "ore", "LanthanumPerChunk", 1,
            "Chance that osmium generates in a chunk. (0 to Disable)", 0, Integer.MAX_VALUE);

    public final IntOption LanthanumMaxVeinSize = new IntOption(this, "ore", "LanthanumVeinSize", 1,
            "Max number of blocks in an osmium vein.", 1, Integer.MAX_VALUE);
}