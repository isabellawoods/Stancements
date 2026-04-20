package melonystudios.stancements.data.model;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.custom.DyedWaterBucketItem;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.select.ComponentContents;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static melonystudios.stancements.item.custom.RecordedDiscItem.DISC_LABEL_MAX;
import static melonystudios.stancements.item.custom.RecordedDiscItem.DISC_LABEL_MIN;
import static net.minecraft.client.data.models.BlockModelGenerators.*;
import static net.minecraft.client.data.models.ItemModelGenerators.BLANK_LAYER;

public class STModelProvider extends ModelProvider {
    public STModelProvider(PackOutput output) {
        super(output, Stancements.MOD_ID);
    }

    @Override
    @NotNull
    public String getName() {
        return Stancements.generatorName("Block & Item Models");
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // === BLOCKS ===

        // Decorative
        // Shelves
        this.createShelf(blockModels, STBlocks.OAK_SHELF.get());
        this.createShelf(blockModels, STBlocks.SPRUCE_SHELF.get());
        this.createShelf(blockModels, STBlocks.BIRCH_SHELF.get());
        this.createShelf(blockModels, STBlocks.JUNGLE_SHELF.get());
        this.createShelf(blockModels, STBlocks.ACACIA_SHELF.get());
        this.createShelf(blockModels, STBlocks.DARK_OAK_SHELF.get());
        this.createShelf(blockModels, STBlocks.MANGROVE_SHELF.get());
        this.createShelf(blockModels, STBlocks.CHERRY_SHELF.get());
        this.createShelf(blockModels, STBlocks.BAMBOO_SHELF.get());
        this.createShelf(blockModels, STBlocks.CRIMSON_SHELF.get());
        this.createShelf(blockModels, STBlocks.WARPED_SHELF.get());

        // Crafting table cloths
        this.createCraftingTableCloth(blockModels, STBlocks.WHITE_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.GRAY_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.BLACK_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.BROWN_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.RED_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.ORANGE_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.YELLOW_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.LIME_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.GREEN_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.CYAN_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.BLUE_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.PURPLE_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.MAGENTA_CRAFTING_TABLE_CLOTH.get());
        this.createCraftingTableCloth(blockModels, STBlocks.PINK_CRAFTING_TABLE_CLOTH.get());

        // Functional
        this.createMusicRecorder(blockModels, STBlocks.MUSIC_RECORDER.get());
        this.createLayeredCauldron(blockModels, STBlocks.DYED_WATER_CAULDRON.get(), Identifier.withDefaultNamespace("block/water_still"));
        this.createLayeredCauldron(blockModels, STBlocks.MILK_CAULDRON.get(), Identifier.fromNamespaceAndPath(NeoForgeMod.MOD_ID, "block/milk_still"));
        this.createEmptyCropPot(blockModels, STBlocks.CROP_POT.get());
        this.createFullCropPot(blockModels, STBlocks.WHEAT_CROP_POT.get(), age -> Identifier.withDefaultNamespace("block/wheat_stage" + age), BlockStateProperties.AGE_7, 0, 1, 2, 3, 4, 5, 6, 7);
        this.createFullCropPot(blockModels, STBlocks.CARROT_CROP_POT.get(), age -> Identifier.withDefaultNamespace("block/carrots_stage" + age), BlockStateProperties.AGE_7, 0, 0, 1, 1, 2, 2, 2, 3);
        this.createFullCropPot(blockModels, STBlocks.POTATO_CROP_POT.get(), age -> Identifier.withDefaultNamespace("block/potatoes_stage" + age), BlockStateProperties.AGE_7, 0, 0, 1, 1, 2, 2, 2, 3);
        this.createFullCropPot(blockModels, STBlocks.BEETROOT_CROP_POT.get(), age -> Identifier.withDefaultNamespace("block/beetroots_stage" + age), BlockStateProperties.AGE_3, 0, 1, 2, 3);
        this.createFullCropPot(blockModels, STBlocks.NETHER_WART_CROP_POT.get(), age -> Identifier.withDefaultNamespace("block/nether_wart_stage" + age), BlockStateProperties.AGE_3, 0, 1, 1, 2);

        // Rails
        blockModels.createActiveRail(STBlocks.GILDED_RAIL.get());
        blockModels.createActiveRail(STBlocks.WHITE_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.LIGHT_GRAY_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.GRAY_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.BLACK_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.BROWN_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.RED_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.ORANGE_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.YELLOW_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.LIME_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.GREEN_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.CYAN_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.LIGHT_BLUE_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.BLUE_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.PURPLE_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.MAGENTA_TAGGING_RAIL.get());
        blockModels.createActiveRail(STBlocks.PINK_TAGGING_RAIL.get());

        // === ITEMS ===

        // Decorative blocks
        // Shelves
        blockModels.registerSimpleItemModel(STItems.OAK_SHELF.get(), Stancements.stancements("block/oak_shelf"));
        blockModels.registerSimpleItemModel(STItems.SPRUCE_SHELF.get(), Stancements.stancements("block/spruce_shelf"));
        blockModels.registerSimpleItemModel(STItems.BIRCH_SHELF.get(), Stancements.stancements("block/birch_shelf"));
        blockModels.registerSimpleItemModel(STItems.JUNGLE_SHELF.get(), Stancements.stancements("block/jungle_shelf"));
        blockModels.registerSimpleItemModel(STItems.ACACIA_SHELF.get(), Stancements.stancements("block/acacia_shelf"));
        blockModels.registerSimpleItemModel(STItems.DARK_OAK_SHELF.get(), Stancements.stancements("block/dark_oak_shelf"));
        blockModels.registerSimpleItemModel(STItems.MANGROVE_SHELF.get(), Stancements.stancements("block/mangrove_shelf"));
        blockModels.registerSimpleItemModel(STItems.CHERRY_SHELF.get(), Stancements.stancements("block/cherry_shelf"));
        blockModels.registerSimpleItemModel(STItems.BAMBOO_SHELF.get(), Stancements.stancements("block/bamboo_shelf"));
        blockModels.registerSimpleItemModel(STItems.CRIMSON_SHELF.get(), Stancements.stancements("block/crimson_shelf"));
        blockModels.registerSimpleItemModel(STItems.WARPED_SHELF.get(), Stancements.stancements("block/warped_shelf"));

        // Crafting table cloths
        blockModels.registerSimpleItemModel(STItems.WHITE_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/white_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/light_gray_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.GRAY_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/gray_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.BLACK_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/black_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.BROWN_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/brown_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.RED_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/red_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.ORANGE_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/orange_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.YELLOW_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/yellow_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.LIME_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/lime_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.GREEN_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/green_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.CYAN_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/cyan_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/light_blue_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.BLUE_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/blue_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.PURPLE_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/purple_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.MAGENTA_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/magenta_crafting_table_cloth"));
        blockModels.registerSimpleItemModel(STItems.PINK_CRAFTING_TABLE_CLOTH.get(), Stancements.stancements("block/pink_crafting_table_cloth"));

        // Functional blocks
        blockModels.registerSimpleItemModel(STItems.MUSIC_RECORDER.get(), Stancements.stancements("block/music_recorder"));
        this.generateCropPot(itemModels, STItems.CROP_POT.get(), ModelTemplates.FLAT_ITEM);

        // Items
        itemModels.generateFlatItem(STItems.STANCEMENTS_LOGO.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.VINYL_DISC.get(), ModelTemplates.FLAT_ITEM);
        this.generateRecordedDisc(itemModels, STItems.RECORDED_DISC.get());
        itemModels.generateFlatItem(STItems.WHITE_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.LIGHT_GRAY_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.GRAY_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.BLACK_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.BROWN_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.RED_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.ORANGE_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.YELLOW_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.LIME_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.GREEN_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.CYAN_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.LIGHT_BLUE_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.BLUE_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.PURPLE_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.MAGENTA_TAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(STItems.PINK_TAG.get(), ModelTemplates.FLAT_ITEM);
        this.generateDyedWaterBucket(itemModels, STItems.DYED_WATER_BUCKET.get(), ModelTemplates.TWO_LAYERED_ITEM);
    }

    /// Makes the block states and models for a {@linkplain melonystudios.stancements.block.custom.STShelfBlock *Stancements* shelf block}.
    /// @param blockModels The block model generator that creates all the files.
    /// @param shelf The shelf block.
    public void createShelf(BlockModelGenerators blockModels, Block shelf) {
        Identifier id = STModelTemplates.SHELF.create(shelf, new TextureMapping()
                        .put(STTextureSlots.SHELF, TextureMapping.getBlockTexture(shelf))
                        .put(STTextureSlots.SHELF_SUPPORT, TextureMapping.getBlockTexture(shelf, "_support")),
                blockModels.modelOutput
        );

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(shelf, plainVariant(id)).with(ROTATION_HORIZONTAL_FACING_ALT));
    }

    public void createCraftingTableCloth(BlockModelGenerators blockModels, Block tableCloth) {
        Identifier model = STModelTemplates.CRAFTING_TABLE_CLOTH.create(tableCloth, new TextureMapping()
                        .put(TextureSlot.TOP, TextureMapping.getBlockTexture(tableCloth, "_top"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(tableCloth, "_side")),
                blockModels.modelOutput
        );

        blockModels.blockStateOutput.accept(createSimpleBlock(tableCloth, plainVariant(model)));
    }

    public void createMusicRecorder(BlockModelGenerators blockModels, Block recorder) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(recorder, "_top"))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(recorder, "_bottom"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(recorder, "_side"));
        blockModels.blockStateOutput.accept(createSimpleBlock(recorder, plainVariant(ModelTemplates.CUBE_BOTTOM_TOP.create(recorder, mapping, blockModels.modelOutput))));
    }

    public void createLayeredCauldron(BlockModelGenerators blockModels, Block cauldron, Identifier contents) {
        Material contentsMaterial = new Material(contents);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(cauldron)
                .with(PropertyDispatch.initial(LayeredCauldronBlock.LEVEL)
                        .select(1, plainVariant(ModelTemplates.CAULDRON_LEVEL1.createWithSuffix(cauldron, "_level1", TextureMapping.cauldron(contentsMaterial), blockModels.modelOutput)))
                        .select(2, plainVariant(ModelTemplates.CAULDRON_LEVEL2.createWithSuffix(cauldron, "_level2", TextureMapping.cauldron(contentsMaterial), blockModels.modelOutput)))
                        .select(3, plainVariant(ModelTemplates.CAULDRON_FULL.createWithSuffix(cauldron, "_full", TextureMapping.cauldron(contentsMaterial), blockModels.modelOutput))))
        );
    }

    public void createEmptyCropPot(BlockModelGenerators blockModels, Block cropPot) {
        Identifier id = ModelLocationUtils.getModelLocation(cropPot);
        Identifier regular = STModelTemplates.CROP_POT_EMPTY.create(id, new TextureMapping()
                        .put(STTextureSlots.POT, TextureMapping.getBlockTexture(cropPot)),
                blockModels.modelOutput
        );
        Identifier hoppingID = BuiltInRegistries.BLOCK.getKey(cropPot).withPath(path -> "block/hopping_" + path);
        Identifier hopping = STModelTemplates.CROP_POT_EMPTY.create(hoppingID, new TextureMapping()
                        .put(STTextureSlots.POT, new Material(hoppingID)),
                blockModels.modelOutput
        );

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(cropPot).with(
                createBooleanModelDispatch(STBlockStateProperties.HOPPING, plainVariant(hopping), plainVariant(regular)))
        );
    }

    // código belíssimo graças a Deus ~isa 20-04-26
    public void createFullCropPot(BlockModelGenerators blockModels, Block cropPot, Function<Integer, Identifier> crop, Property<Integer> property, int... stages) {
        if (property.getPossibleValues().size() != stages.length) throw new IllegalArgumentException();

        Identifier id = BuiltInRegistries.BLOCK.getKey(cropPot);
        Material regularModel = TextureMapping.getBlockTexture(STBlocks.CROP_POT.get());
        Material hoppingModel = new Material(BuiltInRegistries.BLOCK.getKey(STBlocks.CROP_POT.get())
                .withPath(path -> "block/hopping_" + path));

        record PotModel(int age, boolean hopping) {}
        Map<PotModel, Identifier> models = new HashMap<>();

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(cropPot)
                .with(PropertyDispatch.initial(property, STBlockStateProperties.HOPPING).generate((stageS, hopping) -> {
                    int age = stages[stageS];
                    String name = hopping ? "hopping_" : "";

                    return plainVariant(models.computeIfAbsent(new PotModel(age, hopping), potModel -> {
                        Identifier texture = id.withPath(path -> "block/" + name + path + "_stage" + potModel.age());
                        return STModelTemplates.CROP_POT_FULL.create(
                                texture,
                                new TextureMapping().put(STTextureSlots.POT, hopping ? hoppingModel : regularModel).put(TextureSlot.CROP, new Material(crop.apply(potModel.age()))),
                                blockModels.modelOutput
                        );
                    }));
                }))
        );
    }

    public void generateCropPot(ItemModelGenerators itemModels, Item item, ModelTemplate template) {
        Identifier id = BuiltInRegistries.ITEM.getKey(item);
        Identifier regular = ModelLocationUtils.getModelLocation(item);
        Identifier hopping = id.withPath(path -> "item/hopping_" + path);
        template.create(regular, new TextureMapping().put(TextureSlot.LAYER0, new Material(regular)), itemModels.modelOutput);
        template.create(hopping, new TextureMapping().put(TextureSlot.LAYER0, new Material(hopping)), itemModels.modelOutput);

        itemModels.itemModelOutput.accept(item, ItemModelUtils.selectBlockItemProperty(
                STBlockStateProperties.HOPPING,
                ItemModelUtils.plainModel(regular),
                Map.of(true, ItemModelUtils.plainModel(hopping))
        ));
    }

    public void generateRecordedDisc(ItemModelGenerators itemModels, Item item) {
        // generate all item models for each label (+ the fallback model)
        Identifier baseID = ModelLocationUtils.getModelLocation(item);
        ModelTemplates.TWO_LAYERED_ITEM.create(baseID, TextureMapping.layered(
                TextureMapping.getItemTexture(item),
                new Material(ModelLocationUtils.getModelLocation(item, "_label_1"))
        ), itemModels.modelOutput);

        for (int i = DISC_LABEL_MIN; i <= DISC_LABEL_MAX; ++i) {
            Identifier id = ModelLocationUtils.getModelLocation(item, "_label_" + i);
            ModelTemplates.TWO_LAYERED_ITEM.create(id, TextureMapping.layered(TextureMapping.getItemTexture(item), new Material(id)), itemModels.modelOutput);
        }

        // generate the main recorded disc model
        itemModels.itemModelOutput.accept(item, ItemModelUtils.select(
                new ComponentContents<>(STDataComponents.LABEL.get()),
                ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item)),
                ItemModelUtils.when(1F, this.recordedDiscModel(item, 1)),
                ItemModelUtils.when(2F, this.recordedDiscModel(item, 2)),
                ItemModelUtils.when(3F, this.recordedDiscModel(item, 3)),
                ItemModelUtils.when(4F, this.recordedDiscModel(item, 4)),
                ItemModelUtils.when(5F, this.recordedDiscModel(item, 5)),
                ItemModelUtils.when(6F, this.recordedDiscModel(item, 6)),
                ItemModelUtils.when(7F, this.recordedDiscModel(item, 7)),
                ItemModelUtils.when(8F, this.recordedDiscModel(item, 8)),
                ItemModelUtils.when(9F, this.recordedDiscModel(item, 9)),
                ItemModelUtils.when(10F, this.recordedDiscModel(item, 10)),
                ItemModelUtils.when(11F, this.recordedDiscModel(item, 11)),
                ItemModelUtils.when(12F, this.recordedDiscModel(item, 12)),
                ItemModelUtils.when(13F, this.recordedDiscModel(item, 13))
        ));
    }

    private ItemModel.Unbaked recordedDiscModel(Item item, int label) {
        return ItemModelUtils.tintedModel(ModelLocationUtils.getModelLocation(item, "_label_" + label), new Constant(-1), new Dye(0xFFFFFF));
    }

    public void generateDyedWaterBucket(ItemModelGenerators itemModels, Item item, ModelTemplate template) {
        Identifier id = ModelLocationUtils.getModelLocation(item);
        template.create(id, TextureMapping.layered(TextureMapping.getItemTexture(item, "_overlay"), TextureMapping.getItemTexture(item)), itemModels.modelOutput);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.tintedModel(id, new Dye(DyedWaterBucketItem.DEFAULT_WATER_COLOR), BLANK_LAYER));
    }
}
