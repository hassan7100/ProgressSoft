package org.progresssoft.Beans;

import org.progresssoft.Model.Deal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@Configuration
public class IndexMapBean {
    @Bean
    public Map<Long, Deal> indexMap() {
        return Collections.synchronizedMap(new HashMap<>());
    }
}
