package org.cross.words.design;

public class SmallFontConverter {

    public static String convertToSmallFont(int number) {
        String[] smallFontDigits = {
                "₀", "₁", "₂", "₃", "₄", "₅", "₆", "₇", "₈", "₉"
        };

        String numberStr = String.valueOf(number);
        StringBuilder result = new StringBuilder();

        for (char digit : numberStr.toCharArray()) {
            if (Character.isDigit(digit)) {
                int smallFontIndex = Character.getNumericValue(digit);
                result.append(smallFontDigits[smallFontIndex]);
            } else {
                // Se não for um dígito, mantenha o caractere original
                result.append(digit);
            }
        }

        return result.toString();
    }
}
