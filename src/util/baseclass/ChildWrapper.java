package util.baseclass;

import model.BaseClass;

public class ChildWrapper {
    private BaseClass child;
    private double ratio;
    private double absWeight;

    public ChildWrapper(BaseClass childObject, double ratio, double absWeight) {
        this.child = childObject;
        this.ratio = ratio;
        this.absWeight = absWeight;
    }

    // Constructor without absWeight (optional parameter)
    public ChildWrapper(BaseClass childObject, double ratio) {
        this.child = childObject;
        this.ratio = ratio;
        // Default value for absWeight
        this.absWeight = 0.0; // Or any other default value you want
    }

    public BaseClass getChild() {
        return child;
    }

    public double getRatio() {
        return ratio;
    }

    public double getAbsWeight() {
        return absWeight;
    }

    public void setChild(BaseClass baseclass) {
        this.child = baseclass;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public void setAbsWeight(double absWeight) {
        this.absWeight = absWeight;
    }
}
