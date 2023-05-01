import java.util.Scanner;
import java.util.regex.Pattern;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите арифметическое выражение: ");
        String input = scanner.nextLine();

        String result = calc(input);
        if (!result.isEmpty()) System.out.print("Результат: " + result);

    }
    public static String calc(String input){
        String result = "";

        // Проверка входных данных, если пусто то выбросить исключение!
        if (input.isEmpty()) throw new IllegalArgumentException("Вы ввели пустую строку!");

        // Удаление пробелов в начале и конце строки
        input = input.trim();
        String[] string_calc = input.split(" ");
        // for (String i : string_calc) System.out.println(i);
        if (string_calc.length != 3){
            throw new IllegalArgumentException("Неверное количество аргументов!");
        }
        String format = formatNumber(string_calc[0]);
        switch (format){
            case "ARABIC":
                int a = parseNumber(string_calc[0]);
                int b = parseNumber(string_calc[2]);
                result = Integer.toString(calcArabicNamber(a, string_calc[1], b));
                break;
            case "ROMAN":
                int a1 = 0, b1 = 0;
                a1 = romanToArabic(string_calc[0]);
                b1 = romanToArabic(string_calc[2]);

                int res = calcArabicNamber(a1, string_calc[1], b1);
                if (res < 1) throw new IllegalArgumentException("Результат не может быть меньше 1!");
                result = arabicToRoman(res);
                break;
            default: throw new IllegalArgumentException("Введен неверный символ!");
        }
        return result;
    }

    static int calcArabicNamber(int a, String operator, int b){
        int result;
        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            default:
                throw new IllegalArgumentException("Неверная операция");
        }
        return result;
    }

    static int parseNumber(String str) {
        int num;
        try {
            num = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа");
        }
        if (num < 1 || num > 10) {
            throw new IllegalArgumentException("Число должно быть от 1 до 10 включительно");
        }
        return num;
    }

    static String formatNumber(String input) {
        String res ="";
        // Создаем Scanner для считывания строки
        Scanner scanner = new Scanner(input);

        // Используем Pattern для определения формата строки
        Pattern arabicPattern = Pattern.compile("^\\d+$");
        Pattern romanPattern = Pattern.compile("^[IVX]+$");

        // Проверяем, соответствует ли строка формату арабских или римских цифр
        if (scanner.hasNext(arabicPattern)) res = "ARABIC";
        if (scanner.hasNext(romanPattern)) res = "ROMAN";
        return res;
    }

    static int romanToArabic(String str){
        String[] romanSymbols = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "XI", "X"};
        int[] arabicValues = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int i = 0;
        for (String symbl: romanSymbols){
            if (!symbl.equals(str)) i++;
            else break;
        }
        return arabicValues[i];
    }

    static String arabicToRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 3999");
        }

        // Создаем массив для хранения римских цифр и соответствующих им арабских значений
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder roman = new StringBuilder();

        // Проходимся по массиву арабских значений и вычитаем их из числа пока оно не станет равным 0
        for (int i = 0; i < arabicValues.length; i++) {
            while (number >= arabicValues[i]) {
                number -= arabicValues[i];
                roman.append(romanSymbols[i]);
            }
        }

        return roman.toString();
    }
}
