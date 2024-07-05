package net.emilyonaire.metals.item.custom;

import net.emilyonaire.metals.Metalfinders;
import net.emilyonaire.metals.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import net.minecraft.sound.SoundEvents;

import java.util.ArrayList;
import java.util.List;


public class MetalDetectorItem extends Item {

    List<Block> COMMON = new ArrayList<>();
    List<Block> UNCOMMON = new ArrayList<>();
    List<Block> RARE = new ArrayList<>();
    List<Block> EPIC = new ArrayList<>();
    List<Block> denylist = new ArrayList<>();

    public MetalDetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {


        // Rarity 1 blocks are COMMON
        COMMON.add(Blocks.IRON_ORE);
        COMMON.add(Blocks.DEEPSLATE_IRON_ORE);
        COMMON.add(Blocks.COAL_ORE);
        COMMON.add(Blocks.DEEPSLATE_COAL_ORE);
        COMMON.add(Blocks.COPPER_ORE);
        COMMON.add(Blocks.DEEPSLATE_COPPER_ORE);

        for (Block block : COMMON) {
            Metalfinders.LOGGER.info("Adding block: " + block + "to COMMON");
        }

        // Rarity 2 blocks are UNCOMMON
        UNCOMMON.add(Blocks.LAPIS_ORE);
        UNCOMMON.add(Blocks.DEEPSLATE_LAPIS_ORE);
        UNCOMMON.add(Blocks.REDSTONE_ORE);
        UNCOMMON.add(Blocks.DEEPSLATE_REDSTONE_ORE);
        UNCOMMON.add(Blocks.IRON_BLOCK);
        UNCOMMON.add(Blocks.COAL_BLOCK);
        UNCOMMON.add(Blocks.GILDED_BLACKSTONE);
        for (Block block : UNCOMMON) {
            Metalfinders.LOGGER.info("Adding block: " + block + "to UNCOMMON");
        }

        // Rarity 3 blocks are RARE
        RARE.add(Blocks.GOLD_ORE);
        RARE.add(Blocks.DEEPSLATE_GOLD_ORE);
        RARE.add(Blocks.EMERALD_ORE);
        RARE.add(Blocks.DEEPSLATE_EMERALD_ORE);
        RARE.add(Blocks.DIAMOND_ORE);
        RARE.add(Blocks.DEEPSLATE_DIAMOND_ORE);
        RARE.add(Blocks.LAPIS_BLOCK);
        RARE.add(Blocks.REDSTONE_BLOCK);
        RARE.add(Blocks.GOLD_BLOCK);
        RARE.add(Blocks.WET_SPONGE);

        for (Block block : RARE) {
            Metalfinders.LOGGER.info("Adding block: " + block + "to RARE");
        }

        // Rarity 4 blocks are EPIC
        EPIC.add(Blocks.DIAMOND_BLOCK);
        EPIC.add(Blocks.NETHERITE_BLOCK);
        EPIC.add(Blocks.ANCIENT_DEBRIS);
        EPIC.add(Blocks.EMERALD_BLOCK);

        //denylist
        denylist.add(Blocks.CHEST);
        denylist.add(Blocks.TRAPPED_CHEST);
        denylist.add(Blocks.BARREL);
        denylist.add(Blocks.BEACON);
        denylist.add(Blocks.FURNACE);
        denylist.add(Blocks.HOPPER);
        denylist.add(Blocks.SPONGE);
        denylist.add(Blocks.OBSIDIAN);
        denylist.add(Blocks.CRYING_OBSIDIAN);



        for (Block block : EPIC) {
            Metalfinders.LOGGER.info("Adding block: " + block + "to EPIC");
        }
        Metalfinders.LOGGER.info("---------------ADDED ALL TO LISTS");
        //FUNCTIONALITY!
        if(!context.getWorld().isClient()){ //if on server
            Metalfinders.LOGGER.info("---------------NOT CLIENT");
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;
            int rarityOffset = 8;
            int startPos_y = positionClicked.getY();
            int depthToScanTo = startPos_y;

//            switch (context.getStack().getRarity().toString()) {
//                case "COMMON":
//                    depthToScanTo -= rarityOffset;
//                    break;
//                case "UNCOMMON":
//                    depthToScanTo -= rarityOffset * 2;
//                    break;
//                case "RARE":
//                    depthToScanTo -= rarityOffset * 3;
//                    break;
//                case "EPIC":
//                    depthToScanTo -= rarityOffset * 4;
//                    break;
//            }
            Metalfinders.LOGGER.info("--------context.getStack().getRarity().toString(): " + context.getStack().getRarity().toString());
            if(context.getStack().getRarity().toString() == "COMMON") {
                depthToScanTo = startPos_y - rarityOffset;
            } else if (context.getStack().getRarity().toString() == "UNCOMMON") {
                depthToScanTo = startPos_y - rarityOffset*2;
                
            } else if (context.getStack().getRarity().toString() == "RARE") {
                depthToScanTo = startPos_y - rarityOffset*3;

            } else if (context.getStack().getRarity().toString() == "EPIC") {
                depthToScanTo = startPos_y - rarityOffset*4;

            }

            Metalfinders.LOGGER.info("---------------PASSED item rarity check ");
            Metalfinders.LOGGER.info("---------------startPosY : " + startPos_y);
            Metalfinders.LOGGER.info("---------------depthToScanTo : " + depthToScanTo);

            // Scan from the player's clicked position down to the determined depth
            for (int y = startPos_y; y >= depthToScanTo; y--) {
                Metalfinders.LOGGER.info("---------------inside for loop");
                BlockPos currentPos = new BlockPos(positionClicked.getX(), y, positionClicked.getZ());
                BlockState blockState = context.getWorld().getBlockState(currentPos);

                String detectorRarity = context.getStack().getRarity().toString();
                String foundRarity = compareRarity(detectorRarity, blockState);
                if(foundRarity != "none"){
                    if(foundRarity == "deny"){
                        context.getWorld().playSound(null, context.getPlayer().getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 3f, 1f);
                        break;
                    }

                    Metalfinders.LOGGER.info("compareRarity passed, checking headphones");
                    Block block = blockState.getBlock();
                    //if wearing headphones
                    ItemStack helmetStack = player.getEquippedStack(EquipmentSlot.HEAD);
                    if(detectorRarity != "COMMON"){
                        Metalfinders.LOGGER.info("detector rarity not COMMON check has passed, checking headphones");
                        if(!helmetStack.isEmpty() && helmetStack.isOf(Items.DIAMOND_HELMET)){
                            Metalfinders.LOGGER.info(detectorRarity);
                            if(detectorRarity != "EPIC"){
                                player.sendMessage(Text.literal("Found something!"), true);
                                //play basic ding sound
                                if(!context.getWorld().isClient) {
                                    context.getWorld().playSound(null, context.getPlayer().getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), SoundCategory.PLAYERS, 5f, 1f);
                                }


                                break;
                            } else if (detectorRarity == "EPIC")
                            {
                                player.sendMessage(Text.literal("Found " + block.asItem().getName().getString() + " at " + "(" + currentPos.getX() + ", " + currentPos.getY() + ", " + currentPos.getZ() + ")"), true);
                                player.sendMessage(Text.literal("Found " + block.asItem().getName().getString() + " at " + "(" + currentPos.getX() + ", " + currentPos.getY() + ", " + currentPos.getZ() + ")"), false);


                                //play sound based on rarity of found block
                                switch (foundRarity) {
                                    case "COMMON":
                                        context.getWorld().playSound(null, context.getPlayer().getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), SoundCategory.PLAYERS, 5f, 1f);
                                        player.sendMessage(Text.literal("COMMON RARITY"), false);
                                        break;
                                    case "UNCOMMON":
                                        context.getWorld().playSound(null, context.getPlayer().getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), SoundCategory.PLAYERS, 5f, 1.25f);
                                        player.sendMessage(Text.literal("UN-COMMON RARITY"), false);
                                        break;
                                    case "RARE":
                                        context.getWorld().playSound(null, context.getPlayer().getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), SoundCategory.PLAYERS, 5f, 1.5f);
                                        player.sendMessage(Text.literal("RARE RARITY"), false);
                                        break;
                                    case "EPIC":
                                        context.getWorld().playSound(null, context.getPlayer().getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), SoundCategory.PLAYERS, 5f, 2f);
                                        player.sendMessage(Text.literal("EPIC RARITY"), false);
                                        break;
                                }

                                break;
                            }
                        }
                    } else {
                        Metalfinders.LOGGER.info("detector rarity is COMMON, no need to check headphones");
                        player.sendMessage(Text.literal("Found something!"), true);
                    }

                    break;
                }

            }


        }




        return ActionResult.SUCCESS;
    }


    public String compareRarity(String rarity, BlockState blockState){
        String result = "none";
        Metalfinders.LOGGER.info("compareRarity");

        //if in blocklist, then cancel search
        if(denylist.contains(blockState.getBlock())){result = "deny";Metalfinders.LOGGER.info("YES IN DENYLIST");}

        Metalfinders.LOGGER.info("Comparing " + rarity + " with blockstate.getBlock() " + blockState.getBlock().getName().getString());
        switch (rarity) { // will always have one of these 4 so dont need a default state.
            case "COMMON":
                if(COMMON.contains(blockState.getBlock())){result = "COMMON";Metalfinders.LOGGER.info("YES COMMON");}
                break;
            case "UNCOMMON":
                if(COMMON.contains(blockState.getBlock())){result = "COMMON";Metalfinders.LOGGER.info("YES COMMON");}
                if(UNCOMMON.contains(blockState.getBlock())){result = "EPIP";Metalfinders.LOGGER.info("YES UNCOMMON");}
                break;
            case "RARE":
                if(COMMON.contains(blockState.getBlock())){result = "COMMON";Metalfinders.LOGGER.info("YES COMMON");}
                if(UNCOMMON.contains(blockState.getBlock())){result = "UNCOMMON";Metalfinders.LOGGER.info("YES UNCOMMON");}
                if(RARE.contains(blockState.getBlock())){result = "RARE";Metalfinders.LOGGER.info("YES RARE");}
                break;
            case "EPIC":
                if(COMMON.contains(blockState.getBlock())){result = "COMMON";Metalfinders.LOGGER.info("YES COMMON");}
                if(UNCOMMON.contains(blockState.getBlock())){result = "UNCOMMON";Metalfinders.LOGGER.info("YES UNCOMMON");}
                if(RARE.contains(blockState.getBlock())){result = "RARE";Metalfinders.LOGGER.info("YES RARE");}
                if(EPIC.contains(blockState.getBlock())){result = "EPIC";Metalfinders.LOGGER.info("YES EPIC");}
                break;
        }
        Metalfinders.LOGGER.info(String.valueOf(result));
        return result;
    }
}
