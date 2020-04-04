public class Complex {
  public static final Complex ZERO = new Complex(0, 0);
  public static final Complex ONE = new Complex(1, 0);
  public static final Complex NEGATIVE_ONE = new Complex(-1, 0);
  public static final Complex I = new Complex(0, 1);
  public static final Complex NEGATIVE_I = new Complex(0, -1);
  public double re, im;
  public Complex(double re, double im) {
    this.re = re;
    this.im = im;
  }
  public Complex add(Complex c) {
    re+=c.re;
    im+=c.im;
    return this;
  }
  public static Complex add(Complex a, Complex b) {
    return a.copy().add(b);
  }
  public Complex sub(Complex c) {
    re-=c.re;
    im-=c.im;
    return this;
  }
  public static Complex sub(Complex a, Complex b) {
    return a.copy().sub(b);
  }
  public Complex mult(Complex c) {
    double re = this.re*c.re-this.im*c.im;
    double im = this.re*c.im+this.im*c.re;
    this.re = re;
    this.im = im;
    return this;
  }
  public static Complex mult(Complex a, Complex b) {
    return a.copy().mult(b);
  }
  public Complex mult(double d) {
    re*=d;
    im*=d;
    return this;
  }
  public static Complex mult(Complex c, double d) {
    return c.copy().mult(d);
  }
  Complex div(double d) {
    re/=d;
    im/=d;
    return this;
  }
  public double magSq() {
    return re*re+im*im;
  }
  public double mag() {
    return Math.sqrt(magSq());
  }
  public Complex exp() {
    double scale = Math.exp(re);
    double re = Math.cos(this.im);
    double im = Math.sin(this.im);
    this.re = re;
    this.im = im;
    mult(scale);
    return this;
  }
  public static Complex exp(double d) { //e^ix = cos(x)+i•sin(x)
    return new Complex(Math.cos(d), Math.sin(d));
  }
  public static Complex exp(Complex c) { //e^(a+bi) = e^a•e^bi
    return exp(c.im).mult(Math.exp(c.re));
  }
  public Complex copy() {
    return new Complex(re, im);
  }
  public String toString() {
    return "re: "+re+", im: "+im;
  }
  public static Complex lerp(Complex a, Complex b, double d) {
    return a.copy().add(b.copy().sub(a).mult(d));
  }
}