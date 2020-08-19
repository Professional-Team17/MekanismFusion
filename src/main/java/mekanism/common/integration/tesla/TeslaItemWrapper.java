package mekanism.common.integration.tesla;

import mekanism.api.energy.IEnergizedItem;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.ItemCapabilityWrapper.ItemCapability;
import mekanism.common.integration.MekanismHooks;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.common.Optional.Method;

@InterfaceList({
      @Interface(iface = "net.darkhax.tesla.api.ITeslaConsumer", modid = MekanismHooks.TESLA_MOD_ID),
      @Interface(iface = "net.darkhax.tesla.api.ITeslaProducer", modid = MekanismHooks.TESLA_MOD_ID),
      @Interface(iface = "net.darkhax.tesla.api.ITeslaHolder", modid = MekanismHooks.TESLA_MOD_ID)
})
public class TeslaItemWrapper extends ItemCapability implements ITeslaHolder, ITeslaConsumer, ITeslaProducer {

    @Override
    public boolean canProcess(Capability<?> capability) {
        return capability == Capabilities.TESLA_HOLDER_CAPABILITY || capability == Capabilities.TESLA_CONSUMER_CAPABILITY && getItem().canReceive(getStack()) ||
               capability == Capabilities.TESLA_PRODUCER_CAPABILITY && getItem().canSend(getStack());
    }

    public IEnergizedItem getItem() {
        return (IEnergizedItem) getStack().getItem();
    }

    @Override
    @Method(modid = MekanismHooks.TESLA_MOD_ID)
    public long takePower(long power, boolean simulate) {
        if (getItem().canSend(getStack())) {
            long energyRemaining = getStoredPower();
            long toSend = Math.min(power, energyRemaining);
            if (!simulate) {
                getItem().setEnergy(getStack(), getItem().getEnergy(getStack()) - TeslaIntegration.fromTesla(toSend));
            }
            return toSend;
        }
        return 0;
    }

    @Override
    @Method(modid = MekanismHooks.TESLA_MOD_ID)
    public long givePower(long power, boolean simulate) {
        if (getItem().canReceive(getStack())) {
            long energyNeeded = getCapacity() - getStoredPower();
            long toReceive = Math.min(power, energyNeeded);
            if (!simulate) {
                getItem().setEnergy(getStack(), getItem().getEnergy(getStack()) + TeslaIntegration.fromTesla(toReceive));
            }
            return toReceive;
        }
        return 0;
    }

    @Override
    @Method(modid = MekanismHooks.TESLA_MOD_ID)
    public long getStoredPower() {
        return TeslaIntegration.toTesla(getItem().getEnergy(getStack()));
    }

    @Override
    @Method(modid = MekanismHooks.TESLA_MOD_ID)
    public long getCapacity() {
        return TeslaIntegration.toTesla(getItem().getMaxEnergy(getStack()));
    }
}