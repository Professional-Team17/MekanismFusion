package mekanism.common.content.entangloporter;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import java.util.function.Supplier;
import mekanism.api.TileNetworkList;
import mekanism.api.gas.GasTank;
import mekanism.common.config.MekanismConfig;
import mekanism.common.frequency.Frequency;
import mekanism.common.tier.FluidTankTier;
import mekanism.common.tier.GasTankTier;
import mekanism.common.util.TileUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidTank;

public class InventoryFrequency extends Frequency {

    public static final String ENTANGLOPORTER = "Entangloporter";
    private static final Supplier<FluidTank> FLUID_TANK_SUPPLIER = () -> new FluidTank(MekanismConfig.current().general.quantumEntangloporterFluidBuffer.val());
    private static final Supplier<GasTank> GAS_TANK_SUPPLIER = () -> new GasTank(MekanismConfig.current().general.quantumEntangloporterGasBuffer.val());

    public double storedEnergy;
    public FluidTank storedFluid;
    public GasTank storedGas;
    public NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    public double temperature;

    public InventoryFrequency(String n, UUID uuid) {
        super(n, uuid);
        storedFluid = FLUID_TANK_SUPPLIER.get();
        storedGas = GAS_TANK_SUPPLIER.get();
    }

    public InventoryFrequency(NBTTagCompound nbtTags) {
        super(nbtTags);
    }

    public InventoryFrequency(ByteBuf dataStream) {
        super(dataStream);
    }

    @Override
    public void write(NBTTagCompound nbtTags) {
        super.write(nbtTags);
        nbtTags.setDouble("storedEnergy", storedEnergy);
        if (storedFluid.getFluid() != null) {
            nbtTags.setTag("storedFluid", storedFluid.writeToNBT(new NBTTagCompound()));
        }
        if (storedGas.getGas() != null) {
            nbtTags.setTag("storedGas", storedGas.write(new NBTTagCompound()));
        }
        NBTTagList tagList = new NBTTagList();
        for (int slotCount = 0; slotCount < 1; slotCount++) {
            if (!inventory.get(slotCount).isEmpty()) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) slotCount);
                inventory.get(slotCount).writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTags.setTag("Items", tagList);
        nbtTags.setDouble("temperature", temperature);
    }

    @Override
    protected void read(NBTTagCompound nbtTags) {
        super.read(nbtTags);
        storedFluid = FLUID_TANK_SUPPLIER.get();
        storedGas = GAS_TANK_SUPPLIER.get();
        storedEnergy = nbtTags.getDouble("storedEnergy");

        if (nbtTags.hasKey("storedFluid")) {
            storedFluid.readFromNBT(nbtTags.getCompoundTag("storedFluid"));
        }
        if (nbtTags.hasKey("storedGas")) {
            storedGas.read(nbtTags.getCompoundTag("storedGas"));
            storedGas.setMaxGas(MekanismConfig.current().general.quantumEntangloporterGasBuffer.val());
        }

        NBTTagList tagList = nbtTags.getTagList("Items", NBT.TAG_COMPOUND);
        inventory = NonNullList.withSize(2, ItemStack.EMPTY);
        for (int tagCount = 0; tagCount < tagList.tagCount(); tagCount++) {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(tagCount);
            byte slotID = tagCompound.getByte("Slot");
            if (slotID == 0) {
                inventory.set(slotID, new ItemStack(tagCompound));
            }
        }
        temperature = nbtTags.getDouble("temperature");
    }

    @Override
    public void write(TileNetworkList data) {
        super.write(data);
        data.add(storedEnergy);
        TileUtils.addTankData(data, storedFluid);
        TileUtils.addTankData(data, storedGas);
        data.add(temperature);
    }

    @Override
    protected void read(ByteBuf dataStream) {
        super.read(dataStream);
        storedFluid = new FluidTank(FluidTankTier.ULTIMATE.getOutput());
        storedGas = new GasTank(GasTankTier.ULTIMATE.getOutput());
        storedEnergy = dataStream.readDouble();
        TileUtils.readTankData(dataStream, storedFluid);
        TileUtils.readTankData(dataStream, storedGas);
        temperature = dataStream.readDouble();
    }
}