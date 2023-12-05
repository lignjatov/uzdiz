package helper;

public class ArgumentChecker {

    public boolean provjeriArgument(String argument)
    {
        String[] odsjek = argument.split(" ");
        String[] komande = {"--vp","--pv","--pp","--mt", "--vi", "--vs","--ms", "--pr", "--kr"};
        boolean postoji = false;
        for(String p : komande){
            if(p.compareTo(odsjek[0])==0){
                postoji = true;
                break;
            }
        }
        return postoji;
    }
}
