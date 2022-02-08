package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class BoringWorldDemo {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // fills in a block 14 tiles wide by 4 tiles tall
        for (int x = 20; x < 35; x += 1) {
            for (int y = 5; y < 10; y += 1) {
                world[x][y] = Tileset.WALL;
            }
        }
        draw(world, 0, 2, 0, 2, Tileset.FLOWER);
        draw(world, 3, 5, 0, 2, Tileset.AVATAR);
        draw(world, 6, 8, 0, 2, Tileset.FLOOR);
        draw(world, 9, 11, 0, 2, Tileset.GRASS);
        draw(world, 12, 14, 0, 2, Tileset.LOCKED_DOOR);
        draw(world, 15, 17, 0, 2, Tileset.MOUNTAIN);
        draw(world, 18, 20, 0, 2, Tileset.SAND);
        draw(world, 21, 23, 0, 2, Tileset.TREE);
        draw(world, 24, 26, 0, 2, Tileset.UNLOCKED_DOOR);
        draw(world, 27, 29, 0, 2, Tileset.WATER);


        // draws the world to the screen
        ter.renderFrame(world);
    }

    public static void draw(TETile[][] w, int xbegin, int xend, int ybegin, int yend, TETile t) {
        for (int x = xbegin; x <= xend; x++) {
            for (int y = ybegin; y <= yend; y++) {
                w[x][y] = t;
            }
        }
    }
}
