package frc.robot;
import java.util.function.DoubleSupplier;

public class NumberInator {
  double lastNum = 0.0;

  public NumberInator() {};

  public double getNext() {
    this.advance();
    return this.get();
   }

   public void advance() {
    this.lastNum += 1e-2;
    this.lastNum %= 1.0;
   }

   public double get() {
    return this.lastNum;
   }

   public DoubleSupplier supplier = () -> this.getNext();
}
