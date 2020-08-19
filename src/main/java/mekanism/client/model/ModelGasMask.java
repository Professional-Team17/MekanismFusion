package mekanism.client.model;

import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.MekanismRenderer.GlowInfo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelGasMask extends ModelBase {

    ModelRenderer helmetfeed;
    ModelRenderer tubeback;
    ModelRenderer tubeL;
    ModelRenderer tubeR;
    ModelRenderer tubefront;
    ModelRenderer mouthintake;
    ModelRenderer finupperR;
    ModelRenderer finupperL;
    ModelRenderer finmidR;
    ModelRenderer finmidL;
    ModelRenderer finback;
    ModelRenderer topplate;
    ModelRenderer filterL;
    ModelRenderer filterR;
    ModelRenderer filterpipelower;
    ModelRenderer filterpipeupper;
    ModelRenderer glasstop;
    ModelRenderer glassfront;
    ModelRenderer glassR;
    ModelRenderer glassL;
    ModelRenderer glassbackR;
    ModelRenderer glassbackL;
    ModelRenderer pipecornerFL;
    ModelRenderer pipecornerFR;
    ModelRenderer pipecornerBR;
    ModelRenderer pipecornerBL;
    ModelRenderer lightL;
    ModelRenderer lightR;

    public ModelGasMask() {
        textureWidth = 128;
        textureHeight = 64;

        helmetfeed = new ModelRenderer(this, 88, 43);
        helmetfeed.addBox(-2F, -2F, 2F, 4, 3, 4);
        helmetfeed.setRotationPoint(0F, 0F, 0F);
        helmetfeed.setTextureSize(128, 64);
        helmetfeed.mirror = true;
        setRotation(helmetfeed, 0F, 0F, 0F);
        tubeback = new ModelRenderer(this, 106, 50);
        tubeback.addBox(-4.5F, -1F, 4.5F, 9, 1, 1);
        tubeback.setRotationPoint(0F, 0F, 0F);
        tubeback.setTextureSize(128, 64);
        tubeback.mirror = true;
        setRotation(tubeback, 0F, 0F, 0F);
        tubeL = new ModelRenderer(this, 106, 54);
        tubeL.addBox(4.5F, -1F, -4.5F, 1, 1, 9);
        tubeL.setRotationPoint(0F, 0F, 0F);
        tubeL.setTextureSize(128, 64);
        tubeL.mirror = true;
        setRotation(tubeL, 0F, 0F, 0F);
        tubeR = new ModelRenderer(this, 106, 54);
        tubeR.addBox(-5.5F, -1F, -4.5F, 1, 1, 9);
        tubeR.setRotationPoint(0F, 0F, 0F);
        tubeR.setTextureSize(128, 64);
        tubeR.mirror = true;
        setRotation(tubeR, 0F, 0F, 0F);
        tubefront = new ModelRenderer(this, 106, 50);
        tubefront.addBox(-4.5F, -1F, -5.5F, 9, 1, 1);
        tubefront.setRotationPoint(0F, 0F, 0F);
        tubefront.setTextureSize(128, 64);
        tubefront.mirror = true;
        setRotation(tubefront, 0F, 0F, 0F);
        mouthintake = new ModelRenderer(this, 118, 42);
        mouthintake.addBox(-1.5F, -0.7F, -6F, 3, 2, 3);
        mouthintake.setRotationPoint(0F, -2F, 0F);
        mouthintake.setTextureSize(128, 64);
        mouthintake.mirror = true;
        setRotation(mouthintake, 0.2094395F, 0F, 0F);
        finupperR = new ModelRenderer(this, 78, 50);
        finupperR.addBox(-6F, -7.5F, -3.3F, 1, 2, 12);
        finupperR.setRotationPoint(0F, 0F, 0F);
        finupperR.setTextureSize(128, 64);
        finupperR.mirror = true;
        setRotation(finupperR, 0.0698132F, 0F, 0F);
        finupperL = new ModelRenderer(this, 78, 50);
        finupperL.addBox(5F, -7.5F, -3.3F, 1, 2, 12);
        finupperL.setRotationPoint(0F, 0F, 0F);
        finupperL.setTextureSize(128, 64);
        finupperL.mirror = true;
        setRotation(finupperL, 0.0698132F, 0F, 0F);
        finupperL.mirror = false;
        finmidR = new ModelRenderer(this, 72, 34);
        finmidR.addBox(-7.5F, -6F, -1F, 2, 2, 5);
        finmidR.setRotationPoint(0F, 0F, 0F);
        finmidR.setTextureSize(128, 64);
        finmidR.mirror = true;
        setRotation(finmidR, 0F, 0F, 0F);
        finmidL = new ModelRenderer(this, 72, 34);
        finmidL.addBox(5.5F, -6F, -1F, 2, 2, 5);
        finmidL.setRotationPoint(0F, 0F, 0F);
        finmidL.setTextureSize(128, 64);
        finmidL.mirror = true;
        setRotation(finmidL, 0F, 0F, 0F);
        finmidL.mirror = false;
        finback = new ModelRenderer(this, 80, 0);
        finback.addBox(-1F, -9.6F, 2.5F, 2, 10, 3);
        finback.setRotationPoint(0F, 0F, 0F);
        finback.setTextureSize(128, 64);
        finback.mirror = true;
        setRotation(finback, 0F, 0F, 0F);
        topplate = new ModelRenderer(this, 104, 34);
        topplate.addBox(-3F, -10F, -2F, 6, 2, 6);
        topplate.setRotationPoint(0F, 0F, 0F);
        topplate.setTextureSize(128, 64);
        topplate.mirror = true;
        setRotation(topplate, 0.1396263F, 0F, 0F);
        filterL = new ModelRenderer(this, 108, 42);
        filterL.addBox(3.4F, -1.8F, -5F, 2, 3, 3);
        filterL.setRotationPoint(0F, 0F, 0F);
        filterL.setTextureSize(128, 64);
        filterL.mirror = true;
        setRotation(filterL, 0F, 0.3839724F, 0.5061455F);
        filterL.mirror = false;
        filterR = new ModelRenderer(this, 108, 42);
        filterR.addBox(-5.4F, -1.8F, -5F, 2, 3, 3);
        filterR.setRotationPoint(0F, 0F, 0F);
        filterR.setTextureSize(128, 64);
        filterR.mirror = true;
        setRotation(filterR, 0F, -0.3839724F, -0.5061455F);
        filterpipelower = new ModelRenderer(this, 92, 41);
        filterpipelower.addBox(-3F, 1F, -5F, 5, 1, 1);
        filterpipelower.setRotationPoint(0F, 0F, 0F);
        filterpipelower.setTextureSize(128, 64);
        filterpipelower.mirror = true;
        setRotation(filterpipelower, 0F, 0F, 0F);
        filterpipeupper = new ModelRenderer(this, 104, 42);
        filterpipeupper.addBox(-0.5F, 0F, -5F, 1, 1, 1);
        filterpipeupper.setRotationPoint(0F, 0F, 0F);
        filterpipeupper.setTextureSize(128, 64);
        filterpipeupper.mirror = true;
        setRotation(filterpipeupper, 0F, 0F, 0F);
        glasstop = new ModelRenderer(this, 0, 0);
        glasstop.addBox(-4F, -9F, -4F, 8, 1, 8);
        glasstop.setRotationPoint(0F, 0F, 0F);
        glasstop.setTextureSize(128, 64);
        glasstop.mirror = true;
        setRotation(glasstop, 0F, 0F, 0F);
        glassfront = new ModelRenderer(this, 0, 0);
        glassfront.addBox(-4F, -8F, -5F, 8, 7, 1);
        glassfront.setRotationPoint(0F, 0F, 0F);
        glassfront.setTextureSize(128, 64);
        glassfront.mirror = true;
        setRotation(glassfront, 0F, 0F, 0F);
        glassR = new ModelRenderer(this, 0, 0);
        glassR.addBox(-5F, -8F, -4F, 1, 7, 8);
        glassR.setRotationPoint(0F, 0F, 0F);
        glassR.setTextureSize(128, 64);
        glassR.mirror = true;
        setRotation(glassR, 0F, 0F, 0F);
        glassL = new ModelRenderer(this, 0, 0);
        glassL.addBox(4F, -8F, -4F, 1, 7, 8);
        glassL.setRotationPoint(0F, 0F, 0F);
        glassL.setTextureSize(128, 64);
        glassL.mirror = true;
        setRotation(glassL, 0F, 0F, 0F);
        glassbackR = new ModelRenderer(this, 0, 0);
        glassbackR.addBox(-4F, -8F, 4F, 3, 7, 1);
        glassbackR.setRotationPoint(0F, 0F, 0F);
        glassbackR.setTextureSize(128, 64);
        glassbackR.mirror = true;
        setRotation(glassbackR, 0F, 0F, 0F);
        glassbackL = new ModelRenderer(this, 0, 0);
        glassbackL.addBox(1F, -8F, 4F, 3, 7, 1);
        glassbackL.setRotationPoint(0F, 0F, 0F);
        glassbackL.setTextureSize(128, 64);
        glassbackL.mirror = true;
        setRotation(glassbackL, 0F, 0F, 0F);
        pipecornerFL = new ModelRenderer(this, 109, 50);
        pipecornerFL.addBox(3.5F, -1F, -4.5F, 1, 1, 1);
        pipecornerFL.setRotationPoint(0F, 0F, 0F);
        pipecornerFL.setTextureSize(128, 64);
        pipecornerFL.mirror = true;
        setRotation(pipecornerFL, 0F, 0F, 0F);
        pipecornerFR = new ModelRenderer(this, 109, 50);
        pipecornerFR.addBox(-4.5F, -1F, -4.5F, 1, 1, 1);
        pipecornerFR.setRotationPoint(0F, 0F, 0F);
        pipecornerFR.setTextureSize(128, 64);
        pipecornerFR.mirror = true;
        setRotation(pipecornerFR, 0F, 0F, 0F);
        pipecornerBR = new ModelRenderer(this, 109, 50);
        pipecornerBR.addBox(-4.5F, -1F, 3.5F, 1, 1, 1);
        pipecornerBR.setRotationPoint(0F, 0F, 0F);
        pipecornerBR.setTextureSize(128, 64);
        pipecornerBR.mirror = true;
        setRotation(pipecornerBR, 0F, 0F, 0F);
        pipecornerBL = new ModelRenderer(this, 109, 50);
        pipecornerBL.addBox(3.5F, -1F, 4.5F, 1, 1, 1);
        pipecornerBL.setRotationPoint(0F, 0F, -1F);
        pipecornerBL.setTextureSize(128, 64);
        pipecornerBL.mirror = true;
        setRotation(pipecornerBL, 0F, 0F, 0F);
        lightL = new ModelRenderer(this, 89, 37);
        lightL.addBox(5.5F, -6F, -2F, 2, 2, 1);
        lightL.setRotationPoint(0F, 0F, 0F);
        lightL.setTextureSize(128, 64);
        lightL.mirror = true;
        setRotation(lightL, 0F, 0F, 0F);
        lightR = new ModelRenderer(this, 89, 37);
        lightR.addBox(-7.5F, -6F, -2F, 2, 2, 1);
        lightR.setRotationPoint(0F, 0F, 0F);
        lightR.setTextureSize(128, 64);
        lightR.mirror = true;
        setRotation(lightR, 0F, 0F, 0F);
    }

    public void render(float size) {
        helmetfeed.render(size);
        tubeback.render(size);
        tubeL.render(size);
        tubeR.render(size);
        tubefront.render(size);
        mouthintake.render(size);
        finupperR.render(size);
        finupperL.render(size);
        finmidR.render(size);
        finmidL.render(size);
        finback.render(size);
        topplate.render(size);
        filterL.render(size);
        filterR.render(size);
        filterpipelower.render(size);
        filterpipeupper.render(size);

        pipecornerFL.render(size);
        pipecornerFR.render(size);
        pipecornerBR.render(size);
        pipecornerBL.render(size);

        GlowInfo glowInfo = MekanismRenderer.enableGlow();
        lightL.render(size);
        lightR.render(size);

        //Glass needs more settings
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1, 1, 1, 0.3F);
        GlStateManager.enableCull();

        glasstop.render(size);
        glassfront.render(size);
        glassR.render(size);
        glassL.render(size);
        glassbackR.render(size);
        glassbackL.render(size);

        GlStateManager.disableCull();
        MekanismRenderer.resetColor();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        MekanismRenderer.disableGlow(glowInfo);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}