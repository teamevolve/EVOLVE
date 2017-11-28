package graphing;

import java.util.ArrayList;

/**
 * @author alexdennis
 */

public class GraphParameters {
  ArrayList<AxisType> vAxes;
  AxisType hAxis;

  public GraphParameters() {
    vAxes = new ArrayList<AxisType>();
  }

  public void addVerticalAxis(AxisType axis) {
    vAxes.add(axis);
  }

  public ArrayList<AxisType> getVerticalAxes() {
    return vAxes;
  }

  public void setHorizontalAxis(AxisType axis) {
    hAxis = axis;
  }

  public AxisType getHorizontalAxis() {
    return hAxis;
  }
}
