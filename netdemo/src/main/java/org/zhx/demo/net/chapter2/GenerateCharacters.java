package org.zhx.demo.net.chapter2;

import java.io.IOException;
import java.io.OutputStream;

public class GenerateCharacters {
    public static void generateCharactersByByte(OutputStream out) throws IOException {
        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;//126-33+1
        int numberOfCharactersPerLine = 72;

        int start = 126;
        while (true) {
            for (int i = start; i < start + numberOfCharactersPerLine; i++) {
                out.write((
                        (i - firstPrintableCharacter) % numberOfPrintableCharacters)
                        + firstPrintableCharacter);
            }
            out.write('\r');//换行
            out.write('\n');

            start = ((start + 1) - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
        }
    }

    public static void generateCharactersByByteArray(OutputStream out) throws IOException {
        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;//126-33+1
        int numberOfCharactersPerLine = 72;
        byte[] line = new byte[numberOfCharactersPerLine + 2];
        line[72] = '\r';
        line[73] = '\n';
        int start = 126;

        int count = numberOfPrintableCharacters + 2;
        while (count-- > 0) {
            for (int i = start; i < start + numberOfCharactersPerLine; i++) {
                line[i - start] = (byte)((
                        (i - firstPrintableCharacter) % numberOfPrintableCharacters)
                        + firstPrintableCharacter);
            }
            out.write(line);
            start = ((start + 1) - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
        }
    }


    public static void main(String args[]) {
        try {
            //GenerateCharacters.generateCharactersByByte(System.out);
            GenerateCharacters.generateCharactersByByteArray(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
