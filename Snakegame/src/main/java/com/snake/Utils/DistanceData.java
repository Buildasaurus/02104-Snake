package com.snake.Utils;

import com.snake.Model.Vector;

// Made by Jonathan Sommerlund

public class DistanceData
{
    public double distance;
    public Vector tileA;
    public Vector tileB;

    public DistanceData(double a, Vector b, Vector c)
    {
        distance = a;
        tileA = b;
        tileB = c;
    }
}
