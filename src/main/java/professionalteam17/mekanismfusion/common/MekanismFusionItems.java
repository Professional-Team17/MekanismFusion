package professionalteam17.mekanismfusion.common;

import mekanism.common.item.ItemMekanism;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class MekanismFusionItems {
    public static final Item CrystalCarbon = new ItemMekanism();
    public static final Item CrystalSilicon = new ItemMekanism();
    public static final Item CrystalIron = new ItemMekanism();
    public static void registerItems(IForgeRegistry<Item> registry){
        registry.register(init(CrystalCarbon, "CarbonCrystal"));
        registry.register(init(CrystalSilicon, "CarbonSilicon"));
        registry.register(init(CrystalIron,"PureIronCrystal"));
    }
    public static Item init(Item item, String name) {
        return item.setUnlocalizedName(name).setRegistryName("mekanism:" + name);
    }
}
