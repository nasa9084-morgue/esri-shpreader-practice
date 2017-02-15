import java.io.FileInputStream;
import java.io.IOException;

import org.nocrala.tools.gis.data.esri.shapefile.ShapeFileReader;
import org.nocrala.tools.gis.data.esri.shapefile.exception.InvalidShapeFileException;
import org.nocrala.tools.gis.data.esri.shapefile.header.ShapeFileHeader;
import org.nocrala.tools.gis.data.esri.shapefile.shape.AbstractShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.PointData;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PolygonShape;

class Main {
    public static void main(String[] args)
        throws IOException, InvalidShapeFileException {

        FileInputStream is = new FileInputStream("shapes/limit.shp");
        ShapeFileReader r = new ShapeFileReader(is);
        ShapeFileHeader h = r.getHeader();

        System.out.println("The shape type of this file is " + h.getShapeType());

        int total = 0;
        AbstractShape s;
        while ((s = r.next()) != null) {
            total++;
            switch (s.getShapeType()) {
            case POINT:
                // Do something with the point shape
                break;
            case MULTIPOINT_Z:
                // Do something with the MultiPointZ shape
                break;
            case POLYGON:
                doSomethingPolygon((PolygonShape)s);
            }
        }
        System.out.println("total number of polygons is " + total);

        is.close();
    }

    public static void doSomethingPolygon(PolygonShape ps) {
        System.out.println("----- " + ps.getNumberOfParts() + " parts " +ps.getNumberOfPoints() + " points.");
        for (int i = 0; i <ps.getNumberOfParts(); i++) {
            PointData[] points = ps.getPointsOfPart(i);
            System.out.println("* part: " + i + " has " + points.length + " points.");
            // Print points with (X, Y) form
            for (PointData point : points) {
                System.out.println("(" + point.getX() + ", " + point.getY() + ")");
            }
        }
    }
}
