import java.math.BigInteger;

public class User {
    private String name;
    private BigInteger myKeyN,myKey2;
    private User myInterlocutor;
    private Protocol protocol;

    public User(String name) {//конструкто
        this.name = name;
        protocol = new Protocol();
    }

    public User(String name, User myInterlocutor) {//еще 1 конструктор
        this.name = name;
        this.myInterlocutor = myInterlocutor;//добавлена строка, для установки собеседника
        protocol = new Protocol();
    }

    public void createKeys (){//вычисляем ключи и назначаем ключ пользователю
        protocol.createKey();

        System.out.println("\n=============================================================\n");

        System.out.println("Ключ посчитан.");
        System.out.println("Секретный ключ получен.");

        myKeyN = protocol.getN();
        myKey2 = protocol.getD();
    }

    public void setMyInterlocutor(User myInterlocutor) {//устанавливаем собеседника
        this.myInterlocutor = myInterlocutor;
    }

    public void setOpenKey(){//отправить открытый ключ собеседнику

        System.out.println("Открытый ключ отправлен.");
        myInterlocutor.myKeyN = protocol.getN();
        myInterlocutor.myKey2 = protocol.getE();
        System.out.println("Открытый ключ получен.");
    }

    public void sendMail(String mail){//передаем сообщение собеседнику
        System.out.println(name + " : шифрую сообщение.");
        System.out.println(name + " : сообщение отправлено.");

        myInterlocutor.getMail(Encryption.encrypt(myKeyN, myKey2, mail));//вызываем у собеседника функцию для чтения сообщения
    }

    public void getMail(BigInteger [] mail){
        System.out.println(name + " : сообщение получено.");
        System.out.println(name + " : сообщение расшифровано.\n Сообщение : " + Encryption.decrypt(myKeyN, myKey2, mail));
    }
}
