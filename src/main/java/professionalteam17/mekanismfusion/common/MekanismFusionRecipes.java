package professionalteam17.mekanismfusion.common;

import mekanism.api.gas.GasStack;
import mekanism.common.MekanismFluids;
import mekanism.common.block.states.BlockStateMachine;
import mekanism.common.config.MekanismConfig;
import mekanism.common.recipe.RecipeHandler;
import professionalteam17.mekanismfusion.common.MekanismFusion;
import professionalteam17.mekanismfusion.common.MekanismFusionFluids;

public class MekanismFusionRecipes {
    public static void addRecipes(){
        //Chemical Infuser Recipes
        if (MekanismConfig.current().general.machinesManager.isEnabled(BlockStateMachine.MachineType.CHEMICAL_INJECTION_CHAMBER)) {
            RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFluids.Deuterium, 1), new GasStack(MekanismFluids.Deuterium, 1), new GasStack(MekanismFusionFluids.DDFusionFuel, 2));
            RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFluids.Deuterium, 1), new GasStack(MekanismFusionFluids.Helium3, 1), new GasStack(MekanismFusionFluids.DHe3FusionFuel, 2));
            RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFusionFluids.Helium4, 1), new GasStack(MekanismFusionFluids.Helium4, 1), new GasStack(MekanismFusionFluids.He4FusionFuel, 2));
            RecipeHandler.addChemicalInfuserRecipe(new GasStack(MekanismFusionFluids.Carbon, 1), new GasStack(MekanismFusionFluids.Carbon, 1), new GasStack(MekanismFusionFluids.CFusionFuel, 2));
        }
    }


}
