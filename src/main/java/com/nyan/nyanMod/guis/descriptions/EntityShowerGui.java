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

public class EntityShowerGui extends LightweightGuiDescription {
    private WListPanel<String,PlainEntity> list;
    private WGridPanel root ;
    private WButton exitButton;
    private WLabel errorLabel;

    public EntityShowerGui(){
        root  = new WGridPanel();
        root.setSize(300,200);

        exitButton = new WButton();
        errorLabel = new WLabel("");
        errorLabel.setColor(0xFF0000).setDarkmodeColor(0x0000FF);

        BiConsumer<String, PlainEntity> configurator = (String s, PlainEntity destination) -> {
            String s1 = "";
            List<String> listStrings= Arrays.asList(s.split(" "));
            s1+=listStrings.get(1);
            for(int i =2;i<listStrings.size();i++){
                s1+=" ";
                s1 += listStrings.get(i);

            }

            destination.label.setText(s1);
            destination.label.setHost(this);
            //destination.label.setSize(160,16);
            destination.cost.setText(new LiteralText(""));
            destination.button.setOnClick(()->{onEntityButtonPressed(s.split(" ")[5]);});

        };

        list = new WListPanel<>(DataAssembler.entityData(MinecraftClient.getInstance()), PlainEntity::new, configurator);
        list.setListItemHeight(18);
        list.setHost(this);
        list.layout();

        setRootPanel(root);
        root.add(list, 0, 2, 12, 10);
        root.add(exitButton,0,0);
        root.add(errorLabel,1,11);

        exitButton.setSize(30,10);
        exitButton.setLabel(new LiteralText("exit"));
        errorLabel.setSize(100,20);


        exitButton.setOnClick(()->this.ExitButtonExit());

    }




    private void ExitButtonExit(){
        MinecraftClient.getInstance().currentScreen.onClose();
        MinecraftClient.getInstance().send(()->MinecraftClient.getInstance().openScreen(new Screen(new MainGui())));
    }
    private void onEntityButtonPressed(String id){
        MinecraftClient.getInstance().currentScreen.onClose();
        MinecraftClient.getInstance().send(()->MinecraftClient.getInstance().openScreen(new Screen(
                new EntityDescriptionGui(Integer.parseInt(id)))));
    }
}
