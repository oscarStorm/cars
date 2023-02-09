package dat3.experiments;

public class SimpleSanitizer {
    public static String simpleSanitize(String s){

        return s.replaceAll("<[^>]*>", "");
    }
}
