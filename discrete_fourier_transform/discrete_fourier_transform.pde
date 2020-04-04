Wave[] waves;
ArrayList<Complex> drawing;
ArrayList<Complex> shape;
Path path;
int displayFrames;
int total;

static final int WAIT = 0;
static final int DRAW = 1;
static final int DISPLAY = 2;

int mode=WAIT;

void setup() {
  size(800, 600);
  background(0);
  stroke(255);
  strokeWeight(5);
  point(width/2, height/2);
}
void draw() {
  background(0);
  translate(width/2, height/2);
  if (mode==DRAW) {
    drawing.add(new Complex(mouseX-width/2, mouseY-height/2));
    noFill();
    stroke(255);
    beginShape();
    for (Complex c : drawing) {
      vertex((float)c.re, (float)c.im);
    }
    endShape();
  } else if (mode==DISPLAY) {
    if (displayFrames>=total) {
      displayFrames = 0;
      for (Wave w : waves) {
        w.reset();
        shape.clear();
      }
      printArray(shape.toArray());
    }
    Complex sum = Complex.ZERO.copy();
    Complex prev = Complex.ZERO.copy();
    noFill();
    strokeWeight(3);
    for (Wave w : waves) {
      sum.add(w.next());
      stroke(255, 150);
      line((float)prev.re, (float)prev.im, (float)sum.re, (float)sum.im);
      stroke(255, 70);
      ellipse((float)sum.re, (float)sum.im, 2*(float)w.constant.mag(), 2*(float)w.constant.mag());
      prev = sum.copy();
    }
    stroke(255);
    shape.add(sum);
    strokeWeight(5);
    beginShape();
    for (Complex c : shape) {
      vertex((float)c.re, (float)c.im);
    }
    endShape();
    displayFrames++;
  }
}
void mousePressed() {
  if (mode==WAIT||mode==DISPLAY) {
    mode=DRAW;
    drawing = new ArrayList<Complex>();
  }
}
void mouseReleased() {
  if (mode==DRAW) {
    mode = DISPLAY;
    Complex[] drawingArray = new Complex[drawing.size()];
    drawing.toArray(drawingArray);
    path = new Path(drawingArray);
    waves = dft(path, -drawing.size()/2, drawing.size()-drawing.size()/2-1, drawing.size());
    shape = new ArrayList<Complex>();
    displayFrames = 0;
  }
}
// minFreq and maxFreq are inclusive
// N is the number of steps
Wave[] dft(Path x, int minFreq, int maxFreq, int N) {
  printArray(x.path);
  double dt = 1d/N;
  total = N;
  Wave[] X = new Wave[abs(minFreq)+abs(maxFreq)+1];//+1because of 0
  for (int i = 0, k = minFreq; k<=maxFreq; i++, k++) {
    Complex sum = Complex.ZERO.copy();
    int n = 0;
    Wave w = new Wave(Complex.ONE, -k, dt);
    for (double t = 0; n<N; n++, t+=dt) {
      sum.add(Complex.mult(x.lerp(t), w.current));
      w.next();
    }
    sum.div(abs(minFreq)+abs(maxFreq)+1);
    X[i] = new Wave(sum, k, dt);
  }
  return X;
}
