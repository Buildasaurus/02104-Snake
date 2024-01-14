package com.snake.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import com.snake.Settings;
import com.snake.Model.DistanceData;
import com.snake.Model.DoubleVector;
import com.snake.Model.Region;
import com.snake.Model.Tile;
import com.snake.Model.Vector;
import com.snake.Model.Wall;

// Made by Jonathan Sommerlund

public class LevelGenerator
{
    static int width;
    static int height;
    public static int wallCount;
    static long islandTime = 0;

    /**
     * Generates walls around the level. Will always have a clear 8x8 square in the middle Assumes
     * non-jagged array
     *
     * @param board The board to generate the level on. Will modify this board.
     */
    public static void generateLevel(Tile[][] board)
    {
        long startTime = System.nanoTime();
        wallCount = 0;
        // Early return if board is too small
        if (board.length < 1 || (board.length < 8 && board[0].length < 8))
        {
            return;
        }
        height = board.length;
        width = board[0].length;
        double fill = Settings.getGameSettings().getLevelFill();

        // First step - random map
        boolean[][] map = generateMap(fill);

        // Second step - Simplify noise
        for (int i = 0; i < 3; i++)
        {
            map = simplifyNoise(map);
        }

        System.out.println((System.nanoTime() - startTime) / Math.pow(10, 9)
                + "seconds for create and simplify");

        // Third step - Safe Square
        int yMargin = 8;
        int xMargin = 14;
        Vector illegalXVector = new Vector((width - xMargin) / 2, (width + xMargin) / 2);
        Vector illegalYVector = new Vector((height - yMargin) / 2, (height + yMargin) / 2);
        map = createSafeSquare(map, illegalXVector, illegalYVector);

        // Fourth step - Connect islands
        startTime = System.nanoTime();
        ArrayList<ArrayList<Vector>> regions = getRegions(map);
        System.out.println("regionCount before" + regions.size());
        map = connectIslands(map);
        System.out.println((System.nanoTime() - startTime) / Math.pow(10, 9)
                + "seconds for connecting islands");
        System.out.println((islandTime) / Math.pow(10, 9)
                + "seconds for calculating disatnces between islands");
        startTime = System.nanoTime();

        // Fifth step - Simplify again

        for (int i = 0; i < 6; i++)
        {
            map = simplifyNoise(map);
        }

        // Sixth step - remove suicide cells
        removeSuicideCells(map);

        fillBoardWithMap(board, map);
        System.out.println((System.nanoTime() - startTime) / Math.pow(10, 9) + "seconds for rest");
        startTime = System.nanoTime();
    }


    /**
     * Generates a square in the given intervals, in which there will be no walls
     *
     * @param map The map to modify
     * @param xInterval The x interval that must be clear of walls
     * @param yInterval The y interval that must be clear of walls
     * @return A modified map
     */
    private static boolean[][] createSafeSquare(boolean[][] map, Vector xInterval, Vector yInterval)
    {
        for (int rowCount = 0; rowCount < height; rowCount++)
        {
            for (int columnCount = 0; columnCount < width; columnCount++)
            {
                if (isInInterval(columnCount, xInterval) && isInInterval(rowCount, yInterval)
                        && map[rowCount][columnCount])
                {
                    map[rowCount][columnCount] = false;
                }
            }
        }
        return map;
    }

    /**
     * Fills the given board with walls where there is a true value on the map
     *
     * @param board The board to fill with walls
     * @param map The map representing where the walls should be
     */
    private static void fillBoardWithMap(Tile[][] board, boolean[][] map)
    {

        for (int rowCount = 0; rowCount < height; rowCount++)
        {
            for (int columnCount = 0; columnCount < width; columnCount++)
            {
                if (map[rowCount][columnCount])
                {
                    board[rowCount][columnCount] = new Wall();
                    wallCount++;
                }
            }
        }
    }

    /**
     *
     * @param fillValue a value between 0 and 1, where 1 is all are filled, 0 is none.
     * @return
     */
    private static boolean[][] generateMap(double fillValue)
    {
        boolean[][] randomMap = new boolean[height][width];
        // Seed 1544738215 generates two rooms.
        Random randseedGenerator = new Random();
        int seed = randseedGenerator.nextInt();
        System.out.println("seed used is " + seed);
        Random rand = new Random(seed);
        for (int rowCount = 0; rowCount < height; rowCount++)
        {
            for (int columnCount = 0; columnCount < width; columnCount++)
            {
                double randomNumber = rand.nextDouble();
                randomMap[rowCount][columnCount] = randomNumber < fillValue;
            }
        }


        return randomMap;
    }

    /**
     * Finds all regions on the given map and returns them in a 2d Arraylist
     *
     * @param map The map to look for regions in
     * @return A list of lists with where the regions are. Each entry is a region, in which the
     *         coordinates of the squares are stored.
     */
    public static ArrayList<ArrayList<Vector>> getRegions(boolean[][] map)
    {
        ArrayList<ArrayList<Vector>> regions = new ArrayList<ArrayList<Vector>>();
        boolean[][] tilesAlreadyClassified = new boolean[height][width];
        for (int row = 0; row < height; row++)
        {
            for (int column = 0; column < width; column++)
            {
                if (!map[row][column] && !tilesAlreadyClassified[row][column])
                {
                    ArrayList<Vector> region = getRegion(new Vector(column, row), map);
                    for (Vector vector : region)
                    {
                        tilesAlreadyClassified[vector.y][vector.x] = true;
                    }
                    regions.add(region);
                }
            }
        }
        return regions;
    }

    /**
     * Returns an ArrayList of Vectors, that are all in the same region. A region is defined as
     * elements that all are false in the given map, and where you can go from all elements to all
     * elements, without going diagonally.
     *
     * @param startVector The start coordinate to
     * @return
     */
    private static ArrayList<Vector> getRegion(Vector startVector, boolean[][] map)
    {
        LinkedList<Vector> tilesToLookAt = new LinkedList<Vector>();
        ArrayList<Vector> tilesInRegion = new ArrayList<Vector>();
        boolean[][] alreadyLookedAtMap = new boolean[height][width];
        alreadyLookedAtMap[startVector.y][startVector.x] = true;
        tilesToLookAt.add(startVector);
        while (tilesToLookAt.size() > 0)
        {
            Vector currentPoint = tilesToLookAt.poll();
            tilesInRegion.add(currentPoint);
            for (int dx = -1; dx <= 1; dx++)
            {
                for (int dy = -1; dy <= 1; dy++)
                {
                    if (!(dy == 0 && dx == 0) && (dy == 0 || dx == 0))
                    {
                        int modX = mod(currentPoint.x + dx, width);
                        int modY = mod(currentPoint.y + dy, height);
                        if (!alreadyLookedAtMap[modY][modX] && !map[modY][modX])
                        {
                            tilesToLookAt.add(new Vector(modX, modY));
                            alreadyLookedAtMap[modY][modX] = true;
                        }
                    }
                }
            }
        }
        return tilesInRegion;
    }


    /**
     * Finds all cells that have 3 neighboring cells - not on the diagonal. If this is the case,
     * then it makes sure to fill that cell, and any surrounding cells, that perheaps then also
     * become suicide cells
     *
     * @param map The map in which to remove the suicide cells.
     */
    private static boolean[][] removeSuicideCells(boolean[][] map)
    {
        for (int row = 0; row < height; row++)
        {
            for (int column = 0; column < width; column++)
            {
                Vector newPoint = new Vector(column, row);
                if (!map[row][column] && getHorisontalNeighbors(map, newPoint) > 2)
                {
                    map = removeSuicideCell(map, newPoint);
                }
            }
        }
        return map;
    }

    private static Vector[] horisontalDirections =
    {new Vector(1, 0), new Vector(0, 1), new Vector(-1, 0), new Vector(0, -1)};

    /**
     * Removes a suicide cell at a given point, and recursively any possible sucide cells around it.
     *
     * @param map
     * @param point
     * @return
     */
    private static boolean[][] removeSuicideCell(boolean[][] map, Vector point)
    {
        System.out.println("removed suicide cell at " + point);
        map[point.y][point.x] = true;

        for (Vector vector : horisontalDirections)
        {
            vector = point.add(vector).modulo(width, height);
            if (!map[vector.y][vector.x] && getHorisontalNeighbors(map, vector) > 2)
            {
                removeSuicideCell(map, vector);
            }
        }
        return map;
    }

    /**
     * Calculates how many horisontal neighbors a cell have at a given point.
     *
     * @param map
     * @param point
     * @return Returns the neighborCount
     */
    private static int getHorisontalNeighbors(boolean[][] map, Vector vector)
    {
        int horisontalNeighborCount = 0;

        for (Vector direction : horisontalDirections)
        {
            direction = vector.add(direction).modulo(width, height);
            horisontalNeighborCount += map[direction.y][direction.x] ? 1 : 0;
        }
        return horisontalNeighborCount;
    }

    /**
     * Will simplify the noise in the map. Will not modify the original map
     *
     * @param map
     */
    private static boolean[][] simplifyNoise(boolean[][] map)
    {
        if (map.length == 0)
            return map;

        int[][] neighborMap = getSurroundingNeighborCountMap(map);
        for (int row = 0; row < map.length; row++)
        {
            for (int column = 0; column < map[0].length; column++)
            {
                if (neighborMap[row][column] > 4)
                {
                    map[row][column] = true;
                }
                if (neighborMap[row][column] < 4)
                {
                    map[row][column] = false;
                }
            }
        }
        return map;
    }

    /**
     * Returns a map where each cell describes how many neighbors the original map had. Uses modulo,
     * to think of the map as a torus
     *
     * @param map
     */
    private static int[][] getSurroundingNeighborCountMap(boolean[][] map)
    {
        int[][] neighborMap = new int[map.length][map[0].length];
        for (int row = 0; row < map.length; row++)
        {
            for (int column = 0; column < map[0].length; column++)
            {
                if (map[row][column]) // if cell is alive, add to surrounding counts.
                {
                    for (int dy = -1; dy < 2; dy++)
                    {
                        for (int dx = -1; dx < 2; dx++)
                        {
                            if (dy == 0 && dx == 0)
                            {
                                continue;
                            }
                            neighborMap[mod(row + dy, height)][mod(column + dx, width)] += 1;
                        }
                    }
                }
            }
        }
        return neighborMap;
    }

    /**
     * Finds and connects the regions on the map, returns a new map with only one region.
     *
     * @param map The map to connect regions on
     * @return The same map, now modified
     */
    private static boolean[][] connectIslands(boolean[][] map)
    {
        ArrayList<ArrayList<Vector>> regions = getRegions(map);


        ArrayList<Region> allRooms = new ArrayList<Region>();
        for (ArrayList<Vector> region : regions)
        {
            Region reg = new Region(region, map);
            allRooms.add(reg);
        }
        connectRegions(allRooms, map);
        // now figure out how to connect the regions in the best way, where each region is connected
        // to the one closest to itself. This might still result it some larger regions, that again
        // should be connected.

        return map;
    }


    /**
     * Connects all the regions one by one, by finding the two nearest room in different regions,
     * and connecting those. Then it keeps looping until all the regions given are connected
     *
     * @param allRegions The regions to connect
     * @param map The map upon which the regions are. This will be modified
     */
    private static void connectRegions(ArrayList<Region> allRegions, boolean[][] map)
    {
        List<Region> roomListA = new ArrayList<Region>();
        List<Region> roomListB = new ArrayList<Region>();
        roomListA = allRegions;
        roomListB = allRegions;
        double bestDistance = 0;
        Vector bestTileA = new Vector();
        Vector bestTileB = new Vector();
        Region bestRoomA = new Region();
        Region bestRoomB = new Region();
        boolean possibleConnectionFound = false;

        for (Region roomA : roomListA)
        {
            for (Region roomB : roomListB)
            {
                if (roomA == roomB || roomA.IsConnected(roomB))
                {
                    continue;
                }
                long startTime = System.nanoTime();
                DistanceData data = roomA.distanceToOtherRegion(roomB);
                if (data.distance < bestDistance || !possibleConnectionFound)
                {
                    bestDistance = data.distance;
                    possibleConnectionFound = true;
                    bestTileA = data.tileA;
                    bestTileB = data.tileB;
                    bestRoomA = roomA;
                    bestRoomB = roomB;
                }
                islandTime += System.nanoTime() - startTime;
            }
        }
        // At this point, every region will be connected to the closeset region to it.
        // Yet there might still be larger regions, now consisting of several regions

        if (possibleConnectionFound) // this means there was a room that just got connected to
                                     // another.
        {
            CreatePassage(bestRoomA, bestRoomB, bestTileA, bestTileB, map);
            Region.ConnectRooms(bestRoomA, bestRoomB);
            connectRegions(allRegions, map);
        }
    }


    /**
     * Creates a passage between two rooms, from the given tiles. The passage will be a line from
     * which all squares within a distance of two, will become a clear square, if they were a wall
     *
     * @param roomA The first room
     * @param roomB The second room
     * @param tileA The start tile in the first room
     * @param tileB The end tile in the second room
     * @param map The map upon which the passage should be created
     */
    private static void CreatePassage(Region roomA, Region roomB, Vector tileA, Vector tileB,
            boolean[][] map)
    {
        for (int row = 0; row < height; row++)
        {
            for (int column = 0; column < width; column++)
            {
                Vector point = new Vector(column, row);
                if (MinimumDistanceFromPointToLine(new DoubleVector(tileB), new DoubleVector(tileA),
                        new DoubleVector(point)) < 2)
                {
                    map[row][column] = false;
                }
            }
        }
    }

    /**
     * Calculates the minimum distance between a line from v to w, and a point, denoted p. This
     * method was taking from Stack Overflow and converted to java
     * https://stackoverflow.com/questions/849211/shortest-distance-between-a-point-and-a-line-segment
     *
     * @param v The start of the line
     * @param w The end of the line
     * @param p The point which you seek to find the distance to the line
     * @return
     */
    public static double MinimumDistanceFromPointToLine(DoubleVector v, DoubleVector w,
            DoubleVector p)
    {
        double l2 = v.distance(w);
        l2 *= l2;

        if (l2 == 0.0)
            return p.distance(v);

        double t = ((p.subtract(v)).dotProduct(w.subtract(v))) / l2;
        t = Math.max(0, Math.min(1, t));

        DoubleVector projection = v.add((w.subtract(v)).multiply(t));

        return p.distance(projection);
    }

    /**
     * Calculates which elements in a graph are connected, and what regions exist.
     *
     * @param graph
     * @return The regions that exists. Eg [[1,2,3][4,5]]
     */
    public static ArrayList<ArrayList<Integer>> getRegions(ArrayList<ArrayList<Integer>> graph)
    {
        ArrayList<ArrayList<Integer>> regions = new ArrayList<ArrayList<Integer>>();
        boolean[] nodesAlreadyClassified = new boolean[graph.size()];
        for (int nodeIndex = 0; nodeIndex < graph.size(); nodeIndex++)
        {
            if (!nodesAlreadyClassified[nodeIndex])
            {
                ArrayList<Integer> region = getRegion(nodeIndex, graph);
                for (int nodeInRegion : region)
                {
                    nodesAlreadyClassified[nodeInRegion] = true;
                }
                regions.add(region);
            }
        }
        return regions;
    }

    /**
     * Returns an ArrayList of Vectors, that are all in the same region. A region is defined as
     * elements that all are false in the given map, and where you can go from all elements to all
     * elements, without going diagonally.
     *
     * @param startNode The start coordinate to
     * @return
     */
    private static ArrayList<Integer> getRegion(int startNode, ArrayList<ArrayList<Integer>> map)
    {
        LinkedList<Integer> nodesToLookAt = new LinkedList<Integer>();
        ArrayList<Integer> nodesInRegion = new ArrayList<Integer>();
        boolean[] alreadyLookedAtMap = new boolean[map.size()];
        alreadyLookedAtMap[startNode] = true;
        nodesToLookAt.add(startNode);
        while (nodesToLookAt.size() > 0)
        {
            int currentNode = nodesToLookAt.poll();
            nodesInRegion.add(currentNode);
            for (int connectedNode : map.get(currentNode))
            {
                if (!alreadyLookedAtMap[connectedNode])
                {
                    nodesToLookAt.add(connectedNode);
                    alreadyLookedAtMap[connectedNode] = true;
                }
            }
        }
        return nodesInRegion;
    }

    /**
     * Figures if a value is in a interval spanned by a Vector
     *
     * @param value The value
     * @param interval The interval in which the value might be
     * @return True if the value is in the interval, else false.
     */
    private static boolean isInInterval(int value, Vector interval)
    {
        return value > interval.x && value < interval.y;
    }

    /**
     * A mathematically correct modulo, only returning positive values. The syntax is a%b.
     *
     * @param a
     * @param b
     * @return number corresponding to a%b in the interval [0;b-1]
     */
    public static int mod(int a, int b)
    {
        int mod = a % b;
        if (mod < 0)
        {
            return mod + b;
        }
        return mod;
    }
}
