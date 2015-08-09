package read.fujian.nyp.edu.read;

import java.util.Random;

public class Point {

    private int x;
    private int y;
    private static final Random RANDOM = new Random();

    public Point( int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public static Point randomPoint(int x) {
        return new Point(x, RANDOM.nextInt(40));
    }
}
