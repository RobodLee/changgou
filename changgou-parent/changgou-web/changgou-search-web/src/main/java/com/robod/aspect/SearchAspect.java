package com.robod.aspect;

import com.robod.entity.SearchEntity;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Robod
 * @date 2020/7/29 9:17
 */
@Aspect
@Component
public class SearchAspect {

    @Pointcut("execution(public * com.robod.controller.SkuSearchWebController.searchByKeywords(..)) " +
            "&& args(searchEntity,model,request))")
    public void searchAspect(SearchEntity searchEntity, Model model, HttpServletRequest request){
    }

    @Before(value = "searchAspect(searchEntity,model,request)",argNames = "searchEntity,model,request")
    public void doBeforeSearch(SearchEntity searchEntity,Model model, HttpServletRequest request) throws Throwable {
        if (StringUtils.isEmpty(searchEntity.getKeywords())) {
            searchEntity.setKeywords("小米");
        }
        Map<String,String> specs = searchEntity.getSearchSpec();
        if (specs == null) {
            searchEntity.setSearchSpec(new HashMap<>(8));
        } else {
            for (String key:specs.keySet()){
                String value = specs.get(key).replace(" ","+");
                specs.put(key,value);
            }
        }
    }

}
