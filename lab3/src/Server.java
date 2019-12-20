import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Server {

    //base data
    private ArrayList<String> baseName = new ArrayList<>();
    private ArrayList<BigInteger> baseSalt = new ArrayList<>();
    private ArrayList<BigInteger> baseV = new ArrayList<>();
    private Random rand = new Random();

    private BigInteger N, g, k;//Basic data
    private BigInteger b, B, u, S, M, K, R;//Server data
    private BigInteger A;//Client data
    private int idCurrentClient = -1;

    public Server(BigInteger n, BigInteger g, BigInteger k) {
        N = n;
        this.g = g;
        this.k = k;
    }

    public void addNewClient(String name, BigInteger s, BigInteger v){
        baseName.add(name);
        baseSalt.add(s);
        baseV.add(v);
    }

    public void authorization(String name,  String p, BigInteger A){
        if(baseName.contains(name)){
            idCurrentClient = baseName.indexOf(name);
            this.A = A;
        }
    }

    private BigInteger createSecretNumber(){

        b = BigInteger.probablePrime(1024, rand);
        return b;
    }

    public BigInteger createB(){

        B = (k.multiply(baseV.get(idCurrentClient)).add(g.modPow(createSecretNumber(), N))).mod(N);
        return B;
    }

    public BigInteger createu(){

        u = SHA256.hash(A, B);
        return u;
    }

    public void createS(){

        S = A.multiply(baseV.get(idCurrentClient).modPow(u, N)).modPow(b, N);
    }

    public void createK(){

        K = SHA256.hash(S);
    }

    public BigInteger createM(){

        M = SHA256.hash(SHA256.hash(N).xor(SHA256.hash(g)), SHA256.hash(baseName.get(idCurrentClient)), baseSalt.get(idCurrentClient), A, B, K);
        //System.out.println("Ms = " + M);
        return M;
    }

    public BigInteger createR(){

        R = SHA256.hash(A, M, K );
        return R;
    }

    public boolean checkName(String name) {
        return baseName.contains(name);
    }

    public BigInteger gets(){
        return baseSalt.get(idCurrentClient);
    }
}
