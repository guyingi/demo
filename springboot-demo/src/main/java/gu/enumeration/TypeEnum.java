package gu.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum TypeEnum {
    HEAD(1),OCR(2);
    private int val;
    private static final Map<Integer,TypeEnum> keyMap=new HashMap<Integer,TypeEnum>();
    public int getVal(){
        return val;
    }
    TypeEnum(int val){
        this.val=val;
    }
    public static TypeEnum fromVal(int pvnVal){
        return keyMap.get(pvnVal);
    }
}
