import com.sun.org.apache.xerces.internal.xs.StringList;

import java.io.*;
import java.text.CollationKey;
import java.text.Collator;
import java.util.*;

public class FileReader {

    // Tránh trường hợp vào các dấu sau
    public static final char SPACE = ' ';
    public static final char TAB = '\t';
    public static final char BREAK_LINE = '\n';
    public static final char DOT = '.';
    public static final char COMMA = ',';
    public static final char QUESTION_MARK = '?';
    public static final char COLON = ':';
    public static final char MARK = '"';
    public static final char DASH = '-';
    public static final char AMPERSAND = '&';
    public static final char BRACKET ='(';


//    static String[] words; // mang luu cac tu
    public static FileWriter writer; // ghi ra file
    public static Map<String, Integer> wordMap;

    public static StringBuilder result = new StringBuilder() ;
    public  static List<Map.Entry<String, Integer>> list ; // sử dụng list này trong trường hợp tìm kiếm
    public  static String line;
    public  static BufferedReader reader ;
    public  static int countWord;
    public  static  String[] wordList;
    public  static String input;



    public static void main(String[] args) throws IOException {



        File file = new File("C:\\Users\\ABC\\IdeaProjects\\BAI TAP 3\\BaiTap.txt");
        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader input = new InputStreamReader(fileStream);
        reader = new BufferedReader(input);


        // tạo biến đếm
        countWord = 0;

        int paragraphCount = 1;


        // Đọc các dòng
        // file cho đến khi  null thì trả về
        while((line = reader.readLine()) != null)
        {
            if(line.equals(""))
            {
                paragraphCount++;  // đếm toạn
            } else {


                // \\s+ khoảng trắng
                wordList = line.split("\\s+");

                countWord += wordList.length;


                // [!?.:]+ đoạn
                String[] sentenceList = line.split("[!?.:]+");

                result.append(line);
            }
        }



        String urlWrite = "C:\\Users\\ABC\\IdeaProjects\\BAI TAP 3\\KetQua.txt";

        writer = new FileWriter(urlWrite); // ghi ra file mới




        /** ---------------- PHẦN ĐẾM TỔNG CÓ BAO NHIÊU TỪ  ---------------------- **/




        countTheTotalNumberOfWords();


        /** ---------------- PHẦN LIỆT KÊ TẦN SUẤT XUẤT HIỆN BAO NHIÊU TỪ  ---------------------- **/


        listsTheFrequencyOfWordsAppearing();

        /** ---------------- SẮP XẾP CÁC TỪ CÓ TẦN SUẤT NHIỀU NHẤT LÊN CÒN LẠI LÀ Ở ĐẰNG SAU ---------------------- **/


        arrangeWordsBasedOnFrequency();   // sắp xếp ra  từ The lớn nhất khoảng 43000 từ

        /** ---------------- PHẦN TÌM KIẾM ---------------------- **/


        searchBeginningWithTheFirstWords();


        writer.close();   // close để kết thúc quá trình ghi ra file
    }

    private static void countTheTotalNumberOfWords() throws IOException {



        System.out.println("Total word count = " + countWord);

    }

    private static void searchBeginningWithTheFirstWords() throws IOException {
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("\n " + "Tìm kiếm các từ xuất hiện có chữ cái đó: ");
         input = scanner1.nextLine();


        writer.write("\n " + "+ Tìm kiếm các từ với chữ cái đầu tiên dựa vào xuất hiện của các từ: " + input + "\n");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKey().startsWith(input)) {  // tìm kiếm với Key so sánh với chữ cái ban đầu nhập vào từ input
                System.out.println(list.get(i).getKey());
                writer.write("\t" + list.get(i).getKey() + "\n");  // ghi tiếp vào file
            }
        }

    }

    private static void arrangeWordsBasedOnFrequency() throws IOException {
        list = new ArrayList<Map.Entry<String, Integer>>(wordMap.entrySet());  //phương thức entrySet( ) được khai báo bởi Map Interface trả về một Set chứa các Map Entry
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {    // dùng sort để sắp xếp, truyền vào List cần sắp xếp
            @Override
            public int compare(Map.Entry<String, Integer> stringIntegerEntry, Map.Entry<String, Integer> t1) {
                return (t1.getValue().compareTo(stringIntegerEntry.getValue()));  // so sánh các từ có tần suất xuất hiện nhiều hơn sẽ được xếp lên trên
                                                                                 // còn các từ có tần suất xuất hiện nhỏ hơn sẽ nằm ở phía sau
            }
        });
        System.out.println("Dãy sau khi sắp xếp là:");
        writer.write(" + Dãy sau khi sắp xếp là: " + "\n" +list);  // ghi List  vào file mới


        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + " - " + entry.getValue());   // lấy ra kết quả sắp xếp ra
        }


    }

    public static void listsTheFrequencyOfWordsAppearing() throws IOException {

        writer.write(" + Liệt kê số lần xuất hiện của các từ: " + "\n" + "\n");

        System.out.println("+ Liệt kê số lần xuất hiện của các từ: ");
         wordMap = countWords(result);   // truyền result đã có vào countWords method ()
        for (String key : wordMap.keySet()) {
            System.out.print(key + " " + wordMap.get(key) + "\n");
            writer.write("\t" + key + " -------- " + wordMap.get(key) + "\n");
        }

    }


    public static Map<String, Integer> countWords(StringBuilder input) {

        // khởi tạo wordMap
        Map<String, Integer> wordMap = new TreeMap<String, Integer>();
        if (input == null) {
            return wordMap;
        }
        int size = input.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {

            //  nếu k phải mấy cái dấu cách hay tab hay breakline,... thì build 1 từ
            if (input.charAt(i) != SPACE && input.charAt(i) != TAB
                    && input.charAt(i) != BREAK_LINE &&
                    input.charAt(i) != DOT && input.charAt(i) != COMMA &&
            input.charAt(i) != QUESTION_MARK && input.charAt(i) != COLON
                    && input.charAt(i) != MARK && input.charAt(i) != DASH && input.charAt(i) != AMPERSAND
                    && input.charAt(i) != BRACKET

            )  {
                // build một từ
                sb.append(input.charAt(i));
            } else {
                // thêm từ vào wordMap
                addWord(wordMap, sb);
                sb = new StringBuilder();
            }
        }
        // thêm từ cuối cùng tìm được vào wordMap
        addWord(wordMap, sb);
        return wordMap;
    }

    public static void addWord(Map<String, Integer> wordMap, StringBuilder sb) {
        String word = sb.toString();
        if (word.length() == 0) {
            return;
        }
        if (wordMap.containsKey(word)) {
            int count = wordMap.get(word) + 1;
            wordMap.put(word, count);       // them vào wordMap dưới dạng key value
        } else {
            wordMap.put(word, 1);
        }
    }




}
