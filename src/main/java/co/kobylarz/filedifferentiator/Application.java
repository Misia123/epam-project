package co.kobylarz.filedifferentiator;

import java.io.*;

public class Application {


    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: <File path>");
            System.exit(1);
        }
        String fileName = args[0];
        FileChecker[] listOfExtenstionChecker = initCheckers();


    }


    public static FileChecker[] initCheckers() {
        //Paterrns for JPG
        MagicNumberChecker jpgPattern1 = new MagicNumberChecker(new byte[]{
                (byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xDB
        }, new boolean[]{true, true, true, true});
        MagicNumberChecker jpgPattern2 = new MagicNumberChecker(new byte[]{
                (byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0, (byte) 0x00, (byte) 0x10, (byte) 0x4A, (byte) 0x46, (byte) 0x49, (byte) 0x46, (byte) 0x00, (byte) 0x01
        }, new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true});
        MagicNumberChecker jpgPattern3 = new MagicNumberChecker(new byte[]{
                (byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xEE
        }, new boolean[]{true, true, true, true});
        MagicNumberChecker jpgPattern4 = new MagicNumberChecker(new byte[]{
                (byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE1, (byte) 0x00, (byte) 0x00, (byte) 0x45, (byte) 0x78, (byte) 0x69, (byte) 0x66, (byte) 0x00, (byte) 0x00
        }, new boolean[]{true, true, true, true, false, false, true, true, true, true, true, true});

        //Patterns for GIF
        MagicNumberChecker gifPattern1 = new MagicNumberChecker(new byte[]{
                (byte) 0x47, (byte) 0x49, (byte) 0x46, (byte) 0x38, (byte) 0x37, (byte) 0x61
        }, new boolean[]{true, true, true, true, true, true});
        MagicNumberChecker gifPattern2 = new MagicNumberChecker(new byte[]{
                (byte) 0x47, (byte) 0x49, (byte) 0x46, (byte) 0x38, (byte) 0x39, (byte) 0x61
        }, new boolean[]{true, true, true, true, true, true});

        FileChecker jpgFileChecker = new FileChecker("JPG", new MagicNumberChecker[]{jpgPattern1, jpgPattern2, jpgPattern3, jpgPattern4});
        FileChecker gifFileChecker = new FileChecker("GIF", new MagicNumberChecker[]{gifPattern1, gifPattern2});
        //There is no magic pattern for TXT - it is a special file extention. Every file can be txt that does not fit into other known extension.
        FileChecker txtFileChecker = new FileChecker("TXT", new MagicNumberChecker[0]);


        return new FileChecker[]{jpgFileChecker, gifFileChecker, txtFileChecker};

    }


}
