package goldoni.calculadora.calculadora_de_sobras;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractData {
    public List<Element> extractData(String text) {
        List<Element> elementList = new ArrayList<>();

        // Regular expression to find patterns like “1#2Ø10 - 255”
        Pattern pattern = Pattern.compile("(\\d+)#(\\d+)Ø(\\d+) - (\\d+)");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            int position = Integer.parseInt(matcher.group(1)); // Ex: "1"
            int quantity = Integer.parseInt(matcher.group(2)); // Ex: "2"
            int diameter = Integer.parseInt(matcher.group(3)); // Ex: "10"
            int totalLength = Integer.parseInt(matcher.group(4)); // Ex: "255"

            Element element = new Element(position, quantity, diameter, totalLength);
            elementList.add(element);
        }
        return elementList;
    }
}
