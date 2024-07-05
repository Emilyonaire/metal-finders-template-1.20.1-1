package net.emilyonaire.metals.item;

import net.emilyonaire.metals.Metalfinders;
import net.emilyonaire.metals.item.custom.MetalDetectorItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static net.minecraft.item.Items.register;

public class ModItems {
    public static final Item RUBY = registerItem("ruby", new Item(new FabricItemSettings()));
    public static final Item RAW_RUBY = registerItem("raw_ruby", new Item(new FabricItemSettings()));

    public static final Item BASIC_METAL_DETECTOR = registerItem("basic_metal_detector", new MetalDetectorItem(new Item.Settings().maxDamage(64).rarity(Rarity.COMMON)));
    public static final Item GOLD_METAL_DETECTOR = registerItem("gold_metal_detector", new MetalDetectorItem(new Item.Settings().maxDamage(64).rarity(Rarity.UNCOMMON)));
    public static final Item DIAMOND_METAL_DETECTOR = registerItem("diamond_metal_detector", new MetalDetectorItem(new Item.Settings().maxDamage(64).rarity(Rarity.RARE)));
    public static final Item NETHERITE_METAL_DETECTOR = registerItem("netherite_metal_detector", new MetalDetectorItem(new Item.Settings().maxDamage(64).rarity(Rarity.EPIC)));





    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Metalfinders.MOD_ID, name), item);
    }

    public static void registerModItems(){
        Metalfinders.LOGGER.info("Registering Mod Items for " + Metalfinders.MOD_ID);
    }

}
