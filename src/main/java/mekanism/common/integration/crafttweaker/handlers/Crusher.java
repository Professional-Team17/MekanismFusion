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
import mekanism.common.recipe.machines.CrusherRecipe;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.crusher")
@ModOnly("mtlib")
@ZenRegister
public class Crusher {

    public static final String NAME = "Mekanism Crusher";

    @ZenMethod
    public static void addRecipe(IItemStack itemInput, IItemStack itemOutput) {
        if (itemInput == null || itemOutput == null) {
            LogHelper.logError(String.format("Required parameters missing for %s Recipe.", NAME));
            return;
        }

        CrusherRecipe recipe = new CrusherRecipe(InputHelper.toStack(itemInput), InputHelper.toStack(itemOutput));

        CrafttweakerIntegration.LATE_ADDITIONS
              .add(new AddMekanismRecipe<>(NAME, RecipeHandler.Recipe.CRUSHER.get(), recipe));
    }

    @ZenMethod
    public static void removeRecipe(IIngredient itemOutput, @Optional IIngredient itemInput) {
        if (itemOutput == null) {
            LogHelper.logError(String.format("Required parameters missing for %s Recipe.", NAME));
            return;
        }

        if (itemInput == null) {
            itemInput = IngredientAny.INSTANCE;
        }

        CrafttweakerIntegration.LATE_REMOVALS
              .add(new Remove(NAME, RecipeHandler.Recipe.CRUSHER.get(), itemOutput, itemInput));
    }

    private static class Remove extends RemoveMekanismRecipe<ItemStackInput, CrusherRecipe> {

        private IIngredient itemOutput;
        private IIngredient itemInput;

        public Remove(String name, Map<ItemStackInput, CrusherRecipe> map, IIngredient itemOutput,
              IIngredient itemInput) {
            super(name, map);

            this.itemOutput = itemOutput;
            this.itemInput = itemInput;
        }

        @Override
        public void addRecipes() {
            Map<ItemStackInput, CrusherRecipe> recipesToRemove = new HashMap<>();

            for (Map.Entry<ItemStackInput, CrusherRecipe> entry : RecipeHandler.Recipe.CRUSHER.get().entrySet()) {
                IItemStack inputItem = InputHelper.toIItemStack(entry.getKey().ingredient);
                IItemStack outputItem = InputHelper.toIItemStack(entry.getValue().recipeOutput.output);

                if (!StackHelper.matches(itemOutput, outputItem)) {
                    continue;
                }
                if (!StackHelper.matches(itemInput, inputItem)) {
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
