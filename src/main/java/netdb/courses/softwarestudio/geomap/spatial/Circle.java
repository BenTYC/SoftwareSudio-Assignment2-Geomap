package netdb.courses.softwarestudio.geomap.spatial;

/**
 * A circle in a two-dimensional space.
 */
public class Circle extends Shape {
	private Point center;
	private double radius;

	public Circle(Point center, double radius) {
		if (center.getDimension() != 2)
			throw new IllegalArgumentException();
		this.center = center;
		this.radius = radius;
	}
		
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		
		if(!(obj instanceof Circle)) return false;
		Circle c = (Circle) obj;
		return c.center.equals(center) && Double.compare(c.radius, radius) == 0;
	}
	
	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer("Circle@{");
		ret.append(center.toString());
		ret.append(", " + radius + "}");
		return ret.toString();
	}

	@Override
	public double getVolume() {
		return Math.PI * radius * radius;
	}
	
	@Override
	public boolean pointInShapeOrNot(Point p){
		if( this.center.getDistance(p) <= this.radius )
			return true;
		else
			return false;
	}

	@Override
	public double distanceFromPoint(Point p) {
		if( this.pointInShapeOrNot(p) ){
			return 0;
		}
		return this.center.getDistance(p) - this.radius;
	}

	@Override
	public boolean IntersectRectangleOrNot(Rectangle rec) 
	{
		// 判斷圓心是否在rec中
		if( rec.pointInShapeOrNot(this.center) )
			return true;
		
		// rec任一點是否在圓中
		if( this.pointInShapeOrNot(rec.getPointLeftUp()) 
				|| this.pointInShapeOrNot(rec.getPointLeftLow())
				|| this.pointInShapeOrNot(rec.getPointRightUp())
				|| this.pointInShapeOrNot(rec.getPointRightLow()) )
			return true;
		
		// 判斷圓心與rec的最短距離是否小於半徑，是：相交
		if( rec.distanceFromPoint(this.center) <= this.radius )
			return true;
		
		return false;
	}

}
