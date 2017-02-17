package eyeq.moremeat;

import eyeq.moremeat.event.MoreMeatEventHandler;
import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.renderer.ULanguageCreator;
import eyeq.util.client.renderer.ULanguageResourceManager;
import eyeq.util.oredict.CategoryTypes;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

import static eyeq.moremeat.MoreMeat.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class MoreMeat {
    public static final String MOD_ID = "eyeq_moremeat";

    @Mod.Instance(MOD_ID)
    public static MoreMeat instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static boolean isDropBat;
    public static boolean isDropBear;
    public static boolean isDropHorse;
    public static boolean isDropHuman;
    public static boolean isDropLlama;
    public static boolean isDropOcelot;
    public static boolean isDropSquid;
    public static boolean isDropWolf;

    public static Item batRaw;
    public static Item batCooked;
    public static Item bearRaw;
    public static Item bearCooked;
    public static Item horseRaw;
    public static Item horseCooked;
    public static Item humanFlesh;
    public static Item humanCooked;
    public static Item llamaRaw;
    public static Item llamaCooked;
    public static Item ocelotRaw;
    public static Item ocelotCooked;
    public static Item squidRaw;
    public static Item squidCooked;
    public static Item wolfRaw;
    public static Item wolfCooked;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new MoreMeatEventHandler());
        load(new Configuration(event.getSuggestedConfigurationFile()));
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    public static void load(Configuration config) {
        config.load();

        String category = "Boolean";
        isDropBat = config.get(category, "isDropBat", true).getBoolean(false);
        isDropBear = config.get(category, "isDropBear", true).getBoolean(false);
        isDropHorse = config.get(category, "isDropHorse", true).getBoolean(false);
        isDropHuman = config.get(category, "isDropHuman", true).getBoolean(false);
        isDropLlama = config.get(category, "isDropLlama", true).getBoolean(false);
        isDropOcelot = config.get(category, "isDropOcelot", true).getBoolean(false);
        isDropSquid = config.get(category, "isDropSquid", true).getBoolean(false);
        isDropWolf = config.get(category, "isDropWolf", true).getBoolean(false);

        if(config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event){
        batRaw = new ItemFood(1, 0.1F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F).setUnlocalizedName("batRaw");
        batCooked = new ItemFood(3, 0.4F, true).setUnlocalizedName("batCooked");
        bearRaw = new ItemFood(3, 0.3F, true).setPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600, 0), 0.3F).setUnlocalizedName("bearRaw");
        bearCooked = new ItemFood(8, 0.8F, true).setPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600, 0), 0.3F).setUnlocalizedName("bearCooked");
        horseRaw = new ItemFood(3, 0.3F, true).setUnlocalizedName("horseRaw");
        horseCooked = new ItemFood(8, 0.6F, true).setUnlocalizedName("horseCooked");
        humanFlesh = new ItemFood(3, 0.3F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.6F).setUnlocalizedName("humanFlesh");
        humanCooked = new ItemFood(8, 0.8F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.1F).setUnlocalizedName("humanCooked");
        llamaRaw = new ItemFood(2, 0.3F, true).setUnlocalizedName("llamaRaw");
        llamaCooked = new ItemFood(4, 0.6F, true).setUnlocalizedName("llamaCooked");
        ocelotRaw = new ItemFood(2, 0.3F, true).setUnlocalizedName("ocelotRaw");
        ocelotCooked = new ItemFood(6, 0.7F, true).setUnlocalizedName("ocelotCooked");
        squidRaw = new ItemFood(2, 0.2F, true).setUnlocalizedName("squidRaw");
        squidCooked = new ItemFood(6, 0.6F, true).setUnlocalizedName("squidCooked");
        wolfRaw = new ItemFood(3, 0.3F, true).setUnlocalizedName("wolfRaw");
        wolfCooked = new ItemFood(7, 0.6F, true).setUnlocalizedName("wolfCooked");

        GameRegistry.register(batRaw, resource.createResourceLocation("raw_bat"));
        GameRegistry.register(batCooked, resource.createResourceLocation("cooked_bat"));
        GameRegistry.register(bearRaw, resource.createResourceLocation("raw_bear"));
        GameRegistry.register(bearCooked, resource.createResourceLocation("cooked_bear"));
        GameRegistry.register(horseRaw, resource.createResourceLocation("raw_horse"));
        GameRegistry.register(horseCooked, resource.createResourceLocation("cooked_horse"));
        GameRegistry.register(humanFlesh, resource.createResourceLocation("human_flesh"));
        GameRegistry.register(humanCooked, resource.createResourceLocation("cooked_human"));
        GameRegistry.register(llamaRaw, resource.createResourceLocation("raw_llama"));
        GameRegistry.register(llamaCooked, resource.createResourceLocation("cooked_llama"));
        GameRegistry.register(ocelotRaw, resource.createResourceLocation("raw_ocelot"));
        GameRegistry.register(ocelotCooked, resource.createResourceLocation("cooked_ocelot"));
        GameRegistry.register(squidRaw, resource.createResourceLocation("raw_squid"));
        GameRegistry.register(squidCooked, resource.createResourceLocation("cooked_squid"));
        GameRegistry.register(wolfRaw, resource.createResourceLocation("raw_wolf"));
        GameRegistry.register(wolfCooked, resource.createResourceLocation("cooked_wolf"));

        UOreDictionary.registerOre(CategoryTypes.COOKED_MEAT, "bat", batCooked);
        UOreDictionary.registerOre(CategoryTypes.COOKED_MEAT, "bear", bearCooked);
        UOreDictionary.registerOre(CategoryTypes.COOKED_MEAT, "horse", horseCooked);
        UOreDictionary.registerOre(CategoryTypes.COOKED_MEAT, "human", humanCooked);
        UOreDictionary.registerOre(CategoryTypes.COOKED_MEAT, "llama", llamaCooked);
        UOreDictionary.registerOre(CategoryTypes.COOKED_MEAT, "ocelot", ocelotCooked);
        UOreDictionary.registerOre(CategoryTypes.COOKED_MEAT, "squid", squidCooked);
        UOreDictionary.registerOre(CategoryTypes.COOKED_MEAT, "wolf", wolfCooked);

        UOreDictionary.registerOre(CategoryTypes.PREFIX_MEAT, "bat", batRaw);
        UOreDictionary.registerOre(CategoryTypes.PREFIX_MEAT, "bear", bearRaw);
        UOreDictionary.registerOre(CategoryTypes.PREFIX_MEAT, "horse", horseRaw);
        UOreDictionary.registerOre(CategoryTypes.PREFIX_MEAT, "human", humanFlesh);
        UOreDictionary.registerOre(CategoryTypes.COOKED_MEAT, "llama", llamaRaw);
        UOreDictionary.registerOre(CategoryTypes.PREFIX_MEAT, "ocelot", ocelotRaw);
        UOreDictionary.registerOre(CategoryTypes.PREFIX_MEAT, "squid", squidRaw);
        UOreDictionary.registerOre(CategoryTypes.PREFIX_MEAT, "wolf", wolfRaw);
    }

    public static void addRecipes() {
        GameRegistry.addSmelting(batRaw, new ItemStack(batCooked), 0.35F);
        GameRegistry.addSmelting(bearRaw, new ItemStack(bearCooked), 0.35F);
        GameRegistry.addSmelting(horseRaw, new ItemStack(horseCooked), 0.35F);
        GameRegistry.addSmelting(humanFlesh, new ItemStack(humanCooked), 0.35F);
        GameRegistry.addSmelting(llamaRaw, new ItemStack(llamaCooked), 0.35F);
        GameRegistry.addSmelting(ocelotRaw, new ItemStack(ocelotCooked), 0.35F);
        GameRegistry.addSmelting(squidRaw, new ItemStack(squidCooked), 0.35F);
        GameRegistry.addSmelting(wolfRaw, new ItemStack(wolfCooked), 0.35F);
    }

	@SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(batRaw);
        UModelLoader.setCustomModelResourceLocation(batCooked);
        UModelLoader.setCustomModelResourceLocation(bearRaw);
        UModelLoader.setCustomModelResourceLocation(bearCooked);
        UModelLoader.setCustomModelResourceLocation(horseRaw);
        UModelLoader.setCustomModelResourceLocation(horseCooked);
        UModelLoader.setCustomModelResourceLocation(humanFlesh);
        UModelLoader.setCustomModelResourceLocation(humanCooked);
        UModelLoader.setCustomModelResourceLocation(llamaRaw);
        UModelLoader.setCustomModelResourceLocation(llamaCooked);
        UModelLoader.setCustomModelResourceLocation(ocelotRaw);
        UModelLoader.setCustomModelResourceLocation(ocelotCooked);
        UModelLoader.setCustomModelResourceLocation(squidRaw);
        UModelLoader.setCustomModelResourceLocation(squidCooked);
        UModelLoader.setCustomModelResourceLocation(wolfRaw);
        UModelLoader.setCustomModelResourceLocation(wolfCooked);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-MoreMeat");

        ULanguageResourceManager language = new ULanguageResourceManager();

        language.register(ULanguageResourceManager.EN_US, batRaw, "Raw Bat Meat");
        language.register(ULanguageResourceManager.JA_JP, batRaw, "生のコウモリ肉");
        language.register(ULanguageResourceManager.EN_US, batCooked, "Cooked Bat Meat");
        language.register(ULanguageResourceManager.JA_JP, batCooked, "焼いたコウモリ肉");

        language.register(ULanguageResourceManager.EN_US, bearRaw, "Raw Bear Meat");
        language.register(ULanguageResourceManager.JA_JP, bearRaw, "生の熊肉");
        language.register(ULanguageResourceManager.EN_US, bearCooked, "Cooked Bear Meat");
        language.register(ULanguageResourceManager.JA_JP, bearCooked, "焼いた熊肉");

        language.register(ULanguageResourceManager.EN_US, horseRaw, "Raw Horse Meat");
        language.register(ULanguageResourceManager.JA_JP, horseRaw, "生の馬肉");
        language.register(ULanguageResourceManager.EN_US, horseCooked, "Cooked Horse Meat");
        language.register(ULanguageResourceManager.JA_JP, horseCooked, "焼いた馬肉");

        language.register(ULanguageResourceManager.EN_US, humanFlesh, "Flesh Human");
        language.register(ULanguageResourceManager.JA_JP, humanFlesh, "生の人肉");
        language.register(ULanguageResourceManager.EN_US, humanCooked, "Cooked Human");
        language.register(ULanguageResourceManager.JA_JP, humanCooked, "焼いた人肉");

        language.register(ULanguageResourceManager.EN_US, llamaRaw, "Raw Llama Meat");
        language.register(ULanguageResourceManager.JA_JP, llamaRaw, "生のラマ肉");
        language.register(ULanguageResourceManager.EN_US, llamaCooked, "Cooked Llama Meat");
        language.register(ULanguageResourceManager.JA_JP, llamaCooked, "焼いたラマ肉");

        language.register(ULanguageResourceManager.EN_US, ocelotRaw, "Raw Ocelot Meat");
        language.register(ULanguageResourceManager.JA_JP, ocelotRaw, "生のヤマネコ肉");
        language.register(ULanguageResourceManager.EN_US, ocelotCooked, "Cooked Ocelot Meat");
        language.register(ULanguageResourceManager.JA_JP, ocelotCooked, "焼いたヤマネコ肉");

        language.register(ULanguageResourceManager.EN_US, squidRaw, "Raw Squid");
        language.register(ULanguageResourceManager.JA_JP, squidRaw, "生イカ");
        language.register(ULanguageResourceManager.EN_US, squidCooked, "Cooked Squid");
        language.register(ULanguageResourceManager.JA_JP, squidCooked, "焼きイカ");

        language.register(ULanguageResourceManager.EN_US, wolfRaw, "Raw Wolf Meat");
        language.register(ULanguageResourceManager.JA_JP, wolfRaw, "生のオオカミ肉");
        language.register(ULanguageResourceManager.EN_US, wolfCooked, "Cooked Wolf Meat");
        language.register(ULanguageResourceManager.JA_JP, wolfCooked, "焼いたオオカミ肉");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, batRaw, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, batCooked, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, bearRaw, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, bearCooked, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, horseRaw, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, horseCooked, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, humanFlesh, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, humanCooked, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, llamaRaw, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, llamaCooked, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, ocelotRaw, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, ocelotCooked, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, squidRaw, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, squidCooked, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, wolfRaw, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, wolfCooked, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
    }
}
