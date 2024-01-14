package com.snake.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import com.snake.Model.Vector;

//Made by Jonathan Sommerlund

/**
 * A class to keep track of different regions. Idea came from from a video in Sebastian Lague's
 * youtube series on procedural generation. Most code is my own though
 */
public class Region
{
    public ArrayList<Vector> coords;
    public ArrayList<Region> connectedRegions;
    public ArrayList<Vector> edgeTiles;
    int regionSize;
    int regionID = -1;
    boolean[][] map;
    public static int minAvailableRegionID = 0;

    public Region()
    {

    }

    public Region(ArrayList<Vector> coords, boolean[][] map)
    {
        this.coords = coords;
        regionSize = coords.size();
        connectedRegions = new ArrayList<Region>();

        edgeTiles = new ArrayList<Vector>();
        int height = map.length;
        int width = map[0].length;
        for (Vector tile : coords)
        {
            for (int dx = -1; dx <= 1; dx++)
            {
                for (int dy = -1; dy <= 1; dy++)
                {
                    if (!(dy == 0 && dx == 0) && (dy == 0 || dx == 0))
                    {
                        int modX = LevelGenerator.mod(tile.x + dx, width);
                        int modY = LevelGenerator.mod(tile.y + dy, height);
                        if (map[modY][modX])
                        {
                            edgeTiles.add(tile); // some tiles are added multiple times.
                        }
                    }
                }
            }
        }
    }

    public void setRegionID(int id)
    {
        if (id < regionID || regionID == -1)
        {
            regionID = id;
            for (Region region : connectedRegions)
            {
                region.setRegionID(id);
            }
        }
    }

    public boolean IsConnected(Region otherRoom)
    {
        return regionID == otherRoom.regionID && regionID != -1;
    }

    public static void ConnectRooms(Region roomA, Region roomB)
    {
        int regionID = Math.min(roomA.regionID, roomB.regionID);
        if (regionID == -1)
        {
            regionID = minAvailableRegionID;
            minAvailableRegionID++;
        }
        roomA.connectedRegions.add(roomB);
        roomA.setRegionID(regionID);
        roomB.connectedRegions.add(roomA);
        roomB.setRegionID(regionID);
    }

    public void SaveData(Region other, DistanceData data)
    {
        storedConnections.put(other, data);
    }

    HashMap<Region, DistanceData> storedConnections = new HashMap<Region, DistanceData>();

    public DistanceData distanceToOtherRegion(Region roomB)
    {
        if (storedConnections.containsKey(roomB))
        {
            return storedConnections.get(roomB);
        }
        Vector bestTileA = new Vector();
        Vector bestTileB = new Vector();
        double bestDistance = 99999999;
        Region roomA = this;
        for (int tileIndexA = 0; tileIndexA < roomA.edgeTiles.size(); tileIndexA++)
        {
            for (int tileIndexB = 0; tileIndexB < roomB.edgeTiles.size(); tileIndexB++)
            {
                Vector tileA = roomA.edgeTiles.get(tileIndexA);
                Vector tileB = roomB.edgeTiles.get(tileIndexB);
                double distanceBetweenRooms = tileA.distance(tileB);
                if (distanceBetweenRooms < bestDistance)
                {
                    bestTileA = tileA;
                    bestTileB = tileB;
                    bestDistance = distanceBetweenRooms;
                }

            }
        }
        DistanceData data = new DistanceData(bestDistance, bestTileA, bestTileB);
        roomB.SaveData(roomA, data);
        storedConnections.put(roomB, data);
        return data;
    }
}
