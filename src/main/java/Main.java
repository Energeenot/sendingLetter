public class Main {

    public static void main(String[] args) {
        Sender sslSender = new Sender("trapaligatorivan@mail.ru", args[0]);
        sslSender.send("09-152, Абрамов Иван","", "trapaligatorivan@mail.ru", "ommatrenina@kpfu.ru");
    }
}
