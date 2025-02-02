package org.kingdoms.services;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class ServiceVault implements Service {
    private static Economy getEconomy() {
        RegisteredServiceProvider<Economy> service = Bukkit.getServicesManager().getRegistration(Economy.class);
        return service == null ? null : service.getProvider();
    }

    private static Permission getPermission() {
        RegisteredServiceProvider<Permission> service = Bukkit.getServicesManager().getRegistration(Permission.class);
        return service == null ? null : service.getProvider();
    }

    private static Chat getChat() {
        RegisteredServiceProvider<Chat> service = Bukkit.getServicesManager().getRegistration(Chat.class);
        return service == null ? null : service.getProvider();
    }

    public static boolean isAvailable(Component component) {
        switch (component) {
            case ECO:
                return getEconomy() != null;
            case CHAT:
                return getChat() != null;
            case PERM:
                return getPermission() != null;
            default:
                throw new AssertionError("Unknown Vault component: " + component);
        }
    }

    public enum Component {ECO, CHAT, PERM;}

    public static double getMoney(OfflinePlayer player) {
        return getEconomy() == null ? 0 : getEconomy().getBalance(player);
    }

    public static void deposit(OfflinePlayer player, double amount) {
        getEconomy().depositPlayer(player, amount);
    }

    public static boolean hasMoney(OfflinePlayer player, double amount) {
        return getEconomy() != null && getEconomy().has(player, amount);
    }

    public static String getDisplayName(Player player) {
        if (getChat() == null) return player.getDisplayName();
        String prefix = getChat().getPlayerPrefix(player);
        String suffix = getChat().getPlayerSuffix(player);
        return prefix + player.getName() + suffix;
    }

    public static void withdraw(OfflinePlayer player, double amount) {
        getEconomy().withdrawPlayer(player, amount);
    }

    public static String getGroup(Player player) {
        if (getPermission() == null) return "default";
        return getPermission().hasGroupSupport() ? getPermission().getPrimaryGroup(player) : "default";
    }
}
