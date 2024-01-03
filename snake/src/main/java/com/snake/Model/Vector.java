package com.snake.Model;

public class Vector
{
    int y;
    int x;

    public Vector(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector add(Vector other)
    {
        return new Vector(x + other.x, y + other.y);
    }

    public Vector add(int scalar)
    {
        return new Vector(x + scalar, y + scalar);
    }

    public Vector add(int _x, int _y)
    {
        return new Vector(x + _x, y + _y);
    }

    public Vector subtract(Vector other)
    {
        return new Vector(x - other.x, y - other.y);
    }

    public Vector subtract(int scalar)
    {
        return new Vector(x - scalar, y - scalar);
    }

    public Vector subtract(int _x, int _y)
    {
        return new Vector(x - _x, y - _y);
    }

    public Vector multiply(int scalar)
    {
        return new Vector(x * scalar, y * scalar);
    }

    public Vector divide(double scalar)
    {
        return new Vector((int) Math.round(x / scalar), (int) Math.round(y / scalar));
    }

    // Method to check if two points are parallel
    public boolean isParallel(Vector other)
    {
        // Cross product for 2D is just (x1*y2 - y1*x2)
        double crossProduct = this.x * other.y - this.y * other.x;
        return crossProduct == 0;
    }

    public Vector unitVector()
    {
        return this.divide(this.length());
    }

    public double length()
    {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public String toString()
    {
        return x + ", " + y;
    }

    public boolean equals(Vector other)
    {
        return other.x == x && other.y == y;
    }

    /**
     * Returns the largest distance between two squares, on the x, and y coordinate
     *
     * @param other the other point to compare distance to.
     * @return the largest distance between two points
     */
    public int distance(Vector other)
    {
        return Math.max(Math.abs(other.x - x), Math.abs(other.y - y));
    }
}