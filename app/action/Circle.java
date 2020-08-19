package action;

public class Circle implements GeometricObject{
    public static float PI = 3.14f;
    public float r;
    @Override
    public float computArea() {
        return PI*r*r;
    }
    public Circle(float r){
       this.r = r;
    }
}
