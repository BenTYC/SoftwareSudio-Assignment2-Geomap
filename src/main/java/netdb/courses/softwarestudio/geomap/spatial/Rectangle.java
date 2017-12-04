package netdb.courses.softwarestudio.geomap.spatial;

/**
 * A generalized rectangle in a geometric space.
 */
public class Rectangle extends Shape {
	protected Point lower;
	protected Point upper;

	public Rectangle(Point lower, Point upper) {
		if (lower.getDimension() < 2 || lower.getDimension() != upper.getDimension())
			throw new IllegalArgumentException();
		
		//調整成左上右下的點
		double xUp = upper.getX();
		double yUp = upper.getY();
		double xLow = lower.getX();
		double yLow = lower.getY();
		double temp;
		
		if(xUp > xLow){
			temp = xUp;
			xUp = xLow;
			xLow = temp;
		}
		if(yLow > yUp){
			temp=yLow;
			yLow=yUp;
			yUp=temp;
		}
		
		double[] leftUp = { xUp, yUp};
		double[] rightLow = { xLow, yLow};
		
		this.upper = new Point(leftUp); //存左上
		this.lower = new Point(rightLow); //存右下
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		
		if(!(obj instanceof Rectangle)) return false;
		Rectangle rec = (Rectangle) obj;
		return rec.lower.equals(lower) && rec.upper.equals(upper);
	}
	
	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer("Circle@{");
		ret.append(lower.toString());
		ret.append(", " + upper.toString() + "}");
		return ret.toString();
	}

	public double getSide(int dim) {
		return upper.getCoordinate(dim) - lower.getCoordinate(dim);
	}
	
	@Override
	public double getVolume() {
		double sum = 1d;
		for (int i = 0; i < upper.getDimension(); i++) {
			sum *= getSide(i);
		}
		return sum;
	}
	
	public Point getPointLeftUp(){
		double[] coordinatesLeftUp= { this.upper.getX(), this.upper.getY()};
		return new Point(coordinatesLeftUp);
	}
	
	public Point getPointLeftLow(){
		double[] coordinatesLeftLow = { this.upper.getX(), this.lower.getY()};
		return new Point(coordinatesLeftLow);
	}
	
	public Point getPointRightUp(){
		double[] coordinatesRightUp = { this.lower.getX(), this.upper.getY()};
		return new Point(coordinatesRightUp);
	}
	
	public Point getPointRightLow(){
		double[] coordinatesRightLow = { this.lower.getX(), this.lower.getY()};
		return new Point(coordinatesRightLow);
	}
	
	public boolean pointInShapeOrNot(Point p){
		//點p是否在左上右下之間
		if( p.getX() >= this.upper.getX() && p.getX() <= this.lower.getX()
				&& p.getY() >= this.lower.getY() && p.getY() <= this.upper.getY() )
			return true;
		else
			return false;
	}
	
	@Override
	public boolean IntersectRectangleOrNot(Rectangle r) {
		//雙重檢查四個點是否在另一個中
		if( r.pointInShapeOrNot(this.getPointLeftUp()) 
				|| r.pointInShapeOrNot(this.getPointLeftLow()) 
				|| r.pointInShapeOrNot(this.getPointRightUp())
				|| r.pointInShapeOrNot(this.getPointRightLow()) )
			return true;
		if( this.pointInShapeOrNot(r.getPointLeftUp()) 
				|| this.pointInShapeOrNot(r.getPointLeftLow())
				|| this.pointInShapeOrNot(r.getPointRightUp())
				|| this.pointInShapeOrNot(r.getPointRightLow()) )
			return true;	
		return false;
	}	
	
	//定義常數表示point與rec的相對位置
	public static final int POINT_INSIDE_REC = 0;
	public static final int POINT_UPPERLEFT_FROM_REC = 1;
	public static final int POINT_UPPER_FROM_REC = 2;
	public static final int POINT_UPPERRIGHT_FROM_REC = 3;
	public static final int POINT_RIGHT_FROM_REC = 4;
	public static final int POINT_BOTTOMRIGHT_FROM_REC = 5;
	public static final int POINT_BOTTOM_FROM_REC = 6;
	public static final int POINT_BOTTOMLEFT_FROM_REC = 7;
	public static final int POINT_LEFT_FROM_REC = 8;
	public static final int POSITION_ERROR = 9;
	
	//判斷Point與Rectangle的相對位置
	public int pointPositionOfRectangle(Point p)
	{
		if( this.pointInShapeOrNot(p) )
			return POINT_INSIDE_REC;
		
		if( p.getX() < this.getPointLeftUp().getX() 
				&& p.getY() > this.getPointLeftUp().getY() )
			return POINT_UPPERLEFT_FROM_REC;
		
		if( p.getX() <= this.getPointRightLow().getX() 
				&& p.getX() >= this.getPointLeftUp().getX() 
				&& p.getY() >= this.getPointLeftUp().getY() )
			return POINT_UPPER_FROM_REC;
			//dismin=y-y1;
		
		if( p.getX() > this.getPointRightLow().getX() 
				&& p.getY() > this.getPointLeftUp().getY() )
			return POINT_UPPERRIGHT_FROM_REC;
		
		if( p.getX() >= this.getPointRightLow().getX() 
				&& p.getY() <= this.getPointLeftUp().getY() 
				&& p.getY() >= this.getPointRightLow().getY() )
			return POINT_RIGHT_FROM_REC;		
			//dismin=x-x2;
		
		if( p.getX() > this.getPointRightLow().getX() 
				&& p.getY() < this.getPointRightLow().getY() )
			return POINT_BOTTOMRIGHT_FROM_REC;
		
		if( p.getX() <= this.getPointRightLow().getX() 
				&& p.getX() >= this.getPointLeftUp().getX() 
				&& p.getY() <= this.getPointRightLow().getY())
			return POINT_BOTTOM_FROM_REC;
			//dismin=y2-y;
		
		if( p.getX() < this.getPointLeftUp().getX() 
				&& p.getY() < this.getPointRightLow().getY() )
			return POINT_BOTTOMLEFT_FROM_REC;

		if( p.getX() <= this.getPointLeftUp().getX() 
				&& p.getY() <= this.getPointLeftUp().getY() 
				&& p.getY() >= this.getPointRightLow().getY() )
			return POINT_LEFT_FROM_REC;
			//dismin=x1-x;	
		System.out.println("Error: pointPositionOfRectangle");
		return POSITION_ERROR;
	}
	
	public double distanceFromPoint(Point p)
	{
		int pointPositionOfRectangle = this.pointPositionOfRectangle(p);
		double distance = 0;
		
		switch(pointPositionOfRectangle){
			case POINT_INSIDE_REC:
				distance = 0;
				break;
			case POINT_UPPERLEFT_FROM_REC:
				distance = p.distanceFromPoint( this.getPointLeftUp() );
				break;
			case POINT_UPPER_FROM_REC:
				distance = p.getY() - this.upper.getY();
				break;
			case POINT_UPPERRIGHT_FROM_REC:
				distance = p.distanceFromPoint( this.getPointRightUp() );
				break;
			case POINT_RIGHT_FROM_REC:
				distance = p.getX() - this.getPointRightUp().getX();
				break;
			case POINT_BOTTOMRIGHT_FROM_REC:
				distance = p.distanceFromPoint( this.getPointRightLow() );
				break;
			case POINT_BOTTOM_FROM_REC:
				distance = this.lower.getY() - p.getY();
				break;
			case POINT_BOTTOMLEFT_FROM_REC:
				distance = p.distanceFromPoint( this.getPointLeftLow() );
				break;
			case POINT_LEFT_FROM_REC:
				distance = this.getPointLeftLow().getX() - p.getX();
				break;
			default:
				distance = 99899;
				break;
		}
				
		return Math.abs(distance);		
	}
	
}
