package professionalteam17.mekanismfusion.common;

import mekanism.api.gas.GasStack;
import mekanism.common.MekanismBlocks;
import mekanism.common.MekanismFluids;
import mekanism.common.MekanismItems;
import mekanism.common.block.states.BlockStateMachine;
import mekanism.common.config.MekanismConfig;
import mekanism.common.recipe.RecipeHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import professionalteam17.mekanismfusion.proxy.CommonProxy;

@Mod(modid = MekanismFusion.MODID, name = MekanismFusion.NAME, version = MekanismFusion.VERSION, acceptedMinecraftVersions = "1.12.2", dependencies = "mekanismgenerators")
public class MekanismFusion {
    public static final String MODID = "mekanismfusion";
    public static final String NAME = "MekanismFusion";
    public static final String VERSION = "aloha1.0";
    @SidedProxy(clientSide = "professionalteam17.mekanismfusion.proxy.ClientProxy",serverSide = "professionalteam17.mekanismfusion.proxy.CommonProxy")
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
        addRecipesFusion();
    }
    @EventHandler
    public static void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }

    static {
        MekanismFusionFluids.register();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        // Register items and itemBlocks
        MekanismFusionItems.registerItems(event.getRegistry());
        //MekanismBlocks.registerItemBlocks(event.getRegistry());
        //Integrate certain OreDictionary recipes
        //registerOreDict();
    }

    public static void addRecipesFusion(){

        //Chemical Infuser Recipes
        RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFluids.Deuterium, 1), new GasStack(MekanismFluids.Deuterium, 1), new GasStack(MekanismFusionFluids.DDFusionFuel, 2));
        RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFluids.Deuterium, 1), new GasStack(MekanismFusionFluids.Helium3, 1), new GasStack(MekanismFusionFluids.DHe3FusionFuel, 2));
        RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFusionFluids.Helium4, 1), new GasStack(MekanismFusionFluids.EnrichedHelium, 2), new GasStack(MekanismFusionFluids.He4FusionFuel, 3));
        RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFusionFluids.EnrichedHydrogen, 1), new GasStack(MekanismFusionFluids.EnrichedHydrogen, 1), new GasStack(MekanismFusionFluids.HFusionFuel, 2));
        RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFluids.Oxygen, 1), new GasStack(MekanismFluids.Oxygen, 1), new GasStack(MekanismFusionFluids.EnrichedHydrogen, 2));
        RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFusionFluids.Carbon, 1), new GasStack(MekanismFusionFluids.Carbon, 1), new GasStack(MekanismFusionFluids.CFusionFuel, 2));
        RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFusionFluids.Silicon, 1), new GasStack(MekanismFusionFluids.Silicon, 1), new GasStack(MekanismFusionFluids.SiFusionFuel, 2));

        RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFluids.Hydrogen, 1), new GasStack(MekanismFluids.Hydrogen, 1), new GasStack(MekanismFusionFluids.OFusionFuel, 2));
        RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFusionFluids.Helium4, 1), new GasStack(MekanismFusionFluids.Helium4, 1), new GasStack(MekanismFusionFluids.EnrichedHelium, 2));

        RecipeHandler.addChemicalCrystallizerRecipe(new GasStack(MekanismFusionFluids.Silicon, 200), new ItemStack(MekanismFusionItems.CrystalSilicon, 1, 0));
        RecipeHandler.addChemicalCrystallizerRecipe(new GasStack(MekanismFusionFluids.Iron, 200), new ItemStack(MekanismFusionItems.CrystalIron, 1, 0));

        RecipeHandler.addCrusherRecipe(new ItemStack(MekanismFusionItems.CrystalIron),new ItemStack(MekanismItems.Dust,1,0));

        RecipeHandler.addChemicalDissolutionChamberRecipe(new ItemStack(Blocks.COAL_ORE,1),new GasStack(MekanismFusionFluids.DirtyCarbonOreGas, 1000));
        RecipeHandler.addChemicalWasherRecipe(new GasStack(MekanismFusionFluids.DirtyCarbonOreGas, 1), new GasStack(MekanismFusionFluids.CleanCarbonOreGas, 1));
        RecipeHandler.addChemicalCrystallizerRecipe(new GasStack(MekanismFusionFluids.CleanCarbonOreGas, 100), new ItemStack(MekanismFusionItems.CrystalCarbon, 1, 0));

    }
}
