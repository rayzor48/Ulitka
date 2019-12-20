import java.math.BigInteger;

public class Main {
    public static void main(String [] args){
        User Masha = new User("Маша");//создаем пользователей
        User Dima = new User("Дима", Masha);//создаем пользователя-собеседника

        Dima.createKeys();//создаем ключи(вычисляем)
        Dima.setOpenKey();//отправляем Маше открытый ключ
        Dima.sendMail("Маша это твоя последняя лаба, пошли котать?");//Отправляем Маше сообщение

        Masha.setMyInterlocutor(Dima);//Присваиваем Маше собеседника

        System.out.println("\n=============================================================\n");

        Masha.sendMail("Ты тютю? Одиннадцатый час ночи, а ему катать... СПАТЬ!!!" );//отправляем маше ответ
}
}
