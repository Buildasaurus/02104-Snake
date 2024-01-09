package com.snake.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import com.snake.Settings;

public class LevelGenerator
{
    static int width;
    static int height;

    /**
     * Generates walls around the level. Will always have a clear 8x8 square in the middle Assumes
     * non-jagged array
     *
     * @param board The board to generate the level on. Will modify this board.
     */
    public static void generateLevel(Tile[][] board)
    {
        // Early return if board is too small
        if (board.length < 1 || (board.length < 8 && board[0].length < 8))
        {
            return;
        }
        height = board.length;
        width = board[0].length;
        double fill = Settings.getGameSettings().getLevelFill();
        boolean[][] map = generateMap(fill);
        for (int i = 0; i < 3; i++)
        {
            map = simplifyNoise(map);
        }
        ArrayList<ArrayList<Vector>> regions = getRegions(map);
        System.out.println("regionCount before" + regions.size());
        map = connectIslands(map);
        regions = getRegions(map);
        System.out.println("regionCount after" + regions.size());
        // the number of free square in a central square
        int margin = 8;
        // illegal squares, that are to be ignored
        // TODO for now just assuming height 20, width 20. Do maths later.
        Vector illegalXVector = new Vector((height - margin) / 2, (height + margin) / 2);
        Vector illegalYVector = new Vector((width - margin) / 2, (width + margin) / 2);

        for (int rowCount = 0; rowCount < height; rowCount++)
        {
            for (int columnCount = 0; columnCount < width; columnCount++)
            {
                if (!(isInInterval(rowCount, illegalYVector)
                        && isInInterval(columnCount, illegalXVector)) && map[rowCount][columnCount])
                {
                    board[rowCount][columnCount] = new Wall();
                }
            }
        }
        // Generate noise, use this for walls.
    }

    /**
     *
     * @param fillValue a value between 0 and 1, where 1 is all are filled, 0 is none.
     * @return
     */
    private static boolean[][] generateMap(double fillValue)
    {
        boolean[][] randomMap = new boolean[width][height];
        Random rand = new Random(6);
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
        // TODO write this, to make map more smooth.
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

    private static boolean[][] connectIslands(boolean[][] map)
    {
        ArrayList<ArrayList<Vector>> regions = getRegions(map);
        double[][] distances = new double[regions.size()][regions.size()];

        // foreach region, create a Room class.
        /*
         * for (int firstIndex = 0; firstIndex < regions.size(); firstIndex++) { for (int
         * secondIndex = 0; secondIndex < regions.size(); secondIndex++) { if (firstIndex ==
         * secondIndex) continue; distances[firstIndex][secondIndex] =
         * shortestDistanceBetweenRegions( regions.get(firstIndex), regions.get(secondIndex)); } }
         */
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


    private static void connectRegions(ArrayList<Region> allRooms, boolean[][] map)
    {
        List<Region> roomListA = new ArrayList<Region>();
        List<Region> roomListB = new ArrayList<Region>();
        roomListA = allRooms;
        roomListB = allRooms;
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

                for (int tileIndexA = 0; tileIndexA < roomA.edgeTiles.size(); tileIndexA++)
                {
                    for (int tileIndexB = 0; tileIndexB < roomB.edgeTiles.size(); tileIndexB++)
                    {
                        Vector tileA = roomA.edgeTiles.get(tileIndexA);
                        Vector tileB = roomB.edgeTiles.get(tileIndexB);
                        double distanceBetweenRooms = tileA.distance(tileB);

                        if (distanceBetweenRooms < bestDistance || !possibleConnectionFound)
                        {
                            bestDistance = distanceBetweenRooms;
                            possibleConnectionFound = true;
                            bestTileA = tileA;
                            bestTileB = tileB;
                            bestRoomA = roomA;
                            bestRoomB = roomB;
                        }
                    }
                }
            }
        }
        // At this point, every region will be connected to the closeset region to it.
        // Yet there might still be larger regions, now consisting of several regions

        if (possibleConnectionFound) // this means there was a room that just got connected to
                                     // another.
        {
            CreatePassage(bestRoomA, bestRoomB, bestTileA, bestTileB, map);
            Region.ConnectRooms(bestRoomA, bestRoomB);
            connectRegions(allRooms, map);
        }
    }

    private static void CreatePassage(Region roomA, Region roomB, Vector tileA, Vector tileB,
            boolean[][] map)
    {
        System.out.println(tileA + " connects to " + tileB);
        for (int row = 0; row < height; row++)
        {
            for (int column = 0; column < width; column++)
            {
                Vector point = new Vector(column, row);
                if (minimumDistance(tileB, tileA, point) < 2)
                {
                    System.out.println("creating passage at " + point);
                    map[row][column] = false;
                }
            }
        }
    }

    public static double minimumDistance(Vector v, Vector w, Vector p)
    {
        double l2 = v.distance(w);
        l2 *= l2;

        if (l2 == 0.0)
            return p.distance(v);

        int t = (int) Math.round(((p.subtract(v)).dotProduct(w.subtract(v))) / l2);
        t = Math.max(0, Math.min(1, t));

        Vector projection = v.add((w.subtract(v)).multiply(t));

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

    private static boolean isInInterval(int value, Vector interval)
    {
        return value > interval.x && value < interval.y;
    }

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
