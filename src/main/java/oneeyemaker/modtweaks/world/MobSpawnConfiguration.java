package oneeyemaker.modtweaks.world;

import java.io.File;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;

public class MobSpawnConfiguration
{
    private boolean EnableMobSpawnChancesOverride = true;
    private Configuration Configuration;
    public MobSpawnConfiguration(File modConfigurationDirectory)
    {
        this.Configuration = new Configuration(new File(modConfigurationDirectory, "MobSpawnConfiguration.cfg"));
        this.Configuration.load();
        this.Configuration.setCategoryRequiresMcRestart("Mob spawn chances", true);
        this.Configuration.setCategoryComment("Mob spawn chances", "Multiplier for spawn chance for each mob.");
    }
    public void initialize()
    {
        this.EnableMobSpawnChancesOverride = this.Configuration.get("General", "Enable override of mob spawn chances",
                                                                    this.EnableMobSpawnChancesOverride,
                                                                    "If you have another mod, that overrides mob " +
                                                                    "spawn chances, you should disable this option to" +
                                                                    " prevent confilicts.")
                                                               .getBoolean(this.EnableMobSpawnChancesOverride);
    }
    public void postInitialize()
    {
        if (!this.EnableMobSpawnChancesOverride)
        {
            return;
        }
        for (BiomeGenBase biomeGenBase : BiomeGenBase.getBiomeGenArray())
        {
            if (biomeGenBase == null)
            {
                continue;
            }
            for (EnumCreatureType creatureType : EnumCreatureType.values())
            {
                this.loadConfigurationEntries(biomeGenBase, creatureType);
            }
        }
        if (this.Configuration.hasChanged())
        {
            this.Configuration.save();
        }
    }
    private void loadConfigurationEntries(BiomeGenBase biomeGenBase, EnumCreatureType creatureType)
    {
        for (Object object : biomeGenBase.getSpawnableList(creatureType))
        {
            if (object instanceof BiomeGenBase.SpawnListEntry)
            {
                BiomeGenBase.SpawnListEntry spawnListEntry = (BiomeGenBase.SpawnListEntry) object;
                spawnListEntry.itemWeight *= this.Configuration.get("Mob spawn chances",
                                                                    (String) EntityList.classToStringMapping.get(
                                                                        spawnListEntry.entityClass), 1).getInt(1);
            }
        }
    }
}
