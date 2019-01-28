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

        int maxBytesToCheck = findMaxBytesSizeForPatterns(listOfExtenstionChecker);
        byte[] data = readFile(fileName, maxBytesToCheck);

        mainChecker(extensionExtract(fileName), listOfExtenstionChecker, data);


    }

    public static int findMaxBytesSizeForPatterns(FileChecker[] listOfFileCheckers) {
        int maxBytesToCheck = 0;
        for (FileChecker fileChecker : listOfFileCheckers
        ) {
            maxBytesToCheck = Math.max(maxBytesToCheck, fileChecker.findMaxBytesCount());
        }
        return maxBytesToCheck;
    }

    //The main logic for extension and magic number
    public static void mainChecker(String fileExtension, FileChecker[] listOfExtenstionChecker, byte[] data) {

        FileChecker checkerForThisExtension = null;

        for (FileChecker fileChecker : listOfExtenstionChecker
        ) {

            if (fileChecker.matchesExtention(fileExtension)) {
                checkerForThisExtension = fileChecker;
                break;
            }
        }
        if (checkerForThisExtension == null) {
            throw new IllegalArgumentException("Extenstion is not supported");
        }

        //txt never meets this condition
        if (checkerForThisExtension.matchesMaginNumber(data)) {
            System.out.println("A " + fileExtension + " file is a correct " + fileExtension + " file");
            return;
        }

        //checker not fitting to extension. never meets txt
        FileChecker otherMatchingChecker = null;

        for (FileChecker fileChecker : listOfExtenstionChecker) {
            if (fileChecker.matchesMaginNumber(data)) {
                otherMatchingChecker = fileChecker;
                break;
            }
        }

        if (otherMatchingChecker != null) {

            System.err.println("Real extension for " + fileExtension + " should be " + otherMatchingChecker.getExtention());

        } else {

            if (checkerForThisExtension.getExtention().equals("TXT")) {
                //TXT
                System.out.println("This seems to be a true TXT file - no other extension matching");

            } else {

                System.err.println("This file extension is invalid - it is not a TXT nor matches known extensions");

            }

        }


    }

    //Extract the extension from file name
    public static String extensionExtract(String fileName) {
        if (fileName.length() < 3) {
            throw new IllegalArgumentException("File name is too short");
        }
//        String fileExtension = fileName.substring(fileName.length()-3).toUpperCase();
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        //       System.out.println(fileName.substring(lastIndexOf).toUpperCase());
        return fileName.substring(lastIndexOf + 1).toUpperCase();
    }

    //Read a file with max bytes check in pattern
    public static byte[] readFile(String file, int bytesCountToCheck) throws IOException {

        BufferedInputStream bf = null;
        byte[] dataToCheck = null;
        try {
            bf = new BufferedInputStream(new FileInputStream(new File(file)));
            dataToCheck = new byte[bytesCountToCheck];
            bf.read(dataToCheck);
            bf.close();
        } catch (FileNotFoundException e) {
            System.err.println("There is no such file");
            System.exit(1);
            //     e.printStackTrace();
        }

        return dataToCheck;

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
