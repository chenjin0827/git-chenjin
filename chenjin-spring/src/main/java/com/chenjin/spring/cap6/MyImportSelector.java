package com.chenjin.spring.cap6;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector{
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //返回全类名的bean
        return new String[]{"com.chenjin.spring.cap6.Cat","com.chenjin.spring.cap6.Horse"};
    }
}
