package com.weibangong.lucene.test;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by chenbo on 16/8/19.
 */
public class DocumentUtils {

    public static Document bean2Document(Object bean){
        if(bean == null){
            return null;
        }

        Class cls = bean.getClass();
        Field[] fields = cls.getDeclaredFields();

        Document document = new Document();
        for(Field field : fields){
            field.setAccessible(true);
            String name = field.getName();
            try {
                document.add(new TextField(name, field.get(bean).toString(), org.apache.lucene.document.Field.Store.YES));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return document;
    }

    public static Object document2Bean(Document document, Class cls){
        if(document == null){
            return null;
        }

        Object bean = null;
        try{
            bean = cls.newInstance();
            Field[] fields = cls.getDeclaredFields();
            for(Field field : fields){
                Method method = cls.getDeclaredMethod("set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1),
                        field.getType());
                if(field.getType() == Long.class){
                    method.invoke(bean, Long.parseLong(document.get(field.getName())));
                }else {
                    method.invoke(bean, document.get(field.getName()).toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return bean;
    }
}
