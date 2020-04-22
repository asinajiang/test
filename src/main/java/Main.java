import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.google.common.util.concurrent.Futures;
import com.rumwei.enums.ApproximateType;
import com.rumwei.enums.DateType;
import com.rumwei.func.mail.MailUtils;
import com.rumwei.func.sftest.People;
import com.rumwei.func.thread.ThreadRunnable;
import com.rumwei.func.var.Sky;
import com.rumwei.util.BigDecimalUtilGW;
import com.rumwei.util.CalendarUtilGW;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.mail.MessagingException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Main {







    private final HashMap<String,String> map = new HashMap<>();
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {



        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> res = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("执行线程"+Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 34;
            }
        });
        System.out.println(res.get());
        Sky sky = new Sky();

        Object o = new Object();

        String gi = "sky  is    blue ha ha";
        String[] ss = gi.split(" ");
        System.out.println();
        Map<String,String> map = new HashMap<>();
        map.put(null,"gi");
        System.out.println(map.get(null));
        map.put(null,"ti");
        System.out.println(map.get(null));



        String message = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<p>设备离线清单:</p>\n" +
                "\t\n" +
                "    <table width=\"300\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse;\">\n" +
                "      <tr>\n" +
                "        <td>a</td>\n" +
                "        <td>a</td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td>b</td>\n" +
                "        <td>b</td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td>c</td>\n" +
                "        <td>c</td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  \t\n" +
                "</body>\n" +
                "</html>";
        try {
            //MailUtils.sendEmail("guwei@easylinkin.com",
//                    "guwei@easylinkin.com",
//                    "测试",
//                    message,
//                    "2011G&w13791");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ok");



        Map<Integer,Calendar> result = new HashMap<>();
        for(int i=1; i<=Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH); i++){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,i);
            calendar.set(Calendar.HOUR_OF_DAY,23);
            calendar.set(Calendar.MINUTE,59);
            calendar.set(Calendar.SECOND,59);
            calendar.set(Calendar.MILLISECOND,999);
            result.put(i,calendar);
        }
        System.out.println(result.size());


        BigDecimal bigDecimal = new BigDecimal("3.123343");
        bigDecimal = BigDecimalUtilGW.getApproximateValue(bigDecimal,2, ApproximateType.ROUND_CEILING_正数_则小数部分进1_负数_则舍弃小数部分);
        String bd = bigDecimal.toString();
        System.out.println(bigDecimal.toString());

        Date nextWeek = DateUtil.nextWeek();
        System.out.println(nextWeek);

        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:7000/payment/get");
        request.setHeader("User-Agent", "PostmanRuntime/7.22.0");
        request.setHeader("Accept","*/*");
        request.setHeader("Cache-Control","no-cache");
        request.setHeader("Postman-Token","4bafcb40-b36c-414a-b857-5d00e88bef14");
        request.setHeader("Host","localhost:8001");
        request.setHeader("Accept-Encoding","gzip, deflate, br");
        request.setHeader("Cookie","JSESSIONID=D72BFE5D75CA6BD197835A3FC7CF7C21");
        request.setHeader("Connection","keep-alive");
        try{
            HttpResponse response = client.execute(request);
            System.out.println(response);
        }catch(Exception e){
            e.printStackTrace();
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis()+24*3600*1000);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
        Date nc = new Date(calendar.getTimeInMillis() + (30l * 24 * 3600 * 1000));


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
