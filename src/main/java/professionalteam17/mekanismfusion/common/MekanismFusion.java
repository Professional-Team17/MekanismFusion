package professionalteam17.mekanismfusion.common;

import buildcraft.api.fuels.BuildcraftFuelRegistry;
import buildcraft.api.fuels.IFuel;
import buildcraft.api.mj.MjAPI;
import io.netty.buffer.ByteBuf;
import mekanism.api.MekanismAPI;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.infuse.InfuseRegistry;
import mekanism.common.FuelHandler;
import mekanism.common.Mekanism;
import mekanism.common.MekanismFluids;
import mekanism.common.Version;
import mekanism.common.base.IModule;
import mekanism.common.config.MekanismConfig;
import mekanism.common.fixers.MekanismDataFixers.MekFixers;
import mekanism.common.integration.redstoneflux.RFIntegration;
import mekanism.common.multiblock.MultiblockManager;
import mekanism.common.network.PacketSimpleGui;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.util.StackUtils;
import mekanism.generators.common.content.turbine.SynchronizedTurbineData;
import mekanism.generators.common.fixers.GeneratorTEFixer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.CompoundDataFixer;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;
import professionalteam17.mekanismfusion.proxy.CommonProxy;

@Mod(modid = MekanismFusion.MODID, name = MekanismFusion.NAME, version = MekanismFusion.VERSION, acceptedMinecraftVersions = "1.12.2")
public class MekanismFusion {
    public static final String MODID = "mekanismfusion";
    public static final String NAME = "MekanismFusion";
    public static final String VERSION = "aloha1.0";
    @SidedProxy(clientSide = "professionalteam17.mekanismfusion.proxy.ClientProxy”,serverSide = “professionalteam17.mekanismfusion.proxy.CommonProxy")
    public static CommonProxy proxy;
    @Instance(MekanismFusion.MODID)
    public static MekanismFusion instance;
    @EventHandler
    public static void preInit(FMLPreInitializationEvent event){
        proxy.preInit(event);
    }
    @EventHandler
    public static void init(FMLInitializationEvent event){
        proxy.init(event);
    }
    @EventHandler
    public static void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }
}
