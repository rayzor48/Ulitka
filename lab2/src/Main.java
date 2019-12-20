import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String [] args){
        Random random = new Random();

        //выбрать p и g
        BigInteger p = BigInteger.probablePrime(16, random);//где p - случайное простое число
        BigInteger g = GetPRoot(p);//а g - первородный корень по модулю p
        System.out.print("p = " + p);
        System.out.print(" " + g);


        //create users
        User Alice = new User("Алиса", g, p);
        User Bob = new User("Боб", g, p);
        System.out.println("Ева перехватила p и g");

        Alice.setSecretNum(BigInteger.valueOf(random.nextInt( 100 - 0)));//устанавливает секретное число, которое мы выбрали с помощью рандома
        Bob.setSecretNum(BigInteger.valueOf(random.nextInt( 100 - 0)));

        Bob.setOpenKeyB(Alice.calculateOpenKey());//устанавливает открытый ключ собеседника который мы посчитали в calculateOpenKey
        System.out.println("Ева перехватила открытый ключ А от Алисы " + Alice.getOpenKeyA());
        Alice.setOpenKeyB(Bob.calculateOpenKey());//Боб вычисляет свй открытый ключ и передает его Алисе, чтоб  та его установила
        System.out.println("Ева перехватила открытый ключ А от Боба " + Bob.getOpenKeyA());

        Alice.calculateSharedKey();//считаем совместный секретный ключ
        Bob.calculateSharedKey();
    }

    //Говорить: взяли первую функцию которую нашли в инете

    public static BigInteger GetPRoot(BigInteger p) {//функция высчитывающая первообразный корнень
        for (BigInteger i = BigInteger.ZERO; i.compareTo(p) == -1; i = i.add(BigInteger.ONE))//for (i = 1; i < p; i++)
            if (IsPRoot(p, i))//если IsRoot = true(правда)
                return i;//возвращаем i
        return BigInteger.ZERO;//если первородный корень не найден - возвращаем 0
    }

    public static boolean IsPRoot(BigInteger p, BigInteger a) {//p - модуль, а - первородный корень(проверяется на первородный корень)
        if (a.equals(BigInteger.ZERO) ||a.equals(BigInteger.ONE))//если а = 0 или а = 1, то возвращаем ложь
            return false;
        BigInteger last = BigInteger.ONE;//last = 1

        Set<BigInteger> set = new HashSet<>();//создаем HashSet
        for (BigInteger i = BigInteger.ZERO; i.compareTo(p.subtract(BigInteger.ONE)) == -1; i = i.add(BigInteger.ONE)) {//for (i = 1; i < p - 1; i++)
            last = (last.multiply(a)).mod(p);//last = last*a%p. (умножаем last на a и делим по модулю p)
            if (set.contains(last)) // Если в set есть такое значение - возвращаем ложь
                return false;
            set.add(last);//иначе добавляем элемент в коллекцию(set)
        }

        return true;//если до этого не вернули ложь, то возвращаем правду
    }
}
