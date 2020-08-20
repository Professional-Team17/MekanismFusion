package professionalteam17.mekanismfusion.common;

import mekanism.api.gas.GasStack;
import mekanism.common.MekanismFluids;
import mekanism.common.block.states.BlockStateMachine;
import mekanism.common.config.MekanismConfig;
import mekanism.common.recipe.BinRecipe;
import mekanism.common.recipe.GasConversionHandler;
import mekanism.common.recipe.RecipeHandler;
import net.minecraft.item.crafting.IRecipe;
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
    }
    @EventHandler
    public static void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }

    static {
        MekanismFusionFluids.register();
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().register(new BinRecipe());
        addRecipesFusion();
        GasConversionHandler.addDefaultGasMappings();
    }
    public static void addRecipesFusion(){
        //Chemical Infuser Recipes
        if (MekanismConfig.current().general.machinesManager.isEnabled(BlockStateMachine.MachineType.CHEMICAL_INJECTION_CHAMBER)) {
            RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFluids.Deuterium, 1), new GasStack(MekanismFluids.Deuterium, 1), new GasStack(MekanismFusionFluids.DDFusionFuel, 2));
            RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFluids.Deuterium, 1), new GasStack(MekanismFusionFluids.Helium3, 1), new GasStack(MekanismFusionFluids.DHe3FusionFuel, 2));
            RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFusionFluids.Helium4, 1), new GasStack(MekanismFusionFluids.EnrichedHelium, 2), new GasStack(MekanismFusionFluids.He4FusionFuel, 3));
            RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFusionFluids.EnrichedHydrogen, 1), new GasStack(MekanismFusionFluids.EnrichedHydrogen, 1), new GasStack(MekanismFusionFluids.HFusionFuel, 2));
            RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFluids.Oxygen, 1), new GasStack(MekanismFluids.Oxygen, 1), new GasStack(MekanismFusionFluids.EnrichedHydrogen, 2));
            RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFusionFluids.Carbon, 1), new GasStack(MekanismFusionFluids.Carbon, 1), new GasStack(MekanismFusionFluids.CFusionFuel, 2));
            RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFusionFluids.Silicon, 1), new GasStack(MekanismFusionFluids.Silicon, 1), new GasStack(MekanismFusionFluids.SiFusionFuel, 2));

            RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFluids.Hydrogen, 1), new GasStack(MekanismFluids.Hydrogen, 1), new GasStack(MekanismFusionFluids.OFusionFuel, 2));
            RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFusionFluids.Helium4, 1), new GasStack(MekanismFusionFluids.Helium4, 1), new GasStack(MekanismFusionFluids.EnrichedHelium, 2));

        }

        if (MekanismConfig.current().general.machinesManager.isEnabled(BlockStateMachine.MachineType.ELECTROLYTIC_SEPARATOR)) {
            RecipeHandler.addElectrolyticSeparatorRecipe(FluidRegistry.getFluidStack("lava", 2), 2 * MekanismConfig.current().general.FROM_H2.val(),
                    new GasStack(MekanismFusionFluids.Silicon, 1), new GasStack(MekanismFluids.Oxygen, 2));
        }

    }
}
