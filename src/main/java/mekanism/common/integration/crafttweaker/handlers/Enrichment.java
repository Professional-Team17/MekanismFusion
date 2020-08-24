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
import mekanism.common.recipe.machines.EnrichmentRecipe;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.enrichment")
@ModOnly("mtlib")
@ZenRegister
public class Enrichment {

    public static final String NAME = "Mekanism Enrichment Chamber";

    @ZenMethod
    public static void addRecipe(IItemStack itemInput, IItemStack itemOutput) {
        if (itemInput == null || itemOutput == null) {
            LogHelper.logError(String.format("Required parameters missing for %s recipe.", NAME));
            return;
        }

        EnrichmentRecipe recipe = new EnrichmentRecipe(InputHelper.toStack(itemInput), InputHelper.toStack(itemOutput));

        CrafttweakerIntegration.LATE_ADDITIONS
              .add(new AddMekanismRecipe<>(NAME, RecipeHandler.Recipe.ENRICHMENT_CHAMBER.get(), recipe));
    }

    @ZenMethod
    public static void removeRecipe(IIngredient itemInput, @Optional IIngredient itemOutput) {
        if (itemInput == null) {
            LogHelper.logError(String.format("Required parameters missing for %s recipe.", NAME));
            return;
        }

        if (itemOutput == null) {
            itemOutput = IngredientAny.INSTANCE;
        }

        CrafttweakerIntegration.LATE_REMOVALS
              .add(new Remove(NAME, RecipeHandler.Recipe.ENRICHMENT_CHAMBER.get(), itemInput, itemOutput));
    }

    private static class Remove extends RemoveMekanismRecipe<ItemStackInput, EnrichmentRecipe> {

        private IIngredient itemOutput;
        private IIngredient itemInput;

        public Remove(String name, Map<ItemStackInput, EnrichmentRecipe> map, IIngredient itemInput,
              IIngredient itemOutput) {
            super(name, map);
            this.itemOutput = itemOutput;
            this.itemInput = itemInput;
        }

        @Override
        public void addRecipes() {
            Map<ItemStackInput, EnrichmentRecipe> recipesToRemove = new HashMap<>();

            for (Map.Entry<ItemStackInput, EnrichmentRecipe> entry : RecipeHandler.Recipe.ENRICHMENT_CHAMBER.get()
                  .entrySet()) {
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
