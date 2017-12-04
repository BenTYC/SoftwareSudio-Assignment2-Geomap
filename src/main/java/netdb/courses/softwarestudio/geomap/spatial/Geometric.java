package netdb.courses.softwarestudio.geomap.spatial;

/**
 * Measurable in a geometric a space.
 */
public interface Geometric {
	
	String getSpaceName();
	
	double getVolume();
	
	boolean pointInShapeOrNot(Point p);
	
	double distanceFromPoint(Point p);
	
	boolean IntersectRectangleOrNot(Rectangle rec);
}
