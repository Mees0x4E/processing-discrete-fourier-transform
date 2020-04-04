public class Path {
  public Complex[] path;
  public Path(Complex[] path) {
    this.path = path;
  }
  public Path(int numPoints) {
    path = new Complex[numPoints];
  }
  public Complex set(int index, Complex c) {
    return path[index]=c;
  }
  public Complex get(int index) {
    return path[index];
  }
  public Complex lerp(double progress) {
    if (progress>=1) System.out.println(progress);
    progress%=1d;
    int lastIndex = path.length;
    double d = lastIndex*progress;
    int lower = (int)Math.floor(d);
    int upper = ((int)Math.ceil(d))%path.length;
    return Complex.lerp(path[lower], path[upper], d-lower);
  }
}