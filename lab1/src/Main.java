import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //не забудь поменять названия файлов и путь к ним
        String wayToFirstСhapter = "D:\\Labs_JAVA\\Ulitka\\lab1\\src\\glava1.txt";
        String wayToFirstСhapterEnc = "D:\\Labs_JAVA\\Ulitka\\lab1\\src\\glava1enc.txt";
        String wayToFirstСhapterDec = "D:\\Labs_JAVA\\Ulitka\\lab1\\src\\glava1.txt";
        String wayToAllСhaptersOnTom1 = "D:\\Labs_JAVA\\Ulitka\\lab1\\src\\voyna-i-mir-tom-1.txt";

        Map<Character, Double> table1;
        Map<Character, Double> table2;

        String firstСhapter = read(wayToFirstСhapter);//читаем 1 главу

        int shift = 3;//сдвиг для шифровки

        String firstСhapterEnc = enc(firstСhapter, shift, 0);//шифруем 1 главу (и дешифруем, путем сдвига по формуле shiftDec = 33 - shiftEnc)
        write(wayToFirstСhapterEnc, firstСhapterEnc);//пишем в файл

        //формирование частотных таблиц
        table1 = getTable(wayToFirstСhapterEnc);//таблица по зашифрованному файлу
        table2 = getTable(wayToAllСhaptersOnTom1);//таблица по всей книге

        //Вывод частотных таблиц
        System.out.println("table 1 : \n" + table1.toString());
        System.out.println("table 2 : \n" + table2.toString());

        shift = getDifferenceShift(table1, table2);//вычисление shiftDec(сдвиг для расшифровки) по формуле shiftDec = 33 - shiftEnc)

        String firstСhapterDec = enc(firstСhapterEnc, shift, 0); //расшифрованная 1 глава

        write(wayToFirstСhapterDec, firstСhapterDec);//пишем расшифрованную 1 главу в файл
    }


    public static String read(String wayToFile)  {
        StringBuffer firstСhapter = new StringBuffer();
        try {
            FileInputStream fileIn = new FileInputStream(wayToFile);
            InputStreamReader isr = new InputStreamReader(fileIn, StandardCharsets.UTF_8);//Открываем стрим

            while(fileIn.available() > 0){//пока есть символы
                firstСhapter.append((char)isr.read());//считываем
            }

            fileIn.close();
            isr.close();
        } catch (Exception e){
            System.out.println("err");
        }
        return firstСhapter.toString();//возвращаем считанное
    }

    public static void write(String wayToFile, String s){
        try {
            FileOutputStream fileOut = new FileOutputStream(wayToFile);
            OutputStreamWriter osw = new OutputStreamWriter(fileOut, StandardCharsets.UTF_8);

            osw.write(s);
            osw.close();
            fileOut.close();

        } catch (Exception e){
            System.out.println("err1");
        }
    }

    public static String enc(String text, int shift, int che){//che = 0!!!! ahahahaha
        String newText = "";
        for (char c : text.toCharArray()){//проходим в цикле по тексту,
            newText += getNewChar(c, shift, che);
        }

        return newText;
    }

    public static char getNewChar(char c, int shift, int che){
        char [] mass1 = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};
        char [] mass2 = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};

        int index;
        char newChar = c;
        if( c >= 1040 & c <= 1103 || c == 'ё' || c == 'Ё' ){

            if(c <= 1071){
                index = getIndex(c);
                newChar = mass1[(index + shift)% 33];
                if(che == 1){
                    System.out.println("c = " + c + " new c = " + newChar);
                }
            } else {
                index = getIndex(c);
                newChar = mass2[(index + shift)% 33];
                if(che == 1){System.out.println("c = " + c + " new c = " + newChar);}
            }

        }

        return newChar;
    }

    private static Map<Character, Double> getTable(String way){
        Map<Character, Double> table = new HashMap<Character, Double>();//структура которая хранит пары (ключ - значение)
        int lenght = 0;//общее кол-во символов
        for (char c : read(way).toCharArray()) {
            if( c >= 1040 & c <= 1103 || c == 'ё' || c == 'Ё' ){
                if(!table.containsKey(c)){//если таблица не включает символ с, то
                    table.put(c, 1.0);// то мы добавляем 1 по ключу (с),
                } else {//иначе
                    table.put(c, table.get(c) + 1);//берем символ из мапы и увеличиваем его значение на 1
                }
                lenght++;//увеличиваем кол-во символов
            }
        }

        for (Character key : table.keySet()) {
            table.put(key, table.get(key)/lenght);
        }

        return table;
    }

    private static int getDifferenceShift(Map<Character, Double> table1, Map<Character, Double> table2){
        int shift;
        double bigElem = -1;
        Character c1 = ' ', c2 = ' ';

        for(Character c : table1.keySet()){//ищем наиболее часто встречающийся символ в таблице 1
            if(bigElem < table1.get(c)){
                bigElem = Double.parseDouble(table1.get(c).toString());
                c1 = c;
            }
        }

        for(Character c : table2.keySet()){//ищем наиболее часто встречающийся символ в таблице 2
            if(bigElem < table2.get(c)){
                bigElem = table2.get(c);
                c2 = c;
            }
        }

        shift = getIndex(c1) - getIndex(c2);//получаем сдвиг
        System.out.println("shift for decript message = " + (33 - shift) + "\n");//узнаем, на какую величину надо додвигать букву, чтоб она встала на свое место(был сдвиг 1, значит до полного круга еще 32)
        return 33 - shift;
    }

    private static int getIndex(char x){
        int index;//индекс символа
        //приводим все в алфавитный порядок,
        if(x == 'ё' || x == 'Ё'){
            index = 6;
        } else {
            if(x >= 1072){
                index = x - 1072;
            } else {
                index = x - 1040;
            }
        }

        if(( x >= 1046 && x < 1072) || x >= 1078){//если буква х стояла после ё, то увеличиваем индекс на 1, так как мы вставили ё посередине
            index++;
        }
        //System.out.println(index);
        return index;
    }
}
