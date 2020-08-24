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
    public static final Gas HFusionFuel = new Gas("hfusionfuel", 0xd0d0d0);//He4
    public static final Gas DDFusionFuel = new Gas("ddfusionfuel", 0xFF3232);//n+3He
    public static final Gas DHe3FusionFuel = new Gas("dhe3fusionfuel", 0x84C1FF);//p+He4
    public static final Gas He4FusionFuel = new Gas("he4fusionfuel", 0xFF8040);//γ+C

    public static final Gas CFusionFuel = new Gas("cfusionfuel", 0x3C3C3C);//He+Ne
    public static final Gas OFusionFuel = new Gas("ofusionfuel", 0x6BE1FF);//He+Si
    public static final Gas SiFusionFuel = new Gas("sifusionfuel", 0x00FF00);//Fe

    //Resources
    public static final Gas Helium3 = new Gas("helium3", 0xFFAF60);
    public static final Gas Helium4 = new Gas("helium4", 0xFFA042);
    public static final Gas Carbon = new Gas("carbon", 0x272727);
    public static final Gas Silicon = new Gas("gassilicon", 0x006000);
    public static final Gas Iron = new Gas("gasiron", 0xBEBEBE);
    public static final Gas Neon = new Gas("neon", 0xFF5151);

    public static final Gas EnrichedHydrogen = new Gas("EnrichedHydrogen", 0xFF9224);
    public static final Gas EnrichedHelium = new Gas("EnrichedHelium", 0xF0F0F0);

    public static final Gas DirtyCarbonOreGas = new Gas("DirtyCarbonOreGas", 0x242424);
    public static final Gas CleanCarbonOreGas = new Gas("CleanCarbonOreGas", 0x202020);

    public static void register(){
        GasRegistry.register(DDFusionFuel).registerFluid("liquidddfusionfuel");
        GasRegistry.register(DHe3FusionFuel).registerFluid("liquiddhe3fusionfuel");
        GasRegistry.register(He4FusionFuel).registerFluid("liquidhe4fusionfuel");
        GasRegistry.register(CFusionFuel).registerFluid("liquidcfusionfuel");
        GasRegistry.register(HFusionFuel).registerFluid("liquidhfusionfuel");
        GasRegistry.register(OFusionFuel).registerFluid("liquidofusionfuel");
        GasRegistry.register(SiFusionFuel).registerFluid("liquidsifusionfuel");

        GasRegistry.register(Helium3).registerFluid("liquidhelium3");
        GasRegistry.register(Helium4).registerFluid("liquidhelium4");
        GasRegistry.register(Carbon).registerFluid("liquidcarbon");
        GasRegistry.register(Silicon).registerFluid("liquidsilicon");
        GasRegistry.register(Iron).registerFluid("liquidiron");
        GasRegistry.register(Neon).registerFluid("liquidneon");

        GasRegistry.register(EnrichedHydrogen).registerFluid("liquidenrichedhydrogen");
        GasRegistry.register(EnrichedHelium).registerFluid("liquidenrichedhelium");
        //GasRegistry.register().registerFluid("liquid");
        GasRegistry.register(DirtyCarbonOreGas);
        GasRegistry.register(CleanCarbonOreGas);
    }
}
