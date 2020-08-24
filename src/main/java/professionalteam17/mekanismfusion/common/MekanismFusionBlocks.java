package professionalteam17.mekanismfusion.common;

import mekanism.common.Mekanism;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import professionalteam17.mekanismfusion.common.block.*;
import mekanism.common.MekanismBlocks;

@GameRegistry.ObjectHolder(MekanismFusion.MODID)
public class MekanismFusionBlocks{
    public static Block FusionOreBlock = new BlockOre();

    public static void registerBlocks(IForgeRegistry<Block> registry){
        registry.register(init(FusionOreBlock, "OreBlock"));
    }

    public static Block init(Block block, String name) {
        return block.setUnlocalizedName(name).setRegistryName("mekanism:" + name);
    }
}
