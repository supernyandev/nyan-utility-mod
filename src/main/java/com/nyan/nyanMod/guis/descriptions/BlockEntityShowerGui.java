package com.nyan.nyanMod.guis.descriptions;

import com.nyan.nyanMod.guis.Screen;
import com.nyan.nyanMod.guis.util.DataAssembler;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class BlockEntityShowerGui extends LightweightGuiDescription {
    private WListPanel<String,Plain> list;
    private WGridPanel root ;
    private WButton exitButton;
    private WLabel errorLabel;
    private WTextField typeField;
    private WButton typeButton;
    public BlockEntityShowerGui(String type){
        root  = new WGridPanel();
        root.setSize(300,200);

        exitButton = new WButton();
        errorLabel = new WLabel("");
        errorLabel.setColor(0xFF0000).setDarkmodeColor(0x0000FF);
        typeButton = new WButton();
        typeField = new WTextField();
        typeField.setText(type);
        typeField.setHost(this);

        BiConsumer<String, Plain> configurator = (String s, Plain destination) -> {
            destination.label.setSize(200,16);
            System.out.println("out");
            destination.label.setText(s);

            destination.label.setHost(this);
            destination.cost.setText(new LiteralText(""));


        };

        list = new WListPanel<>(DataAssembler.bEntityData(MinecraftClient.getInstance(),type), Plain::new, configurator);
        list.setListItemHeight(18);
        list.setHost(this);
        list.layout();

        setRootPanel(root);
        root.add(list, 0, 2);
        root.add(exitButton,0,0);
        root.add(errorLabel,1,11);
        root.add(typeButton,5,11);
        root.add(typeField,0,11);

        list.setSize(14*18,8*18-7);
        exitButton.setSize(30,10);
        exitButton.setLabel(new LiteralText("exit"));
        errorLabel.setSize(100,20);
        typeField.setSize(5*18,18);
        typeField.setMaxLength(100);
        typeButton.setOnClick(this::onTypeButtonPressed);
        exitButton.setOnClick(this::ExitButtonExit);

    }




    private void ExitButtonExit(){
        MinecraftClient.getInstance().currentScreen.onClose();
        MinecraftClient.getInstance().send(()->MinecraftClient.getInstance().openScreen(new Screen(new MainGui())));
    }

    private void onTypeButtonPressed(){
        MinecraftClient.getInstance().currentScreen.onClose();
        MinecraftClient.getInstance().send(()->MinecraftClient.getInstance().openScreen(new Screen(
                new BlockEntityShowerGui(typeField.getText()))));
    }
}
