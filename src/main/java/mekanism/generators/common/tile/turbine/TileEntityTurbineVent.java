package mekanism.generators.common.tile.turbine;

import java.util.EnumSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import mekanism.common.base.FluidHandlerWrapper;
import mekanism.common.base.IFluidHandlerWrapper;
import mekanism.common.util.CapabilityUtils;
import mekanism.common.util.EmitUtils;
import mekanism.common.util.PipeUtils;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileEntityTurbineVent extends TileEntityTurbineCasing implements IFluidHandlerWrapper {

    public FluidTankInfo fakeInfo = new FluidTankInfo(null, 1000);

    public TileEntityTurbineVent() {
        super("TurbineVent");
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote && structure != null && structure.flowRemaining > 0) {
            FluidStack fluidStack = new FluidStack(FluidRegistry.WATER, structure.flowRemaining);
            EmitUtils.forEachSide(getWorld(), getPos(), EnumSet.allOf(EnumFacing.class), (tile, side) -> {
                IFluidHandler handler = CapabilityUtils.getCapability(tile, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite());
                if (handler != null && PipeUtils.canFill(handler, fluidStack)) {
                    structure.flowRemaining -= handler.fill(fluidStack, true);
                }
            });
        }
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        return ((!world.isRemote && structure != null) || (world.isRemote && clientHasStructure)) ? new FluidTankInfo[]{fakeInfo} : PipeUtils.EMPTY;
    }

    @Override
    public FluidTankInfo[] getAllTanks() {
        return getTankInfo(null);
    }

    @Override
    @Nullable
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
        int amount = Math.min(maxDrain, structure.flowRemaining);
        if (amount <= 0) {
            return null;
        }
        FluidStack fluidStack = new FluidStack(FluidRegistry.WATER, amount);
        if (doDrain) {
            structure.flowRemaining -= amount;
        }
        return fluidStack;
    }

    @Override
    public boolean canDrain(EnumFacing from, @Nullable FluidStack fluid) {
        return structure != null && (fluid == null || fluid.getFluid() == FluidRegistry.WATER);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing side) {
        if ((!world.isRemote && structure != null) || (world.isRemote && clientHasStructure)) {
            if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
                return true;
            }
        }
        return super.hasCapability(capability, side);
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing side) {
        if ((!world.isRemote && structure != null) || (world.isRemote && clientHasStructure)) {
            if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new FluidHandlerWrapper(this, side));
            }
        }
        return super.getCapability(capability, side);
    }
}