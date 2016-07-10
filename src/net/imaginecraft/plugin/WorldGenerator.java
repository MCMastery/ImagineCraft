package net.imaginecraft.plugin;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public enum WorldGenerator {
    DEFAULT("Default", new ItemStack(Material.COBBLESTONE)) {
        @Override
        public World generate(String name) {
            WorldCreator wc = new WorldCreator(name);
            wc = wc.environment(World.Environment.NORMAL);
            wc = wc.generateStructures(true);
            wc = wc.seed(new Random().nextLong());
            wc = wc.type(WorldType.NORMAL);
            return wc.createWorld();
        }
    },
    FLAT("Flat", new ItemStack(Material.GRASS)) {
        @Override
        public World generate(String name) {
            WorldCreator wc = new WorldCreator(name);
            wc = wc.environment(World.Environment.NORMAL);
            wc = wc.generateStructures(true);
            wc = wc.seed(new Random().nextLong());
            wc = wc.type(WorldType.FLAT);
            return wc.createWorld();
        }
    },
    AMPLIFIED("Amplified", new ItemStack(Material.COBBLESTONE)) {
        @Override
        public World generate(String name) {
            WorldCreator wc = new WorldCreator(name);
            wc = wc.environment(World.Environment.NORMAL);
            wc = wc.generateStructures(true);
            wc = wc.seed(new Random().nextLong());
            wc = wc.type(WorldType.AMPLIFIED);
            return wc.createWorld();
        }
    },
    EMPTY("Empty", new ItemStack(Material.STAINED_GLASS_PANE)) {
        @Override
        public World generate(String name) {
            WorldCreator wc = new WorldCreator(name);
            wc = wc.environment(World.Environment.NORMAL);
            wc = wc.generateStructures(true);
            wc = wc.seed(new Random().nextLong());
            wc = wc.generator(new EmptyWorldGenerator());
            return wc.createWorld();
        }
    },
    NETHER("Nether", new ItemStack(Material.LAVA)) {
        @Override
        public World generate(String name) {
            WorldCreator wc = new WorldCreator(name);
            wc = wc.environment(World.Environment.NETHER);
            wc = wc.generateStructures(true);
            wc = wc.seed(new Random().nextLong());
            wc = wc.type(WorldType.NORMAL);
            return wc.createWorld();
        }
    },
    END("End", new ItemStack(Material.ENDER_STONE)) {
        @Override
        public World generate(String name) {
            WorldCreator wc = new WorldCreator(name);
            wc = wc.environment(World.Environment.THE_END);
            wc = wc.generateStructures(true);
            wc = wc.seed(new Random().nextLong());
            wc = wc.type(WorldType.NORMAL);
            return wc.createWorld();
        }
    }
    ;

    private final String name;
    // for use in WorldPanel GUI
    private final ItemStack item;

    WorldGenerator(String name, ItemStack item) {
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return this.name;
    }
    public ItemStack getItemRepresentation() {
        return this.item;
    }

    public abstract World generate(String name);
}
