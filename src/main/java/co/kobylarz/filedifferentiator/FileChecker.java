package co.kobylarz.filedifferentiator;

public class FileChecker {

    String extention;
    MagicNumberChecker[] listOfHexSignature;

    public FileChecker(String extention, MagicNumberChecker[] listOfHexSignature) {
        this.extention = extention;
        this.listOfHexSignature = listOfHexSignature;
    }

    //Check if data input fits to any patterns
    public boolean matchesMaginNumber(byte[] dataFile) {
        for (MagicNumberChecker magicNumberChecher : listOfHexSignature
        ) {
            if(magicNumberChecher.matches(dataFile)){
                return true;
            }
        }
        return false;
    }

    //Check if file name has correct extention for file checker
    public boolean matchesExtention(String fileExtension){
        return fileExtension.equals(extention);
    }

    //Find max number of bytes to check
    public int findMaxBytesCount(){
        int maxBytes = 0;
        for (MagicNumberChecker magicNumberChecker: listOfHexSignature
             ) {
            maxBytes = Math.max(maxBytes, magicNumberChecker.bytesCount);
        }
        return maxBytes;
    }



    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public MagicNumberChecker[] getListOfHexSignature() {
        return listOfHexSignature;
    }

    public void setListOfHexSignature(MagicNumberChecker[] listOfHexSignature) {
        this.listOfHexSignature = listOfHexSignature;
    }
}
