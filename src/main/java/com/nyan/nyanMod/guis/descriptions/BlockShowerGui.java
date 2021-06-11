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
import java.util.function.BiConsumer;

public class BlockShowerGui extends LightweightGuiDescription {
    private WListPanel<String,Plain> list;
    private WGridPanel root ;
    private WTextField textField ;
    private WButton button;
    private WButton exitButton;
    private WLabel errorLabel;
    private WTextField lightField;
    public BlockShowerGui(){
        root  = new WGridPanel();
        root.setSize(300,200);
        lightField = new WTextField();
        textField = new WTextField();
        textField.setHost(this);
        lightField.setHost(this);
        textField.setText("28");
        lightField.setText("7");
        exitButton = new WButton();
        errorLabel = new WLabel("");
        errorLabel.setColor(0xFF0000).setDarkmodeColor(0x0000FF);
        button = new WButton();
        BiConsumer<String, Plain> configurator = (String s, Plain destination) -> {
            destination.setSize(200,16);
            destination.label.setText(s);
            destination.label.setHost(this);

            destination.cost.setText(new LiteralText(""));

        };

        list = new WListPanel<>(DataAssembler.blockData(MinecraftClient.getInstance(),28,7), Plain::new, configurator);
        list.setListItemHeight(18);
        list.setHost(this);
        list.layout();

        setRootPanel(root);
        root.add(list, 0, 2, 12, 6);
        root.add(lightField,3,9);
        root.add(button,5,9,2,1);
        root.add(textField, 1,9);
        root.add(exitButton,0,0);
        root.add(errorLabel,1,11);

        textField.setSize(30,30);
        lightField.setSize(30,10);
        exitButton.setSize(30,10);
        exitButton.setLabel(new LiteralText("exit"));
        errorLabel.setSize(100,20);
        button.setLabel(new LiteralText("apply"));
        button.setOnClick(()->this.OnButtonPressedConfig(textField.getText()));
        exitButton.setOnClick(()->this.ExitButtonExit());

    }
    private void OnButtonPressedConfig(String text){
        try{
        root.remove(list);
        int light = Integer.parseInt(lightField.getText());
        int distance = Integer.parseInt(text);
        BiConsumer<String, Plain> configurator = (String s, Plain destination) -> {
            destination.label.setText(s);
            destination.label.setHost(this);
            destination.cost.setText(new LiteralText(""));

        };
        list = new WListPanel<>(DataAssembler.blockData(MinecraftClient.getInstance(),distance,light), Plain::new, configurator);
        list.setHost(this);
        list.setListItemHeight(18);
        root.add(list, 0, 2, 12, 6);
        list.layout();
        errorLabel.setText(new LiteralText(""));}
        catch (Throwable ex){
            errorLabel.setText(new LiteralText("Must be an integer"));

        }


//        MinecraftClient.getInstance().currentScreen.onClose();
    }
    private void ExitButtonExit(){
        MinecraftClient.getInstance().currentScreen.onClose();
        MinecraftClient.getInstance().send(()->MinecraftClient.getInstance().openScreen(new Screen(new MainGui())));
    }
}
