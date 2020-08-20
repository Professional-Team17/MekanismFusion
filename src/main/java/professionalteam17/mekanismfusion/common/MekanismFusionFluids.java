package professionalteam17.mekanismfusion.common;

import java.util.Locale;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.OreGas;
import mekanism.common.Mekanism;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
public class MekanismFusionFluids {
    //FusionFuels
    public static final Gas HFusionFuel = new Gas("H fusionfuel", 0xd0d0d0);//He4
    public static final Gas DDFusionFuel = new Gas("D-D fusionfuel", 0xFF3232);//n+3He
    public static final Gas DHe3FusionFuel = new Gas("D-He3 fusionfuel", 0x84C1FF);//p+He4
    public static final Gas He4FusionFuel = new Gas("He4 fusionfuel", 0xFF8040);//γ+C

    public static final Gas CFusionFuel = new Gas("C12 fusionfuel", 0x3C3C3C);//He+Ne
    public static final Gas OFusionFuel = new Gas("O fusionfuel", 0x6BE1FF);//He+Si
    public static final Gas SiFusionFuel = new Gas("Si fusionfuel", 0x00FF00);//Fe

    //Resources
    public static final Gas Helium3 = new Gas("Helium3", 0xFFAF60);
    public static final Gas Helium4 = new Gas("Helium4", 0xFFA042);
    public static final Gas Carbon = new Gas("Carbon", 0x272727);
    public static final Gas Silicon = new Gas("GasSilicon", 0x006000);
    public static final Gas Iron = new Gas("GasIron", 0xBEBEBE);
    public static final Gas Neon = new Gas("Neon", 0xFF5151);

    public static final Gas EnrichedHydrogen = new Gas("EnrichedHydrogen", 0xFF9224);
    public static final Gas EnrichedHelium = new Gas("EnrichedHelium", 0xF0F0F0);

    public static void register(){
        GasRegistry.register(DDFusionFuel).registerFluid("liquid D-D FusionFuel");
        GasRegistry.register(DHe3FusionFuel).registerFluid("liquid D-He3 FusionFuel");
        GasRegistry.register(He4FusionFuel).registerFluid("liquid He4 FusionFuel");
        GasRegistry.register(CFusionFuel).registerFluid("liquid C FusionFuel");
        GasRegistry.register(HFusionFuel).registerFluid("liquid H FusionFuel");
        GasRegistry.register(OFusionFuel).registerFluid("liquid O FusionFuel");
        GasRegistry.register(SiFusionFuel).registerFluid("liquid Si FusionFuel");

        GasRegistry.register(Helium3).registerFluid("liquidHelium3");
        GasRegistry.register(Helium4).registerFluid("liquidHelium4");
        GasRegistry.register(Carbon).registerFluid("liquidCarbon");
        GasRegistry.register(Silicon).registerFluid("liquidSilicon");
        GasRegistry.register(Iron).registerFluid("liquidIron");
        GasRegistry.register(Neon).registerFluid("liquidNeon");

        GasRegistry.register(EnrichedHydrogen).registerFluid("liquidEnrichedHydrogen");
        GasRegistry.register(EnrichedHelium).registerFluid("liquidEnrichedHelium");
        //GasRegistry.register().registerFluid("liquid");

    }
}
