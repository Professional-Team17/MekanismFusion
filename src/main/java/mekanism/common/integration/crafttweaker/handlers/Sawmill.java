package mekanism.common.integration.crafttweaker.handlers;

import static com.blamejared.mtlib.helpers.InputHelper.toStack;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.helpers.StackHelper;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientAny;
import java.util.HashMap;
import java.util.Map;
import mekanism.common.integration.crafttweaker.CrafttweakerIntegration;
import mekanism.common.integration.crafttweaker.util.AddMekanismRecipe;
import mekanism.common.integration.crafttweaker.util.RemoveMekanismRecipe;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.recipe.inputs.ItemStackInput;
import mekanism.common.recipe.machines.SawmillRecipe;
import mekanism.common.recipe.outputs.ChanceOutput;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.sawmill")
@ModOnly("mtlib")
@ZenRegister
public class Sawmill {

    public static final String NAME = "Mekanism Sawmill";

    @ZenMethod
    public static void addRecipe(IItemStack itemInput, IItemStack itemOutput, @Optional IItemStack optionalItemOutput,
          @Optional double optionalChance) {
        if (itemInput == null || itemOutput == null) {
            LogHelper.logError(String.format("Required parameters missing for %s Recipe.", NAME));
            return;
        }

        ItemStackInput input = new ItemStackInput(toStack(itemInput));
        ChanceOutput output = optionalItemOutput == null ? new ChanceOutput(toStack(itemOutput))
              : new ChanceOutput(toStack(itemOutput), toStack(optionalItemOutput), optionalChance);

        SawmillRecipe recipe = new SawmillRecipe(input, output);

        CrafttweakerIntegration.LATE_ADDITIONS
              .add(new AddMekanismRecipe<>(NAME, RecipeHandler.Recipe.PRECISION_SAWMILL.get(), recipe));
    }

    @ZenMethod
    public static void removeRecipe(IIngredient itemInput, @Optional IIngredient itemOutput,
          @Optional IIngredient optionalItemOutput) {
        if (itemInput == null) {
            LogHelper.logError(String.format("Required parameters missing for %s Recipe.", NAME));
            return;
        }

        if (itemOutput == null) {
            itemOutput = IngredientAny.INSTANCE;
        }
        if (optionalItemOutput == null) {
            optionalItemOutput = IngredientAny.INSTANCE;
        }

        CrafttweakerIntegration.LATE_REMOVALS
              .add(new Remove(NAME, RecipeHandler.Recipe.PRECISION_SAWMILL.get(), itemInput, itemOutput,
                    optionalItemOutput));
    }

    private static class Remove extends RemoveMekanismRecipe<ItemStackInput, SawmillRecipe> {

        private IIngredient itemInput;
        private IIngredient itemOutput;
        private IIngredient optionalItemOutput;

        public Remove(String name, Map<ItemStackInput, SawmillRecipe> map, IIngredient itemInput,
              IIngredient itemOutput, IIngredient optionalItemOutput) {
            super(name, map);
            this.itemInput = itemInput;
            this.itemOutput = itemOutput;
            this.optionalItemOutput = optionalItemOutput;
        }

        @Override
        public void addRecipes() {
            Map<ItemStackInput, SawmillRecipe> recipesToRemove = new HashMap<>();

            for (Map.Entry<ItemStackInput, SawmillRecipe> entry : RecipeHandler.Recipe.PRECISION_SAWMILL.get()
                  .entrySet()) {
                IItemStack inputItem = InputHelper.toIItemStack(entry.getKey().ingredient);
                IItemStack outputItem = InputHelper.toIItemStack(entry.getValue().recipeOutput.primaryOutput);
                IItemStack outputItemOptional = InputHelper.toIItemStack(entry.getValue().recipeOutput.secondaryOutput);

                if (!StackHelper.matches(itemOutput, outputItem)) {
                    continue;
                }
                if (!StackHelper.matches(itemInput, inputItem)) {
                    continue;
                }
                if (!StackHelper.matches(optionalItemOutput, outputItemOptional)) {
                    continue;
                }

                recipesToRemove.put(entry.getKey(), entry.getValue());
            }

            if (!recipesToRemove.isEmpty()) {
                recipes.putAll(recipesToRemove);
            } else {
                LogHelper.logInfo(
                      String.format("No %s recipe found for %s and %s. Command ignored!", NAME, itemInput.toString(),
                            itemOutput.toString()));
            }
        }
    }
}
