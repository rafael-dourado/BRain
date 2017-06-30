package brain.util.math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import brain.util.math.Point2D;
public class Polygon2D {


	private List<Point2D> vertexes;

	public Polygon2D(List<Point2D> vertexes) {
		this.vertexes = new ArrayList<Point2D>(vertexes);
	}

	public List<Point2D> getVertexes() {
		return this.vertexes;
	}

	public Polygon2D(Point2D... point2ds) {
		for (Point2D point : point2ds) {
			vertexes.add(point);
		}
	}


	public boolean isValid(){
		return false;
	} 

	public boolean intersects(Polygon2D other){
		for(Point2D p : other.getVertexes()){
			if(this.hasInside(p))
				return true;
		}
		return false;
	}

	
	
	public boolean hasInside(Point2D point){
		int count = 0;
		Set<Point2D> vertexesIntAux = new HashSet<Point2D>();
		Line2D auxLine = new Line2D(new Point2D(0.0,point.getY()), point);
		for(int i = 0; i < vertexes.size(); i++){
			Point2D a = new Point2D(vertexes.get(i));
			Point2D b;
			
			if(i != vertexes.size()-1)
				b = new Point2D(vertexes.get(i+1));
			else
				b = new Point2D(vertexes.get(0));
			
			if(auxLine.contains(a)){
				vertexesIntAux.add(a);
			}
			
			Line2D edge = new Line2D(a,b);
			if(edge.getIntersctPointIn(auxLine) != null)
				count++;	
		}
		
		return (count - vertexesIntAux.size()) % 2 == 1;
	}

}

