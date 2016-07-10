package net.imaginecraft.plugin.utils;

import net.imaginecraft.plugin.Player;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {
    private static final String WELCOME_PATH = ConfigUtils.MESSAGE_PATH + ".welcome";
    private static final String QUIT_PATH = ConfigUtils.MESSAGE_PATH + ".quit";
    private static final String FIRST_TIME_WELCOME_PATH = ConfigUtils.MESSAGE_PATH + ".first-time-welcome";
    private static final String PLAYER_ONLY_COMMAND_PATH = ConfigUtils.MESSAGE_PATH + ".player-only-command";
    private static final String UNKNOWN_COMMAND_PATH = ConfigUtils.MESSAGE_PATH + ".unknown-command";
    private static final String UNKNOWN_PLAYER_PATH = ConfigUtils.MESSAGE_PATH + ".unknown-player";
    private static final String UNKNOWN_ROLE_PATH = ConfigUtils.MESSAGE_PATH + ".unknown-role";
    private static final String FORBIDDEN_COMMAND_PATH = ConfigUtils.MESSAGE_PATH + ".forbidden-command";
    private static final String RELOADED_CONFIG_PATH = ConfigUtils.MESSAGE_PATH + ".reloaded-config";
    private static final String RESET_CONFIG_PATH = ConfigUtils.MESSAGE_PATH + ".reset-config";
    private static final String COMMAND_USAGE_PATH = ConfigUtils.MESSAGE_PATH + ".command-usage";
    private static final String UPDATED_ROLE_PATH = ConfigUtils.MESSAGE_PATH + ".updated-role";
    private static final String SET_SPAWN_PATH = ConfigUtils.MESSAGE_PATH + ".set-spawn";
    private static final String TELEPORTATION_COMPLETED_PATH = ConfigUtils.MESSAGE_PATH + ".teleportation-completed";
    private static final String TELEPORTATION_CANCELLED_PATH = ConfigUtils.MESSAGE_PATH + ".teleportation-cancelled";
    private static final String TELEPORT_TO_SELF_PATH = ConfigUtils.MESSAGE_PATH + ".teleport-to-self";
    private static final String TELEPORT_PATH = ConfigUtils.MESSAGE_PATH + ".teleport";
    private static final String HELP_HEADER_PATH = ConfigUtils.MESSAGE_PATH + ".help-header";
    private static final String TELEPORT_REQUEST_PATH = ConfigUtils.MESSAGE_PATH + ".teleport-request";
    private static final String TELEPORT_REQUEST_TARGET_PATH = ConfigUtils.MESSAGE_PATH + ".teleport-request-target";
    private static final String TELEPORT_REQUEST_ACCEPTED_PATH = ConfigUtils.MESSAGE_PATH + ".teleport-request-accepted";
    private static final String TELEPORT_REQUEST_DENIED_PATH = ConfigUtils.MESSAGE_PATH + ".teleport-request-denied";
    private static final String NO_PENDING_REQUEST_PATH = ConfigUtils.MESSAGE_PATH + ".no-pending-request";
    private static final String NO_CONVERSATION_PATH = ConfigUtils.MESSAGE_PATH + ".no-conversation";
    private static final String INVENTORY_CLEARED_PATH = ConfigUtils.MESSAGE_PATH + ".inventory-cleared";
    private static final String LAND_NOT_OWNED_PATH = ConfigUtils.MESSAGE_PATH + ".land-not-owned";
    private static final String UNCLAIMED_LAND_PATH = ConfigUtils.MESSAGE_PATH + ".unclaimed-land";
    private static final String NO_CLAIM_SELECTION_PATH = ConfigUtils.MESSAGE_PATH + ".no-claim-selection";
    private static final String DIFFERENT_WORLDS_PATH = ConfigUtils.MESSAGE_PATH + ".different-worlds";
    private static final String DISPLAY_COINS_PATH = ConfigUtils.MESSAGE_PATH + ".display-coins";
    private static final String CLAIM_PATH = ConfigUtils.MESSAGE_PATH + ".claim";
    private static final String RECEIVE_CLAIM_TOOL_PATH = ConfigUtils.MESSAGE_PATH + ".receive-claim-tool";
    private static final String RESTART_MESSAGE_PATH = ConfigUtils.MESSAGE_PATH + ".restart";
    private static final String MOTD_PATH = ConfigUtils.MESSAGE_PATH + ".motd";

    private MessageUtils() {}

    public static String getMessage(String path) {
        String msg = ConfigUtils.loadString(path);
        if (msg == null) {
            LogUtils.error(path + " message is null!");
            return "";
        }
        return StringUtils.format(msg);
    }


    public static List<String> getMotds() {
        List<String> motds = ConfigUtils.loadStringList(MOTD_PATH);
        if (motds == null) {
            LogUtils.error("No available MOTDs!");
            return new ArrayList<>();
        }
        for (int i = 0; i < motds.size(); i++)
            motds.set(i, StringUtils.format(motds.get(i)));
        return motds;
    }


    public static String getRestartMessage() {
        return getMessage(RESTART_MESSAGE_PATH);
    }
    public static void setRestartMessage(String restartMessage) {
        ConfigUtils.save(RESTART_MESSAGE_PATH, restartMessage);
        ConfigUtils.saveConfig();
    }


    public static String getReceiveClaimToolMessage() {
        return getMessage(RECEIVE_CLAIM_TOOL_PATH);
    }
    public static void setReceiveClaimToolMessage(String receiveClaimToolMessage) {
        ConfigUtils.save(RECEIVE_CLAIM_TOOL_PATH, receiveClaimToolMessage);
        ConfigUtils.saveConfig();
    }


    public static String getClaimMessage() {
        return getMessage(CLAIM_PATH);
    }
    public static void setClaimMessage(String claimMessage) {
        ConfigUtils.save(CLAIM_PATH, claimMessage);
        ConfigUtils.saveConfig();
    }


    public static String getDifferentWorldsMessage() {
        return getMessage(DIFFERENT_WORLDS_PATH);
    }
    public static void setDifferentWorldsMessage(String differentWorldsMessage) {
        ConfigUtils.save(DIFFERENT_WORLDS_PATH, differentWorldsMessage);
        ConfigUtils.saveConfig();
    }


    public static String getNoClaimSelectionMessage() {
        return getMessage(NO_CLAIM_SELECTION_PATH);
    }
    public static void setNoClaimSelectionMessage(String noClaimSelectionMessage) {
        ConfigUtils.save(NO_CLAIM_SELECTION_PATH, noClaimSelectionMessage);
        ConfigUtils.saveConfig();
    }


    public static String getDisplayCoinsMessage(String coins) {
        return StringUtils.insertVariables(getMessage(DISPLAY_COINS_PATH), "coins", coins);
    }
    public static void setDisplayCoinsMessage(String displayCoinsMessage) {
        ConfigUtils.save(DISPLAY_COINS_PATH, displayCoinsMessage);
        ConfigUtils.saveConfig();
    }


    public static String getUnclaimedLandMessage() {
        return getMessage(UNCLAIMED_LAND_PATH);
    }
    public static void setUnclaimedLandMessage(String unclaimedLandMessage) {
        ConfigUtils.save(UNCLAIMED_LAND_PATH, unclaimedLandMessage);
        ConfigUtils.saveConfig();
    }


    public static String getLandNotOwnedMessage() {
        return getMessage(LAND_NOT_OWNED_PATH);
    }
    public static void setLandNotOwnedMessage(String landNotOwnedMessage) {
        ConfigUtils.save(LAND_NOT_OWNED_PATH, landNotOwnedMessage);
        ConfigUtils.saveConfig();
    }


    public static String getInventoryClearedMessage() {
        return getMessage(INVENTORY_CLEARED_PATH);
    }
    public static void setInventoryClearedMessage(String inventoryClearedMessage) {
        ConfigUtils.save(INVENTORY_CLEARED_PATH, inventoryClearedMessage);
        ConfigUtils.saveConfig();
    }


    public static String getNoConversationMessage() {
        return getMessage(NO_CONVERSATION_PATH);
    }
    public static void setNoConversationMessage(String noConversationMessage) {
        ConfigUtils.save(NO_CONVERSATION_PATH, noConversationMessage);
        ConfigUtils.saveConfig();
    }


    public static String getNoPendingRequestMessage() {
        return getMessage(NO_PENDING_REQUEST_PATH);
    }
    public static void setNoPendingRequestMessage(String noPendingRequestMessage) {
        ConfigUtils.save(NO_PENDING_REQUEST_PATH, noPendingRequestMessage);
        ConfigUtils.saveConfig();
    }


    public static String getTeleportRequestAcceptedMessage() {
        return getMessage(TELEPORT_REQUEST_ACCEPTED_PATH);
    }
    public static void setTeleportRequestAcceptedMessage(String teleportRequestAcceptedMessage) {
        ConfigUtils.save(TELEPORT_REQUEST_ACCEPTED_PATH, teleportRequestAcceptedMessage);
        ConfigUtils.saveConfig();
    }


    public static String getTeleportRequestDeniedMessage() {
        return getMessage(TELEPORT_REQUEST_DENIED_PATH);
    }
    public static void setTeleportRequestDeniedMessage(String teleportRequestDeniedMessage) {
        ConfigUtils.save(TELEPORT_REQUEST_DENIED_PATH, teleportRequestDeniedMessage);
        ConfigUtils.saveConfig();
    }


    public static String getTeleportRequestMessage(Player target) {
        return StringUtils.insertVariables(getMessage(TELEPORT_REQUEST_PATH), "target", target.getName());
    }
    public static void setTeleportRequestMessage(String teleportRequestMessage) {
        ConfigUtils.save(TELEPORT_REQUEST_PATH, teleportRequestMessage);
        ConfigUtils.saveConfig();
    }


    public static String getTeleportRequestTargetMessage(Player requester) {
        return StringUtils.insertVariables(getMessage(TELEPORT_REQUEST_TARGET_PATH), "requester", requester.getName());
    }
    public static void setTeleportRequestTargetMessage(String teleportRequestTargetMessage) {
        ConfigUtils.save(TELEPORT_REQUEST_TARGET_PATH, teleportRequestTargetMessage);
        ConfigUtils.saveConfig();
    }


    public static String getWelcomeMessage(Player player) {
        return StringUtils.insertVariables(getMessage(WELCOME_PATH), "playerName", player.getName());
    }
    public static void setWelcomeMessage(String welcomeMessage) {
        ConfigUtils.save(WELCOME_PATH, welcomeMessage);
        ConfigUtils.saveConfig();
    }


    public static String getQuitMessage(Player player) {
        return StringUtils.insertVariables(getMessage(QUIT_PATH), "playerName", player.getName());
    }
    public static void setQuitMessage(String quitMessage) {
        ConfigUtils.save(QUIT_PATH, quitMessage);
        ConfigUtils.saveConfig();
    }


    public static String getFirstTimeWelcomeMessage(Player player) {
        return StringUtils.insertVariables(getMessage(FIRST_TIME_WELCOME_PATH), "playerName", player.getName(), "playerNumber", String.valueOf(ConfigUtils.getRegisteredPlayers() + 1));
        // + 1 since the player file has not been made yet
    }
    public static void setFirstTimeWelcomeMessage(String welcomeMessage) {
        ConfigUtils.save(FIRST_TIME_WELCOME_PATH, welcomeMessage);
        ConfigUtils.saveConfig();
    }


    public static String getPlayerOnlyCommandMessage() {
        return getMessage(PLAYER_ONLY_COMMAND_PATH);
    }
    public static void setPlayerOnlyCommandMessage(String playerOnlyCommandMessage) {
        ConfigUtils.save(PLAYER_ONLY_COMMAND_PATH, playerOnlyCommandMessage);
        ConfigUtils.saveConfig();
    }


    public static String getUnknownCommandMessage(String label) {
        return StringUtils.insertVariables(getMessage(UNKNOWN_COMMAND_PATH), "label", label);
    }
    public static void setUnknownCommandMessage(String unknownCommandMessage) {
        ConfigUtils.save(UNKNOWN_COMMAND_PATH, unknownCommandMessage);
        ConfigUtils.saveConfig();
    }


    public static String getUnknownPlayerMessage(String playerName) {
        return StringUtils.insertVariables(getMessage(UNKNOWN_PLAYER_PATH), "playerName", playerName);
    }
    public static void setUnknownPlayerMessage(String unknownPlayerMessage) {
        ConfigUtils.save(UNKNOWN_PLAYER_PATH, unknownPlayerMessage);
        ConfigUtils.saveConfig();
    }


    public static String getUnknownRoleMessage(String role) {
        return StringUtils.insertVariables(getMessage(UNKNOWN_ROLE_PATH), "role", role);
    }
    public static void setUnknownRoleMessage(String unknownRoleMessage) {
        ConfigUtils.save(UNKNOWN_ROLE_PATH, unknownRoleMessage);
        ConfigUtils.saveConfig();
    }


    public static String getForbiddenCommandMessage() {
        return getMessage(FORBIDDEN_COMMAND_PATH);
    }
    public static void setForbiddenCommandMessage(String forbiddenCommandMessage) {
        ConfigUtils.save(FORBIDDEN_COMMAND_PATH, forbiddenCommandMessage);
        ConfigUtils.saveConfig();
    }


    public static String getReloadedConfigMessage() {
        return getMessage(RELOADED_CONFIG_PATH);
    }
    public static void setReloadedConfigMessage(String reloadedConfigMessage) {
        ConfigUtils.save(RELOADED_CONFIG_PATH, reloadedConfigMessage);
        ConfigUtils.saveConfig();
    }


    public static String getResetConfigMessage() {
        return getMessage(RESET_CONFIG_PATH);
    }
    public static void setResetConfigMessage(String resetConfigMessage) {
        ConfigUtils.save(RESET_CONFIG_PATH, resetConfigMessage);
        ConfigUtils.saveConfig();
    }


    public static String getCommandUsageMessage(String usage) {
        return StringUtils.insertVariables(getMessage(COMMAND_USAGE_PATH), "usage", usage);
    }
    public static void setCommandUsageMessage(String commandUsageMessage) {
        ConfigUtils.save(COMMAND_USAGE_PATH, commandUsageMessage);
        ConfigUtils.saveConfig();
    }


    public static String getHelpHeader(int currentPage, int totalPages) {
        return StringUtils.insertVariables(getMessage(HELP_HEADER_PATH), "currentPage", String.valueOf(currentPage), "totalPages", String.valueOf(totalPages));
    }
    public static void setHelpHeader(String helpHeader) {
        ConfigUtils.save(HELP_HEADER_PATH, helpHeader);
        ConfigUtils.saveConfig();
    }


    public static String getSetSpawnMessage() {
        return getMessage(SET_SPAWN_PATH);
    }
    public static void setSetSpawnMessage(String setSpawnMessage) {
        ConfigUtils.save(SET_SPAWN_PATH, setSpawnMessage);
        ConfigUtils.saveConfig();
    }


    public static String getTeleportMessage(String destination) {
        return StringUtils.insertVariables(getMessage(TELEPORT_PATH), "destination", destination);
    }
    public static void setTeleportMessage(String spawnMessage) {
        ConfigUtils.save(TELEPORT_PATH, spawnMessage);
        ConfigUtils.saveConfig();
    }


    public static String getTeleportationCompletedMessage() {
        return getMessage(TELEPORTATION_COMPLETED_PATH);
    }
    public static void setTeleportationCompletedMessage(String teleportationCompletedMessage) {
        ConfigUtils.save(TELEPORTATION_COMPLETED_PATH, teleportationCompletedMessage);
        ConfigUtils.saveConfig();
    }


    public static String getTeleportationCancelledMessage() {
        return getMessage(TELEPORTATION_CANCELLED_PATH);
    }
    public static void setTeleportationCancelledMessage(String teleportationCancelledMessage) {
        ConfigUtils.save(TELEPORTATION_CANCELLED_PATH, teleportationCancelledMessage);
        ConfigUtils.saveConfig();
    }


    public static String getUpdatedRoleMessage(Player player) {
        return StringUtils.insertVariables(getMessage(UPDATED_ROLE_PATH), "playerName", player.getName());
    }
    public static void setUpdatedRoleMessage(String updatedRoleMessage) {
        ConfigUtils.save(UPDATED_ROLE_PATH, updatedRoleMessage);
        ConfigUtils.saveConfig();
    }


    public static String getTeleportToSelfMessage() {
        return StringUtils.insertVariables(getMessage(TELEPORT_TO_SELF_PATH));
    }
    public static void setTeleportToSelfMessage(String teleportToSelfMessage) {
        ConfigUtils.save(TELEPORT_TO_SELF_PATH, teleportToSelfMessage);
        ConfigUtils.saveConfig();
    }
}
