package tfar.collectthebody;

import net.minecraftforge.fml.common.Mod;

@Mod(CollectTheBody.MOD_ID)
public class CollectTheBodyForge {
    
    public CollectTheBodyForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        CollectTheBody.LOG.info("Hello Forge world!");
        CollectTheBody.init();
        
    }
}