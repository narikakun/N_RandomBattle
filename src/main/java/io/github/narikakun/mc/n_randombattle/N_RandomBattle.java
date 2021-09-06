package io.github.narikakun.mc.n_randombattle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.commons.lang.StringUtils.isEmpty;

public final class N_RandomBattle extends JavaPlugin implements CommandExecutor {

    private int id;
    private int interval;
    private int interval2;

    @Override
    public void onEnable() {
        // Plugin startup logic
        id = -1;
        interval = 30;
        interval2 = 40;
        getLogger().info("プラグインが読み込まれました。");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("プラグインが無効化されました。");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "このコマンドを実行するには、OPが必要です！");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("rb") && args.length!=0) {
            switch (args[0]) {
                case "start":
                    if (id != -1) {
                        sender.sendMessage(ChatColor.RED + "既に開始されているため、このコマンドは実行できません");
                        return true;
                    }
                    Bukkit.broadcastMessage(ChatColor.GOLD + "Random Battle Start!");
                    id = 1;
                    Bukkit.getServer().getScheduler().runTaskLater(this, this::randomTeleport, randomInterval() * 20);
                    break;
                case "stop":
                    if (id == -1) {
                        sender.sendMessage(ChatColor.RED + "開始されていないため、このコマンドは実行できません");
                        return true;
                    }
                    id = -1;
                    Bukkit.broadcastMessage(ChatColor.RED + "Random Battle End.");
                    id = -1;
                    break;
                case "interval":
                    if (isEmpty(args[1])) {
                        sender.sendMessage(ChatColor.RED + "指定方法が正しくありません。/rb interval <間隔1> <間隔2> 又は /rb interval <間隔1>");
                        return true;
                    }
                    interval = Integer.parseInt(args[1]);
                    if (!isEmpty(args[2])) {
                        interval2 = Integer.parseInt(args[2]);
                    } else {
                        interval2 = Integer.parseInt(args[1]);
                    }
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "テレポート間隔を " + ChatColor.WHITE + args[1] + "秒" + ChatColor.YELLOW + "から" + ChatColor.WHITE + args[2] + "秒" + ChatColor.YELLOW + "に変更しました。");
                    break;
                default:
                    sender.sendMessage("/rb start\n/rb stop\n/rb interval <間隔1> <間隔2>");
                    break;
            }
        } else {
            sender.sendMessage("/rb start\n/rb stop\n/rb interval <間隔1> <間隔2>");
        }
        return true;
    }

    private void randomTeleport()
    {
        if (id==-1) return;
        Bukkit.getServer().getScheduler().runTaskLater(this, this::randomTeleport,randomInterval() * 20);
        final List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        final List<Player> target = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(players);
        for (int i = 0; i < Bukkit.getOnlinePlayers().size(); i++) target.get(i).teleport(players.get(i));
    }

    private int randomInterval () {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        int randomNum = tlr.nextInt(interval, interval2 + 1);
        getLogger().info(String.valueOf(randomNum));
        return randomNum;
    }
}
