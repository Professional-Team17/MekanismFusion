package mekanism.common.tile;

import io.netty.buffer.ByteBuf;
import mekanism.api.Coord4D;
import mekanism.api.Range4D;
import mekanism.api.TileNetworkList;
import mekanism.common.LaserManager;
import mekanism.common.LaserManager.LaserInfo;
import mekanism.common.Mekanism;
import mekanism.common.base.IActiveState;
import mekanism.common.config.MekanismConfig;
import mekanism.common.network.PacketTileEntity.TileEntityMessage;
import mekanism.common.tile.prefab.TileEntityNoisyBlock;
import mekanism.common.util.InventoryUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileEntityLaser extends TileEntityNoisyBlock implements IActiveState {

    public Coord4D digging;
    public double diggingProgress;

    public boolean isActive;

    public boolean clientActive;

    public TileEntityLaser() {
        super("machine.laser", "Laser", 2 * MekanismConfig.current().usage.laserUsage.val());
        inventory = NonNullList.withSize(0, ItemStack.EMPTY);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (world.isRemote) {
            if (isActive) {
                RayTraceResult mop = LaserManager
                      .fireLaserClient(this, facing, MekanismConfig.current().usage.laserUsage.val(), world);
                Coord4D hitCoord = mop == null ? null : new Coord4D(mop, world);

                if (hitCoord == null || !hitCoord.equals(digging)) {
                    digging = hitCoord;
                    diggingProgress = 0;
                }

                if (hitCoord != null) {
                    IBlockState blockHit = hitCoord.getBlockState(world);
                    TileEntity tileHit = hitCoord.getTileEntity(world);
                    float hardness = blockHit.getBlockHardness(world, hitCoord.getPos());

                    if (!(hardness < 0 || (LaserManager.isReceptor(tileHit, mop.sideHit) && !(LaserManager
                          .getReceptor(tileHit, mop.sideHit).canLasersDig())))) {
                        diggingProgress += MekanismConfig.current().usage.laserUsage.val();

                        if (diggingProgress < hardness * MekanismConfig.current().general.laserEnergyNeededPerHardness
                              .val()) {
                            Mekanism.proxy.addHitEffects(hitCoord, mop);
                        }
                    }
                }
            }
        } else {
            if (getEnergy() >= MekanismConfig.current().usage.laserUsage.val()) {
                setActive(true);

                LaserInfo info = LaserManager
                      .fireLaser(this, facing, MekanismConfig.current().usage.laserUsage.val(), world);
                Coord4D hitCoord = info.movingPos == null ? null : new Coord4D(info.movingPos, world);

                if (hitCoord == null || !hitCoord.equals(digging)) {
                    digging = hitCoord;
                    diggingProgress = 0;
                }

                if (hitCoord != null) {
                    IBlockState blockHit = hitCoord.getBlockState(world);
                    TileEntity tileHit = hitCoord.getTileEntity(world);
                    float hardness = blockHit.getBlockHardness(world, hitCoord.getPos());

                    if (!(hardness < 0 || (LaserManager.isReceptor(tileHit, info.movingPos.sideHit) && !(LaserManager
                          .getReceptor(tileHit, info.movingPos.sideHit).canLasersDig())))) {
                        diggingProgress += MekanismConfig.current().usage.laserUsage.val();

                        if (diggingProgress >= hardness * MekanismConfig.current().general.laserEnergyNeededPerHardness
                              .val()) {
                            LaserManager.breakBlock(hitCoord, true, world);
                            diggingProgress = 0;
                        }
                    }
                }

                setEnergy(getEnergy() - MekanismConfig.current().usage.laserUsage.val());
            } else {
                setActive(false);
                diggingProgress = 0;
            }
        }
    }

    @Override
    public boolean sideIsConsumer(EnumFacing side) {
        return side == facing.getOpposite();
    }

    @Override
    public boolean getActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;

        if (clientActive != active) {
            Mekanism.packetHandler
                  .sendToReceivers(new TileEntityMessage(Coord4D.get(this), getNetworkedData(new TileNetworkList())),
                        new Range4D(Coord4D.get(this)));
            clientActive = active;
        }
    }

    @Override
    public boolean renderUpdate() {
        return false;
    }

    @Override
    public boolean lightUpdate() {
        return false;
    }

    @Override
    public TileNetworkList getNetworkedData(TileNetworkList data) {
        super.getNetworkedData(data);

        data.add(isActive);

        return data;
    }

    @Override
    public void handlePacketData(ByteBuf dataStream) {
        super.handlePacketData(dataStream);

        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            clientActive = dataStream.readBoolean();

            if (clientActive != isActive) {
                isActive = clientActive;
                MekanismUtils.updateBlock(world, getPos());
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);

        isActive = nbtTags.getBoolean("isActive");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);

        nbtTags.setBoolean("isActive", isActive);

        return nbtTags;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return InventoryUtils.EMPTY;
    }
}
