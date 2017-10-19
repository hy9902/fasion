package com.hydt.app;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.geccocrawler.gecco.GeccoEngine;
import com.google.common.html.HtmlEscapers;
import com.hydt.app.common.User;
import com.hydt.app.service.CService;
import com.hydt.app.service.MyService;
import com.hydt.app.utils.ExcelUtils;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.web.util.TextEscapeUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import sun.misc.BASE64Encoder;
import sun.security.rsa.RSAPrivateCrtKeyImpl;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bean_huang on 2017/7/11.
 */
public class JavaTest {
    private static Logger logger = LoggerFactory.getLogger(JavaTest.class);

    @Test
    public void testCalssInterfaces(){
        Class cls = MyService.class;
        Class[] clss = cls.getInterfaces();
        for(Class c : clss){
            logger.debug(c.getName());
        }
    }

    @Test
    public void testInterfaces(){
        Class cls = CService.class;
        Class[] clss = cls.getInterfaces();
        for(Class c : clss){
            logger.debug(c.getName());
        }
    }

    @Test
    public void testDate(){
        Date d = new Date(-1L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.debug(sdf.format(d));
    }

    @Test
    public void testJava8Date(){
        Optional<Integer> optional = Optional.of(Integer.valueOf("a"));
        if(optional.isPresent()){
            logger.error("is null");
        } else {
            logger.error("is not null");
            Integer i = optional.orElse(1);
        }

    }

    @Test
    public void testMax(){
        Integer i1 = Integer.MAX_VALUE;
        Integer i2 = i1+1;
        System.out.println(i2>i1);
    }

    @Test
    public void testP(){
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        ac.getBean("");
    }

    @Test
    public void testAWT(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // NullPointerException does not have to be thrown
        ((sun.awt.Win32GraphicsEnvironment) ge).displayChanged();
    }

    @Test
    public void testMove(){
        int i = 1;
        System.out.println(i<<2);
    }

    @Test
    public void testEscapeHtml(){
        String s = "<script>alert('黄义')</script>";
        System.out.println(TextEscapeUtils.escapeEntities(s));
    }

    @Test
    public void testEscapeHtml2(){
        String s = "<script>alert('黄义')</script>";
        System.out.println(StringEscapeUtils.escapeHtml4(s));
    }

    @Test
    public void testEscapeHtml3(){
        String s = "<script type=\"text/javascript\">alert('黄义')</script>";
        System.out.println(HtmlEscapers.htmlEscaper().escape(s));
    }

    @Test
    public void testJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        objectMapper.enable(SerializationFeature.CLOSE_CLOSEABLE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        Map<String,Object> map = new HashMap<>();
        map.put("a1", null);
        map.put("a2", "123");
        map.put("a3", "");
        System.out.println(objectMapper.writeValueAsString(map));

        User user = new User();
        user.setAge(0);
        user.setId(null);
        user.setName("DISCOUNT_AMOUNT");
        System.out.println(objectMapper.writeValueAsString(user));
    }

    @Test
    public void testAsyncSubject(){
        AsyncSubject subject = AsyncSubject.create();
        subject.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("ch1: " + o);
            }
        });

        subject.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("ch2: " + o);
            }
        });

        subject.onNext("a");
        subject.onNext("c1");
        subject.onNext("c2");
        subject.onNext("c3");
        subject.onNext("c4");
        subject.onComplete();
        subject.onNext("c5");
        subject.onNext("c6");
        subject.onNext("c7");
        subject.onNext("c8");
        subject.onNext("c9");
        subject.onNext("c0");

        subject.onComplete();
        System.out.println(subject.lastElement().blockingGet());
        //subject.onError(new RuntimeException("test"));
    }

    @Test
    public void testAsyn1(){
        Flowable.empty().startWithArray(1,2,3).subscribe(i -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(i);
        });
    }

    @Test
    public void testAsyn2() throws InterruptedException {
        Flowable.range(1, 1000).parallel(1).runOn(Schedulers.computation()).sequential().subscribe(i -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(i);
        });

        Thread.sleep(50000);
    }

    /**
     *  正则表达式检查8位长度的字符串包括数字+字母，不区分大小写
     */
    @Test
    public void testCode(){
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8}$");
        Matcher m = p.matcher("12345678");
        System.out.println(m.matches());
    }

    @Test
    public void testExcelExport(){
        String tmp = "BUSINESS";
        String title = "舱单信息";
        String[] rowsName = new String[]{"序号","货物运输批次号","提运单号","状态","录入人","录入时间"};
        List<Object[]> dataList = new ArrayList<>();
        Object[] objs = null;
        for (int i = 0; i < 10; i++) {
            objs = new Object[rowsName.length];
            objs[0] = i;
            objs[1] = UUID.randomUUID().version();
            objs[2] = UUID.randomUUID().toString();
            objs[3] = "海关接受报告";
            objs[4] = "xyz";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(new Date());
            objs[5] = date;
            dataList.add(objs);
        }
        ExcelUtils ex = new ExcelUtils(title, rowsName, dataList);
        ex.export();
    }

    @Test
    public void testCrawler(){
        GeccoEngine.create()
                .classpath("com.hydt.app.crawler")
                .start("https://github.com/xtuhcy/gecco")
                .thread(1)
                .interval(2000)
                .loop(true)
                .mobile(false)
                .start();
    }

    @Test
    public void testMD5() throws NoSuchAlgorithmException {
        String source = "";
        Digest digest = new MD5Digest();
        digest.update(source.getBytes(), 0, source.length());
        byte[] des = new byte[digest.getDigestSize()];
        digest.doFinal(des, 0);
        for (int i = 0; i < des.length; i++) {
            byte b = des[i];
            System.out.println(Integer.valueOf(b)+ "--------------------------" + Integer.toBinaryString(b & 0xfff));
            System.out.println(b>>>4 & 0xf);
            System.out.println(b & 0xf);
            System.out.println(b & 0xfff);
            System.out.println("--------------------------");
            System.out.println();
        }
    }

    @Test
    public void testHex2() throws NoSuchAlgorithmException {
        System.out.println(Integer.toBinaryString(-1&0xf) );
        System.out.println(Integer.toBinaryString(15&0xf) );

    }

    @Test
    public void testKeyStone() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        Resource resource = new ClassPathResource("keystore");
        String pw = "hydt2017";
        if(resource.getFile().exists()){
            System.out.println("keystore is good");
            KeyStore keyStore =  KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(resource.getInputStream(),pw.toCharArray());
            Key key = keyStore.getKey("jetty",pw.toCharArray());
            System.out.println(key.getClass().getName());
            RSAPrivateCrtKeyImpl privateCrtKey = (RSAPrivateCrtKeyImpl)key;
            BASE64Encoder encoder = new BASE64Encoder();
            System.out.println("-----BEGIN RSA PRIVATE KEY-----");
            System.out.println(encoder.encode(privateCrtKey.getEncoded()) );
            System.out.println("-----END RSA PRIVATE KEY-----");

            Certificate certificate = keyStore.getCertificate("jetty");
            if( certificate == null){
                System.out.println("Failed to find UTF cert.");
            } else {
                System.out.println("certificate." + certificate.getPublicKey());
            }
        } else {
            System.out.println("file is not exists");
        }
    }

    @Test
    public void testKeyStoneCsr() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        Resource resource = new ClassPathResource("jetty.csr");
        String pw = "hydt2017";
        if(resource.getFile().exists()){
            System.out.println("keystore is good");
            KeyStore keyStore =  KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(resource.getInputStream(),pw.toCharArray());
            Key key = keyStore.getKey("jetty",pw.toCharArray());
            System.out.println(key.getClass().getName());
            RSAPrivateCrtKeyImpl privateCrtKey = (RSAPrivateCrtKeyImpl)key;
            System.out.println(privateCrtKey.toString());
            Certificate certificate = keyStore.getCertificate("jetty");
            if( certificate == null){
                System.out.println("Failed to find UTF cert.");
            } else {
                System.out.println("certificate." + certificate.getPublicKey());
            }
        } else {
            System.out.println("file is not exists");
        }
    }

    @Test
    public void testKeyStoneWithCA() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        Resource resource = new ClassPathResource("cmclinkcert.jks");
        String pw = "GjSc3q75ChZy";
        if(resource.getFile().exists()){
            System.out.println("keystore is good");
            KeyStore keyStore =  KeyStore.getInstance("jks");
            keyStore.load(resource.getInputStream(),pw.toCharArray());
            Enumeration<String> enumeration =  keyStore.aliases();
            while (enumeration.hasMoreElements()){
                System.out.println(enumeration.nextElement());
            }
            Key key =  keyStore.getKey("*.cmclink.com (geotrust ssl ca - g3)",pw.toCharArray());
            System.out.println(key.getClass().getName());
        } else {
            System.out.println("file is not exists");
        }

        String test = "E10ADC3949BA59ABBE56E057F20F883E";
        String abcd = "E10ADC3949BA59ABBE56E057F20F883E";
    }

    @Test
    public void testStreamCopy() throws Exception{
        InputStream is = new FileInputStream(new File("d:\\test.txt"));
        String content = StreamUtils.copyToString(is, Charset.forName("utf-8"));
        System.out.println(content);
        System.out.println(is.available());
    }

    @Test
    public void testLoop1(){
        int i = 2017;
        int count = 0;
        while (i > 1){
            count++;
            i/=2;
            System.out.println(count);
        }
    }

    @Test
    public void testFileEncode(){
        String utf8String = "我是中国人";
        String newString =  new String(utf8String.getBytes(Charset.forName("ISO-8859-1")));
        System.out.println(newString);

    }
}
