package com.nyan.nyanMod.guis.descriptions;

import com.nyan.nyanMod.guis.NListPanel;
import com.nyan.nyanMod.guis.Screen;
import com.nyan.nyanMod.guis.util.DataAssembler;
import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class MainGui extends LightweightGuiDescription {
    private WGridPanel root ;
    private WButton buttonEntity;
    private WButton buttonBlock;


    public MainGui(){
        root  = new WGridPanel();
        buttonEntity = new WButton();
        buttonBlock = new WButton();
        setRootPanel(root);
        root.setSize(300,200);
        root.add(buttonEntity,4,5);
        root.add(buttonBlock,4,6);
        buttonEntity.setSize(3*18,17);
        buttonEntity.setLabel(new LiteralText("Entity"));
        buttonBlock.setLabel(new LiteralText("Block"));
        buttonBlock.setSize(3*18,17);
        buttonEntity.setOnClick(()->onButtonShowPressed(new EntityShowerGui("all")));
        buttonBlock.setOnClick(()->onButtonShowPressed(new BlockShowerGui()));

    }
    private void onButtonShowPressed(GuiDescription gui){
        MinecraftClient.getInstance().currentScreen.onClose();
        MinecraftClient.getInstance().send(()->MinecraftClient.getInstance().openScreen(new Screen(gui)));
    }


}
