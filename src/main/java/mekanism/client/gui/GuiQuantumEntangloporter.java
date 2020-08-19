package mekanism.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mekanism.api.EnumColor;
import mekanism.api.TileNetworkList;
import mekanism.client.gui.button.GuiButtonDisableableImage;
import mekanism.client.gui.element.GuiScrollList;
import mekanism.client.gui.element.tab.GuiSecurityTab;
import mekanism.client.gui.element.tab.GuiSideConfigurationTab;
import mekanism.client.gui.element.tab.GuiTransporterConfigTab;
import mekanism.client.gui.element.tab.GuiUpgradeTab;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.Mekanism;
import mekanism.common.frequency.Frequency;
import mekanism.common.frequency.FrequencyManager;
import mekanism.common.inventory.container.ContainerQuantumEntangloporter;
import mekanism.common.network.PacketTileEntity.TileEntityMessage;
import mekanism.common.tile.TileEntityQuantumEntangloporter;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class GuiQuantumEntangloporter extends GuiMekanismTile<TileEntityQuantumEntangloporter> {

    private GuiButton publicButton;
    private GuiButton privateButton;
    private GuiButton setButton;
    private GuiButton deleteButton;
    private GuiButton checkboxButton;
    private GuiScrollList scrollList;
    private GuiTextField frequencyField;
    private boolean privateMode;

    public GuiQuantumEntangloporter(InventoryPlayer inventory, TileEntityQuantumEntangloporter tile) {
        super(tile, new ContainerQuantumEntangloporter(inventory, tile));
        ResourceLocation resource = getGuiLocation();
        addGuiElement(scrollList = new GuiScrollList(this, resource, 28, 37, 120, 4));
        addGuiElement(new GuiSideConfigurationTab(this, tileEntity, resource));
        addGuiElement(new GuiTransporterConfigTab(this, 34, tileEntity, resource));
        addGuiElement(new GuiUpgradeTab(this, tileEntity, resource));
        addGuiElement(new GuiSecurityTab(this, tileEntity, resource));
        if (tileEntity.frequency != null) {
            privateMode = !tileEntity.frequency.publicFreq;
        }
        ySize += 64;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
        buttonList.add(publicButton = new GuiButton(0, guiLeft + 27, guiTop + 14, 60, 20, LangUtils.localize("gui.public")));
        buttonList.add(privateButton = new GuiButton(1, guiLeft + 89, guiTop + 14, 60, 20, LangUtils.localize("gui.private")));
        buttonList.add(setButton = new GuiButton(2, guiLeft + 27, guiTop + 116, 60, 20, LangUtils.localize("gui.set")));
        buttonList.add(deleteButton = new GuiButton(3, guiLeft + 89, guiTop + 116, 60, 20, LangUtils.localize("gui.delete")));
        frequencyField = new GuiTextField(4, fontRenderer, guiLeft + 50, guiTop + 104, 86, 11);
        frequencyField.setMaxStringLength(FrequencyManager.MAX_FREQ_LENGTH);
        frequencyField.setEnableBackgroundDrawing(false);
        buttonList.add(checkboxButton = new GuiButtonDisableableImage(5, guiLeft + 137, guiTop + 103, 11, 11, xSize, 11, -11, getGuiLocation()));
        updateButtons();
    }

    public void setFrequency(String freq) {
        if (freq.isEmpty()) {
            return;
        }
        TileNetworkList data = TileNetworkList.withContents(0, freq, !privateMode);
        Mekanism.packetHandler.sendToServer(new TileEntityMessage(tileEntity, data));
    }

    public String getSecurity(Frequency freq) {
        return !freq.publicFreq ? EnumColor.DARK_RED + LangUtils.localize("gui.private") : LangUtils.localize("gui.public");
    }

    public void updateButtons() {
        if (tileEntity.getSecurity().getClientOwner() == null) {
            return;
        }
        List<String> text = new ArrayList<>();
        if (privateMode) {
            for (Frequency freq : tileEntity.privateCache) {
                text.add(freq.name);
            }
        } else {
            for (Frequency freq : tileEntity.publicCache) {
                text.add(freq.name + " (" + freq.clientOwner + ")");
            }
        }
        scrollList.setText(text);
        if (privateMode) {
            publicButton.enabled = true;
            privateButton.enabled = false;
        } else {
            publicButton.enabled = false;
            privateButton.enabled = true;
        }
        if (scrollList.hasSelection()) {
            Frequency freq = privateMode ? tileEntity.privateCache.get(scrollList.getSelection()) : tileEntity.publicCache.get(scrollList.getSelection());
            setButton.enabled = tileEntity.getFrequency(null) == null || !tileEntity.getFrequency(null).equals(freq);
            deleteButton.enabled = tileEntity.getSecurity().getOwnerUUID().equals(freq.ownerUUID);
        } else {
            setButton.enabled = false;
            deleteButton.enabled = false;
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        updateButtons();
        frequencyField.updateCursorCounter();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        super.mouseClicked(mouseX, mouseY, button);
        updateButtons();
        frequencyField.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected ResourceLocation getGuiLocation() {
        return MekanismUtils.getResource(ResourceType.GUI, "GuiTeleporter.png");
    }

    @Override
    public void keyTyped(char c, int i) throws IOException {
        if (!frequencyField.isFocused() || i == Keyboard.KEY_ESCAPE) {
            super.keyTyped(c, i);
        }
        if (i == Keyboard.KEY_RETURN) {
            if (frequencyField.isFocused()) {
                setFrequency(frequencyField.getText());
                frequencyField.setText("");
            }
        }
        if (Character.isDigit(c) || Character.isLetter(c) || isTextboxKey(c, i) || FrequencyManager.SPECIAL_CHARS.contains(c)) {
            frequencyField.textboxKeyTyped(c, i);
        }
        updateButtons();
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) throws IOException {
        super.actionPerformed(guibutton);
        if (guibutton.id == publicButton.id) {
            privateMode = false;
        } else if (guibutton.id == privateButton.id) {
            privateMode = true;
        } else if (guibutton.id == setButton.id) {
            int selection = scrollList.getSelection();
            if (selection != -1) {
                Frequency freq = privateMode ? tileEntity.privateCache.get(selection) : tileEntity.publicCache.get(selection);
                setFrequency(freq.name);
            }
        } else if (guibutton.id == deleteButton.id) {
            int selection = scrollList.getSelection();
            if (selection != -1) {
                Frequency freq = privateMode ? tileEntity.privateCache.get(selection) : tileEntity.publicCache.get(selection);
                TileNetworkList data = TileNetworkList.withContents(1, freq.name, freq.publicFreq);
                Mekanism.packetHandler.sendToServer(new TileEntityMessage(tileEntity, data));
                scrollList.clearSelection();
            }
        } else if (guibutton.id == checkboxButton.id) {
            setFrequency(frequencyField.getText());
            frequencyField.setText("");
        }
        updateButtons();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(tileEntity.getName(), (xSize / 2) - (fontRenderer.getStringWidth(tileEntity.getName()) / 2), 4, 0x404040);
        fontRenderer.drawString(LangUtils.localize("gui.owner") + ": " + (tileEntity.getSecurity().getClientOwner() != null
                                                                          ? tileEntity.getSecurity().getClientOwner()
                                                                          : LangUtils.localize("gui.none")), 8, (ySize - 96) + 4, 0x404040);
        fontRenderer.drawString(LangUtils.localize("gui.freq") + ":", 32, 81, 0x404040);
        fontRenderer.drawString(LangUtils.localize("gui.security") + ":", 32, 91, 0x404040);
        Frequency frequency = tileEntity.getFrequency(null);
        fontRenderer.drawString(" " + (frequency != null ? frequency.name : EnumColor.DARK_RED + LangUtils.localize("gui.none")),
              32 + fontRenderer.getStringWidth(LangUtils.localize("gui.freq") + ":"), 81, 0x797979);
        fontRenderer.drawString(" " + (frequency != null ? getSecurity(frequency) : EnumColor.DARK_RED + LangUtils.localize("gui.none")),
              32 + fontRenderer.getStringWidth(LangUtils.localize("gui.security") + ":"), 91, 0x797979);
        String str = LangUtils.localize("gui.set") + ":";
        renderScaledText(str, 27, 104, 0x404040, 20);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(int xAxis, int yAxis) {
        super.drawGuiContainerBackgroundLayer(xAxis, yAxis);
        frequencyField.drawTextBox();
        MekanismRenderer.resetColor();
    }
}