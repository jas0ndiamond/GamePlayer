package org.jason.gameplayer.world.zone;

public class Zone2D extends Zone{

	public final static String X_DIM = "x";
	public final static String Y_DIM = "y";
	
	
	public Zone2D(int x, int y) {
		super();

		setDimension(X_DIM, x);
		setDimension(Y_DIM, y);
	}
	
	public int getX() {
		return getDimension(X_DIM);
	}
	public void setX(int x) {
		setDimension(X_DIM, x);
	}
	public int getY() {
		return getDimension(Y_DIM);
	}
	public void setY(int y) {
		setDimension(Y_DIM, y);
	}

	@Override
	public boolean equals(Zone o) 
	{
		return (getX() == o.getDimension(X_DIM)) && (getY() == o.getDimension(Y_DIM)); 
	}

	@Override
	public int compareTo(Zone o) 
	{
		//return (getX() - o.getDimension(X_DIM)) - (getY() - o.getDimension(Y_DIM)); 
		
        double delta = (getX()*getX() + getY()*getY()) - (o.getDimension(X_DIM)*o.getDimension(X_DIM) + o.getDimension(Y_DIM)*o.getDimension(Y_DIM));
        if (delta < 0) return -1;
        if (delta > 0) return +1;
        return 0;
	}
}
