import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {//получение хэшкода(Взято у рыженкова, на крайняк можно скачать другую из гита, но это было проще)
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();//создаем StringBuffer(сборщик)
        for (byte hash_byte : hash) {//проходим по массиву бит (хэшу)
            String hex = Integer.toHexString(0xff & hash_byte);//строка hex = хекс представлению int без битов равных 0
            if (hex.length() == 1) hexString.append('0');//если длина строки равна 1, то добавляем в сборщик "0"
            hexString.append(hex);//иначе добавляем hex
        }
        return hexString.toString();//возвращаем строку с собранным хэшем
    }

    public static BigInteger hash(Object... input) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");//создаем экземпляр класса MessageDigest и в каестве параметра передаем ему строку с названем алгоритма дайджеста конкретного сообщения.
            for (Object i : input) {//проходим по объектам которые были переданны в функцию
                //System.out.println(i.toString());
                if (i instanceof String) {//если на вход пришел стринг(строка)
                    //так как у нас может быть не один блок данных, который мы хотим хэшировать, то используем метод update
                    sha256.update(((String) i).getBytes());
                } else if (i instanceof BigInteger) {//если на вход пришел бигИнтегер
                    sha256.update(((BigInteger) i).toString(10).getBytes());
                } else if (i instanceof byte[]) {//если на вход пришел массив байт
                    sha256.update((byte[]) i);
                } else throw new IllegalArgumentException();
            }
            return new BigInteger(bytesToHex(sha256.digest()), 16);//Завершает вычисление хеша, выполняя заключительные операции, такие как заполнение.
        } catch (NoSuchAlgorithmException e) {//ловим исключение если в параметре передавался незнакмый алгоритм?
            e.printStackTrace();
            return BigInteger.ZERO;//возвращаем 0
        }
    }
}