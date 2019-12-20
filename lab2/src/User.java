import java.math.BigInteger;

public class User {
    private String name;
    private BigInteger secretNum;
    private BigInteger sharedKey;
    private BigInteger openKeyA, openKeyB;

    private BigInteger g, p;

    public User(String name, BigInteger g, BigInteger p) {
        this.name = name;
        this.g = g;
        this.p = p;
        System.out.println(name + ", установил(а) g = " + g + " p = " + p);
    }

    public void setSecretNum(BigInteger secretNum) {//устанавливает секретное число
        this.secretNum = secretNum;

        System.out.println(name + ", выбрал(а)  секретное число : " + secretNum);

    }

    public BigInteger calculateOpenKey(){//вычислить открутый ключ А(мой)
        openKeyA = g.modPow(secretNum, p);
        System.out.println(name + ", посчитал(а)  открытый ключ : " + openKeyA + " и передал(а) его");

        return openKeyA;
    }

    public BigInteger calculateSharedKey(){//вычислить совместный секретный кдюч
        sharedKey = openKeyB.modPow(secretNum, p);
        System.out.println(name + ", посчитал(а)  секретный ключ : " + sharedKey);

        return sharedKey;
    }

    public void setOpenKeyB(BigInteger openKeyB) {//установить открытый ключ собеседника(В)
        this.openKeyB = openKeyB;
        System.out.println(name + ", принял(а)  открытый ключ " + openKeyB);
    }

    public BigInteger getOpenKeyA() {
        return openKeyA;
    }
}
