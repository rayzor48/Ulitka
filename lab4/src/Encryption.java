import java.math.BigInteger;

public class Encryption {//Тут шифруется и расифровывается сообщение(с помощью матана)

    public static BigInteger[] encrypt (BigInteger n, BigInteger e, String message){

        char[] messageMass = message.toCharArray();//берем массив букв из сообщения, которое хотим зашифровать
        BigInteger[] encryptedMessage = new BigInteger[messageMass.length];//создаем массив бигИнтегеров в котором будем хранить зашифрованные символы
        int buffer;//буффер для хранения кода текущего символа

        for (int i = 0; i < messageMass.length; i ++) {//messageMass.length - длина строки
            buffer = (int)(messageMass[i]);//присваивааем текущий код символа
            encryptedMessage[i] = BigInteger.valueOf(buffer).modPow(e, n);//шифруем символ и кладем его в массив(возводим в степень по модулю n)
        }
        return encryptedMessage;//возвращаем массив зашифрованных символов( зашифрованное сообщение )
    }

    public static String decrypt(BigInteger n, BigInteger d, BigInteger[] encryptedMessage){
        String decryptedMessage = "";//расшифрованное сообщение
        int buffer;//буффер для хранения кода текущего символа

        for (BigInteger c : encryptedMessage){//пробегаем по массиву БигИнтегеров от начала до конца
            buffer = (c.modPow(d, n)).intValue();//расшифровываем его (возводим в степень по модулю n)
            decryptedMessage += (char)buffer;//прибавляем пасшифрованную букву к строке
        }

        return decryptedMessage;//расшифровываем строку
    }
}
