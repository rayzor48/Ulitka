import java.math.BigInteger;
import java.util.Random;

public class Protocol {
    private BigInteger p, q;//secret key
    private BigInteger n, e;//open key
    private BigInteger f, d;

    private Random random = new Random();

    public void createKey(){//функция вычисляет приватные и открытый ключи
        //выбираем 2 простых числа
        p = BigInteger.probablePrime(1024, random);//1024 - кол-во бит числа p
        q = BigInteger.probablePrime(1024, random);//1024 - кол-во бит числа q

        n = p.multiply(q);//перемножаем их получаем 1 часть ключа

        System.out.println("n = " + n);
        f = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));//вычисляем значение функции Эйлера

        e = BigInteger.TWO;//выбираем е взаимопростое с f
        while((e.compareTo(f) == -1) & (e.gcd(f).compareTo(BigInteger.ONE) != 0)){
            e = e.add(BigInteger.ONE);
        }
        System.out.println("e = " + e);

        d = e.modInverse(f);//Вычисляется число d, мультипликативно обратное к числу e по модулю (n), то есть число
        System.out.println("d = " + d);
        //Пара (e,n) публикуется в качестве открытого ключа RSA
        //Пара (d,n) играет роль закрытого ключа RSA и держится в секрете.
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getD() {
        return d;
    }
}
