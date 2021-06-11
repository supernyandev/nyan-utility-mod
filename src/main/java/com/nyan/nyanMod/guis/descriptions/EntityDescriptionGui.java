package com.nyan.nyanMod.guis.descriptions;

import com.nyan.nyanMod.guis.Screen;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;

public class EntityDescriptionGui extends LightweightGuiDescription {
        WLabel entityName;
        WDynamicLabel entityCoords;
        WLabel entityHealth;
        WDynamicLabel entityHealthD;
        WGridPanel root;
        WButton exitButton;
    public EntityDescriptionGui(int id){
        root  = new WGridPanel();
        root.setSize(300,200);
        exitButton = new WButton();
        this.setRootPanel(root);
        Entity currEntity = MinecraftClient.getInstance().world.getEntityById(id);
        System.out.println(id);
        String type =currEntity.getType().toString().substring(17);
        String eName = type;
        entityHealth = new WLabel("null");
        switch(type){
            case("player"):
                eName = ((PlayerEntity)currEntity).getName().toString().split("\'")[1];
                entityHealth.setText(new LiteralText(""));
                entityHealthD = new WDynamicLabel(()->"Health: "+(((PlayerEntity) currEntity).getHealth()));
                root.add(entityHealthD,0,6);
                break;
            case("item"):
                    eName = ((ItemEntity)currEntity).toString().split("\'")[1];
                    eName+=((ItemEntity) currEntity).getStack().getCount();
                    break;


        }
        entityName = new WLabel("Name: "+eName);
        entityCoords = new WDynamicLabel(()-> I18n.translate(
                "Coordinates: "+(int)currEntity.getPos().x+" "+(int)currEntity.getPos().y+" "+(int)currEntity.getPos().z));


        //entityCoords.setText("Coordinates: "+(int)currEntity1.getPos().x+" "+(int)currEntity1.getPos().y+" "+(int)currEntity1.getPos().z);

        if(currEntity instanceof MobEntity ){
            entityHealth.setText(new LiteralText(""));
            entityHealthD = new WDynamicLabel(()->"Health: "+(((MobEntity) currEntity).getHealth()));
            root.add(entityHealthD,0,6);

        }
        root.add(exitButton,0,0);
        root.add(entityName,0,2);
        root.add(entityCoords,0,4);
        root.add(entityHealth,0,6);

        entityCoords.setHost(this);
        entityCoords.setSize(200,18);

        exitButton.setSize(30,10);
        exitButton.setLabel(new LiteralText("exit"));
        exitButton.setOnClick(this::ExitButtonExit);
    }
    private void ExitButtonExit(){
        MinecraftClient.getInstance().currentScreen.onClose();
        MinecraftClient.getInstance().send(()->MinecraftClient.getInstance().openScreen(new Screen(new EntityShowerGui())));
    }
}
