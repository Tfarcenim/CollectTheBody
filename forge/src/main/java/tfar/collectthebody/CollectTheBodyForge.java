package tfar.collectthebody;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.commons.lang3.tuple.Pair;
import tfar.collectthebody.client.CollectTheBodyClientForge;
import tfar.collectthebody.data.Datagen;
import tfar.collectthebody.network.PacketHandlerForge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod(CollectTheBody.MOD_ID)
public class CollectTheBodyForge {
    
    public CollectTheBodyForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        //CollectTheBody.LOG.info("Hello Forge world!");
        IEventBus bus  = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::register);
        bus.addListener(this::commonSetup);
        bus.addListener(Datagen::gather);
        MinecraftForge.EVENT_BUS.addListener(this::login);
        MinecraftForge.EVENT_BUS.addListener(this::playerTick);
        MinecraftForge.EVENT_BUS.addListener(this::eyeHeight);
        if (FMLEnvironment.dist.isClient()) {
            bus.addListener(this::clientSetup);
        }
        CollectTheBody.init();
    }

    public void eyeHeight(EntityEvent.Size event) {
        if (event.getEntity() instanceof Player player) {
            float heightOffset = CollectTheBody.getPlayerHeightOffset(player);
            if (heightOffset != 0) {
                //event.setNewEyeHeight(heightOffset * .9f);
               // event.setNewEyeHeight(event.getOldEyeHeight() + heightOffset);
                event.setNewSize( EntityDimensions.scalable(0.6F,1.8f + heightOffset),false);
            }
        }
    }

    private void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            CollectTheBody.playerTick((ServerPlayer) event.player);
        }
    }

    private void login(PlayerEvent.PlayerLoggedInEvent event) {
        CollectTheBody.serverLogin(event.getEntity());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        registerLater.clear();
        PacketHandlerForge.registerMessages();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        CollectTheBodyClientForge.clientSetup();
    }

    public static Map<Registry<?>, List<Pair<ResourceLocation, Supplier<?>>>> registerLater = new HashMap<>();
    private void register(RegisterEvent e) {
        for (Map.Entry<Registry<?>,List<Pair<ResourceLocation, Supplier<?>>>> entry : registerLater.entrySet()) {
            Registry<?> registry = entry.getKey();
            List<Pair<ResourceLocation, Supplier<?>>> toRegister = entry.getValue();
            for (Pair<ResourceLocation,Supplier<?>> pair : toRegister) {
                e.register((ResourceKey<? extends Registry<Object>>)registry.key(),pair.getLeft(),(Supplier<Object>)pair.getValue());
            }
        }
    }
}