package action;

public class MyRectangle implements GeometricObject{
    public float w;
    public float l;

    public MyRectangle(float w,float l){
        this.w = w;
        this.l = l;
    }
    @Override
    public float computArea() {
        return w * l ;
    }
}
