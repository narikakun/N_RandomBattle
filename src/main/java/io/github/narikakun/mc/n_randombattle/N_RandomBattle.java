package io.github.narikakun.mc.n_randombattle;

import org.bukkit.plugin.java.JavaPlugin;

public final class N_RandomBattle extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("プラグインが読み込まれました。");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("プラグインが無効化されました。");
    }
}
