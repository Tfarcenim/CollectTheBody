package tfar.collectthebody.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

public class Datagen {

    public static void gather(GatherDataEvent event) {
        boolean client = event.includeClient();
        DataGenerator dataGenerator = event.getGenerator();
       // PackOutput packOutput = dataGenerator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        dataGenerator.addProvider(client,new ModLangProvider(dataGenerator));
    }

  /*  public static Stream<Block> getKnownBlocks() {
        return getKnown(BuiltInRegistries.BLOCK);
    }
    public static Stream<Item> getKnownItems() {
        return getKnown(BuiltInRegistries.ITEM);
    }

    public static <V> Stream<V> getKnown(Registry<V> registry) {
        return registry.stream().filter(o -> registry.getKey(o).getNamespace().equals(CollectTheBody.MOD_ID));
    }*/
}
