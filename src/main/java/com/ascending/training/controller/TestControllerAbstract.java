//package com.ascending.training.controller;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//@RestController
//@RequestMapping(value = {"/test"})
//public class TestControllerAbstract {
//    private Logger logger=LoggerFactory.getLogger(getClass());
//    /**
//     * GET /test
//     * @return
//     */
//    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Map<String,Object> getSampleJson(){
//        Map<String, Object> m = new HashMap<>();
//        m.put("id",1);
//        m.put("name","HR");
//        m.put("capacity",500);
//        //logger
//        return m;
//    }
//    //POST /test/example
//    @RequestMapping(value = {"/example"},method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Map<String,Object> createObject(){
//        Map<String, Object> m = new HashMap<>();
//        Random r = new Random();
//        m.put("id",r.nextInt());
//        m.put("name","HR");
//        m.put("capacity",500);
//        logger.debug("create an object with id:"+m.get("id"));
//        return m;
//    }
//}
