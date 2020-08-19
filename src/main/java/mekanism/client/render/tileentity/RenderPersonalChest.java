package mekanism.client.render.tileentity;

import mekanism.client.render.MekanismRenderer;
import mekanism.common.tile.TileEntityPersonalChest;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPersonalChest extends TileEntitySpecialRenderer<TileEntityPersonalChest> {

    private ModelChest model = new ModelChest();

    @Override
    public void render(TileEntityPersonalChest tileEntity, double x, double y, double z, float partialTick, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y + 1F, (float) z);
        GlStateManager.rotate(90, 0, 1, 0);
        bindTexture(MekanismUtils.getResource(ResourceType.RENDER, "PersonalChest.png"));

        MekanismRenderer.rotate(tileEntity.facing, 270, 90, 0, 180);
        switch (tileEntity.facing) {
            case NORTH:
                GlStateManager.translate(1.0F, 0, 0);
                break;
            case SOUTH:
                GlStateManager.translate(0, 0, -1.0F);
                break;
            case EAST:
                GlStateManager.translate(1.0F, 0, -1.0F);
                break;
        }

        float lidangle = tileEntity.prevLidAngle + (tileEntity.lidAngle - tileEntity.prevLidAngle) * partialTick;
        lidangle = 1.0F - lidangle;
        lidangle = 1.0F - lidangle * lidangle * lidangle;
        model.chestLid.rotateAngleX = -((lidangle * 3.141593F) / 2.0F);
        GlStateManager.rotate(180, 0, 0, 1);
        model.renderAll();
        GlStateManager.popMatrix();
    }
}