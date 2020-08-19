package professionalteam17.mekanismfusion.common;

import java.util.Locale;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.OreGas;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
public class MekanismFusionFluids {
    //FusionFuels
    public static final Gas DDFusionFuel = new Gas("D-D fusionfuel", 0xFF3232);
    public static final Gas DHe3FusionFuel = new Gas("D-He3 fusionfuel", 0x84C1FF);
    public static final Gas He4FusionFuel = new Gas("He4 fusionfuel", 0xFF8040);
    public static final Gas CFusionFuel = new Gas("C12 fusionfuel", 0x3C3C3C);

    //Resources
    public static final Gas Helium3 = new Gas("Helium3", 0xFFAF60);
    public static final Gas Helium4 = new Gas("Helium4", 0xFFA042);
    public static final Gas Carbon = new Gas("Carbon", 0x272727);

}
