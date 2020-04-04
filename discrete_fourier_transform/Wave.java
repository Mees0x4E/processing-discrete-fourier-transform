public class Wave {
  public int frequency;
  public Complex constant; // the value which this wave represents at t=0
  public Complex current;
  public Complex step; // this represents how far the wave moves in one step
  private static final double TWO_PI = 2*Math.PI;
  public Wave(Complex vec, int freq, double dt) {
    frequency = freq;
    constant = vec.copy();
    current = vec.copy();
    step = Complex.exp(TWO_PI*dt*frequency);
  }
  // @param t: a number from 0 to 1 representing time
  public Complex value(double t) {
    current = Complex.exp(TWO_PI*t*frequency).mult(constant);
    return current;
  }
  public Complex next() {
    current.mult(step);
    return current;
  }
  public String toString() {
    return constant.toString()+", frequency: "+frequency;
  }
  public Complex reset() {
    current = constant.copy();
    return current;
  }
}
