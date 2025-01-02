package refil.mysticpickaxe.events;

import refil.mysticpickaxe.Refilmysticpickaxe;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.configuration.file.FileConfiguration;

public class MysticListener implements Listener {
    private final NamespacedKey key;
    private final Refilmysticpickaxe plugin;

    // ThreadLocal flag to prevent recursive BlockBreakEvent calls
    private static final ThreadLocal<Boolean> isHandlingBreakEvent = ThreadLocal.withInitial(() -> false);

    public MysticListener(Refilmysticpickaxe plugin) {
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, "mystic_pickaxe");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Prevent recursion by checking if we're already handling a BlockBreakEvent
        if (isHandlingBreakEvent.get()) {
            return;
        }

        isHandlingBreakEvent.set(true); // Mark the event as being handled
        try {
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();
            FileConfiguration config = plugin.getConfig();

            // Get configured pickaxe type
            String configuredPickaxeType = config.getString("pickaxe.type", "DIAMOND_PICKAXE").toUpperCase();
            Material pickaxeMaterial = Material.matchMaterial(configuredPickaxeType);

            // Check if the player is using the Mystic Pickaxe
            if (item != null && item.getType() == pickaxeMaterial &&
                    item.hasItemMeta() &&
                    item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {

                Block centerBlock = event.getBlock();
                Location eyeLoc = player.getEyeLocation();
                RayTraceResult result = player.getLocation().getWorld().rayTraceBlocks(eyeLoc, eyeLoc.getDirection(), 10, FluidCollisionMode.NEVER);

                BlockFace face = result != null ? result.getHitBlockFace() : null;
                if (face == null) {
                    return;
                }

                String miningArea = config.getString("pickaxe.area", "3x3");

                // Default mining range
                int[] xRange = {-1, 1};
                int[] yRange = {-1, 1};
                int[] zRange = {-1, 1};

                if (miningArea.equals("3x3")) {
                    // Adjust ranges based on the dominant axis (BlockFace)
                    switch (face) {
                        case WEST: // Positive X
                            xRange = new int[]{0, 0};
                            yRange = new int[]{-1, 1};
                            zRange = new int[]{-1, 1};
                            break;
                        case EAST: // Negative X
                            xRange = new int[]{0, 0};
                            yRange = new int[]{-1, 1};
                            zRange = new int[]{-1, 1};
                            break;
                        case DOWN: // Positive Y
                            xRange = new int[]{-1, 1};
                            yRange = new int[]{0, 0};
                            zRange = new int[]{-1, 1};
                            break;
                        case UP: // Negative Y
                            xRange = new int[]{-1, 1};
                            yRange = new int[]{0, 0};
                            zRange = new int[]{-1, 1};
                            break;
                        case NORTH: // Positive Z
                            xRange = new int[]{-1, 1};
                            yRange = new int[]{-1, 1};
                            zRange = new int[]{0, 0};
                            break;
                        case SOUTH: // Negative Z
                            xRange = new int[]{-1, 1};
                            yRange = new int[]{-1, 1};
                            zRange = new int[]{0, 0};
                            break;
                        default:
                            break;
                    }
                } else if (miningArea.equals("3x3x3")) {
                    // Adjust ranges based on the dominant axis (BlockFace)
                    switch (face) {
                        case WEST: // Positive X
                            xRange = new int[]{0, 2};
                            yRange = new int[]{-1, 1};
                            zRange = new int[]{-1, 1};
                            break;
                        case EAST: // Negative X
                            xRange = new int[]{-2, 0};
                            yRange = new int[]{-1, 1};
                            zRange = new int[]{-1, 1};
                            break;
                        case DOWN: // Positive Y
                            xRange = new int[]{-1, 1};
                            yRange = new int[]{0, 2};
                            zRange = new int[]{-1, 1};
                            break;
                        case UP: // Negative Y
                            xRange = new int[]{-1, 1};
                            yRange = new int[]{-2, 0};
                            zRange = new int[]{-1, 1};
                            break;
                        case NORTH: // Positive Z
                            xRange = new int[]{-1, 1};
                            yRange = new int[]{-1, 1};
                            zRange = new int[]{0, 2};
                            break;
                        case SOUTH: // Negative Z
                            xRange = new int[]{-1, 1};
                            yRange = new int[]{-1, 1};
                            zRange = new int[]{-2, 0};
                            break;
                        default:
                            break;
                    }
                }
                else {
                    plugin.getLogger().warning("Invalid mining area specified in config. Defaulting to 3x3.");
                    return;
                }
                // Mine blocks in the specified range
                for (int x = xRange[0]; x <= xRange[1]; x++) {
                    for (int y = yRange[0]; y <= yRange[1]; y++) {
                        for (int z = zRange[0]; z <= zRange[1]; z++) {
                            Block nearbyBlock = centerBlock.getWorld().getBlockAt(
                                    centerBlock.getX() + x,
                                    centerBlock.getY() + y,
                                    centerBlock.getZ() + z
                            );

                            // Skip air or bedrock blocks
                            if (nearbyBlock.getType().isAir() || nearbyBlock.getType() == Material.BEDROCK) {
                                continue;
                            }

                            // Trigger BlockBreakEvent for each block
                            BlockBreakEvent blockBreakEvent = new BlockBreakEvent(nearbyBlock, player);
                            plugin.getServer().getPluginManager().callEvent(blockBreakEvent);

                            // If the event isn't cancelled, break the block naturally
                            if (!blockBreakEvent.isCancelled()) {
                                nearbyBlock.breakNaturally(item);
                            }
                        }
                    }
                }

                // Prevent double-breaking the central block
                event.setCancelled(true);
            }
        } finally {
            isHandlingBreakEvent.set(false); // Reset the flag
        }
    }
}