package com.nyan.nyanMod;

import com.mojang.brigadier.context.CommandContext;
import com.nyan.nyanMod.guis.Screen;
import com.nyan.nyanMod.guis.descriptions.MainGui;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;

public class Mod implements ModInitializer {

    @Override
    public void onInitialize() {


        ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("fuck").executes(Mod::cmdReload));

    }

    private static int cmdReload(CommandContext<FabricClientCommandSource> ctx)
    {

        MinecraftClient.getInstance().currentScreen.onClose();
        MinecraftClient.getInstance().send(()->MinecraftClient.getInstance().openScreen(new Screen(new MainGui())));
        return 1;
    }

}
