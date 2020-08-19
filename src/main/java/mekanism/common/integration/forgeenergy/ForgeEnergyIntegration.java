package mekanism.common.integration.forgeenergy;

import mekanism.common.base.IEnergyWrapper;
import mekanism.common.config.MekanismConfig;
import mekanism.common.util.MekanismUtils;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

public class ForgeEnergyIntegration implements IEnergyStorage {

    public IEnergyWrapper tileEntity;

    public EnumFacing side;

    public ForgeEnergyIntegration(IEnergyWrapper tile, EnumFacing facing) {
        tileEntity = tile;
        side = facing;
    }

    public static double fromForge(int forge) {
        return forge * MekanismConfig.current().general.FROM_FORGE.val();
    }

    public static int toForge(double joules) {
        return MekanismUtils.clampToInt(joules * MekanismConfig.current().general.TO_FORGE.val());
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return toForge(tileEntity.acceptEnergy(side, fromForge(maxReceive), simulate));
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return toForge(tileEntity.pullEnergy(side, fromForge(maxExtract), simulate));
    }

    @Override
    public int getEnergyStored() {
        return toForge(tileEntity.getEnergy());
    }

    @Override
    public int getMaxEnergyStored() {
        return toForge(tileEntity.getMaxEnergy());
    }

    @Override
    public boolean canExtract() {
        return tileEntity.sideIsOutput(side);
    }

    @Override
    public boolean canReceive() {
        return tileEntity.sideIsConsumer(side);
    }
}