package com.nyan.nyanMod.guis.util;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.WorldChunk;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class DataAssembler {
    public static ArrayList<String> blockData(MinecraftClient client,int distance, int light){
        ArrayList<String> returned = new ArrayList<>();
     Vec3d playerPos = client.player.getPos();
        long prevTime = System.currentTimeMillis();
     for(int x = (int)playerPos.x-distance; x<(int)playerPos.x+distance;x++){
         for(int y = (int)playerPos.y-distance; y<(int)playerPos.y+distance;y++){
             int leftBound = (int)playerPos.z-distance;
             int rightBound = (int)playerPos.z+distance;
             double v = (x - playerPos.x) * (x - playerPos.x);
             double v1 = (y - playerPos.y) * (y - playerPos.y);
             leftBound = (int)playerPos.z-(int)Math.sqrt(
                     distance*distance-v-v1)-1;

             rightBound = (int)playerPos.z+(int)Math.sqrt(
                     distance*distance- v - v1)+1;

             for(int z = leftBound; z<rightBound;z++){

                 if(Math.sqrt((x-playerPos.x)*(x-playerPos.x)
                         +(y-playerPos.y)*(y-playerPos.y)
                         +(z-playerPos.z)*(z-playerPos.z)) <distance) {

                     boolean isSpawnable = client.world.getBlockState(
                             new BlockPos(x,y,z)).allowsSpawning(client.world.getExistingChunk(x/8,z/8),
                             new BlockPos(x,y,z),
                             EntityType.ZOMBIE);
                     boolean isLuminate = MinecraftClient.getInstance().world.getLightLevel(
                             LightType.BLOCK,
                             new BlockPos(x,y+1,z)
                     ) <=light;
                     Block block1 = client.world.getBlockState(new BlockPos(x,y+1,z)).getBlock();
                     Block block2 = client.world.getBlockState(new BlockPos(x,y+2,z)).getBlock();
                     boolean block1IsPlant = (block1 instanceof PlantBlock)||(block1 instanceof AbstractPlantStemBlock)
                             ||(block1 instanceof AbstractPlantPartBlock);
                     boolean block1IsSnow = false;
                     if(block1 instanceof  SnowBlock){
                         block1IsSnow=Integer.parseInt(client.world.getBlockState(new BlockPos(x,y+1,z)).toString().split("=")[1].replaceAll("]",""))<=1;
                     }

//                     boolean block1IsSnow = (block1 instanceof SnowBlock)?
//                             Integer.parseInt(client.world.getBlockState(new BlockPos(x,y+1,z)).toString().split("=")[1]
//                                     .replaceAll("]",""))<=1:false;
                     boolean block2IsPlant = (block2 instanceof PlantBlock)||(block2 instanceof AbstractPlantStemBlock)
                             ||(block2 instanceof AbstractPlantPartBlock);
                     boolean isAirAbove = ((client.world.getBlockState(
                             new BlockPos(x,y+1,z)).isAir()||block1IsPlant||block1IsSnow)
                             &&(client.world.getBlockState(
                                     new BlockPos(x,y+2,z)).isAir()||block2IsPlant));

                     if(isSpawnable && isAirAbove&&isLuminate){

                         returned.add((client.world.getBlockState(new BlockPos(x,y,z)).getBlock().toString().substring(16,client.world.getBlockState(new BlockPos(x,y,z)).getBlock().toString().length()-1)+" "+x+" "+y+" "+z));
                     }

                 }
             }

         }
     }
     return returned;

    }
    public static ArrayList<String> entityData(MinecraftClient client,String filter){
        ArrayList<String> returned = new ArrayList<>();
        for(Entity currEntity: client.world.getEntities()){
            String ans = "";
            ans+=""+client.player.getPos().distanceTo(currEntity.getPos())+" ";
            String type = currEntity.getType().toString().substring(17,currEntity.getType().toString().length());
            if(filter.equals("all")||filter.equals(type)){
            switch(type){
                case("player"):
                    type = ((PlayerEntity)currEntity).getName().toString().split("\'")[1];
                    break;
                case("item"):
                    type = ((ItemEntity)currEntity).toString().split("\'")[1]+((ItemEntity) currEntity).getStack().getCount();
            }
            ans += type+" ";
            ans+=(int)currEntity.getPos().x+" "+(int)currEntity.getPos().y+" "+(int)currEntity.getPos().z+" "+currEntity.getEntityId();
            if(currEntity instanceof  MobEntity){
                ans+=" "+ ((MobEntity) currEntity).getHealth();
            }
            if(currEntity instanceof PlayerEntity){
                ans+=" "+ ((PlayerEntity) currEntity).getHealth();
            }
            returned.add(ans);}
            }


        Collections.sort(returned);
        return  returned;

    }
    public static int compare2String(String o1,String o2,int x,int y,int z){
        int x1 = Integer.parseInt(o1.split(" ")[2]);
        int y1 = Integer.parseInt(o1.split(" ")[3]);
        int z1 = Integer.parseInt(o1.split(" ")[4]);

        int x2 = Integer.parseInt(o2.split(" ")[2]);
        int y2 = Integer.parseInt(o2.split(" ")[3]);
        int z2 = Integer.parseInt(o2.split(" ")[4]);

        return (x1-x)*(x1-x)+(y1-y)*(y1-y)+(z1-z)*(z1-z)-((x2-x)*(x2-x)+(y2-y)*(y2-y)+(z2-z)*(z2-z));

    }
}
