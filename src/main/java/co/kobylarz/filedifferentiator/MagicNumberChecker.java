package co.kobylarz.filedifferentiator;


public class MagicNumberChecker {

    byte[] hexSingature;
    boolean[] mask;
    int bytesCount;

    public MagicNumberChecker(byte[] hexSingature, boolean[] mask) {
        this.hexSingature = hexSingature;
        this.mask = mask;
        this.bytesCount = hexSingature.length;

    }
    //Check if magic number appears in input data
    public boolean matches(byte[] dataFile) {
        if (dataFile.length < hexSingature.length) {
            return false;
        }


        for (int i = 0; i < hexSingature.length; i++) {
            if (mask[i] == true) {
                if (dataFile[i] != hexSingature[i]) {
                    return false;
                }
            }
        }

        return true;
    }

    public int getBytesCount() {
        return bytesCount;
    }

    public void setBytesCount(int bytesCount) {
        this.bytesCount = bytesCount;
    }

    public byte[] getHexSingature() {
        return hexSingature;
    }

    public void setHexSingature(byte[] hexSingature) {
        this.hexSingature = hexSingature;
    }

    public boolean[] getMask() {
        return mask;
    }

    public void setMask(boolean[] mask) {
        this.mask = mask;
    }
}
