package mekanism.common.integration.crafttweaker.handlers;

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
import mekanism.common.recipe.machines.SmeltingRecipe;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.smelter")
@ModOnly("mtlib")
@ZenRegister
public class EnergizedSmelter {

    public static final String NAME = "Mekanism Smelter";
    private static boolean removedRecipe = false;
    private static boolean addedRecipe = false;

    public static boolean hasRemovedRecipe() {
        return removedRecipe;
    }

    public static boolean hasAddedRecipe() {
        return addedRecipe;
    }

    @ZenMethod
    public static void addRecipe(IItemStack itemInput, IItemStack itemOutput) {
        if (itemInput == null || itemOutput == null) {
            LogHelper.logError(String.format("Required parameters missing for %s Recipe.", NAME));
            return;
        }

        SmeltingRecipe recipe = new SmeltingRecipe(InputHelper.toStack(itemInput), InputHelper.toStack(itemOutput));

        CrafttweakerIntegration.LATE_ADDITIONS
              .add(new AddMekanismRecipe<>(NAME, RecipeHandler.Recipe.ENERGIZED_SMELTER.get(), recipe));
        addedRecipe = true;
    }

    @ZenMethod
    public static void removeRecipe(IIngredient itemInput, @Optional IIngredient itemOutput) {
        if (itemInput == null) {
            LogHelper.logError(String.format("Required parameters missing for %s Recipe.", NAME));
            return;
        }

        if (itemOutput == null) {
            itemOutput = IngredientAny.INSTANCE;
        }

        CrafttweakerIntegration.LATE_REMOVALS
              .add(new Remove(NAME, RecipeHandler.Recipe.ENERGIZED_SMELTER.get(), itemInput, itemOutput));
        removedRecipe = true;
    }

    private static class Remove extends RemoveMekanismRecipe<ItemStackInput, SmeltingRecipe> {

        private IIngredient itemInput;
        private IIngredient itemOutput;

        public Remove(String name, Map<ItemStackInput, SmeltingRecipe> map, IIngredient itemInput,
              IIngredient itemOutput) {
            super(name, map);

            this.itemInput = itemInput;
            this.itemOutput = itemOutput;
        }

        @Override
        public void addRecipes() {
            Map<ItemStackInput, SmeltingRecipe> recipesToRemove = new HashMap<>();

            for (Map.Entry<ItemStackInput, SmeltingRecipe> entry : RecipeHandler.Recipe.ENERGIZED_SMELTER.get()
                  .entrySet()) {
                IItemStack inputItem = InputHelper.toIItemStack(entry.getKey().ingredient);
                IItemStack outputItem = InputHelper.toIItemStack(entry.getValue().recipeOutput.output);

                if (!StackHelper.matches(itemInput, inputItem)) {
                    continue;
                }
                if (!StackHelper.matches(itemOutput, outputItem)) {
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
