package Main.Java.DataModel;

public class Color {
    public String ColorID;
    public String ColorName;
    public double Amount;

    @Override
    public String toString() {
        return this.ColorID + "\t" + this.ColorName + "\t" + this.Amount;
    }

}
