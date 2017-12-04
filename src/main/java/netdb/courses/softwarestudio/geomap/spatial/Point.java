package netdb.courses.softwarestudio.geomap.spatial;

/**
 * A point in a geometric space.
 */
public class Point extends Shape {
	private double[] coordinates;

	public Point(double[] coordinates) {
		if (coordinates.length < 1)
			throw new IllegalArgumentException();
		this.coordinates = new double[coordinates.length];
		System.arraycopy(coordinates, 0, this.coordinates, 0, coordinates.length);
	}
	
	public double getX(){
		return coordinates[0];
	}
	
	public double getY(){
		return coordinates[1];
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		
		if(!(obj instanceof Point)) return false;
		Point p = (Point) obj;
		if (p.coordinates.length != coordinates.length)
			return false;
		for (int i = 0; i < coordinates.length; i++) {
			if (Double.compare(p.coordinates[i], coordinates[i]) != 0)
				return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer("Point@{");
		for (int i = 0; i < coordinates.length; i++) {
			if(i > 0) ret.append(", "); 
			ret.append(coordinates[i]);
		}
		ret.append("}");
		return ret.toString();
	}

	public double getCoordinate(int dim) {
		return coordinates[dim];
	}

	public int getDimension() {
		return coordinates.length;
	}

	@Override
	public double getVolume() {
		return 0;
	}
	
	//與另一點距離
	public double getDistance(Point p){
		double distanceX = p.getX() - this.getX() ;
		double distanceY = p.getY() - this.getY() ;
		return Math.sqrt( distanceX * distanceX + distanceY * distanceY );
	}

	@Override
	public boolean pointInShapeOrNot(Point p) {
		Double pX = new Double(p.getX());
		Double pY = new Double(p.getY());
		if(pX.equals(this.getX()) && pY.equals(this.getY()))
			return true;
		else
			return false;
	}

	@Override
	public boolean IntersectRectangleOrNot(Rectangle rec) {
		return rec.pointInShapeOrNot(this);	
	}

	@Override
	public double distanceFromPoint(Point p) {
		return this.getDistance(p);
	}	

}
