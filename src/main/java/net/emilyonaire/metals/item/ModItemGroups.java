package net.emilyonaire.metals.item;

import net.emilyonaire.metals.Metalfinders;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup DETECTORS_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Metalfinders.MOD_ID, "ruby"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.detectors"))
                    .icon(() -> new ItemStack(ModItems.RUBY)).entries((displayContext, entries) -> {
                        entries.add(ModItems.RUBY);
                        entries.add(ModItems.RAW_RUBY);
                        entries.add(ModItems.BASIC_METAL_DETECTOR);
                        entries.add(ModItems.GOLD_METAL_DETECTOR);
                        entries.add(ModItems.DIAMOND_METAL_DETECTOR);
                        entries.add(ModItems.NETHERITE_METAL_DETECTOR);


                        entries.add(Items.DIAMOND);
                    }).build());

    public static void registerItemGroups() {
        Metalfinders.LOGGER.info("Registrering Item Groups for " + Metalfinders.MOD_ID);
    }
}
