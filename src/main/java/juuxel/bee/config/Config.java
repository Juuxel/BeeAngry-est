package juuxel.bee.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Lazy;
import net.minecraft.util.Util;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Config {
    private static final Lazy<Config> CONFIG = new Lazy<>(Config::load);

    private Map<String, Boolean> gameRuleDefaults = new HashMap<>();

    private Map<String, Boolean> compat = new HashMap<>();

    public Map<String, Boolean> getGameRuleDefaults() {
        return Collections.unmodifiableMap(gameRuleDefaults);
    }

    public boolean getDefault(String rule, boolean orElse) {
        return gameRuleDefaults.getOrDefault(rule, orElse);
    }

    public boolean getCompatDefault(String rule, boolean orElse) { return compat.getOrDefault(rule, orElse); }

    private static Config createDefault() {
        return Util.make(new Config(), it -> {
           it.gameRuleDefaults.put("saveScoopedBeeNbt", true);
           it.gameRuleDefaults.put("alwaysDropScoopedBees", true);
           it.gameRuleDefaults.put("beesSeekRainShelter", true);
           it.gameRuleDefaults.put("beesExplode", true);

           it.compat.put("lambdaControlsCompatEnabled", true);
        });
    }

    private static Config load() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Path configPath = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("BeeAngry-est.json");

        if (Files.notExists(configPath)) {
            Config config = createDefault();
            try (Writer writer = Files.newBufferedWriter(configPath)) {
                gson.toJson(config, writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return config;
        } else {
            try (Reader reader = Files.newBufferedReader(configPath)) {
                return gson.fromJson(reader, Config.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Config get() {
        return CONFIG.get();
    }
}
