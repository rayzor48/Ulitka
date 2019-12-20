import java.math.BigInteger;
import java.util.Random;

public class Client {

    private Random rand = new Random();

    private BigInteger N, g, k;//Basic data
    private BigInteger s, a, A, u, x, S, M, K, R;//Client data
    private BigInteger B;//Server data
    private String name, pass;

    public Client(BigInteger n, BigInteger g, BigInteger k) {
        N = n;
        this.g = g;
        this.k = k;
    }

    private BigInteger createSecretNumber() {

        a = BigInteger.probablePrime(1024, rand);//генерация простого 1024 битного числа
        return a;
    }

    public BigInteger createSalt(int size) {
        return new BigInteger(size, rand);
    }

    public BigInteger createA() {
        A = g.modPow(createSecretNumber(), N);
        return A;
    }

    public BigInteger createx(String p) {

        x = SHA256.hash(s, p);
        return x;
    }

    public BigInteger createv(String p) {

        return g.modPow(createx(p), N);
    }

    public BigInteger createu(BigInteger B) {
        this.B = B;
        u = SHA256.hash(A, B);
        return u;
    }

    public void createS() {
        S = (B.subtract(k.multiply(g.modPow(createx(pass), N))).modPow(a.add(u.multiply(createx(pass))), N));
    }

    public void createK() {

        K = SHA256.hash(S);
    }

    public BigInteger createM() {

        M = SHA256.hash(SHA256.hash(N).xor(SHA256.hash(g)), SHA256.hash(name), s, A, B, K);

        //System.out.println("Mc = " + M);
        return M;
    }

    public BigInteger createR() {

        R = SHA256.hash(A, M, K);
        return R;
    }

    public void setNameAndPass(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public void sets(BigInteger s) {
        this.s = s;
    }
}
