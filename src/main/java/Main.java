import com.fasterxml.jackson.databind.util.BeanUtil;
import com.rumwei.enums.DateType;
import com.rumwei.func.sftest.People;
import com.rumwei.util.CalendarUtilGW;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private final HashMap<String,String> map = new HashMap<>();
    public static void main(String[] args) throws IOException {

        String ss = "hello1234";
        System.out.println(ss.substring(ss.length()-4));


        ExecutorService consumer = Executors.newFixedThreadPool(5);
        ExecutorService producer = Executors.newFixedThreadPool(5);
        final Lock lock = new ReentrantLock();

        final List<Integer> list = new ArrayList<>();
        final List<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);
        data.add(5);
        while (data.size() > 0 && data.get(data.size()-1) > 3){
            if (data.get(data.size()-1) > 3){
                data.remove(data.size()-1);
            }
        }
        System.out.println();


        consumer.execute(() -> {
            while(true){
                try{
                    if (list.size() > 0){
                        lock.lock();
                        Integer ele = list.get(list.size()-1);
                        list.remove(list.size()-1);
                        lock.unlock();
                        System.out.println(ele);
                        try{
                            Thread.sleep(3000);
                        }catch(Exception e){}
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                }

            }
        });
        producer.execute(()->{
            while (true){
                try{
                    if(data.size() > 0){
                        System.out.println("--------------1----------------");
                        lock.lock();
                        list.add(data.get(data.size()-1));
                        data.remove(data.size()-1);
                        lock.unlock();
                        System.out.println("--------------2----------------");
                    }else {
                        break;
                    }
                    try{
                        Thread.sleep(800);
                    }catch(Exception e){}
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                }

            }
        });

    }



    private static String now(Map<String,String> input) {
        List<People> peoples = new LinkedList<>();
        peoples.add(new People(12,"dd"));
        peoples.add(new People(23,"ji"));
        peoples.add(new People(16,"uy"));
        Collections.sort(peoples,(p,t)->(p.getAge()-t.getAge()));
        return "nu";
    }


    public static long numberOfDaysInYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
            return 366;
        } else {
            return 365;
        }
    }

    public static String getSha1(String str) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;

            for(int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 15];
                buf[k++] = hexDigits[byte0 & 15];
            }

            return new String(buf);
        } catch (Exception var9) {
            return null;
        }
    }


    private static void swap(int[] arr, int left, int right){
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    private static int halfSort(int[] arr, int left, int right){
        int indexTemp = left;
        while(left < right){
            while (arr[left] < arr[indexTemp] && left < right){
                left++;
            }
            while (arr[indexTemp] < arr[right] && left < right){
                right--;
            }
            swap(arr,left,right);
        }
        swap(arr,indexTemp,right);
        return right;
    }

    private static int[] quickSort(int[] arr, int left, int right){
        if (left < right){
            int middle = halfSort(arr,left,right);
            quickSort(arr,left,middle-1);
            quickSort(arr,middle+1,right);
        }
        return arr;
    }

}
