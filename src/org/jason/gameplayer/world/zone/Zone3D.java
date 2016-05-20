package org.jason.gameplayer.world.zone;

public class Zone3D extends Zone{

	public final static String X_DIM = "x";
	public final static String Y_DIM = "y";
	public final static String Z_DIM = "z";
	
	
	public Zone3D(int x, int y, int z) {
		super();

		setDimension(X_DIM, x);
		setDimension(Y_DIM, y);
		setDimension(Z_DIM, z);
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
	
	public int getZ() {
		return getDimension(Z_DIM);
	}
	public void setZ(int z) {
		setDimension(Z_DIM, z);
	}

	@Override
	public boolean equals(Zone o) 
	{
		return 	(getX() == o.getDimension(X_DIM)) && 
				(getY() == o.getDimension(Y_DIM)) && 
				(getZ() == o.getDimension(Z_DIM)); 
	}

	@Override
	public int compareTo(Zone o) 
	{
		return 	(getX() - o.getDimension(X_DIM)) - 
				(getY() - o.getDimension(Y_DIM)) -
				(getZ() - o.getDimension(Z_DIM)); 
	}
}
