import java.math.BigInteger;
import java.util.Random;

public class Main {

    public  static void main(String [] args){
        BigInteger q, N, g, k;

        Random random = new Random();

        //считаем общщие данные для входа
        q = BigInteger.probablePrime(1024, random);
        N = q.multiply(BigInteger.TWO).add(BigInteger.ONE);//модуль

        g = BigInteger.TWO;// В оригинале должен был быть первородный корень по модулю N
        k = SHA256.hash(N, g);

        Server s = new Server(N, g, k);//создание сервера
        Client c = new Client(N, g, k);//создание клиента

        //регестрируем клиентов
        reg("vova", "Sek", s, c);
        reg("Koreec", "Hohol", s, c);
        reg("Koreec2", "Hohol2", s, c);
        //клиенты входят
        login("vova", "Sek", s, c);
        login("Koreec", "Hohol", s, c);
        login("Koreec2", "Hohol2", s, c);


    }

    private static void reg(String name, String pass, Server s, Client c){//добавляем новых пользователей

        BigInteger salt = c.createSalt(32);//создаем 32 битную соль (10 значное число)
        c.sets(salt);//добавляем соль

        s.addNewClient(name, salt, c.createv(pass));//добавляем нового клиента
    }

    //Все методы которые create - создают необходимые данные для прохожедения процесса аунтификации
    //Все идет как в алгоритме srp-6a

    private static void login(String name, String pass, Server s, Client c) {//логинимся за пользователя
        BigInteger A, B;
        if (s.checkName(name)) {//проверяем, есть ли такой пользователь в базе данных
            c.setNameAndPass(name, pass);//устанавливаем клиенту данные для входа
            A = c.createA();
            if (A.equals(BigInteger.ZERO ) ) {//проверяем А
                System.out.println("Error : A == 0");
            }

            s.authorization(name, pass, A);//устанавливаем серверу данные для входа

            B = s.createB();

            if (B.equals(BigInteger.ZERO ) ) {//проверяем B
                System.out.println("Error : B == 0");
            }

            c.sets(s.gets());//сервер "отсылает соль клиенту"

            if (c.createu(B).equals(BigInteger.ZERO ) || s.createu().equals(BigInteger.ZERO ) ) {//проверяем u если u == 0 - отключение
                System.out.println("Error : u== 0");
            } else {
                //создаем S и K у клиента и сервера
                c.createx(pass);
                c.createS();
                c.createK();

                s.createS();
                s.createK();

                if (c.createM().equals(s.createM())) {//проверяем М, если совпадают сервер отсылает клиенту R
                    if (c.createR().equals(s.createR())) {//проверяем R, если совпадают клиент считает, что сервер настоящий
                        System.out.println("connection established");
                    } else {
                        System.out.println("Disconnection");
                    }
                } else {
                    System.out.println("invalid password");
                }
            }
        } else {
            System.out.println("Client " + name + " is not found");
        }
    }
}
