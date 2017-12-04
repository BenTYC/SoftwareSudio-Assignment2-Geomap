package netdb.courses.softwarestudio.geomap.map;

import java.util.ArrayList;
import netdb.courses.softwarestudio.geomap.spatial.Point;
import netdb.courses.softwarestudio.geomap.spatial.Rectangle;
import netdb.courses.softwarestudio.geomap.spatial.Shape;

public class GeoMapImpl implements GeoMap
{
	//用ArrayList來放shapes
	private ArrayList<Shape> shapesList;
	
	public GeoMapImpl() {
		this.shapesList = new ArrayList<Shape>(); 
	}
	
	/**
	 * Add a Shape object to the map.
	 * 
	 * @param s the Shape object you want to add
	 */ 
	public void addShape(Shape s){
		this.shapesList.add(s);
	}
	
	/**
	 * Add a group of Shape objects to the map.
	 * 
	 * @param s a group of Shape objects
	 */
	public void addShapes(Shape[] s){
		for(Shape i : s)
			this.addShape(i);
	}
	
	/**
	 * Query Shape objects covered by a specific range in this map.
	 * 
	 * @param rec the range you want to query
	 * @return the Shape objects in the range
	 */
	public Shape[] rangeQuery(Rectangle rec)
	{
		Shape[] coveredShapes = new Shape[this.shapesList.size()]; 
		int i, coveredCount = 0;
		
		//檢查所有shapes有沒有跟rec重合，有就放入coveredShapes
		for(i = 0; i < this.shapesList.size(); i++){
			if( this.shapesList.get(i).IntersectRectangleOrNot(rec) ){
				coveredShapes[coveredCount] = this.shapesList.get(i);
				//System.out.println("In shape: "+i);
				coveredCount++;
			}
		}
		
		Shape[] ShapesForReturn = new Shape[coveredCount];
		for(i = 0; i < coveredCount; i++){
			ShapesForReturn[i] = coveredShapes[i];
		}
		
		return ShapesForReturn;
	}
	
	/**
	 * Query the closest k Shape objects from the point p in this map.
	 * 
	 * @param p a point you want to query from
	 * @param k the number of objects you want to query
	 * @return the closest k Shape objects
	 */
	public Shape[] knnQuery(Point p, int k)
	{
		double[] shapeDistanceFromP = new double[this.shapesList.size()];
		int indexOfRankedShapes[] = new int[this.shapesList.size()];
		Shape[] shapesForReturn = new Shape[k]; 
		int i, j, temp;
		double tempD;
		
		//Initialize array
		for(i = 0; i < this.shapesList.size(); i++)
			indexOfRankedShapes[i] = i;
		
		//列出每個shape與point的distance
		for(i = 0; i < this.shapesList.size(); i++)
			shapeDistanceFromP[i] = this.shapesList.get(i).distanceFromPoint(p);
		
		//根據distance對shapesList做sorting (最短的排最前)
		for (i = 0; i < this.shapesList.size(); i++){
			for (j = i + 1; j < this.shapesList.size(); j++) {
				if ( shapeDistanceFromP[i] > shapeDistanceFromP[j] ) {
					temp = indexOfRankedShapes[i];
					tempD = shapeDistanceFromP[i];
					indexOfRankedShapes[i] = indexOfRankedShapes[j];
					shapeDistanceFromP[i] = shapeDistanceFromP[j];
					indexOfRankedShapes[j] = temp;
					shapeDistanceFromP[j] = tempD;
				}
			}
		}
		
		
		//抓出前k個回傳
		for(i = 0; i < k; i++){
			shapesForReturn[i] = this.shapesList.get(indexOfRankedShapes[i]);
		}
		
		return shapesForReturn;
	}
	
}
