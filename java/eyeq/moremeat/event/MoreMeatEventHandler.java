package eyeq.moremeat.event;

import eyeq.moremeat.MoreMeat;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.*;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MoreMeatEventHandler {
    private static class DropData {
        final Item rawMeat;
        final Item cookedMeat;
        final int maxDrop;
        final boolean isLooting;

        DropData(Item rawMeat, Item cookedMeat) {
            this(rawMeat, cookedMeat, 1);
        }

        DropData(Item rawMeat, Item cookedMeat, int maxDrop) {
            this.rawMeat = rawMeat;
            this.cookedMeat = cookedMeat;
            this.maxDrop = maxDrop;
            this.isLooting = maxDrop > 1;
        }
    }

    private static final Map<Class, DropData> dropDataMap = new HashMap<>();

    static {
        dropDataMap.put(EntityBat.class, new DropData(MoreMeat.batRaw, MoreMeat.batCooked));
        dropDataMap.put(EntitySquid.class, new DropData(MoreMeat.squidRaw, MoreMeat.squidCooked));

        dropDataMap.put(EntityVillager.class, new DropData(MoreMeat.humanFlesh, MoreMeat.humanCooked, 3));

        dropDataMap.put(EntityHorse.class, new DropData(MoreMeat.horseRaw, MoreMeat.horseCooked, 3));
        dropDataMap.put(EntityLlama.class, new DropData(MoreMeat.llamaRaw, MoreMeat.llamaCooked, 3));
        dropDataMap.put(EntityPolarBear.class, new DropData(MoreMeat.bearRaw, MoreMeat.bearCooked, 3));

        dropDataMap.put(EntityOcelot.class, new DropData(MoreMeat.ocelotRaw, MoreMeat.ocelotCooked, 2));
        dropDataMap.put(EntityWolf.class, new DropData(MoreMeat.wolfRaw, MoreMeat.wolfCooked, 2));
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if(entity == null) {
            return;
        }
        if(entity instanceof EntityAgeable && entity.isChild()) {
            return;
        }
        Class entityClass = entity.getClass();
        for(Class aClass : dropDataMap.keySet()) {
            if(aClass.isAssignableFrom(entityClass)) {
                DropData drop = dropDataMap.get(aClass);
                Item item = entity.isBurning() ? drop.cookedMeat : drop.rawMeat;
                Random rand = entity.getRNG();
                int num;
                if(drop.isLooting) {
                    num = rand.nextInt(drop.maxDrop) + rand.nextInt(1 + event.getLootingLevel());
                } else {
                    num = drop.maxDrop;
                }
                for(int i = 0; i < num; i++) {
                    entity.dropItem(item, 1);
                }
                break;
            }
        }
    }
}
