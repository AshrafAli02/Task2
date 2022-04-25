package Main.Java.DataModel;

public class Color {
    public String ColorID;
    public String ColorName;
    public double Amount;

    @Override
    public String toString() {
        return "Color Name \t:" + this.ColorName + "\nColor Amount \t :" + this.Amount;
    }

}
